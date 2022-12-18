package com.Math.Bot.Main.Screens;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.Editable.Button.Button;
import com.Math.Bot.Main.Utils.Editable.Button.ButtonMaker;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox.ScreenTextBox;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox.ScreenTextBoxMaker;

// Optimized Stage 1
public class AlgebraSolverMenu implements Menu {
    // BotPanel
    private final BotPanel bp = BotPanel.bp;

    // Editables
    Button[] buttons;
    ScreenTextBox[] textBoxes;

    // Constructor
    public AlgebraSolverMenu(){
        init();
    }

    // Initializing
    private void init(){
        loadImages();
        loadConfig();
        loadPositions();
        loadButtons();
    }

    private void loadImages(){

    }
    private void loadConfig(){

    }
    private void loadPositions(){

    }
    private void loadButtons(){
        buttons = ButtonMaker.getButtonsForState(bp.ALGEBRA_SOLVER_STATE);
        textBoxes = ScreenTextBoxMaker.getScreenTextBoxesForState(bp.ALGEBRA_SOLVER_STATE);
    }

    // Update
    @Override public void update() {}

    // Draw
    @Override public void draw() {}

    @Override public Button[] getButtons() {
        return buttons;
    }
    @Override public ScreenTextBox[] getTextWriters() {
        return textBoxes;
    }
}
