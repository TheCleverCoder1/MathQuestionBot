package com.Math.Bot.Main.Utils.FontHandler;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.TextHandler.TextHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// Optimized Stage 2
public class FontHandler {
    private static BotPanel bp;

    // PathToFont
    public static final String PathToFont = "D:/java codes/MathQuestionBot/src/com/Math/Bot/Main/Utils/FontHandler/Fonts";

    // Fonts
    private static Font[] fonts;

    // Initializing
    public static void init(){
        // Initializing BotPanel
        bp = TextHandler.bp;

        ArrayList<Font> fontArrayList = loadFonts();
        convertArrayListToArray(fontArrayList);
    }
    private static ArrayList<Font> loadFonts(){
        ArrayList<Font> fontArrayList = new ArrayList<>();

        // The Paths of the font
        String[] fontString = {
                PathToFont + "/Font1-Medium.ttf"
        };

        // Loading all fonts step by step
        for (String font : fontString)
            try{
                fontArrayList.add(Font.createFont(Font.TRUETYPE_FONT, new File(font)));
            }catch(FontFormatException | IOException e){e.printStackTrace();}
        return fontArrayList;
    }
    private static void convertArrayListToArray(ArrayList<Font> al){
        fonts = new Font[al.toArray().length];
        for (byte i = 0; i < al.toArray().length; i++)
            fonts[i] = (Font) al.toArray()[i];
    }

    // Get Method
    public static Font[] getFonts(){return fonts;}
    public static Font getFontByTag(String tag){
        switch (tag.toLowerCase()){
            case "medium", "medium.ttf", "font1 medium", "font1-medium", "font1 medium.ttf", "font1-medium.ttf" -> {
                return getFonts()[0];
            }
            default -> {
                return null;
            }
        }
    }

}
