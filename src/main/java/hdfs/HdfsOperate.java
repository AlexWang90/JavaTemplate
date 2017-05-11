package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URI;

/**
 * Created by hzwangjian1 on 2016/8/30.
 */
public class HdfsOperate implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(HdfsOperate.class);
    private static Configuration conf = new Configuration();
    private static BufferedWriter writer = null;

    public static boolean isExist(String path) {
        try {
            FileSystem fileSystem = FileSystem.get(conf);
            Path path1 = new Path(path);
            if (fileSystem.exists(path1)) {
                return true;
            }
        } catch (Exception e) {
            logger.error("[HdfsOperate]>>>isExist error", e);
        }
        return false;
    }

    public static void deleteIfExist(String path) {
        try {
            FileSystem fileSystem = FileSystem.get(conf);
            Path path1 = new Path(path);
            if (fileSystem.exists(path1)) {
                fileSystem.delete(path1, true);
            }
        } catch (Exception e) {
            logger.error("[HdfsOperate]>>>deleteHdfsFile error", e);
        }
    }

    public static void openHdfsFile(String path) {
        try {
            FileSystem fs = FileSystem.get(URI.create(path), conf);
            writer = new BufferedWriter(new OutputStreamWriter(fs.create(new Path(path))));
            if (null != writer) {
                logger.info("[HdfsOperate]>> initialize writer succeed!");
            }
        } catch (Exception e) {
            logger.error("[HdfsOperate]>>>openHdfsFile error", e);
        }
    }

    public static void writeString(String line) {
        try {
            writer.write(line + "\n");
        } catch (Exception e) {
            logger.error("[HdfsOperate]>> writer a line error:", e);
        }
    }

    public static void mkdir(String dir) {
        try {
            FileSystem fileSystem = FileSystem.get(conf);
            Path path1 = new Path(dir);
            if (!fileSystem.exists(path1)) {
                fileSystem.mkdirs(path1);
            }
        } catch (Exception e) {
            logger.error("[HdfsOperate]>>>mkdir error", e);
        }
    }

    public static void closeHdfsFile() {
        try {
            if (null != writer) {
                writer.close();
                logger.info("[HdfsOperate]>> closeHdfsFile close writer succeed!");
            } else {
                logger.error("[HdfsOperate]>> closeHdfsFile writer is null");
            }
        } catch (Exception e) {
            logger.error("[HdfsOperate]>> closeHdfsFile close hdfs error:" + e);
        }
    }

    public static void main(String[] args) {

    }
}
