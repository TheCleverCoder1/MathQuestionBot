package com.Math.Bot.Main.Screens;

import com.Math.Bot.Main.Utils.Editable.Button.Button;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox.ScreenTextBox;

// Optimized Stage 1
public interface Menu {
    void update();
    void draw();

    Button[] getButtons();
    ScreenTextBox[] getTextWriters();
}
