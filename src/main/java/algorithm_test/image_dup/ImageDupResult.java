package algorithm_test.image_dup;

/**
 * Created by hzwangjian1 on 2018/3/8.
 */
public class ImageDupResult {

    private String imageUrlOne;
    private String imageUrlTwo;

    private String message;
    private int[] siftmatches;
    private double phogDistance;
    private double ceddSimilarity;

    private boolean siftSimilarity = false;
    private boolean siftDuplicate = false;
    private boolean ceddphogDuplicate = false;

    public ImageDupResult() {
    }

    public ImageDupResult(String imageUrlOne, String imageUrlTwo) {
        this.imageUrlOne = imageUrlOne;
        this.imageUrlTwo = imageUrlTwo;
    }

    public String getImageUrlOne() {
        return imageUrlOne;
    }

    public void setImageUrlOne(String imageUrlOne) {
        this.imageUrlOne = imageUrlOne;
    }

    public String getImageUrlTwo() {
        return imageUrlTwo;
    }

    public void setImageUrlTwo(String imageUrlTwo) {
        this.imageUrlTwo = imageUrlTwo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int[] getSiftmatches() {
        return siftmatches;
    }

    public void setSiftmatches(int[] siftmatches) {
        this.siftmatches = siftmatches;
    }

    public double getPhogDistance() {
        return phogDistance;
    }

    public boolean isSiftSimilarity() {
        return siftSimilarity;
    }

    public void setSiftSimilarity(boolean siftSimilarity) {
        this.siftSimilarity = siftSimilarity;
    }

    public boolean isSiftDuplicate() {
        return siftDuplicate;
    }

    public void setSiftDuplicate(boolean siftDuplicate) {
        this.siftDuplicate = siftDuplicate;
    }

    public boolean isCeddphogDuplicate() {
        return ceddphogDuplicate;
    }

    public void setCeddphogDuplicate(boolean ceddphogDuplicate) {
        this.ceddphogDuplicate = ceddphogDuplicate;
    }

    public void setPhogDistance(double phogDistance) {
        this.phogDistance = phogDistance;
    }

    public double getCeddSimilarity() {
        return ceddSimilarity;
    }

    public void setCeddSimilarity(double ceddSimilarity) {
        this.ceddSimilarity = ceddSimilarity;
    }
}
