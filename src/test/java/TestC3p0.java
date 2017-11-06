
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.C3p0Util;

import java.sql.Connection;

/**
 * Created by hzwangjian1 on 2017/9/14.
 */
public class TestC3p0 {
    private static Logger logger = LoggerFactory.getLogger(TestC3p0.class);



    public static void main(String[] args) throws Exception{
        Connection con = C3p0Util.getConnection();
        logger.info("" + con.isClosed());
        con.close();
        C3p0Util.closeC3P0();
    }
}
