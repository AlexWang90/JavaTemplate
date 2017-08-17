package test.java;

import org.ansj.app.crf.pojo.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.VectorProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by hzwangjian1 on 2017/8/17.
 */
public class TestCallable {

    private static Logger logger = LoggerFactory.getLogger(TestCallable.class);

    public void test() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

        for(int i=0; i< 100; i++){
             futureList.add(fixedThreadPool.submit(new SubThread()));
        }

        try {
            // Tell threads to finish off.
            fixedThreadPool.shutdown();
            // Wait for everything to finish.
            while (!fixedThreadPool.awaitTermination(100, TimeUnit.MINUTES)) {
                logger.info("Awaiting completion of threads.");
            }
        }catch ( Exception e){
            logger.error("", e);
        }

        for(Future<Integer> future: futureList){
            try {
                logger.info( "" + future.get());
            }catch (Exception e){
                logger.error("" , e);
            }
        }
    }

    class SubThread implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {

            return new Random().nextInt();
        }
    }

    public static void main(String[] args) {
        TestCallable testCallable = new TestCallable();
        testCallable.test();
    }

}
