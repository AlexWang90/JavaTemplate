package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hzwangjian1 on 2017/9/21.
 */
public class PrintUtil {

    private static Logger logger = LoggerFactory.getLogger(PrintUtil.class);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    public static void info(String message, String... args){
        String format = "";
        for(String arg : args){
            format += arg;
        }
        logger.info(format + message + ANSI_RESET);
    }

    /**
     * 用户指定logger
     * @param preferedLogger
     * @param message
     * @param args
     */
    public static void info(Logger preferedLogger, String message, String... args){
        String format = "";
        for(String arg : args){
            format += arg;
        }
        preferedLogger.info(format + message + ANSI_RESET);
    }

    public static void debug(String message, String... args){
        String format = "";
        for(String arg : args){
            format += arg;
        }
        logger.debug(format + message + ANSI_RESET);
    }

    /**
     * 用户指定logger
     * @param preferedLogger
     * @param message
     * @param args
     */
    public static void debug(Logger preferedLogger,String message, String... args){
        String format = "";
        for(String arg : args){
            format += arg;
        }
        preferedLogger.debug(format + message + ANSI_RESET);
    }

    public static void error(String message, String... args){
        String format = "";
        for(String arg : args){
            format += arg;
        }
        logger.error(format + message + ANSI_RESET);
    }

    /**
     * 用户指定logger
     * @param preferedLogger
     * @param message
     * @param args
     */
    public static void error(Logger preferedLogger,String message, String... args){
        String format = "";
        for(String arg : args){
            format += arg;
        }
        preferedLogger.error(format + message + ANSI_RESET);
    }

    public static void testPrintColot() {
        logger.info(ANSI_GREEN_BACKGROUND + "This text has a green background but default text!" + ANSI_RESET);
        logger.info(ANSI_RED + "This text has red text but a default background!" + ANSI_RESET);
        logger.info(ANSI_GREEN_BACKGROUND + ANSI_RED + "This text has a green background and red text!" + ANSI_RESET);
    }

    public static void main(String[] args) {
//        testPrintColot();
        info("test info", ANSI_RED, ANSI_CYAN_BACKGROUND);
    }
}
