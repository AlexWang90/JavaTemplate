package algorithm_test.sift;

import com.alibaba.simpleimage.analyze.sift.scale.KDFeaturePoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzwangjian1 on 2017/8/15.
 */
public class SiftFeature {
    private String url = "";
    private List<KDFeaturePoint> siftFea = new ArrayList<KDFeaturePoint>();
    private Map<Integer, Integer> kmeansFea = new HashMap<Integer, Integer>(); // 相同视觉特征个数>=3需要sift匹配
    private Map<Integer, Integer> softKmeansFea = new HashMap<Integer, Integer>();// 相同视觉特征个数>=30需要sift匹配

    public SiftFeature() {
    }

    public SiftFeature(String url, List<KDFeaturePoint> siftFea, Map<Integer, Integer> kmeansFea, Map<Integer, Integer> softKmeansFea) {
        this.url = url;
        this.siftFea = siftFea;
        this.kmeansFea = kmeansFea;
        this.softKmeansFea = softKmeansFea;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<KDFeaturePoint> getSiftFea() {
        return siftFea;
    }

    public void setSiftFea(List<KDFeaturePoint> siftFea) {
        this.siftFea = siftFea;
    }

    public Map<Integer, Integer> getKmeansFea() {
        return kmeansFea;
    }

    public void setKmeansFea(Map<Integer, Integer> kmeansFea) {
        this.kmeansFea = kmeansFea;
    }

    public Map<Integer, Integer> getSoftKmeansFea() {
        return softKmeansFea;
    }

    public void setSoftKmeansFea(Map<Integer, Integer> softKmeansFea) {
        this.softKmeansFea = softKmeansFea;
    }
}
