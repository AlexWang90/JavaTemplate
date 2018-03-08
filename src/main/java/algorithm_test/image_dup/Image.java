package algorithm_test.image_dup;

import com.alibaba.fastjson.JSON;
import com.alibaba.simpleimage.analyze.sift.SIFT;
import com.alibaba.simpleimage.analyze.sift.match.Match;
import com.alibaba.simpleimage.analyze.sift.match.MatchKeys;
import com.alibaba.simpleimage.analyze.sift.render.RenderImage;
import com.alibaba.simpleimage.analyze.sift.scale.KDFeaturePoint;
import de.lmu.ifi.dbs.jfeaturelib.features.CEDD;
import de.lmu.ifi.dbs.jfeaturelib.features.PHOG;
import ij.process.ColorProcessor;
import org.apache.commons.lang.StringUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwangjian1 on 2018/3/8.
 * 抽取CEDD+PHOG+SIFT、SIFT图像特征提取和匹配
 */
public class Image {
    private static Logger logger = LoggerFactory.getLogger(Image.class);

    // create temp dir to save images
    private static final Path CURRENTPATH = Paths.get(System.getProperty("user.dir"));
    private static final Path TEMPPATH = Paths.get(CURRENTPATH.toString(), "temp");
    private static final String TEMPPATHSTRING = TEMPPATH.toString();

    static{
        try {
            File file = new File(TEMPPATHSTRING);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            logger.error("create dir error:" + TEMPPATHSTRING, e);
        }
    }

    /**
     *
     * @param imageUrlOne
     * @param imageUrlTwo
     * @return
     */
    public static ImageDupResult duplicateCheck(String imageUrlOne, String imageUrlTwo){
        ImageDupResult imageDupResult = new ImageDupResult(imageUrlOne, imageUrlTwo);
        String imageNameOne = StringUtils.substringAfterLast(imageUrlOne, "/");
        String imageNameTwo = StringUtils.substringAfterLast(imageUrlTwo, "/");

        String imagePathOne = Paths.get(TEMPPATHSTRING, imageNameOne).toString();
        String imagePathTwo = Paths.get(TEMPPATHSTRING, imageNameTwo).toString();

        // download images
        try{
            downloadImage(imageUrlOne, imagePathOne);
            downloadImage(imageUrlTwo, imagePathTwo);
        }catch (Exception e){
            logger.error("", e);
            imageDupResult.setMessage("Download image error.");
            return imageDupResult;
        }

        try {
            BufferedImage imgOne = ImageIO.read(new File(imagePathOne));
            BufferedImage imgTwo = ImageIO.read(new File(imagePathTwo));

            // extract features
            SIFT sift = new SIFT();
            List<KDFeaturePoint> siftFeatureOne = extractSiftFeature(sift, imgOne);
            List<KDFeaturePoint> siftFeatureTwo = extractSiftFeature(sift, imgTwo);
            double[] ceddFeaOne = extractCeddFea(imagePathOne);
            double[] ceddFeaTwo = extractCeddFea(imagePathTwo);
            double[] phogFeaOne = extractPhogFea(imagePathOne);
            double[] phogFeaTwo = extractPhogFea(imagePathTwo);

            // delete image files
            deleteFile(imagePathOne);
            deleteFile(imagePathTwo);

            // features match
            int[] siftmatches = siftMatch(siftFeatureOne, siftFeatureTwo);
            double phogDistance = Distance.norm2Distance(phogFeaOne, phogFeaTwo);
            double xx = Distance.dotPruduct(ceddFeaOne, ceddFeaOne);
            double yy = Distance.dotPruduct(ceddFeaTwo, ceddFeaTwo);
            double xy = Distance.dotPruduct(ceddFeaOne, ceddFeaTwo);
            double ceddSimilarity = xy / (xx + yy - xy);

            imageDupResult.setSiftmatches(siftmatches);
            imageDupResult.setPhogDistance(phogDistance);
            imageDupResult.setCeddSimilarity(ceddSimilarity);

            if(siftmatches[0] > 6 && siftmatches[1] > 6){
                imageDupResult.setSiftSimilarity(true);
            }
            if(siftmatches[0] > 10 && siftmatches[1] > 10){
                imageDupResult.setSiftDuplicate(true);
            }
            if (ceddSimilarity >= 0.99 || phogDistance <= 0.001){
                imageDupResult.setCeddphogDuplicate(true);
            } else if (phogDistance <= 0.003 || ceddSimilarity >= 0.9) {
                if(siftmatches[0] > 6 && siftmatches[1] > 6){
                    imageDupResult.setCeddphogDuplicate(true);
                }
            }

        }catch (Exception e){
            logger.error("", e);
        }
        return imageDupResult;
    }



    public static int[] siftMatch(List<KDFeaturePoint> siftFeatureOne, List<KDFeaturePoint> siftFeatureTwo){
        logger.info("size of siftFeatureOne feature:" + siftFeatureOne.size());
        logger.info("size of siftFeatureTwo feature:" + siftFeatureTwo.size());
        int[] matches = new int[2];
        List<Match> ms = MatchKeys.findMatchesBBF(siftFeatureOne, siftFeatureTwo);
        ms = filterMore(ms);
        logger.info("size of ms:" + ms.size());
        List<Match> msReverse = MatchKeys.findMatchesBBF(siftFeatureTwo, siftFeatureOne);
        msReverse = filterMore(msReverse);
        logger.info("size of msReverse:" + msReverse.size());
        matches[0] = ms.size();
        matches[1] = msReverse.size();
        return matches;
    }

    public static ArrayList<Match> filterMore(List<Match> matches) {
        Map<KDFeaturePoint, Integer> map1 = new HashMap<KDFeaturePoint, Integer>();
        Map<KDFeaturePoint, Integer> map2 = new HashMap<>();

        for (Match m : matches) {
            Integer kp1V = map1.get(m.fp1);
            int lI = (kp1V == null) ? 0 : (int) kp1V;
            map1.put(m.fp1, lI + 1);
            Integer kp2V = map2.get(m.fp2);
            int rI = (kp2V == null) ? 0 : (int) kp2V;
            map2.put(m.fp2, rI + 1);
        }
        ArrayList<Match> survivors = new ArrayList<Match>();
        for (Match m : matches) {
            Integer kp1V = map1.get(m.fp1);
            Integer kp2V = map2.get(m.fp2);
            if (kp1V <= 1 && kp2V <= 1) survivors.add(m);
        }
        return (survivors);
    }

    public static double[] extractPhogFea(String imagePath) {
        try {
            PHOG phog = new PHOG();
            ColorProcessor colorProcessor = new ColorProcessor(ImageIO.read(new File(imagePath)));
            phog.run(colorProcessor);
            double[] fea = phog.getFeatures().get(0);
            return fea;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static double[] extractCeddFea(String imagePath) {
        try {
            CEDD cedd = new CEDD();
            ColorProcessor colorProcessor = new ColorProcessor(ImageIO.read(new File(imagePath)));
            cedd.run(colorProcessor);
            double[] fea = cedd.getFeatures().get(0);
            return fea;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static List<KDFeaturePoint> extractSiftFeature(SIFT sift, BufferedImage img){
        img = scaleImageWithRatio(img, 80); //图片缩放，短边减小到80
        RenderImage ri = new RenderImage(img);
        sift.detectFeatures(ri.toPixelFloatArray(null));
        List<KDFeaturePoint> al = sift.getGlobalKDFeaturePoints();
        return al;
    }

    public static BufferedImage scaleImageWithRatio(BufferedImage image, double maxSize) {
        double ratio = 1;
        int width = image.getWidth();
        int height = image.getHeight();

        if (width <= maxSize || height <= maxSize) {
            return image;
        }

        ratio = maxSize / Math.min(width, height);  //小的变缩放到maxSize

        int newWidth = (int) (ratio * width);
        int newHeight = (int) (ratio * height);

        image = Scalr.resize(image, Scalr.Method.BALANCED, newWidth, newHeight);
        return image;
    }

    public static void deleteFile(String filePath){
        try {
            File fileOne = new File(filePath);
            fileOne.delete();
        }catch(Exception e){
            logger.error("", e);
        }
    }

    /**
     * 根据路径 下载图片 然后 保存到对应的目录下
     *
     * @param urlString
     * @param filePath
     * @return
     * @throws Exception
     */
    public static void downloadImage(String urlString, String filePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求的路径
        con.setConnectTimeout(5 * 1000);
        // 超时时间
        con.setReadTimeout(60 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(filePath);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    public static void main(String[] args) {
        String imageUrl = "http://spider.nosdn.127.net/bb9891bc04023b23b0175b6900281580.jpeg";
        ImageDupResult imageDupResult = duplicateCheck(imageUrl, imageUrl);
        System.out.println(JSON.toJSONString(imageDupResult));
    }

}
