package util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwangjian1 on 2016/10/23.
 */
public class ImageDownload {

    /**
     * 根据路径 下载图片 然后 保存到对应的目录下
     *
     * @param urlString
     * @param filename
     * @param savePath
     * @return
     * @throws Exception
     */
    public static void downloadImage(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求的路径
        con.setConnectTimeout(5 * 1000);
        // 超时时间
        con.setReadTimeout(60 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "/" + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    /**
     * 根据路径 下载图片 然后 保存到对应的目录下
     *
     * @param urlString
     * @param filename
     * @return
     * @throws Exception
     */
    public static void downloadImage(String urlString, String filename) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求的路径
        con.setConnectTimeout(5 * 1000);
        // 超时时间
        con.setReadTimeout(60 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }


    public static void main(String[] args) throws Exception {

        ImageDownload imageDownload = new ImageDownload();
        String url = "http://dmr.nosdn.127.net/inews-20161015-7dddb87bbb1675a33edc7b2dc67324d9.jpg";
        String savePath = "E://workspace/data/hotnesspredict/temp/imgs";
        imageDownload.downloadImage(url, "1.jpg", savePath);
    }
}
