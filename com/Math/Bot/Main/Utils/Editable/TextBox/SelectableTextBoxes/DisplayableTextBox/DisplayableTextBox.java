package com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.SelectionTextBox;
import com.Math.Bot.Main.Utils.Sprite.Sprite;
import com.Math.Bot.Main.Utils.Text.Text;
import com.Math.Bot.Main.Utils.Util;

import java.awt.*;
import java.util.Arrays;

// Optimized Stage 3
public abstract class DisplayableTextBox extends SelectionTextBox {
    // Display Modes
    public static final byte LIGHT_MODE = 1;
    public static final byte DARK_MODE = 0;
    protected final byte displayMode;

    /*
     * This is a variable to store if we have just selected a word.
     * This is because so that if a person mis-clicked there again
     * the selected word must not be unselected because it is annoying
     * that this happens even if we not click I don't know why but
     * this happens.
     */
    public boolean justSelectedWord = false;

    // Font
    protected Text displayableText;

    // Text Positions
    protected short textX, textY;
    protected short disToTextX;
    protected short textLimit;

    // Offset
    private short offsetX = 0, startViewableIdx = 0, lastViewableIdx = 0;

    // Timer
    protected static final int TIMER = 100;
    public boolean hide = true;
    public int cTimer = 1;

    public DisplayableTextBox(BotPanel bp, short[] pos, float fontSize, Font font, byte displayMode, Color fontColor, boolean isPassword){
        if (pos.length != 2)
            throw new IllegalArgumentException("Pos needs to be have two short but it is: " + Arrays.toString(pos));
        if (displayMode>1||displayMode<0)
            throw new IllegalArgumentException("DisplayMode should be 1 or 0 it couldn't be: " + displayMode);
        this.bp = bp;
        this.pos = pos;
        this.displayMode = displayMode;
        displayableText = new Text(text, font, fontSize, isPassword, fontColor);
        initialize();
    }

    // Initialize
    protected abstract void initialize();
    protected void initializeUsingGraphics(){
        displayableText.initializeMetrics(Sprite.g);
    }

    // Changing Cursor by Mouse
    public int getCursorByDeltaX(int deltaX){
        if (deltaX <= 0) return 0;
        if (deltaX >= getTextWidth()) return text.length();

        Point searchingPoint = new Point(deltaX, 5);
        for (byte idx = 0; idx < getText().length(); idx++) {
            int[] indexes = {0, idx+1};
            int[] sorted = Util.giveShortestFirst(indexes);
            Rectangle r = new Rectangle(0, 0, getTextWidth(sorted[0], sorted[1]), 10);
            if (r.contains(searchingPoint)) {
                if (r.width - (getTextWidth(String.valueOf(text.charAt(idx))) / 2) <= deltaX) ++idx;
                return idx;
            }
        }
        return -1;
    }
    public void setCursorOnPoint(Point p){
        if (!checkIfClicking(p)) return;

        // Checking if there is no words selected.
        if (!justSelectedWord) resetSelected();
        else justSelectedWord = false;

        short deltaX = (short) (p.x - textX);
        cursorIdx = (byte) getCursorByDeltaX(deltaX);
    }
    public void selectWordAtPoint(Point p){
        if (!checkIfClicking(p)) return;

        final short deltaX = (short) (p.x - textX);
        final byte idx = (byte) getCursorByDeltaX(deltaX);

        final byte wordIdx = (byte) (text.substring(0, idx).split(" ").length - 1);
        String[] words = getText().split(" ");

        byte idxOfWord = Util.boolToByte(text.charAt(0) == ' ');

        for (byte i = 0; i <= wordIdx; i++){
            if (i != wordIdx)
                idxOfWord += words[i].length() + 1;
            else{
                selTextStartIdx = idxOfWord;
                selTextEndIdx = (byte) (idxOfWord + words[wordIdx].length());
                justSelectedWord = true;
                return;
            }
        }
    }

    // Viewable Text
    private int getViewableStartingIdx(){
        return Util.giveShortestFirst(cursorIdx, startViewableIdx)[0];
    }
    private int getViewableEndingIdx(){
        return Util.giveShortestFirst(cursorIdx, lastViewableIdx)[1];
    }

    private int getTextWidth(int startingIdx, int endingIdx){
        return displayableText.getMetrics().stringWidth(text.substring(startingIdx, endingIdx));
    }
    private int getTextWidth(int startingIdx){
        return displayableText.getMetrics().stringWidth(text.substring(startingIdx));
    }
    private int getTextWidth(){
        return displayableText.getMetrics().stringWidth(getText());
    }
    private int getTextWidth(String str) {
        return displayableText.getMetrics().stringWidth(str);
    }

    // Related to Selection
    public void selectText(Point point){
        // Getting the Selection Area
        Point startPoint = bp.mh.draggingStartPoint;
        if (startPoint.x - textX >= getTextWidth() || !checkIfClicking(startPoint)) return;

        int deltaX = startPoint.x - textX;
        int lastDeltaX = point.x - textX;

        selTextStartIdx = (byte) getCursorByDeltaX(deltaX);
        selTextEndIdx = (byte) getCursorByDeltaX(lastDeltaX);
    }

    private void resetCursor(){
        cTimer = TIMER;
        hide = false;
    }
    public void paste(){
        super.paste();
        resetCursor();
    }
    public void copy(){
        super.copy();
        resetCursor();
    }
    public void cut(){
        super.cut();
        resetCursor();
    }

    // Changing Cursor by KeyBoard
    public void shiftCursor(boolean right, boolean selectIt){
        super.shiftCursor(right, selectIt);
        resetCursor();
    }
    public void shiftCursorByWord(boolean right, boolean selectIt){
        super.shiftCursorByWord(right, selectIt);
        resetCursor();
    }

    @Override public void draw() {
        if (displayableText.getFontHeight() == 0)
            initializeUsingGraphics();

        // Drawing
        if (sprite != null)
            sprite.draw();
        drawText();
        drawSelectedTextBackground();
        drawCursor();
    }
    private void drawSelectedTextBackground(){
        try {
            if (selTextStartIdx >= 0 && selTextEndIdx >= 0) {
                short startingX = (short) (getTextWidth(0, getSelectionStartingPoint()) + textX);
                short width = (short) (getTextWidth(getSelectionStartingPoint(), getSelectionEndingPoint()));

                if (width + startingX > textX + textLimit){
                    width = (short) (textLimit - startingX);
                }

                Graphics2D g = Sprite.g;
                g.setColor(new Color(20, 200, 250, 100));
                g.fillRect(startingX, (int) (textY - displayableText.getBaseLineHeight()), width, (int) displayableText.getBaseLineHeight());
            }
        }catch (Exception ignored){}
    }
    private void drawText(){
        displayableText.setPos(textX, textY);
        displayableText.draw();
    }
    private void drawCursor(){
        updateHideAndShow();
        if (!hide && couldWrite){
            Graphics2D g = Sprite.g;
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(displayableText.getFontSize() * 0.1f));
            short offsetX = getCursorOffsetX();
            g.drawLine(textX + offsetX - this.offsetX, (int) (textY - displayableText.getBaseLineHeight()), textX + offsetX - this.offsetX, textY);
        }
    }

    private void updateHideAndShow(){
        if (--cTimer == 0){
            cTimer = TIMER;
            hide = !hide;
        }
    }
    private short getCursorOffsetX(){
        try{
            return (short) getTextWidth(startViewableIdx, cursorIdx);
        }catch(Exception e){
            return 0;
        }
    }
}
