package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzwangjian1 on 2018/1/10.
 */
public class ProgressBarUtil {
    private static Logger logger = LoggerFactory.getLogger(ProgressBarUtil.class);

    /**
     * @param done        已经完成的部分
     * @param total       总量
     * @param maxBareSize 100%的unit个数
     */
    public static void progressPercentage(int done, int total, int maxBareSize) {
        if (done > total) {
            throw new IllegalArgumentException();
        }
//        int maxBareSize = 10; // 10unit for 100%
//        int doneProcent = ((100 * done) / total) / maxBareSize;
        int doneProcent = (int)(((done * 1.0) / total) * maxBareSize);
        logger.info(String.format("done:%s, doneProcent:%s", done, doneProcent));
        char defaultChar = '-';
        String icon = "*";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();
        bareDone.append("[");
        for (int i = 0; i < doneProcent; i++) {
            bareDone.append(icon);
        }
        String bareRemain = bare.substring(doneProcent, bare.length());
        System.out.println("\r" + bareDone + bareRemain + " " + doneProcent * 100 / maxBareSize + "%");
        if (done == total) {
            logger.info("\n");
        }
    }

    public static void main(String[] args) {
        int total = 52340;
        for (int i = 0; i <= 52340; i += 10) {
            progressPercentage(i, total, 100);
        }
    }

}
