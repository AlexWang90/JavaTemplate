package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzwangjian1 on 2017/6/13.
 */
public class Distance {
    private static Logger logger = LoggerFactory.getLogger(Distance.class);

    public static double norm2Distance(double[] vecOne, double[] vecTwo) {
        try {
            double distance = 0.0;
            for (int i = 0; i < vecOne.length; i++) {
                distance += (vecOne[i] - vecTwo[i]) * (vecOne[i] - vecTwo[i]);
            }
            return distance;
        } catch (Exception e) {
            logger.error("norm2Distance error", e);
        }
        return Double.MAX_VALUE;
    }

    public static double dotPruduct(double[] vecOne, double[] vecTwo) {
        if(vecOne == null || vecTwo == null){
            return 0;
        }
        if (vecOne.length != vecTwo.length) {
            logger.error("vecOne.length != vecTwo.length");
            return -1;
        }
        double sum = 0;
        for (int i = 0; i < vecOne.length; i++) {
            sum += vecOne[i] * vecTwo[i];
        }
        return sum;
    }
}
