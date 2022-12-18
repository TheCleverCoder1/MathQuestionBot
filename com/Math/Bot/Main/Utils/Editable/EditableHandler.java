package com.Math.Bot.Main.Utils.Editable;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.Editable.Button.Button;
import com.Math.Bot.Main.Utils.Editable.TextBox.SelectableTextBoxes.DisplayableTextBox.ScreenTextBox;

import java.util.ArrayList;
import java.util.Collections;

// Optimized Stage 3
public class EditableHandler {
    private static final BotPanel bp = BotPanel.bp;

    public static ScreenTextBox[] getTextBoxes() {
        return textBoxes;
    }
    public static Button[] getButtons() {
        return buttons;
    }

    private static ScreenTextBox[] textBoxes;
    private static Button[] buttons;

    public static void setEditable(Editable[][] editables) {
        loadEditables(editables);
    }

    private static void loadEditables(Editable[][] editables){
        ArrayList<Editable>[] arrayLists = loadEditableArrayList((Button[]) editables[0], (ScreenTextBox[]) editables[1]);
        convertButtonArrayList(arrayLists[0]);
        convertTextWriterArrayList(arrayLists[1]);
    }
    private static ArrayList<Editable>[] loadEditableArrayList(Button[] buttons, ScreenTextBox[] textBoxes){
        // Making an arraylist and adding there the buttons list stuff
        ArrayList<Editable> buttonArrayList = new ArrayList<>();
        ArrayList<Editable> textArrayList = new ArrayList<>();
        {
            Collections.addAll(buttonArrayList, buttons);
            for (Button[] _buttons : bp.debugger.getPopUpMenu().buttons) Collections.addAll(buttonArrayList, _buttons);
        } // For Buttons
        Collections.addAll(textArrayList, textBoxes);

        ArrayList<Editable>[] arrayList = new ArrayList[]{
                buttonArrayList, textArrayList
        };
        return arrayList;
    }
    private static void  convertTextWriterArrayList(ArrayList<Editable> arrayList){
        textBoxes = new ScreenTextBox[arrayList.size()];
        Object[] objs = arrayList.toArray();
        for (byte idx = 0; idx < textBoxes.length; idx++)
            textBoxes[idx] = (ScreenTextBox) objs[idx];
    }
    private static void convertButtonArrayList(ArrayList<Editable> arrayList){
        // Converting the array list in the Button[] array
        buttons = new Button[arrayList.size()];
        Object[] objs = arrayList.toArray();
        for (byte idx = 0; idx < buttons.length; idx++)
            buttons[idx] = (Button) objs[idx];
    }
}