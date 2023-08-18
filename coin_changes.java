import java.util.*;

public class coinChange {

    public static void main(String[] args){

        int[] coins = {1, 2, 5, 25};
        System.out.println(coinChange(coins, 37));
    }

    //在编程表示中，我们发现 DP 数组中的值最大也只能是 amount（只有 1 元硬币的情况，硬币数量等于金额数），
    //   PS：为啥 dp 数组初始化为 amount + 1 呢，
    //   因为凑成 amount 金额的数最多只可能等于 amount（全用 1 元面值的），
    //   所以初始化为 amount + 1 就相当于初始化为正无穷，便于后续取最小值。


        //we have to compute all minimum counts for amounts up to i.
        //dp[n]的值： 表示的凑成总金额为n所需的最少的硬币个数
        public static int coinChange(int[] coins, int amount) {
            int[] dp = new int[amount + 1];
            dp[0] = 0;

            for (int i = 1; i < dp.length; i++){
                dp[i] = amount + 1;
            }

            for (int i = 1; i < dp.length; i++){
                for (int coin : coins){
                    if (i >= coin){
                        // dp[i]有两种实现的方式，
                        // 一种是包含当前的coins[i],那么剩余钱就是 i-coins[i],这种操作要兑换的硬币数是 dp[i-coins[j]] + 1
                        // 另一种就是不包含，要兑换的硬币数是memo[i]

                        dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                    }
                }
            }

            // to print out coin list
            List<Integer> coinList = new ArrayList<>();
            int remainingAmount = amount;
            while (remainingAmount > 0) {
                for (int coin : coins) {
                    if (remainingAmount - coin >= 0 && dp[remainingAmount] == dp[remainingAmount - coin] + 1) {
                        coinList.add(coin);
                        remainingAmount -= coin;
                        break;
                    }
                }
            }

//            for (int coin: coinList){
//                System.out.println(coin);
//            }

            // to print out coin count for each coint
            int[] coinCount = new int[coins.length];
            int remaining = amount;
            int coinIndex = coins.length - 1;

            while (remaining > 0) {
                for (int i = coinIndex; i >= 0; i--) {
                    if (coins[i] <= remaining && dp[remaining] == dp[remaining - coins[i]] + 1) {
                        coinCount[i]++;
                        remaining -= coins[i];
                        break;
                    }
                }
            }
            for (int coin: coinCount){
                System.out.println(coin);
            }


            return dp[amount] == amount + 1 ? - 1 : dp[amount];
        }
    }

// public class Solution {
// public int coinChange(int[] coins, int amount) {
//     if(amount<1) return 0;
//     return helper(coins, amount, new int[amount]);
// }

// private int helper(int[] coins, int rem, int[] count) { /
// rem:        remaining coins after the last step;
// count[rem]: minimum number of coins to sum up to rem
//     if(rem<0) return -1; // not valid
//     if(rem==0) return 0; // completed
//     if(count[rem-1] != 0) return count[rem-1]; // already computed, so reuse
//     int min = Integer.MAX_VALUE;
//     for(int coin : coins) {
//         int res = helper(coins, rem-coin, count);
//         if(res>=0 && res < min)
//             min = 1+res;
//     }
//     count[rem-1] = (min==Integer.MAX_VALUE) ? -1 : min;
//     return count[rem-1];
// }
// }

