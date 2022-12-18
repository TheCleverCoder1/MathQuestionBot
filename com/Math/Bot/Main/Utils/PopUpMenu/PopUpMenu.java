package com.Math.Bot.Main.Utils.PopUpMenu;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Debugger;
import com.Math.Bot.Main.Utils.Editable.Button.Button;
import com.Math.Bot.Main.Utils.Editable.Button.ButtonMaker;
import com.Math.Bot.Main.Utils.FontHandler.FontHandler;
import com.Math.Bot.Main.Utils.Function.ButtonFunction;
import com.Math.Bot.Main.Utils.ImageHandler.ImageHandler;
import com.Math.Bot.Main.Utils.Sprite.Sprite;
import com.Math.Bot.Main.Utils.Util;

import java.awt.*;
import java.util.Arrays;

// Optimized Stage 2
public class PopUpMenu {
    private BotPanel bp;

    // Position and some raw stuff
    public short x = 0;
    public short y = 0;
    public boolean show = false;
    public Button[][] buttons = new Button[1][];
    public Rectangle rect = null;
    private short heightOfButton = 0;
    private short widthOfButton = 0;

    // Menus stuff
    public boolean subMenu = true;
    public byte subMenuIdx = 0;
    public Rectangle subMenuRect = null;
    public boolean updated = false;
    public boolean create = false;
    public byte createIdx = -1;

    // Constructor
    public PopUpMenu() {
        this.bp = BotPanel.bp;
        init();
    }

    // Initializing
    private void init(){
        Image[] popUpImages;
        {
            popUpImages = ImageHandler.getPopUpImage();
            heightOfButton = (short) popUpImages[0].getHeight(null);
            widthOfButton = (short) popUpImages[0].getWidth(null);

            buttons = new Button[1][];
            // The [0][0] one would be that button only but
            // after that [0][1] would be the first button of the menu
            buttons[0] = new Button[2];
        } // Loading some stuff
        {
            // The first button "CREATE NEW ITEM"
            buttons[0][0] = new Button(bp, new ButtonFunction() {
                @Override
                public void onClick() {
                }

                @Override
                public void onHover() {
                    subMenu = true;
                    subMenuIdx = 0;
                }

                @Override
                public void onExit() {

                }
            },
                    new short[]{-1000, -1000}, popUpImages[0], new String[]{}, ButtonMaker.RECTANGLE);
        } // Creating button in buttons[0][0]
        {
            buttons[0][1] = new Button(bp, new ButtonFunction() {
                @Override
                public void onClick() {
                    if (subMenu && subMenuRect != null && !create) {
                        create = true;
                        createIdx = Debugger.BUTTON;
                    }
                    bp.mh.resetPopUpMenu();
                    bp.mh.resetPopUpSubMenu();
                }

                @Override
                public void onHover() {
                }

                @Override
                public void onExit() {

                }
            }, new short[]{-1000, -1000}, popUpImages[1], new String[]{}, ButtonMaker.RECTANGLE);
        } // Creating button in buttons[0][1]
        makeRectangle();
    }

    // Updates
    public void makeRectangle(){
        rect = new Rectangle(x - 1, y - 1, widthOfButton + 2, (buttons.length * heightOfButton) + 2);
    }
    public boolean isNotCollidingWithRectangle(Point p){
        if (rect != null) {
            if (subMenuRect != null)
                return !rect.contains(p) && !subMenuRect.contains(p);
            else return !rect.contains(p);
        }
        return false;
    }
    public void update(){
        updateSubMenuRect();
    }
    private void updateButtonRects(){
        for (Button[] buttons : this.buttons)
            for (Button button : buttons) if (button != null) button.updateRect();
        updated = true;
    }
    private void updateSubMenuRect(){
        if (subMenu && !(subMenuIdx >= buttons.length)){
            if (subMenuIdx == 0){
                subMenuRect = new Rectangle(rect.x + rect.width, y - 1, widthOfButton + 2, ((buttons[subMenuIdx].length - 1) * heightOfButton) + 2);
            }
        }
    }
    private void updateButtonPositions(){
        // Updating Positions
        for (short idx = 0, y = this.y; idx < buttons.length; idx++, y += heightOfButton){
            buttons[idx][0].pos = new short[]{this.x, y};
        }

        if (subMenu){
            for (short idx = 1, y = this.y; idx < buttons[subMenuIdx].length; idx++, y += heightOfButton){
                buttons[subMenuIdx][idx].pos = new short[]{(short) (this.x + widthOfButton + 3), y};
            }
        }
    }

    // Drawing Stuff
    public void draw(){
        if (!show) return;
        updateButtonRects();
        updateButtonPositions();
        drawBorder(subMenu);
        drawButtons();
    }
    private void drawButtons(){
        // Drawing Buttons
        for (byte i = 0; i < buttons.length; i++){
            if (i == subMenuIdx && subMenu){
                for (Button button : buttons[i])
                    button.draw();
            }
            else{
                buttons[i][0].draw();
            }
        }
    }
    private void drawBorder(boolean isSubMenu){
        Graphics2D g = Sprite.g;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);
        g.drawRect(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2);
        if (!isSubMenu){
            return;
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(subMenuRect.x, subMenuRect.y, subMenuRect.width, subMenuRect.height);
        g.setColor(Color.BLACK);
        g.drawRect(subMenuRect.x - 1, subMenuRect.y - 1, subMenuRect.width + 2, subMenuRect.height + 2);
    }
    public void drawInfo(){
        if (!bp.debug) return;
        Graphics2D g = Sprite.g;
        {
            g.setColor(Color.BLACK);
            g.setFont(FontHandler.getFonts()[0].deriveFont(15f));
        } // Initialize something
        {
            g.drawString("  POP UP MENU   ", 10, 100);
            g.drawString("Show: " + Util.boolToString(show), 10, 130);
            g.drawString("Pos: " + Arrays.toString(new short[]{x, y}), 10, 160);
            g.drawString("SubMenu: " + Util.boolToString(subMenu), 10, 190);
            g.drawString("SubMenuIdx: " + subMenuIdx, 10, 220);
        } // Show pop up menu
        {
            g.drawString("   CREATE   ", 10, 260);
            g.drawString("CREATE: " + Util.boolToString(create), 10, 290);
            g.drawString("CREATE INDEX: " + bp.getEditableString(createIdx), 10, 320);
        } // Showing Create Idx
    }
}
