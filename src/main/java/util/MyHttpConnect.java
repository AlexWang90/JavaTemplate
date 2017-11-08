package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwangjian1 on 2016/10/23.
 */
public class MyHttpConnect {

    private static Logger logger = LoggerFactory.getLogger(MyHttpConnect.class);
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
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);

            long timeStart = System.currentTimeMillis();

            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            long endTime = System.currentTimeMillis();
            long costTime = endTime - timeStart;
//            System.out.println("get image feature cost time:"  + costTime);
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPOSTRequest(String requestUrl, Map<String, Object> requestParams) throws IOException {
        StringBuilder result =new StringBuilder();
        BufferedReader in =null;
        InputStream inputStream= null;

        //1.请求nginx主机
        inputStream = createPostRequest(requestUrl, requestParams, "POST");

        //定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
    private static InputStream createPostRequest(String url, Map<String, Object> requestParams, String requestMethod){
        OutputStream out =null;
        URLConnection connection = null;
        try {
            URL realUrl = new URL(url);
            if (url.toLowerCase().startsWith("https")) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) realUrl.openConnection();

                httpsConn.setHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                connection = httpsConn;
            } else {
                connection = realUrl.openConnection();
            }

            StringBuffer strBuf = new StringBuffer();
            if (requestMethod.equals("POST")) {
                ((HttpURLConnection) connection).setRequestMethod("POST");
                // 设置链接主机超时时间
                connection.setConnectTimeout(3000);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                out = new DataOutputStream(connection.getOutputStream());
                if (requestParams != null && !requestParams.isEmpty()) {
                    for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
                        strBuf.append(entry.getKey()).append("=")
                                .append(URLEncoder.encode((String) entry.getValue(), "UTF-8")).append("&");
                    }
                }
                strBuf.deleteCharAt(strBuf.length() - 1);
                // 获得上传信息的字节大小及长度
                byte[] mydata = strBuf.toString().getBytes();

                out.write(mydata);
                out.flush();
                out.close();
            }
            return connection.getInputStream();
        }catch (IOException e) {
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    public static void main(String[] args) throws Exception {

        MyHttpConnect myHttpConnect = new MyHttpConnect();
        String url = "http://dmr.nosdn.127.net/inews-20161015-7dddb87bbb1675a33edc7b2dc67324d9.jpg";
        String savePath = "E://workspace/data/hotnesspredict/temp/imgs";
        myHttpConnect.downloadImage(url, "1.jpg", savePath);
    }
}
