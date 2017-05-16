package util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hzwangjian1 on 2016/8/14.
 */
public class MinHeapSort<E> {
    private List<E> topItems = null;
    private Double[] topScores = null;
    private int size = 0;
    LinkedHashMap<E, Double> topWords;

    /**
     * 构造函数，构造大小为size的最小堆
     *
     * @param size
     */
    public MinHeapSort(int size) {
        this.size = size;
        topItems = new ArrayList<E>(size + 1);  // 索引从1到num
        topScores = new Double[size + 1];
        topWords = new LinkedHashMap<E, Double>();
        for (int i = 0; i < size + 1; i++) {  //初始化
//            topItems.set(i, null);  //null pointer exception
            topItems.add(null);
            topScores[i] = Double.MIN_VALUE;
        }
    }

    /**
     * 替换掉最小堆的root节点后，需要把root往下移
     *
     * @param item
     * @param score
     */
    private void shiftRootDown(E item, Double score) {
        int i = 1;
        int half = size / 2;
        while (i <= half) {
            int child = 2 * i;
            int right = child + 1;
            if (right <= size && topScores[child] > topScores[right]) {
                child = right;
            }

            if (score < topScores[child]) {
                break;
            }

            topItems.set(i, topItems.get(child));
            topScores[i] = topScores[child];
            i = child;
        }

        topItems.set(i, item);
        topScores[i] = score;
    }

    /**
     * 把下一个项和最小堆的root做对比，如果大于root则替换
     *
     * @param item
     * @param score
     */
    public void tryAddItem(E item, Double score) {
        if (score > topScores[1]) { // root小于当前项，替换掉root
            topItems.set(1, item);
            topScores[1] = score;
            shiftRootDown(item, score);
        }
    }

    /**
     * 依次从最小堆堆顶删除root，保存到LinkedHashMap中
     */
    public void sort() {
        E tempStr = null;
        Double tempDouble = null;
        int num = size;
        for (int i = 0; i < num; i++) { // 依次从最小堆堆顶删除root
            tempStr = topItems.get(1);
            tempDouble = topScores[1];

            topItems.set(1, topItems.get(size));
            topScores[1] = topScores[size];

            topItems.set(size, tempStr);
            topScores[size] = tempDouble;
            size--;

            shiftRootDown(topItems.get(1), topScores[1]);
        }

        for (int i = 0; i < num; i++) {

            topWords.put(topItems.get(i + 1), topScores[i + 1]);
        }
    }

    /**
     * 获取LinkedHashMap
     *
     * @return
     */
    public LinkedHashMap<E, Double> getTopWords() {
        return topWords;
    }

    public void printResult(){
        String str = "";
        for(E key: topWords.keySet()){
            str += topWords.get(key) + "\t";
        }
        System.out.println(str);
    }
}
