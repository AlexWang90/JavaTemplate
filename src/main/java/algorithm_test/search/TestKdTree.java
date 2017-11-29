package algorithm_test.search;

import smile.neighbor.KDTree;
import smile.neighbor.LSH;

/**
 * Created by hzwangjian1 on 2017/8/4.
 */
public class TestKdTree {

    public static void testKdtree(){

        double[][]key = new double[10000][128];
        Integer [] data = new Integer[10000];

        KDTree<Integer>  kdTree = new KDTree<Integer>(key, data);
        LSH<Integer> lsh = new LSH<Integer>(key, data);
    }

    public static void main(String[] args) {

    }
}
