package algorithm_test.sift;

import com.alibaba.fastjson.JSON;
import com.alibaba.simpleimage.analyze.sift.SIFT;
import com.alibaba.simpleimage.analyze.sift.render.RenderImage;
import com.alibaba.simpleimage.analyze.sift.scale.KDFeaturePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smile.neighbor.KDTree;
import smile.neighbor.Neighbor;
import util.ImageDownload;
import util.ImageProcess;
import util.VectorProcess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzwangjian1 on 2017/8/14.
 */
public class SoftQuantization {
    private static Logger logger = LoggerFactory.getLogger(SoftQuantization.class);

    private static KDTree<Integer> kdTree = null;
    //    private static String rootPath = "E://temp/docduplicate/image";
    private static String rootPath = "/home/nlp/hzwangjian1/temp";
    private static String downloadPath = rootPath + "/download";
    private static String featurePath = rootPath + "/feature";
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);

    static {
        loadModel();
    }

    /**
     * kmeans模型加载
     */
    public static void loadModel() {
        try {
            double[][] key = new double[10000][128];
            Integer[] data = new Integer[10000];
            for (int i = 0; i < 10000; i++) {
                data[i] = i + 1;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(SoftQuantization.class.getClassLoader().getResourceAsStream("kmeansModel10000c_500i_17000000_format"), "UTF-8"));
            String line = null;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] subElems = line.split(",");
                for (int i = 0; i < 128; i++) {
                    key[count][i] = Double.valueOf(subElems[i]);
                }
                count++;
            }
            logger.info("" + count);
            List<Double> array = new ArrayList<Double>();
            for (int i = 0; i < 128; i++) {
                array.add(key[35][i]);
            }
            logger.info(VectorProcess.join(array, ","));

            kdTree = new KDTree<Integer>(key, data);
            reader.close();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static Map<Integer, Integer> siftQuantization(List<KDFeaturePoint> siftFea, int neighborNum) {
        Map<Integer, Integer> kmeansFea = new HashMap<Integer, Integer>();
        for (KDFeaturePoint kdFeaturePoint : siftFea) {
            int[] descriptor = kdFeaturePoint.descriptor;
            double[] query = new double[descriptor.length];
            for (int i = 0; i < descriptor.length; i++) {
                query[i] = descriptor[i];
            }
            Neighbor<double[], Integer>[] neighbors = kdTree.knn(query, neighborNum);
            for (int i = 0; i < neighborNum; i++) {
                int index = neighbors[i].value;
                if (kmeansFea.containsKey(index)) {
                    kmeansFea.put(index, kmeansFea.get(index) + 1);
                } else {
                    kmeansFea.put(index, 1);
                }
            }
        }

        return kmeansFea;
    }

    public void testSoftQuantization(String readPath, String writePath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(readPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(writePath));
        String line = null;

        fixedThreadPool = Executors.newFixedThreadPool(20);
        while ((line = reader.readLine()) != null) {
            try {
                fixedThreadPool.submit(new FeatureExt(line));
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        // Tell threads to finish off.
        fixedThreadPool.shutdown();
        // Wait for everything to finish.
        while (!fixedThreadPool.awaitTermination(100, TimeUnit.MINUTES)) {
            logger.info("Awaiting completion of threads.");
        }
        reader.close();

        fixedThreadPool = Executors.newFixedThreadPool(20);
        reader = new BufferedReader(new FileReader(readPath));
        while ((line = reader.readLine()) != null) {
            try {
                fixedThreadPool.submit(new CompareKmeansSift(line, this, writer));
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        // Tell threads to finish off.
        fixedThreadPool.shutdown();
        // Wait for everything to finish.
        while (!fixedThreadPool.awaitTermination(100, TimeUnit.MINUTES)) {
            logger.info("Awaiting completion of threads.");
        }

        writer.close();
    }

    class CompareKmeansSift implements Runnable{
        private String line;
        private SoftQuantization softQuantization;
        private BufferedWriter writer;

        public CompareKmeansSift(String line, SoftQuantization softQuantization, BufferedWriter writer) {
            this.line = line;
            this.softQuantization = softQuantization;
            this.writer = writer;
        }

        @Override
        public void run() {
            try{
                String[] subStrs = line.split("\t");
                String urlOne = subStrs[0].trim();
                String[] elemsOne = urlOne.split("/");
                String urlOneBaseName = elemsOne[elemsOne.length - 1];

                String urlTwo = subStrs[1].trim();
                String[] elemsTwo = urlTwo.split("/");
                String urlTwoBaseName = elemsTwo[elemsTwo.length - 1];

                String imgPathOne = downloadPath + "/" + urlOneBaseName;
                String feaPathOne = featurePath + "/" + urlOneBaseName;
                String imgPathTwo = downloadPath + "/" + urlTwoBaseName;
                String feaPathTwo = featurePath + "/" + urlTwoBaseName;

                File feaFileOne = new File(feaPathOne);
                File feaFileTwo = new File(feaPathTwo);

                SiftFeature siftFeatureOne = null;
                SiftFeature siftFeatureTwo = null;
                if (feaFileOne.exists()) {
                    siftFeatureOne = loadFeaFromFile(feaFileOne);
//                    if (siftFeatureOne == null || !siftFeatureOne.getUrl().equals(urlOne)) {
//                        siftFeatureOne = extractSiftFea(urlOne, urlOneBaseName, imgPathOne, feaPathOne);
//                    }
                }

                if (feaFileTwo.exists()) {
                    siftFeatureTwo = loadFeaFromFile(feaFileTwo);
                }

                if (siftFeatureOne != null && siftFeatureTwo != null && siftFeatureOne.getUrl().equals(urlOne) && siftFeatureTwo.getUrl().equals(urlTwo)) {
                    synchronized (softQuantization) {
                        writer.write(String.format("%s\t%d\t%d\t%d\t%d\t%d\t%d\n", line, sameKmeansFea(siftFeatureOne.getKmeansFea(), siftFeatureTwo.getKmeansFea()),
                                kmeansFeaCount(siftFeatureOne.getKmeansFea()), kmeansFeaCount(siftFeatureTwo.getKmeansFea()),
                                sameKmeansFea(siftFeatureOne.getSoftKmeansFea(), siftFeatureTwo.getSoftKmeansFea()),
                                kmeansFeaCount(siftFeatureOne.getSoftKmeansFea()), kmeansFeaCount(siftFeatureTwo.getSoftKmeansFea())));
                    }
                }
            }catch (Exception e){
                logger.error("", e);
            }
        }
    }

    public int sameKmeansFea(Map<Integer, Integer> kmeansFeaOne, Map<Integer, Integer> kmeansFeaTwo) {
        int sameNum = 0;
        for (Integer key : kmeansFeaOne.keySet()) {
            int feaOne = kmeansFeaOne.get(key);
            if (kmeansFeaTwo.containsKey(key)) {
                int feaTwo = kmeansFeaTwo.get(key);
                sameNum += Math.min(feaOne, feaTwo);
            }
        }
        return sameNum;
    }

    public int kmeansFeaCount(Map<Integer, Integer> kmeansFea) {
        int sum = 0;
        for (Integer value : kmeansFea.values()) {
            sum += value;
        }
        return sum;
    }

    public SiftFeature loadFeaFromFile(File feaFile) {
        SiftFeature siftFeature = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(feaFile));
            String line = reader.readLine();
            if (line != null) {
                siftFeature = JSON.parseObject(line, SiftFeature.class);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("", e);
        }
        return siftFeature;
    }

    public SiftFeature extractSiftFea(String url, String urlBaseName, String imgPath, String feaPath) {
        File imgFile = new File(imgPath);
        File feaFile = new File(feaPath);
        if (feaFile.exists()) {
            try {
                return loadFeaFromFile(feaFile);
            } catch (Exception e) {
                logger.error("", e);
            }
        } else {
            try {
                ImageDownload.downloadImage(url, imgPath);
                List<KDFeaturePoint> siftFea = extractSiftFea(imgPath);
                imgFile.delete(); //提取完特征删除图片
                Map<Integer, Integer> kmeansFea = siftQuantization(siftFea, 1);
                Map<Integer, Integer> softKmeansFea = siftQuantization(siftFea, 3);
                SiftFeature siftFeature = new SiftFeature(url, siftFea, kmeansFea, softKmeansFea);

                BufferedWriter writer = new BufferedWriter(new FileWriter(feaFile));
                writer.write(JSON.toJSONString(siftFeature));
                writer.close();
                return siftFeature;
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return null;
    }

    public List<KDFeaturePoint> extractSiftFea(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            img = ImageProcess.scaleImageWithRatio(img, 80); //图片缩放，短边减小到80
            RenderImage ri = new RenderImage(img);
            SIFT sift = new SIFT();
            sift.detectFeatures(ri.toPixelFloatArray(null));
            List<KDFeaturePoint> al = sift.getGlobalKDFeaturePoints();
            return al;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    class FeatureExt implements Runnable {
        private String line;

        public FeatureExt(String line) {
            this.line = line;
        }

        @Override
        public void run() {
            try {
                String[] subStrs = line.split("\t");
                String urlOne = subStrs[0].trim();
                String[] elemsOne = urlOne.split("/");
                String urlOneBaseName = elemsOne[elemsOne.length - 1];

                String urlTwo = subStrs[1].trim();
                String[] elemsTwo = urlTwo.split("/");
                String urlTwoBaseName = elemsTwo[elemsTwo.length - 1];

                String imgPathOne = downloadPath + "/" + urlOneBaseName;
                String feaPathOne = featurePath + "/" + urlOneBaseName;
                String imgPathTwo = downloadPath + "/" + urlTwoBaseName;
                String feaPathTwo = featurePath + "/" + urlTwoBaseName;

                extractSiftFea(urlOne, urlOneBaseName, imgPathOne, feaPathOne);
                extractSiftFea(urlTwo, urlTwoBaseName, imgPathTwo, feaPathTwo);
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }

    public static void combineFiles() throws Exception {
        String needReviewPath = rootPath + "/NeedReviewImages2017-01-18";
        String trueNegativePath = rootPath + "/TrueNegative2017-01-18";
        String truePositivePath = rootPath + "/TruePositive2017-01-18";
        String testDataPath = rootPath + "/SiftLabelledDataSmall2017-01-18";
        BufferedReader reader = new BufferedReader(new FileReader(needReviewPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(testDataPath));
        String line = null;
        int maxCount = 1000;
        int count = 0;

        while ((line = reader.readLine()) != null) {
            String[] subStr = line.split("\t");
            String urlOne = subStr[0].trim();
            String urlTwo = subStr[1].trim();
            writer.write(String.format("%s\t%s\t%d\n", urlOne, urlTwo, 0));
            count++;
            if (count >= maxCount) break;
        }
        reader.close();

        count = 0;
        reader = new BufferedReader(new FileReader(truePositivePath));
        while ((line = reader.readLine()) != null) {
            String[] subStr = line.split("\t");
            String urlOne = subStr[0].trim();
            String urlTwo = subStr[1].trim();
            writer.write(String.format("%s\t%s\t%d\n", urlOne, urlTwo, 1));
            count++;
            if (count >= maxCount) break;
        }
        reader.close();

        count = 0;
        reader = new BufferedReader(new FileReader(trueNegativePath));
        while ((line = reader.readLine()) != null) {
            String[] subStr = line.split("\t");
            String urlOne = subStr[0].trim();
            String urlTwo = subStr[1].trim();
            writer.write(String.format("%s\t%s\t%d\n", urlOne, urlTwo, -1));
            count++;
            if (count >= maxCount) break;
        }
        reader.close();

        writer.close();
    }

    public void testSoftQuantizationSmall() throws Exception {
        String readPath = rootPath + "/SiftLabelledDataSmall2017-01-18";
        String writePath = rootPath + "/SiftLabelledDataSmallResult";
        testSoftQuantization(readPath, writePath);
    }

    public void testSoftQuantizationBig() throws Exception {
        String readPath = rootPath + "/SiftLabelledData2017-01-18";
        String writePath = rootPath + "/SiftLabelledDataBigResult";
        testSoftQuantization(readPath, writePath);
    }

    public static void main(String[] args) throws Exception {
//        logger.info("test");
        SoftQuantization softQuantization = new SoftQuantization();
        softQuantization.testSoftQuantizationSmall();
//        combineFiles();

    }

}
