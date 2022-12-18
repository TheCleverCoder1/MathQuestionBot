package com.Math.Bot.Main.Screens;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.Editable.Button.Button;
import com.Math.Bot.Main.Utils.Editable.Button.ButtonMaker;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox.ScreenTextBox;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox.ScreenTextBoxMaker;
import com.Math.Bot.Main.Utils.ImageHandler.ImageHandler;
import com.Math.Bot.Main.Utils.Sprite.Sprite;

import java.awt.*;

// Optimized Stage 1
public class HomeMenu implements Menu {
    // BotPanel
    private BotPanel bp;

    // Images
    private Image Logo;

    // Positions
    private short[] LogoPos;

    // Editables
    Button[] buttons;
    ScreenTextBox[] textBoxes;

    public HomeMenu(){
        this.bp = BotPanel.bp;
        init();
    }

    // Initializing
    private void init(){
        loadImages();
        loadConfig();
        loadPositions();
        loadEditables();
    }
    private void loadImages(){
        Logo = ImageHandler.getLogo();
    }
    private void loadPositions(){
        LogoPos = new short[]{
                (short) ((bp.sWidth/2) - (Logo.getWidth(null)/2)),
                30
        };
    }
    private void loadEditables() {
        buttons = ButtonMaker.getButtonsForState(bp.MENU_STATE);
        textBoxes = ScreenTextBoxMaker.getScreenTextBoxesForState(bp.MENU_STATE);
    }
    private void loadConfig(){

    }

    // Update
    @Override public void update() {
    }

    // Draw
    @Override public void draw() {
        // Drawing Logo
        Sprite.g.drawImage(Logo, LogoPos[0], LogoPos[1], null);
        for (Button button : buttons)
            button.draw();
        for (ScreenTextBox stb : textBoxes)
            stb.draw();

    }

    @Override public Button[] getButtons() {
        return buttons;
    }

    @Override public ScreenTextBox[] getTextWriters() {
        return textBoxes;
    }
}
