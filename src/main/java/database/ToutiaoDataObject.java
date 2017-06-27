package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwangjian1 on 2016/10/18.
 */
public class ToutiaoDataObject {

    String docid;
    String title;
    String content;
    String category;
    String img; //展示图片
    String bigImg = ""; //大图
    String publishTime;
    String insertTime;
    String source = "未知";
    boolean pics = false; //是否是图集
    List<String> picUrls = new ArrayList<String>(); //如果是图集，直接使用这个作为正文图片集合

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public boolean isPics() {
        return pics;
    }

    public void setPics(boolean pics) {
        this.pics = pics;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
