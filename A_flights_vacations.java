第一轮，白人小哥，非常nice不断提示，题目是飞不同城市最大化holiday天数，比利口上的标签题简单。
但follow-up把我卡住了，要求在最大化同时最少化飞行次数，有个edge case一直过不去：
比如
w1:  [2,0,2]
w2:  [3,0,3]
w3:  [1,0,1]
w4:  [0,0,2]
三个坐标分别代表三个城市在这周的假日，所以需要从第一周开始就待在第三个城市。其实解法不难，但当时时间紧卡住了，小哥说没关系。我以为挂在这一轮。

外coding1 你用的是dfs吗？还是dp
   
比那个容易的题，因为这里没有flight矩阵，可以假定任意两两城市之间都有航班。
不需要dfs或者dp

   哦哦，那第一问就是纯粹greedy了，每周挑假期最多的待着
第二问确实不好想，目前我就想到dfs肯定能做，优化成dp的话目前只能想到三维dp，周，城市号，flight 次数是三个纬度，不确定能不能走通
   


(3) 题目是给你一些国家和这些国家放假的日期，假设你可以随意旅行，最大化利用每个国家的放假时间，让自己休息最长时间。
没有给定严格的输入输出，先讨论用什么数据结构，要存些什么信息，然后我先用了一个暴力解法，面试官认可。
随后要求优化，要把旅行的开销算进去，就是你要找到一个可以待最长时间不旅行，又可以最大化休息日的国家，我只讲了一下思路，没有写完代码。


*****************************************************************************************************
定义状态：
dp[i][j] 表示第 i 周在城市 j 的最大假期天数。
   
转移方程：
dp[i][j] 可以由 dp[i-1][k] + days[i][j] 转移而来，其中 k 是前一周可以选择的城市。
   
初始化：
第 0 周的假期天数直接由 days[0][j] 决定，因为没有前一周。
   
求解：
迭代每一周和每一个城市，计算最大假期天数。

   

//public class MaxVacationDays {

    public int maxVacationDays(int[][] days) {
        int n = days.length;
        int k = days[0].length;

        // dp[i][j] 表示第 i 周在城市 j 的最大假期天数
        int[][] dp = new int[n][k];

        // 初始化第一周的假期天数
        for (int j = 0; j < k; j++) {
            dp[0][j] = days[0][j];
        }

        // 动态规划计算每一周的假期天数
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                dp[i][j] = 0;
                for (int l = 0; l < k; l++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][l] + days[i][j]);
                }
            }
        }

        // 找出最后一周的最大假期天数
        int maxVacationDays = 0;
        for (int j = 0; j < k; j++) {
            maxVacationDays = Math.max(maxVacationDays, dp[n-1][j]);
        }

        return maxVacationDays;
    }

    public static void main(String[] args) {
        MaxVacationDays solution = new MaxVacationDays();
        int[][] days = {
            {2, 0, 2},
            {3, 0, 3},
            {1, 0, 1},
            {0, 0, 2}
        };
        int result = solution.maxVacationDays(days);
        System.out.println("Max Vacation Days: " + result); // Output: 7
    }
}

 ----------哦哦，那第一问就是纯粹greedy了，每周挑假期最多的待着--------------------

    public int maxVacationDays(int[][] days) {
        int n = days.length; // number of weeks
        int k = days[0].length; // number of cities

        int totalVacationDays = 0;

        // 每周选择假期最多的城市
        for (int i = 0; i < n; i++) {
            int maxDays = 0;
            for (int j = 0; j < k; j++) {
                maxDays = Math.max(maxDays, days[i][j]);
            }
            totalVacationDays += maxDays;
        }

        return totalVacationDays;
    }




*****************************************************************************************************
要在最大化假期天数的同时最小化飞行次数，这需要在动态规划的基础上增加额外的状态，以记录飞行次数。我们可以用一个新的二维数组来存储每周在每个城市的最大假期天数和最小飞行次数。

定义状态：
dp[i][j][0] 表示第 i 周在城市 j 的最大假期天数。
dp[i][j][1] 表示第 i 周在城市 j 的最小飞行次数。
   
转移方程：
如果从城市 k 到城市 j，并且 dp[i-1][k][0] + days[i][j] > dp[i][j][0]，则更新 dp[i][j][0] 和 dp[i][j][1]。
如果假期天数相同，选择飞行次数更少的方案。
   
初始化：
第 0 周的假期天数直接由 days[0][j] 决定，飞行次数为 0。
   
求解：
迭代每一周和每一个城市，计算最大假期天数和最小飞行次数。

public class MaxVacationDays {

    public int[] maxVacationDays(int[][] days) {
        int n = days.length;
        int k = days[0].length;

        // dp[i][j][0] 表示第 i 周在城市 j 的最大假期天数
        // dp[i][j][1] 表示第 i 周在城市 j 的最小飞行次数
        int[][][] dp = new int[n][k][2];

        // 初始化第一周的假期天数
        for (int j = 0; j < k; j++) {
            dp[0][j][0] = days[0][j];
            dp[0][j][1] = 0; // 第一周没有飞行
        }

        // 动态规划计算每一周的假期天数和最小飞行次数
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                dp[i][j][0] = 0;
                dp[i][j][1] = Integer.MAX_VALUE;
                for (int l = 0; l < k; l++) {
                    int newVacationDays = dp[i-1][l][0] + days[i][j];
                    int newFlights = dp[i-1][l][1] + (l == j ? 0 : 1);
                    if (newVacationDays > dp[i][j][0]) {
                        dp[i][j][0] = newVacationDays;
                        dp[i][j][1] = newFlights;
                    } else if (newVacationDays == dp[i][j][0]) {
                        if (newFlights < dp[i][j][1]) {
                            dp[i][j][1] = newFlights;
                        }
                    }
                }
            }
        }

        // 找出最后一周的最大假期天数和最小飞行次数
        int maxVacationDays = 0;
        int minFlights = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            if (dp[n-1][j][0] > maxVacationDays) {
                maxVacationDays = dp[n-1][j][0];
                minFlights = dp[n-1][j][1];
            } else if (dp[n-1][j][0] == maxVacationDays) {
                if (dp[n-1][j][1] < minFlights) {
                    minFlights = dp[n-1][j][1];
                }
            }
        }

        return new int[]{maxVacationDays, minFlights};
    }

    public static void main(String[] args) {
        MaxVacationDays solution = new MaxVacationDays();
        int[][] days = {
            {2, 0, 2},
            {3, 0, 3},
            {1, 0, 1},
            {0, 0, 2}
        };
        int[] result = solution.maxVacationDays(days);
        System.out.println("Max Vacation Days: " + result[0] + ", Min Flights: " + result[1]); // Output: [7, 2]
    }
}






========================================================================================================   
(4) LC 568

   地里汇报这题没有LC中的城市间flights约束，默认所有城市都有航班，所以比较简单。
   但是有一个follow up： 要求总flights最少。这哥么说的很简单，没有继续解释。
   所以我只能猜测他说的是指还是要求vacation days最多，但是如果有两个方案vacation days一样多
   ，那就选需要flights少的那个方案，并返回总vacation days 和 相对应的flights counts。 以下代码基于这个假设。

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    class VacationInfo {
        int vacationDays;
        int flights;

        VacationInfo(int vacationDays, int flights) {
            this.vacationDays = vacationDays;
            this.flights = flights;
        }
    }

    public VacationInfo maxVacationDays2(int[][] days) {
        int nweek = days.length;
        int mcities = days[0].length;
        
        if (nweek < 1) {
            return new VacationInfo(0, 0);
        }

        List<VacationInfo> thisWeekDp = new ArrayList<>();
        for (int i = 0; i < mcities; i++) {
            int vday = days[0][i];
            if (i == 0) {
                thisWeekDp.add(new VacationInfo(vday, 0));
            } else {
                thisWeekDp.add(new VacationInfo(vday, 1));
            }
        }

        for (int i = 1; i < nweek; i++) {
            List<VacationInfo> nextWeekDp = new ArrayList<>(thisWeekDp);
            for (int j = 0; j < mcities; j++) {
                for (int k = 0; k < mcities; k++) {
                    int newVacationDays = thisWeekDp.get(k).vacationDays + days[i][j];
                    int newFlights = j == k ? thisWeekDp.get(k).flights : thisWeekDp.get(k).flights + 1;
                    
                    if (newVacationDays > nextWeekDp.get(j).vacationDays) {
                        nextWeekDp.set(j, new VacationInfo(newVacationDays, newFlights));
                    } else if (newVacationDays == nextWeekDp.get(j).vacationDays) {
                        if (newFlights < nextWeekDp.get(j).flights) {
                            nextWeekDp.get(j).flights = newFlights;
                        }
                    }
                }
            }
            thisWeekDp = nextWeekDp;
        }

        VacationInfo best = new VacationInfo(0, 0);
        for (VacationInfo choice : thisWeekDp) {
            if (choice.vacationDays > best.vacationDays) {
                best = choice;
            } else if (choice.vacationDays == best.vacationDays) {
                if (choice.flights < best.flights) {
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
        
        VacationInfo result = sol.maxVacationDays2(days);
        System.out.println("Max Vacation Days: " + result.vacationDays + ", Min Flights: " + result.flights);
    }
}







====================================================================================================
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
