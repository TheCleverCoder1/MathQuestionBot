package com.Math.Bot.Main;

import com.Math.Bot.Main.Utils.Editable.Button.Button;
import com.Math.Bot.Main.Utils.Editable.EditableHandler;
import com.Math.Bot.Main.Utils.FontHandler.FontHandler;
import com.Math.Bot.Main.Utils.ImageHandler.ImageHandler;
import com.Math.Bot.Main.Utils.Input.KeyHandler;
import com.Math.Bot.Main.Utils.PopUpMenu.PopUpMenu;
import com.Math.Bot.Main.Utils.Sprite.Sprite;
import com.Math.Bot.Main.Utils.Util;

import java.awt.*;

// Optimized Stage 3
public class Debugger {
    private PopUpMenu popUpMenu;
    private short[][] posOfDebugIcons;
    private short widthOfDebugIcon;

    // Getters and Setters
    public byte getDebugState() {
        return cDebugState;
    }
    public void setDebugState(byte cDebugState) {
        this.cDebugState = cDebugState;
    }
    public short getWidthOfDebugIcon() {
        return widthOfDebugIcon;
    }
    public short[][] getPosOfDebugIcons() {
        return posOfDebugIcons;
    }
    public PopUpMenu getPopUpMenu() {
        return popUpMenu;
    }

    // Debug States
    private byte cDebugState;
    public static final byte numOfDebugStates = 2;
    public static final byte INFO_DEBUG_STATE = 0;
    public static final byte EDIT_DEBUG_STATE = 1;

    // Debug Items
    public static final byte BUTTON = 0;
    public static final byte TEXT_BOX = 1;

    // BotPanel and Graphics2D and KeyHandler
    private final BotPanel bp = BotPanel.bp;
    private Graphics2D g = Sprite.g;
    private final KeyHandler kh = bp.kh;

    private Image[] debugIcons;

    public Debugger(){
        init();
    }

    private void init(){
        initializeDebugIconsPositions();
        popUpMenu = new PopUpMenu();
        cDebugState = INFO_DEBUG_STATE;
        debugIcons = ImageHandler.getDebugIcons();
    }
    private void initializeDebugIconsPositions() {
        // Calculation of width of button
        widthOfDebugIcon = (short) ImageHandler.getDebugIcons()[0].getWidth(null);

        // Calculating starting position
        final short y = (short) ((bp.sHeight/2) - (ImageHandler.getDebugIcons()[0].getHeight(null)/2));
        short distanceFromOneDebugButtonToAnother = 150;
        short firstButtonX = (short) ((bp.sWidth/2) - (((numOfDebugStates-1) * distanceFromOneDebugButtonToAnother) + (numOfDebugStates * widthOfDebugIcon))/2);

        // Calculating the gap
        short gap = (short) (widthOfDebugIcon + distanceFromOneDebugButtonToAnother);

        // Initializing the position of debug icons
        posOfDebugIcons = new short[numOfDebugStates][2];
        for (short x = firstButtonX, idx = 0; x < bp.sWidth - widthOfDebugIcon && idx < numOfDebugStates; x += gap, idx++)
            posOfDebugIcons[idx] = new short[]{x, y};
    }
    public String getDebugStateName(){
        return (cDebugState == INFO_DEBUG_STATE ? "Information" : (cDebugState == EDIT_DEBUG_STATE ? "Editing" : "NuLL"));
    }

    // Drawing Debug
    private void drawDebugMode(){
        // Drawing Debug things
        g = Sprite.g;
        g.setFont(FontHandler.getFonts()[0].deriveFont(15f));
        g.setColor(Color.BLACK);
        g.drawString("Debug: " + Util.boolToString(bp.debug), 10, 30);
        if (bp.debug)
            g.drawString("Debug Mode: " + getDebugStateName(), 10, 60);
    }
    public void draw(){
        g = Sprite.g;
        if (bp.debug){
            if (cDebugState == INFO_DEBUG_STATE) drawDebugInformation();
            popUpMenu.draw();
            if (kh.changingDebugStatesByMouseAndKeyBoard) drawDebugModeSwitchingIcons();
        }
        drawDebugMode(); // Displays whether debug is on or off and the mode it is currently on.
        popUpMenu.drawInfo();
    }
    private void drawDebugInformation() {
        for (Button button : EditableHandler.getButtons()){
            if (button != null)
                button.drawInfo();
        }
    }
    private void drawDebugIcons(){
        // Loading Debug Images
        short widthOfButton = (short) debugIcons[0].getWidth(null);

        // Drawing the icons
        for (byte idx = 0; idx < numOfDebugStates; idx++){
            g.drawImage(debugIcons[idx], posOfDebugIcons[idx][0], posOfDebugIcons[idx][1], null);
            if (kh.cDebugButtonIdx == idx)
                g.drawImage(ImageHandler.getArrow(), posOfDebugIcons[idx][0] + widthOfButton + 10, posOfDebugIcons[idx][1] + 50, null);
        }
    }
    private void drawDebugModeSwitchingIcons(){

        blurScreenWhite();
        drawDebugIcons();
    }
    private void blurScreenWhite(){
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(0, 0, bp.sWidth, bp.sHeight);
    }

    public void update(){
        if (bp.debug) popUpMenu.update();
    }
}