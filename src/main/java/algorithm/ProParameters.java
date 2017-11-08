package algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PathUtil;
import util.ProperityHandler;

import java.util.Properties;

/**
 * Created by hzwangjian1 on 2017/11/8.
 */
public class ProParameters {
    private static Logger logger = LoggerFactory.getLogger(ProParameters.class);

    public final static String PropertiesFilePath = PathUtil.getCurrentDir() + "/parameters.properties";
    //所有配置参数通过ProParameters.properties访问
    public static Properties properties = null;

    static {
        properties = ProperityHandler.loadPropertiesFromFile(PropertiesFilePath);
    }

    public static void main(String[] args) {
        logger.info(PropertiesFilePath);
    }
}
