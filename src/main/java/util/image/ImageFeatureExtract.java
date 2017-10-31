package util.image;

import com.alibaba.simpleimage.analyze.sift.SIFT;
import com.alibaba.simpleimage.analyze.sift.render.RenderImage;
import com.alibaba.simpleimage.analyze.sift.scale.KDFeaturePoint;
import de.lmu.ifi.dbs.jfeaturelib.features.CEDD;
import de.lmu.ifi.dbs.jfeaturelib.features.PHOG;
import ij.process.ColorProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ImageProcess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by hzwangjian1 on 2017/10/31.
 */
public class ImageFeatureExtract {
    private static Logger logger = LoggerFactory.getLogger(ImageFeatureExtract.class);

    public static double[] extractPhogFea(String imagePath) {
        try {
            PHOG phog = new PHOG();
            ColorProcessor colorProcessor = new ColorProcessor(ImageIO.read(new File(imagePath)));
            phog.run(colorProcessor);
            double[] fea = phog.getFeatures().get(0);
            return fea;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static double[] extractCeddFea(String imagePath) {
        try {
            CEDD cedd = new CEDD();
            ColorProcessor colorProcessor = new ColorProcessor(ImageIO.read(new File(imagePath)));
            cedd.run(colorProcessor);
            double[] fea = cedd.getFeatures().get(0);
            return fea;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public static List<KDFeaturePoint> extractSiftFea(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            img = ImageProcess.scaleImageWithRatio(img, 80); //图片缩放，短边减小到80
            RenderImage ri = new RenderImage(img);
            SIFT sift = new SIFT();
            sift.detectFeatures(ri.toPixelFloatArray(null));
            List<KDFeaturePoint> al = sift.getGlobalKDFeaturePoints();
            return al;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
}
