package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by hzwangjian1 on 2016/12/8.
 */
public class VectorProcess {

    private static Logger logger = LoggerFactory.getLogger(VectorProcess.class);

    public static String vec2Str(int[] vec) {
        String str = "";
        for (int i = 0; i < vec.length; i++) {
            str += vec[i] + ", ";
        }
        return str;
    }

    public static String vec2Str(float[] vec) {
        String str = "";
        for (int i = 0; i < vec.length; i++) {
            str += vec[i] + ", ";
        }
        return str;
    }

    public static String vec2Str(double[] vec) {
        String str = "";
        for (int i = 0; i < vec.length; i++) {
            str += vec[i] + ", ";
        }
        return str;
    }

    /**
     * vector 加权相加
     *
     * @param vecOne
     * @param vecTwo
     * @param weight
     * @return
     */
    public static float[] vecAddWeight(float[] vecOne, float[] vecTwo, float weight) {
        if (vecOne.length != vecTwo.length) {
            logger.error("vecOne.length != vecTwo.length. length of vecOne:" + vecOne.length + " ,length of vecTwo:" + vecTwo.length);
            return null;
        }

        for (int i = 0; i < vecOne.length; i++) {
            vecOne[i] += vecTwo[i] * weight;
        }

        return vecOne;
    }

    public static double cosSimilarity(float[] vecOne, float[] vecTwo){
        double similarity = -1;
        if (vecOne.length != vecTwo.length) {
            logger.error("vecOne.length != vecTwo.length. length of vecOne:" + vecOne.length + " ,length of vecTwo:" + vecTwo.length);
            return similarity;
        }

        similarity = 0;
        double vecOneSquare = 0;
        double vecTwoSquare = 0;

        for(int i=0 ; i< vecOne.length; i++){
            similarity += vecOne[i] * vecTwo[i];
            vecOneSquare += vecOne[i] * vecOne[i];
            vecTwoSquare += vecTwo[i] * vecTwo[i];
        }

        vecOneSquare  = Math.sqrt(vecOneSquare);
        vecTwoSquare = Math.sqrt(vecTwoSquare);

        similarity = similarity / vecOneSquare / vecTwoSquare;
        return similarity;
    }

    public static String printList(List<String> stringList){
        String str = "";
        str = join(stringList, ",");

        return str;
    }

    public static String printSet(Set<String> stringSet){
        String str=  "";
        str = join(stringSet, ",");
        return str;
    }

    public static String join(Iterable<?> iterable, String separator) {
        return iterable == null?null:join(iterable.iterator(), separator);
    }

    public static String join(Iterator<?> iterator, String separator) {
        if(iterator == null) {
            return null;
        } else if(!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if(!iterator.hasNext()) {
                String buf1 = toString(first);
                return buf1;
            } else {
                StringBuilder buf = new StringBuilder(256);
                if(first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    if(separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if(obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String toString(Object obj) {
        return obj == null?"":obj.toString();
    }

    public static void main(String[] args) {
        Set<String> stringSet = new HashSet<String>();
        stringSet.add("a");
        stringSet.add("b");
        stringSet.add("c");
        System.out.println(printSet(stringSet));

        List<String> stringList = new ArrayList<String>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        System.out.println(printList(stringList));
    }

}
