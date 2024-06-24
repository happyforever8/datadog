第一轮，白人小哥，非常nice不断提示，题目是飞不同城市最大化holiday天数，比利口上的标签题简单。
但follow-up把我卡住了，要求在最大化同时最少化飞行次数，有个edge case一直过不去：
比如
w1:  [2,0,2]
w2:  [3,0,3]
w3:  [1,0,1]
w4:  [0,0,2]
三个坐标分别代表三个城市在这周的假日，所以需要从第一周开始就待在第三个城市。其实解法不难，但当时时间紧卡住了，小哥说没关系。我以为挂在这一轮。


地里汇报这题没有LC中的城市间flights约束，默认所有城市都有航班，所以比较简单。
   但是有一个follow up： 要求总flights最少。这哥么说的很简单，没有继续解释。所以我只能猜测他说的是指还是要求vacation days最多，
   但是如果有两个方案vacation days一样多，那就选需要flights少的那个方案，
   并返回总vacation days 和 相对应的flights counts。 以下代码基于这个假设。

   import java.util.Arrays;

class Solution {
    public int[] maxVacationDays2(int[][] days) {
        int nweek = days.length;
        int mcities = days[0].length;

        if (nweek < 1) {
            return new int[]{0, 0};
        }

        int[][] thisWeekDp = new int[mcities][2];

        for (int i = 0; i < mcities; i++) {
            int vday = days[0][i];
            if (i == 0) {
                thisWeekDp[i][0] = vday;
                thisWeekDp[i][1] = 0;
            } else {
                thisWeekDp[i][0] = vday;
                thisWeekDp[i][1] = 1;
            }
        }

        for (int i = 1; i < nweek; i++) {
            int[][] nextWeekDp = new int[mcities][2];
            for (int j = 0; j < mcities; j++) {
                nextWeekDp[j] = Arrays.copyOf(thisWeekDp[j], 2);
            }

            for (int j = 0; j < mcities; j++) {
                for (int k = 0; k < mcities; k++) {
                    if (thisWeekDp[k][0] + days[i][j] > nextWeekDp[j][0]) {
                        if (j == k) {
                            nextWeekDp[j][0] = thisWeekDp[k][0] + days[i][j];
                            nextWeekDp[j][1] = thisWeekDp[k][1];
                        } else {
                            nextWeekDp[j][0] = thisWeekDp[k][0] + days[i][j];
                            nextWeekDp[j][1] = thisWeekDp[k][1] + 1;
                        }
                    } else if (thisWeekDp[k][0] + days[i][j] == nextWeekDp[j][0]) {
                        if (j == k) {
                            if (thisWeekDp[k][1] < nextWeekDp[j][1]) {
                                nextWeekDp[j][1] = thisWeekDp[k][1];
                            }
                        } else {
                            if (thisWeekDp[k][1] + 1 < nextWeekDp[j][1]) {
                                nextWeekDp[j][1] = thisWeekDp[k][1] + 1;
                            }
                        }
                    }
                }
            }
            thisWeekDp = nextWeekDp;
        }

        int[] best = new int[]{0, 0};
        for (int[] choice : thisWeekDp) {
            if (choice[0] > best[0]) {
                best = choice;
            } else if (choice[0] == best[0]) {
                if (choice[1] < best[1]) {
                    best = choice;
                }
            }
        }
        return best;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] days = {
                {1, 3, 1},
                {6, 0, 3},
                {3, 3, 3}
        };
        int[] result = sol.maxVacationDays2(days);
        System.out.println("Max Vacation Days: " + result[0]);
        System.out.println("Min Flights: " + result[1]);
    }
}
