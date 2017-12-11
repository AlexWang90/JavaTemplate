package util;

import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.execution.Except;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by hzwangjian1 on 2017/8/18.
 */
public class PathUtil {

    private static Logger logger = LoggerFactory.getLogger(PathUtil.class);


    /**
     * 返回当前目录
     * @return
     */
    public static String getCurrentDir() {
        return System.getProperty("user.dir");
    }

    /**
     * 创建目录
     * @param dirName
     * @return
     */
    public static boolean createDirIfNotExists(String dirName) {
        try {
            File file = new File(dirName);
            if (!file.exists()) {
                file.mkdir();
            }
            return true;
        } catch (Exception e) {
            logger.error("create dir error:" + dirName, e);
        }
        return false;
    }

    /**
     * 删除目录，返回是否成功
     * @param dirPath
     * @return
     */
    public static boolean deleteDir(String dirPath){
        try {
            FileUtils.deleteDirectory(new File(dirPath));
        }catch (Exception e){
            logger.error("delete dir error:" + dirPath, e);
            return false;
        }
        return true;
    }

}
