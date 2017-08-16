package test.java;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by hzwangjian1 on 2017/5/19.
 */
public class Test {

    public static void geneHashCode() throws Exception{
//        BufferedImage image = ImageIO.read(new File("E://temp/水印检测/竞品水印/凤凰网2.png"));
        int hashCode = "头条号15.jpg".hashCode();
        System.out.println("20170815" + hashCode + ".jpg");
    }

    public static void main(String[] args) throws  Exception{
//        System.out.println("dsfds");
        geneHashCode();
    }
}
