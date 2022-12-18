package com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes;

import com.Math.Bot.Main.Utils.Editable.TextBox.TextBox;
import com.Math.Bot.Main.Utils.Util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.PrintStream;

// Optimized Stage 2
public abstract class SelectionTextBox extends TextBox {
    // Selected Text
    public short selTextStartIdx = -2;
    public short selTextEndIdx = -1;

    // Text Editing
    @Override public void appendChar(char c) {
        if (isSelectedSomething()) removeSelected();
        super.appendChar(c);
    }
    @Override public void removeChar(boolean isBackspace) {
        if (!isSelectedSomething()) {
            super.removeChar(isBackspace);
            return;
        }
        removeSelected();
    }
    @Override public void removeWord(boolean isBackspace) {
        if (!isSelectedSomething()) {
            super.removeWord(isBackspace);
        }
        else removeSelected();
    }

    // Getters
    public String getSelectedText(){
        if (isSelectedSomething())
            return text.substring(selTextEndIdx > selTextStartIdx ? selTextStartIdx : selTextEndIdx, selTextEndIdx > selTextStartIdx ? selTextEndIdx : selTextStartIdx);;
        return "";
    }

    // Selection Related Stuff
    protected boolean isSelectedSomething() {
        return selTextEndIdx >= 0 && selTextStartIdx >= 0;
    }
    public void resetSelected() {
        selTextStartIdx = -2;
        selTextEndIdx = -1;
    }
    public void selectAll(){
        selTextStartIdx = 0; selTextEndIdx = (byte) text.length();
    }
    public void removeSelected() {
        text.delete(getSelectionStartingPoint(), getSelectionEndingPoint());
        cursorIdx = (byte) getSelectionStartingPoint();
        resetSelected();
    }
    protected int getSelectionStartingPoint(){
        int[] sorted = Util.giveShortestFirst(selTextEndIdx, selTextStartIdx);
        return sorted[0];
    }
    protected int getSelectionEndingPoint(){
        int[] sorted = Util.giveShortestFirst(selTextEndIdx, selTextStartIdx);
        return sorted[1];
    }

    // Cut, Copy, Paste
    protected void copy(){
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (isSelectedSomething()){
            String text = this.text.substring(getSelectionStartingPoint(), getSelectionEndingPoint());
            StringSelection data = new StringSelection(text);
            c.setContents(data, data);
            return;
        }
        StringSelection nothing = new StringSelection("");
        c.setContents(nothing, nothing);
    }
    protected void cut(){
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        if (isSelectedSomething()){
            String text = this.text.substring(getSelectionStartingPoint(), getSelectionEndingPoint());
            StringSelection data = new StringSelection(text);
            c.setContents(data, data);
            removeSelected();
            return;
        }
        StringSelection nothing = new StringSelection("");
        c.setContents(nothing, nothing);
    }
    protected void paste(){
        if (isSelectedSomething()) removeSelected();
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        PrintStream errStream = System.err; // The console where we see errors
        try {
            System.setErr(new PrintStream("error.log"));
        }
        catch(Exception ignored){}
        finally{
            try {
                String pastingText = (String) c.getData(DataFlavor.stringFlavor);
                for (byte i = 0; i < pastingText.length(); i++)
                    appendChar(pastingText.charAt(i));
            }
            catch(Exception ignored){}
            finally{
                System.setErr(errStream);
            }
        }
    }

    // Shifting Cursor
    protected void shiftCursor(boolean right, boolean selectIt){
        if (!selectIt && isSelectedSomething())
            resetSelected();
        if (!isSelectedSomething() && selectIt)
            selTextStartIdx = cursorIdx;
        super.shiftCursor(right);
        if (selectIt) selTextEndIdx = cursorIdx;
    }
    protected void shiftCursorByWord(boolean right, boolean selectIt){
        if (!selectIt && isSelectedSomething())
            resetSelected();
        if (selectIt && !isSelectedSomething())
            selTextStartIdx = cursorIdx;
        super.shiftCursorByWord(right);
        if (selectIt) selTextEndIdx = cursorIdx;}
}
