package com.uzpeng.sign.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author uzpeng on 2018/4/16.
 */
public class RandomUtil {

    public static <T>  List<T> pickAmountRandomly(List<T> list, int amount){
        List<T> copyList = new ArrayList<>(list);

        List<T> result = new ArrayList<>();

        int size = copyList.size();
        if(size == 0) {
            return result;
        }

        int index = size;
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int randomNum = random.nextInt(index);
            result.add(copyList.get(randomNum));

            copyList.remove(randomNum);
            index--;
        }

        return result;
    }
}
