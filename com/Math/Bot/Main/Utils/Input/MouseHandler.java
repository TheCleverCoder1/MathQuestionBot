package com.Math.Bot.Main.Utils.Input;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Debugger;
import com.Math.Bot.Main.Utils.Editable.Button.Button;
import com.Math.Bot.Main.Utils.Editable.EditableHandler;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox.ScreenTextBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Optimized Stage 2
public class MouseHandler implements MouseListener, MouseWheelListener, MouseMotionListener {
    // BotPanel
    private BotPanel bp;
    private Debugger debugger;

    // Constructor
    public MouseHandler(){
        this.bp = BotPanel.bp;
        debugger = bp.debugger;
    }

    // Some booleans
    public boolean isTextWriterSelected = false;
    public ScreenTextBox selectedTextBox;

    // Dragging
    public boolean dragging = false;
    public Point draggingStartPoint;

    // private methods
    public void resetPopUpSubMenu(){
        debugger.getPopUpMenu().subMenu = false;
        debugger.getPopUpMenu().subMenuRect = null;
        debugger.getPopUpMenu().subMenuIdx = 0;
        debugger.getPopUpMenu().updated = false;
    }
    public void resetPopUpMenu(){
        debugger.getPopUpMenu().subMenu = false;
        debugger.getPopUpMenu().show = false;
        debugger.getPopUpMenu().x = -1000;
        debugger.getPopUpMenu().y = -1000;
        debugger.getPopUpMenu().makeRectangle();
        debugger.getPopUpMenu().updated = false;
    }
    public void resetSelectedTextBox(){
        selectedTextBox.couldWrite = false;
        isTextWriterSelected = false;
        selectedTextBox.cTimer = 1;
        selectedTextBox.hide = true;
        selectedTextBox = null;
    }
    private void setPopUpMenu(short x, short y){
        debugger.getPopUpMenu().x = x;
        debugger.getPopUpMenu().y = y;
        debugger.getPopUpMenu().show = true;
        debugger.getPopUpMenu().makeRectangle();
        debugger.getPopUpMenu().updated = false;
    }
    private void setDebugState(byte idx){
        debugger.setDebugState(idx); bp.kh.cDebugButtonIdx = idx;
        bp.kh.shift = bp.kh.control = bp.kh.changingDebugStatesByMouseAndKeyBoard = false;
    }
    private void changeDebugState(Point p){
        byte idx = 0;
        for (short[] button : debugger.getPosOfDebugIcons()) {
            Rectangle rect = new Rectangle(button[0], button[1], debugger.getWidthOfDebugIcon(), debugger.getWidthOfDebugIcon());
            if (rect.contains(p)){
                setDebugState(idx);
                break;
            }
            idx++;
        }
    }

    // On Methods
    private void onLeftClick(MouseEvent e){
        Point point = e.getPoint();
        dragging = false;

        for (Button button : EditableHandler.getButtons())
            if (button != null)
                button.onClick();

        for (ScreenTextBox textBox : EditableHandler.getTextBoxes()){
            if (textBox != null) {
                if (selectedTextBox != null && textBox == selectedTextBox) {
                    if (textBox.checkIfClicking(point)){
                        textBox.setCursorOnPoint(point);
                    }
                    break;
                }
                if (textBox.checkIfClicking(point)){
                    textBox.couldWrite = true;
                    textBox.cTimer = 1;
                    textBox.hide = true;
                    isTextWriterSelected = true;
                    selectedTextBox = textBox;
                }
            }
        }

        if (selectedTextBox != null){
            if (!selectedTextBox.checkIfClicking(point)) {
                selectedTextBox.resetSelected();
                selectedTextBox.couldWrite = false;
                isTextWriterSelected = false;
                selectedTextBox.cTimer = 1;
                selectedTextBox.hide = true;
                selectedTextBox = null;
            }
        }

        // resetting the popUpMenu if not in use
        if (debugger.getPopUpMenu().isNotCollidingWithRectangle(point) && debugger.getPopUpMenu().x != -1000 && debugger.getPopUpMenu().y != -1000)
            resetPopUpMenu();

        if (bp.kh.changingDebugStatesByMouseAndKeyBoard)
            changeDebugState(point);
    }

    private void onRightClick(MouseEvent e){
        if (bp.debug && debugger.getDebugState() == debugger.EDIT_DEBUG_STATE)
            setPopUpMenu((short) e.getX(), (short) e.getY());
    }

    private void onDoubleClick(MouseEvent e){
        if (selectedTextBox != null)
            selectedTextBox.selectWordAtPoint(e.getPoint());
    }

    private void onDrag(MouseEvent e){
        if (selectedTextBox != null) selectedTextBox.selectText(e.getPoint());
    }

    private void onMove(MouseEvent e){
        for (Button button : EditableHandler.getButtons())
            if (button != null)
                button.onHover(e.getPoint());

        if (debugger.getPopUpMenu().isNotCollidingWithRectangle(e.getPoint()) && debugger.getPopUpMenu().subMenu)
            resetPopUpSubMenu();
    }

    private void onRelease(MouseEvent e){
        if (dragging && selectedTextBox != null)
            if (!selectedTextBox.checkIfClicking(draggingStartPoint))
                resetSelectedTextBox();
        dragging = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            onLeftClick(e);
        }
        else if (SwingUtilities.isRightMouseButton(e)){
            onRightClick(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.getClickCount() == 2) {
                onDoubleClick(e);
                return;
            }
            onLeftClick(e);
        }
        else if (SwingUtilities.isRightMouseButton(e)){
            onRightClick(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        onRelease(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!dragging) draggingStartPoint = e.getPoint();
        dragging = true;
        onDrag(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        onMove(e);
    }
}
