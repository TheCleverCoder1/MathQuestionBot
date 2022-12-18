package com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.ImageHandler.ImageHandler;
import com.Math.Bot.Main.Utils.Sprite.Sprite;
import com.Math.Bot.Main.Utils.Util;

import java.awt.*;

// Optimized Stage 3
public class ScreenTextBox extends DisplayableTextBox {
    // Constructors
    public ScreenTextBox(BotPanel bp, short[] pos, float fontSize, Font font, byte displayMode, boolean isPassword){
        super(bp, pos, fontSize, font, displayMode, Color.BLACK, isPassword);
    }
    public ScreenTextBox(BotPanel bp, short[] pos, float fontSize, Font font, byte displayMode, Color fontColor, boolean isPassword) {
        super(bp, pos, fontSize, font, displayMode, fontColor, isPassword);
    }

    // Initialization
    @Override protected void initialize() {
        textX = (short) (pos[0] + (displayableText.getFontSize() * 0.75));
        disToTextX = (short) (textX - pos[0]);
        sprite = new Sprite(pos[0], pos[1], ImageHandler.getTextBox()[Util.boolToByte(displayMode == LIGHT_MODE)]);
    }
    @Override protected void initializeUsingGraphics(){
        super.initializeUsingGraphics();
        textY = (short) (pos[1] + displayableText.getMetrics().getAscent() + (displayableText.getFontSize() * 0.01));
        size = new short[]{
                (short) (((displayableText.getFontHeight() * 8) + (displayableText.getFontSize() * 0.2))),
                (short) (displayableText.getFontHeight() + (displayableText.getFontSize() * 0.2))
        };
        textLimit = (short) (pos[0] - (displayableText.getFontSize() * 3f));
        sprite.setImg(ImageHandler.ScaleImage(sprite.getImg(), size[0], size[1]));
    }
}