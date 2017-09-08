package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * config文件操作
 * Created by hzwangjian1 on 2017/9/8.
 */
public class ConfUtil {
    private static Logger logger = LoggerFactory.getLogger(ConfUtil.class);

    /**
     * 获取property文件key对应的value值
     * @param filePath
     * @param key
     * @return
     */
    public static String getProperty(String filePath, String key) {
        try {
            Properties props = new Properties();
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            String value = props.getProperty(key);
            in.close();
            return value;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 更新property文件的key值
     * 如果key值不存在则追加key=value对
     * @param filePath
     * @param key
     * @param value
     * @return
     */
    public static boolean setProperty(String filePath, String key, String value) {
        try {
            Properties props = new Properties();
            OutputStream fos = new FileOutputStream(filePath);
            props.setProperty(key, value);
            props.store(fos, "update value of %s".format(key));
            fos.close();
            return true;
        }catch (Exception e){
            logger.error("", e);
            return false;
        }
    }

    public static void main(String[] args) {
        String propertyFile = "E://workspace/servers.properties";
        logger.info(getProperty(propertyFile, "database"));
        logger.info("" + setProperty(propertyFile, "database", "abc"));
        logger.info(getProperty(propertyFile, "database"));
        logger.info("" + setProperty(propertyFile, "table", "mytable"));
        logger.info(getProperty(propertyFile, "table"));
    }

}
