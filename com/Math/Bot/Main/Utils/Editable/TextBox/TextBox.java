package com.Math.Bot.Main.Utils.Editable.TextBox;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.Editable.Editable;
import com.Math.Bot.Main.Utils.Util;

// Optimized Stage 3
public abstract class TextBox extends Editable {
    // Some Stuff which is not needed
    @Override public void update(){}

    // Some Stuff
    protected BotPanel bp;
    protected StringBuilder text = new StringBuilder();
    public boolean couldWrite = false;

    // Cursor
    public short cursorIdx = 0;

    // Append Character
    protected void appendChar(char c){
        text.insert(cursorIdx++, c);
    }

    // Remove Character
    protected void removeChar(boolean isBackspace){
        if (isBackspace) removeCharOnBackspace();
        else removeCharOnDelete();
    }
    private void removeCharOnBackspace(){
        if (cursorIdx == 0) return;
        text.deleteCharAt((cursorIdx--) - 1);
    }
    private void removeCharOnDelete(){
        if (cursorIdx == text.length()) return;
        text.deleteCharAt(cursorIdx);
    }

    // Remove Word
    protected void removeWord(boolean isBackspace){
        if (isBackspace) removeWordOnBackspace();
        else removeWordOnDelete();
    }
    private void removeWordOnBackspace(){
        if (cursorIdx == 0) return;

        // Checking if we have a space after the last word
        byte isSpace = Util.boolToByte(text.charAt(cursorIdx - 1) == ' ');

        // Removing The Last Word from all the words
        String[] words = text.substring(0, cursorIdx).split(" ");
        byte removingWord = Util.boolToByte(words.length != 0);
        String[] nWords = new String[words.length - removingWord];
        for (byte i = 0; i < nWords.length; i++)
            nWords[i] = words[i];

        // Getting the text after the cursor
        String afterText = text.substring(cursorIdx);

        // Deleting everything and then adding updated text.
        text.delete(0, text.length());
        for (String nWord : nWords) {
            text.append(nWord);
            text.append(' ');
        }
        text.append(afterText);

        // Updating the cursor index
        cursorIdx -= words[words.length - removingWord].length() + isSpace;
    }
    private void removeWordOnDelete(){
        if (cursorIdx == text.length()) return;

        // Checking if we have a space before the just word
        boolean isSpace = text.charAt(cursorIdx) == ' ';

        // Removing The Just Before Word from all the words
        String[] words = text.substring(cursorIdx).split(" ");
        String[] nWords = new String[words.length - 1];
        for (byte i = 0; i < nWords.length; i++)
            nWords[i] = words[i + 1];

        // Deleting everything except the before text and then adding updated text.
        text.delete(cursorIdx, text.length());

        if (isSpace) text.append(' ');
        for (String nWord : nWords) {
            text.append(nWord);
            text.append(' ');
        }
    }

    // Getters
    public String getText(){
        return text.toString();
    }

    // Shift Cursor
    protected void shiftCursor(boolean right){
        if (right) shiftCursorRight();
        else shiftCursorLeft();
    }
    private void shiftCursorLeft() {
        if (cursorIdx==0) return;
        cursorIdx--;
    }
    private void shiftCursorRight() {
        if (cursorIdx==text.length()) return;
        cursorIdx++;
    }

    protected void shiftCursorByWord(boolean right){
        if (right) shiftCursorByWordRight();
        else shiftCursorByWordLeft();
    }
    private void shiftCursorByWordLeft() {
        if (cursorIdx <= 0) return;

        byte isSpace = Util.boolToByte(text.charAt(cursorIdx - 1) == ' ');

        String[] words = text.substring(0, cursorIdx).split(" ");

        if (words.length - 1 >= 0)
            cursorIdx -= words[words.length - 1].length() + isSpace;

    }
    private void shiftCursorByWordRight() {
        if (cursorIdx >= text.length()) return;

        // Checking if we have a space before the just word
        byte isSpace = Util.boolToByte(text.charAt(cursorIdx) == ' ');

        // Removing The Just Before Word from all the words
        String[] words = text.substring(cursorIdx).split(" ");
        cursorIdx += words[isSpace].length() + isSpace;
    }

    public void onEnter(){}
}
