import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on:  Jul 03, 2020
 * Questions: https://leetcode.com/problems/prison-cells-after-n-days/
 */
public class PrisonCellsAfterNDays {
    public static void main(String[] args) {
        System.out.println("*********************Ugly Number*************** Solution 1 *****************************");
        System.out.println(Arrays.toString(prisonAfterNDays(new int[]{0, 1, 0, 1, 1, 0, 0, 1}, 7)) + " should be [0,0,1,1,0,0,0,0].");
        System.out.println(Arrays.toString(prisonAfterNDays(new int[]{1, 0, 0, 1, 0, 0, 1, 0}, 1000000000)) + " should be [0,0,1,1,1,1,1,0].");

        System.out.println("************************************ Solution 2 *****************************");
//        System.out.println(Arrays.toString(prisonAfterNDays_rev1(new int[]{0, 1, 0, 1, 1, 0, 0, 1}, 7)) + " should be [0,0,1,1,0,0,0,0].");
        System.out.println(Arrays.toString(prisonAfterNDays_rev1(new int[]{1, 0, 0, 1, 0, 0, 1, 0}, 1000000000)) + " should be [0,0,1,1,1,1,1,0].");
    }

    public static int[] prisonAfterNDays_rev1(int[] cells, int N) {
        Map<String, Integer> set = new HashMap<>();
        int len = cells.length;
        while (N > 0) {
            set.put(Arrays.toString(cells), N--);
            int[] temp = new int[len];
            for (int i = 1; i < len - 1; i++) {
                temp[i] = cells[i - 1] ^ cells[i + 1];
            }
            N--;
            String tempStr = Arrays.toString(temp);
            if (set.containsKey(tempStr)) {
                System.out.println("Found after : " + set.size() + " at " + N + " new n = " + N % set.size());
                N %= (set.get(tempStr) - N);
            }
            cells = temp;
        }
        return cells;
    }

    public static int[] prisonAfterNDays(int[] cells, int N) {
        Map<String, Integer> map = new HashMap<>();
        while (N > 0) {
            map.put(Arrays.toString(cells), N--);
            int[] temp = new int[8];
            for (int i = 1; i < 7; i++) {
                temp[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            }
            String tempS = Arrays.toString(temp);
            if (map.containsKey(tempS)) {
                System.out.println("Found : " + tempS + " at old n =" + N + " new n = " + N % (map.get(tempS) - N) + " found at: " + map.get(tempS));
                N %= map.get(tempS) - N;
            }
            cells = temp;
        }
        return cells;
    }
}
