package org.tango.utils;

import java.util.*;

/**
 * User: tAngo
 * Date: 13-2-20
 * Time: 上午1:51
 */
public final class RandomUtils {

    /**
     * 数组打散顺序,不重复
     *
     * @param start 开始数字
     * @param end   结束数字
     * @return
     */
    public static final int[] randomNum(int start, int end) {
        int length = end - start + 1;
        int[] numbers = new int[length];
        for (int i = 0; i < length; i++) {
            numbers[i] = start + i;
        }
        Random random = new Random();
        for (int i = length; i > 0; i--) {
            int rand = random.nextInt(i);
            int temp = numbers[rand];
            numbers[rand] = numbers[length - 1];
            numbers[length - 1] = temp;
            length--;
        }
        return numbers;
    }
}
