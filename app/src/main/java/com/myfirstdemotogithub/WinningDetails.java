package com.myfirstdemotogithub;
import java.util.Arrays;

public class WinningDetails {
    private int[] selectedNumbers; // 选出的号码
    private int[] winningNumbers; // 开奖号码

    public static void main(String[] args) {
        int[] selectedNumbers = {1, 2, 3, 4, 5, 6}; // 选出的号码
        int[] winningNumbers = {3, 5, 11, 16, 24, 33}; // 开奖号码

        WinningDetails lottery = new WinningDetails(selectedNumbers, winningNumbers);
        int matchingCount = lottery.countMatchingNumbers();
        System.out.println("中奖号码中和选出号码相同的个数为：" + matchingCount);
    }


    public WinningDetails(int[] selectedNumbers, int[] winningNumbers) {
        this.selectedNumbers = selectedNumbers;
        this.winningNumbers = winningNumbers;
    }

    public int countMatchingNumbers() {
        // 对选出的号码和开奖号码进行比对，计算相同号码的个数
        int count = 0;
        for (int num : selectedNumbers) {
            if (containsNumber(winningNumbers, num)) {
                count++;
            }
        }
        return count;
    }

    private boolean containsNumber(int[] arr, int target) {
        for (int num : arr) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }


}
