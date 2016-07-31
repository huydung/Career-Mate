package com.careermate.CORE_LIBS.HELPER;

import java.util.Random;

/**
 * Created by TuAnh on 6/16/2016.
 */
public class RandomUtils {
    public static final Random r= new Random();
    public static int nextInt(int k){
        return r.nextInt(k);
    }
    public static boolean nextBoolean(){
        return r.nextBoolean();
    }
}
