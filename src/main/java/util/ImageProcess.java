package util;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

/**
 * 按比例进行缩放
 * Created by hzwangjian1 on 2016/11/3.
 */
public class ImageProcess {
    private static Logger logger = LoggerFactory.getLogger(ImageProcess.class);


    /**
     * 下载图片
     * @param url
     * @return
     */
    public static String getImage(String url) {
        String savePath = "/home/nlp/hzwangjian1/docDuplicate/imgs";
        return getImage(url, savePath, 1);
    }

    public static String getImage(String url, int threadNum) {
        String savePath = "/home/nlp/hzwangjian1/docDuplicate/imgs";
        return getImage(url, savePath, threadNum);
    }

    public static String getImage(String url, String savePath, int threadNum) {
        String postfix = ".jpg";
        if(url.toLowerCase().endsWith("png")){
            postfix = ".png";
        }

        String saveFileName = "imageHistogram" + threadNum + url.hashCode() + postfix;  //哈希编码混淆，防止图片重复
        try {
            ImageDownload.downloadImage(url, saveFileName, savePath);
        } catch (Exception e) {
            logger.error("getImage error:" + url, e);
            return null;
        }
        return savePath + "/" + saveFileName;
    }

    /**
     * 图片按比例缩放
     * @param image
     * @return
     */
    public static BufferedImage scaleImageWithRatio(BufferedImage image) {
        double maxSize = 80.0;  //较短边的最大长度
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
}
