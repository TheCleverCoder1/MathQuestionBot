package com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.Color.ColorManager;
import com.Math.Bot.Main.Utils.FontHandler.FontHandler;
import com.Math.Bot.Main.Utils.TextHandler.TextHandler;

import java.awt.*;

// Optimized Stage 3
public class ScreenTextBoxMaker {
    private static ScreenTextBox makeScreenTextBox(String[] info){
        if (info.length != TextHandler.lengthOfScreenTextBoxInformation)
            throw new IllegalArgumentException("The length of the button information can't be" +
                    "lower or greater than " + TextHandler.lengthOfScreenTextBoxInformation);

        short[] pos = {
                Short.parseShort(info[0]), Short.parseShort(info[1])
        };
        float fontSize = Float.parseFloat(info[2]);
        Font font = FontHandler.getFontByTag(info[3]);
        byte displayMode = Byte.parseByte(info[5]);
        Color color = ColorManager.getColorByTagName(info[6]);
        boolean isPassword = Boolean.parseBoolean(info[7]);

        return new ScreenTextBox(BotPanel.bp, pos, fontSize, font, displayMode, color, isPassword);
    }
    public static ScreenTextBox[] getScreenTextBoxesForState(byte state){
        String[][] textBoxes = TextHandler.getScreenTextBoxOfState(state);
        ScreenTextBox[] textBoxesInState = new ScreenTextBox[textBoxes.length];
        for (byte idx = 0; idx < textBoxes.length; idx++) {
            textBoxesInState[idx] = makeScreenTextBox(textBoxes[idx]);
        }
        return textBoxesInState;
    }
}
