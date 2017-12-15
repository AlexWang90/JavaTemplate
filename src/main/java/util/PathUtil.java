package util;

import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.execution.Except;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwangjian1 on 2017/8/18.
 */
public class PathUtil {

    private static Logger logger = LoggerFactory.getLogger(PathUtil.class);
    private static SimpleDateFormat simpleDayFormat = new SimpleDateFormat("yyyy-MM-dd");

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

    /**
     * 删除dir下days之前的目录
     * @param dir
     *         dir下的子目录格式：2017-12-13  2017-12-14  2017-12-15  2017-12-16
     * @param days
     */
    public static void cleanHistoryDirs(String dir, int days) {
        String filtTime = simpleDayFormat.format(DateProcess.kDaysAgeDate(days));

        try {
            File file = new File(dir);
            File[] subFiles = file.listFiles();
            List<File> filesToDelete = new ArrayList<>();
            for (File subFile : subFiles) {
                if (subFile.isDirectory() && subFile.getName().compareTo(filtTime) < 0) {
                    filesToDelete.add(subFile);
                }
            }

            for (File subfile : filesToDelete) {
                try {
                    FileUtils.deleteDirectory(subfile);
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }catch (Exception e){
            logger.error("", e);
        }
    }

}
