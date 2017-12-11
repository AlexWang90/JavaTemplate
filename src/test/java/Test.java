
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.VectorProcess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by hzwangjian1 on 2017/5/19.
 */
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);
    public static void geneHashCode() throws Exception{
//        BufferedImage image = ImageIO.read(new File("E://temp/水印检测/竞品水印/凤凰网2.png"));
        int hashCode = "奇趣5.jpg".hashCode();
        System.out.println("20170823" + hashCode + ".jpg");
    }

    public static void docidSelect(){
        try {
            Map<String, Integer> categoryCount = new HashMap<String, Integer>();
            BufferedReader reader = new BufferedReader(new FileReader("E://temp/docduplicate/image/video_article_since_2017-08-10"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("E://temp/docduplicate/image/video_article_part"));
            String line = null;
            while((line = reader.readLine()) != null){
                String[]substr = line.split("\t");
                if(substr.length < 2) continue;
                String docid = substr[0].trim();
                String category = substr[1].trim();
                String rootCategory = category.split("/")[0];

                if(categoryCount.containsKey(rootCategory)){
                    if(categoryCount.get(rootCategory) > 30){
                        continue;
                    }
                    categoryCount.put(rootCategory, categoryCount.get(rootCategory) + 1);
                }else{
                    categoryCount.put(rootCategory, 1);
                }
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();

        }catch ( Exception e){
            logger.error("", e);
        }
    }

    public static void testComparator(){
        List<Integer> integerList = new ArrayList<Integer>(){{
            add(3);
            add(7);
            add(3);
            add(8);
            add(6);
            add(2);
            add(7);
        }};
        Collections.sort(integerList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1 < o2){
                    return -1;
                }else if(o1 > o2){
                    return 1;
                }
                return 0;
            }
        });
        logger.info(VectorProcess.join(integerList, ","));
    }

    public static void test(){
        logger.info(String.format("%d is test", 4));
    }

    public static void main(String[] args) throws  Exception{
//        System.out.println("dsfds");
        geneHashCode();
//        docidSelect();
        test();

        testComparator();
    }
}
