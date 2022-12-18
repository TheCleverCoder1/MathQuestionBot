package com.Math.Bot.Main.Utils.ImageHandler;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Debugger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Optimized Stage 3
public class ImageHandler {
    // Images
    private static Image Logo;
    private static Image AdditionSymbolTitle;
    private static Image SubtractionSymbolTitle;
    private static Image DivisionSymbolTitle;
    private static Image AlgebricXSymbolTitle;
    private static Image AlgebraSolverButton;
    private static Image[] DebugIcons;
    private static Image[] PopUpImages;
    private static Image arrow;
    private static Image[] textBox;
    private static Image[] croppedTextBox;

    // PathToRes
    public static final String PathToRes = "D:/java codes/MathQuestionBot/src/com/Math/Bot/Main/Utils/ImageHandler/Res";

    // Initializing
    public static void LoadImages(){
        // Loading Images
        {
        Logo = LoadImageAtPath(PathToRes + "/MathBotTitle.png");
        Logo = ScaleImage(Logo, (short) (Logo.getWidth(null) * 0.8), (short) (Logo.getHeight(null) * 0.8));
        } // 1. Loading Logo
        {
            AdditionSymbolTitle = LoadImageAtPath(PathToRes + "/AdditionTitle.png");

            SubtractionSymbolTitle = LoadImageAtPath(PathToRes + "/SubtractionTitle.png");

            DivisionSymbolTitle = LoadImageAtPath(PathToRes + "/DivisionTitle.png");

            AlgebricXSymbolTitle = LoadImageAtPath(PathToRes + "/xTitle.png");

            AlgebraSolverButton = LoadImageAtPath(PathToRes + "/AlgebraSolverButton.png");
        } // (2-6) Loading something related to the title
        {
            DebugIcons = new Image[Debugger.numOfDebugStates];
            String[] icons = {
                    PathToRes + "/InfoModeDebug.png",
                    PathToRes + "/EditModeDebug.png"
            };

            for (byte idx = 0; idx < Debugger.numOfDebugStates; idx++)
                DebugIcons[idx] = LoadImageAtPath(icons[idx]);

            // Scaling every image by 0.7
            for (byte idx = 0; idx < DebugIcons.length; idx++)
                DebugIcons[idx] = ScaleImage(DebugIcons[idx], (short) (DebugIcons[idx].getWidth(null) * 0.7f), (short) (DebugIcons[idx].getHeight(null) * 0.7f));
        } // 7. Loading DebugImages
        {
            arrow = LoadImageAtPath(PathToRes + "/arrow.png");
            arrow = ScaleImage(arrow, (short) (arrow.getWidth(null) * 0.3), (short) (arrow.getHeight(null) * 0.3));
        } // 8. Get Arrow Keys
        {
            PopUpImages = new Image[2];
            Image popUpImg = LoadImageAtPath(PathToRes + "/PopUpButton.png");
            short height = (short) (popUpImg.getHeight(null) / PopUpImages.length);
            for (byte idx = 0; idx < PopUpImages.length; idx++)
                PopUpImages[idx] = SubImage(popUpImg, (short) 0, (short) (idx * height), (short) popUpImg.getWidth(null), height);
        } // 9. Get PopUpImages
        {
            textBox =croppedTextBox = new Image[2];
            Image img = LoadImageAtPath(PathToRes + "/typer.png");
            textBox[0] = SubImage(img, (short) 0, (short) 0, (short) img.getWidth(null), (short) (img.getHeight(null)/2));
            textBox[1] = SubImage(img, (short) 0, (short) (img.getHeight(null)/2), (short) img.getWidth(null), (short) (img.getHeight(null)/2));
        } // 10. Get ScreenTextBox Images i.e. the place where we type in.
    }

    // Image Formatting Functions
    public static Image LoadImageAtPath(String path){
        try {
            return ImageIO.read(new File(path));
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("The error above is for an image at path \"" + path + "\"");
            return null;
        }
    }
    public static Image ScaleImage(Image img, short width, short height){
        return img == null ? null : img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    public static Image SubImage(Image img, short x, short y, short width, short height){
        return ((BufferedImage) img).getSubimage(x, y, width, height);
    }

    // Get Functions
    public static Image getLogo(){return Logo;}
    public static Image getAdditionSymbolTitle(){return AdditionSymbolTitle;}
    public static Image getSubtractionSymbolTitle(){return SubtractionSymbolTitle;}
    public static Image getDivisionSymbolTitle(){return DivisionSymbolTitle;}
    public static Image getAlgebricXSymbolTitle(){return AlgebricXSymbolTitle;}
    public static Image getAlgebraSolverButton(){return AlgebraSolverButton;}
    public static Image[] getDebugIcons(){return DebugIcons;}
    public static Image[] getPopUpImage(){return PopUpImages;}
    public static Image getArrow(){return arrow;}
    public static Image[] getTextBox(){return textBox;}
    public static Image[] getCroppedTextBox(){return croppedTextBox;}

    // Get Image by Tag
    public static Image getImageByTagOrName(String tag){
        switch (tag.toLowerCase()){
            case "logo", "title" -> {
                return getLogo();
            }

            case "algebrabutton", "algebrasolver" -> {
                return getAlgebraSolverButton();
            }

            case "additiontitle", "floatingaddtion" -> {
                return getAdditionSymbolTitle();
            }

            case "subtractiontitle", "floatingsubtraction" -> {
                return getSubtractionSymbolTitle();
            }

            case "divisiontitle", "floatingdivision" -> {
                return getDivisionSymbolTitle();
            }

            case "xtitle", "floatingx" -> {
                return getAlgebricXSymbolTitle();
            }

            default -> throw new IllegalArgumentException("Tag \"" + tag + "\" has no Image attached");
        }
    }
}
