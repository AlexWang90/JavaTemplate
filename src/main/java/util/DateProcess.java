package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hzwangjian1 on 2016/11/24.
 */
public class DateProcess {

    private static SimpleDateFormat simpleDateFormatNorm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * k天之前
     * @param k
     * @return
     */
    public static String kDaysAge(int k){
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, -k);
//        return simpleDateFormatNorm.format(calendar.getTime());

        Date date = new Date();
        long currentTime = date.getTime();
        long oneHour = 3600 * 1000;
        long kDay = k * 24 * oneHour;

        return simpleDateFormatNorm.format(new Date(currentTime - kDay));

    }

    public static Date kDaysAgeDate(int k){
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, -k);
//        return simpleDateFormatNorm.format(calendar.getTime());

        Date date = new Date();
        long currentTime = date.getTime();
        long oneHour = 3600 * 1000;
        long kDay = k * 24 * oneHour;

        return new Date(currentTime - kDay);

    }

    public static Date kHoursAgeDate(int k){

        Date date = new Date();
        long currentTime = date.getTime();
        long oneHour = 3600 * 1000;
        long kDay = k * oneHour;

        return new Date(currentTime - kDay);

    }

    public static void main(String[] args) {

        System.out.println(kDaysAge(30));
        System.out.println(kDaysAge(3600));
    }
}
