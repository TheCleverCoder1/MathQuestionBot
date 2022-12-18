package com.Math.Bot.Main.Utils.Color;

import java.awt.Color;

// Optimized Stage 3
public class ColorManager {
    public static Color getColorByTagName(String tag){
        switch (tag.toLowerCase()){
            case "red" -> { return Color.RED; }
            case "yellow" -> { return Color.YELLOW; }
            case "blue" -> { return Color.BLUE; }
            case "orange" -> { return Color.ORANGE; }
            case "grey", "gray" -> { return Color.GRAY; }
            case "green" -> { return Color.GREEN; }

            default -> {
                return Color.BLACK;
            }
        }
    }
}
