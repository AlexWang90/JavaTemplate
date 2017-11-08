package algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PathUtil;

/**
 * Created by hzwangjian1 on 2017/11/8.
 */
public class ProParameters {
    private static Logger logger = LoggerFactory.getLogger(ProParameters.class);

    public static final String PropertiesFilePath = PathUtil.getCurrentDir() + "/parameters.properties";

    public static void main(String[] args) {
        logger.info(PropertiesFilePath);
    }
}
