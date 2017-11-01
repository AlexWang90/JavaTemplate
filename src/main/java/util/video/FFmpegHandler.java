package util.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by hzwangjian1 on 2017/11/1.
 */
public class FFmpegHandler {

    private static Logger logger = LoggerFactory.getLogger(FFmpegHandler.class);

    /**
     * ffprobe获取视频信息
     * @param videoUrl
     * @return
     */
    public static String ffprobe(String videoUrl){
        String command = String.format("ffprobe -v quiet -show_format -show_streams -print_format json %s", videoUrl);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = reader.readLine())!= null){
               stringBuilder.append(line);
            }

            process.waitFor();
            reader.close();
            process.destroy();
        }catch (Exception e){
            logger.error("",e);
        }
        return stringBuilder.toString();
    }

    /**
     * 提取视频关键帧
     * @param videoUrl  input video file name or url
     * @param saveDir  output images file name with c print int format, like 'pics/thumbnails-%03d.jpeg'
     * @return
     */
    public static boolean ffmpeg_keyframe(String videoUrl, String saveDir){
        String command  = String.format("ffmpeg -hide_banner -nostats -v quiet -skip_frame nokey -vsync 0 -t 100 -i  %s -f image2  %s", videoUrl, saveDir);
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            process.waitFor();
            process.destroy();
            return true;
        }catch (Exception e){
            logger.error("",e);
        }
        return false;
    }

    /**
     * 视频帧提取
     * @param videoUrl  input video file name or url
     * @param saveDir  output images file name with c print int format, like 'pics/thumbnails-%03d.jpeg'
     * @return
     */
    public static boolean ffmpeg_frames(String videoUrl, String saveDir){
        String command  = String.format("ffmpeg  -i  %s  %s", videoUrl, saveDir);
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            process.waitFor();
            process.destroy();
            return true;
        }catch (Exception e){
            logger.error("",e);
        }
        return false;
    }

    /**
     * 视频合成
     * @param imageRoot input images file name with c print int format, like 'pics/thumbnails-%03d.jpeg'
     * @param videoPath output video file path
     * @return
     */
    public static boolean ffmpeg_compose_video(String imageRoot, String videoPath){
        String command = String.format("ffmpeg -r 25 -i %s -vb 20M %s", imageRoot, videoPath);
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
            process.waitFor();
            process.destroy();
            return true;
        }catch (Exception e){
            logger.error("",e);
        }
        return false;
    }
}
