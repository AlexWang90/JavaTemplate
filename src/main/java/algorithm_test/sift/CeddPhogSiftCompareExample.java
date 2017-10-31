package algorithm_test.sift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Flag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzwangjian1 on 2017/9/11.
 */
public class CeddPhogSiftCompareExample {
    private static Logger logger = LoggerFactory.getLogger(CeddPhogSiftCompareExample.class);
    private static Flag flag = new Flag();
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    private static boolean writeDB = false;//是否写数据库

    class ProcessClass implements Runnable {

        @Override
        public void run() {

//            if (historyToutiaoFeature.getDocid().endsWith(toutiaoFeature.getDocid())) continue;
//            titleContentCompare(toutiaoFeature, historyToutiaoFeature);
//
//            for (String url1 : toutiaoFeature.getCeddFeatureMap().keySet()) {
//                for (String url2 : historyToutiaoFeature.getCeddFeatureMap().keySet()) {
//                    try {
//                        double phogDistance = Distance.norm2Distance(toutiaoFeature.getPhogFeatureMap().get(url1), historyToutiaoFeature.getPhogFeatureMap().get(url2));
//
//                        double xx = toutiaoFeature.getCeddFeatureSquare().get(url1);
//                        double yy = historyToutiaoFeature.getCeddFeatureSquare().get(url2);
//                        double xy = Distance.dotPruduct(toutiaoFeature.getCeddFeatureMap().get(url1), historyToutiaoFeature.getCeddFeatureMap().get(url2));
//                        double ceddDistance = xy / (xx + yy - xy);
//
//                        if (phogDistance <= 0.003 || ceddDistance >= 0.9) {
//                            validBySIFT(toutiaoFeature, historyToutiaoFeature, url1, url2, phogDistance, ceddDistance, writer);
//                        }
//
//                    } catch (Exception e) {
//                        logger.error("", e);
//                    }
//                }
//            }
        }
    }

//    public static void validBySIFT(ToutiaoFeature toutiaoFeature, ToutiaoFeature historyToutiaoFeature, String url1, String url2, double phogDistance, double ceddDistance, BufferedWriter writer) {
//        double contentTdidfCosSimilarity = 0;
//        try {
//            contentTdidfCosSimilarity = SimilarityCalc.calcByCos(toutiaoFeature.getContent_tdidf(), historyToutiaoFeature.getContent_tdidf());  //正文匹配
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//
//        try {
//            if (ceddDistance >= 0.99 || phogDistance <= 0.001) {
//                synchronized (flag) {
//                    try {
//                        String str = String.format("%s\t%s\t%f\t%f", url1, url2, phogDistance, ceddDistance);
//                        writer.write(str + "\n");
//                        //展示图片相同，结果插入到数据库
//                        //展示图片相同，结果插入到数据库
//                        String docidOne = toutiaoFeature.getDocid();
//                        String docidTwo = historyToutiaoFeature.getDocid();
//                        if (writeDB) {
//                            DatabaseControlScreen.insertDuplicateRecord(docidOne, docidTwo, imageMethodType);
//                            DatabaseControlScreen.insertDuplicateRecord(docidTwo, docidOne, imageMethodType);
//                        }
//                        //test 如果正文相似度大于阈值，结果插入到推荐去重数据表
//                        try {
//                            if (contentTdidfCosSimilarity >= 0.5 || toutiaoFeature.getArea().equals("S") || historyToutiaoFeature.getArea().equals("S")) {
//                                if (writeDB) {
//                                    DatabaseControl.insertDuplicateRecord(docidOne, docidTwo, imageMethodType);
//                                    DatabaseControl.insertDuplicateRecord(docidTwo, docidOne, imageMethodType);
//                                }
//                            }
//                        } catch (Exception e) {
//                            logger.error("", e);
//                            if (writeDB) {
//                                DatabaseControl.insertDuplicateRecord(docidOne, docidTwo, imageMethodType);
//                                DatabaseControl.insertDuplicateRecord(docidTwo, docidOne, imageMethodType);
//                            }
//                        }
//                        return;
//                    } catch (Exception e) {
//                        logger.error("", e);
//                    }
//                }
//            }
//
//            //使用SIFT特征进行匹配
//            List<KDFeaturePoint> siftFeaOne = toutiaoFeature.getSiftFeatureMap().get(url1);
//            List<KDFeaturePoint> siftFeaTwo = historyToutiaoFeature.getSiftFeatureMap().get(url2);
//            List<Match> ms = MatchKeys.findMatchesBBF(siftFeaOne, siftFeaTwo);
//            ms = MatchKeys.filterMore(ms);
//            if (ms.size() > 6) {  //大于4，表示是相同图片
//                List<Match> msReverse = MatchKeys.findMatchesBBF(siftFeaTwo, siftFeaOne);
//                msReverse = MatchKeys.filterMore(msReverse);
//                if (msReverse.size() > 6) {
//                    synchronized (flag) {
//                        try {
//                            String str = String.format("%s\t%s\t%f\t%f\t%d\t%d", url1, url2, phogDistance, ceddDistance, ms.size(), msReverse.size());
//                            writer.write(str + "\n");
//                            //展示图片相同，结果插入到数据库
//                            //展示图片相同，结果插入到数据库
//                            String docidOne = toutiaoFeature.getDocid();
//                            String docidTwo = historyToutiaoFeature.getDocid();
//                            if (writeDB) {
//                                DatabaseControlScreen.insertDuplicateRecord(docidOne, docidTwo, imageMethodType);
//                                DatabaseControlScreen.insertDuplicateRecord(docidTwo, docidOne, imageMethodType);
//                            }
//                            //test 如果正文相似度大于阈值，结果插入到推荐去重数据表
//                            try {
//                                if (contentTdidfCosSimilarity >= 0.5 || toutiaoFeature.getArea().equals("S") || historyToutiaoFeature.getArea().equals("S")) {
//                                    if (writeDB) {
//                                        DatabaseControl.insertDuplicateRecord(docidOne, docidTwo, imageMethodType);
//                                        DatabaseControl.insertDuplicateRecord(docidTwo, docidOne, imageMethodType);
//                                    }
//                                }
//                            } catch (Exception e) {
//                                logger.error("", e);
//                                if (writeDB) {
//                                    DatabaseControl.insertDuplicateRecord(docidOne, docidTwo, imageMethodType);
//                                    DatabaseControl.insertDuplicateRecord(docidTwo, docidOne, imageMethodType);
//                                }
//                            }
//                        } catch (Exception e) {
//                            logger.error("", e);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//    }



    public static void main(String[] args) {
        String str = String.format("%f\t%f", -1.0, 0.0);
        logger.info(str);
    }
}
