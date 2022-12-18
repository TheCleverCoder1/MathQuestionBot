package com.Math.Bot.Main.Utils;

import java.util.Arrays;

// Optimized Stage 3
public class Util {
    public static String boolToString(boolean bool){
        return (bool ? "TRUE" : "FALSE");
    }
    public static byte boolToByte(boolean bool) {return (byte) (bool ? 1 : 0);}
    public static int[] giveShortestFirst(int... intList){
        Arrays.sort(intList);
        return intList;
    }
}
