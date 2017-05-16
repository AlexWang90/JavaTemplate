package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwangjian1 on 2016/10/17.
 */
public class LevenshteinDistance {

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= lhs.length(); i++)
            for (int j = 1; j <= rhs.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

        return distance[lhs.length()][rhs.length()];
    }

    public static int levenshteinDistance(List<String> firstWordList, List<String> secondWordList) {
        int firstLen = firstWordList.size();
        int secondLen = secondWordList.size();
        int[][] distance = new int[firstLen + 1][secondLen + 1];

        for (int i = 0; i <= firstLen; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= secondLen; j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= firstLen; i++) {
            for (int j = 1; j <= secondLen; j++) {
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + (firstWordList.get(i - 1) == secondWordList.get(j - 1) ? 0 : 1));
            }
        }
        return distance[firstLen][secondLen];
    }

    public static void main(String[] args) {
        List<String> wordList1 = new ArrayList<String>();
        wordList1.add("ab");
        wordList1.add("df");
        wordList1.add("dsf");
        wordList1.add("hgf");
        wordList1.add("ed");
        wordList1.add("gs");
        List<String> wordList2 = new ArrayList<String>();
        wordList2.add("df");
        wordList2.add("hgf");
        wordList2.add("ed");
        wordList2.add("gs");
        wordList2.add("ab");

        System.out.println(levenshteinDistance(wordList1, wordList2));
    }
}
