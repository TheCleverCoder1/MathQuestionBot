package com.Math.Bot.Main.Utils.Input;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Debugger;
import com.Math.Bot.Main.Utils.TextHandler.TextHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Optimized Stage 3
public class KeyHandler implements KeyListener {
    private final BotPanel bp = BotPanel.bp;
    private Debugger debugger = bp.debugger;

    // Special Keys
    public boolean control = false;
    public boolean shift = false;

    // Debug
    public boolean changingDebugStatesByMouseAndKeyBoard = false;
    public byte cDebugButtonIdx = 0;

    // Private methods
    private void changeDebugState(boolean add){
        if (!changingDebugStatesByMouseAndKeyBoard) return;
        if (add){
            cDebugButtonIdx++;
            cDebugButtonIdx = (byte) (cDebugButtonIdx % Debugger.numOfDebugStates);
            return;
        }
        cDebugButtonIdx--;
        if (cDebugButtonIdx < 0) cDebugButtonIdx += Debugger.numOfDebugStates;
    }
    private void setDebugState(){
        if (!changingDebugStatesByMouseAndKeyBoard) return;
        if (debugger == null) debugger = bp.debugger;
        debugger.setDebugState(cDebugButtonIdx);
        changingDebugStatesByMouseAndKeyBoard = false;
    }

    // On Methods
    private void onPress(KeyEvent e){
        int code = e.getKeyCode();

        if (!bp.mh.isTextWriterSelected) {
            switch (code) {
                case KeyEvent.VK_F9 -> {
                    if (control) bp.debug = !bp.debug;
                }
                case KeyEvent.VK_F8 -> {
                    if (debugger == null) debugger = bp.debugger;
                    if (control) debugger.setDebugState((byte) ((debugger.getDebugState() + 1) % Debugger.numOfDebugStates));
                }
                case KeyEvent.VK_V -> changingDebugStatesByMouseAndKeyBoard = control && shift && bp.debug;

                case KeyEvent.VK_RIGHT, KeyEvent.VK_UP -> changeDebugState(true);
                case KeyEvent.VK_LEFT, KeyEvent.VK_DOWN -> changeDebugState(false);
                case KeyEvent.VK_ENTER -> setDebugState();
            }
            return;
        }
        if (control){
            if      (KeyEvent.VK_V == code) bp.mh.selectedTextBox.paste();
            else if (KeyEvent.VK_C == code) bp.mh.selectedTextBox.copy();
            else if (KeyEvent.VK_X == code) bp.mh.selectedTextBox.cut();
            else if (KeyEvent.VK_A == code) bp.mh.selectedTextBox.selectAll();
        }
        if (code == KeyEvent.VK_ENTER) {
            bp.mh.resetSelectedTextBox();
            bp.mh.selectedTextBox.onEnter();
            return;
        }
        char Char = e.getKeyChar();
        if (TextHandler.isCharAlphabet(Char) || TextHandler.isCharSymbol(Char) || TextHandler.isCharNumber(Char) || TextHandler.isCharSpace(Char)) {
            bp.mh.selectedTextBox.appendChar(Char);
        }
        else if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE){
            if (!control) bp.mh.selectedTextBox.removeChar(code == KeyEvent.VK_BACK_SPACE);
            else bp.mh.selectedTextBox.removeWord(code == KeyEvent.VK_BACK_SPACE);
        }
        else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT){
            if (!control) bp.mh.selectedTextBox.shiftCursor(code == KeyEvent.VK_RIGHT, shift);
            else bp.mh.selectedTextBox.shiftCursorByWord(code == KeyEvent.VK_RIGHT, shift);
        }
    }
    private void onRelease(KeyEvent e){
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_CONTROL -> control = false;
            case KeyEvent.VK_SHIFT -> shift = false;
            case KeyEvent.VK_V -> setDebugState();
        }
    }

    // The override methods
    @Override public void keyTyped(KeyEvent e) {
        if (bp.mh.isTextWriterSelected) return;
        onPress(e);
    }
    @Override public void keyPressed(KeyEvent e) {
        onPress(e);
        switch (e.getKeyCode()){
            case KeyEvent.VK_CONTROL -> control = true;
            case KeyEvent.VK_SHIFT -> shift = true;
        }
    }
    @Override public void keyReleased(KeyEvent e) {
        onRelease(e);
    }
}
