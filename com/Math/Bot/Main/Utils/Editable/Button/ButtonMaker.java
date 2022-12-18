package com.Math.Bot.Main.Utils.Editable.Button;

import com.Math.Bot.Main.Utils.Function.ButtonFunction;
import com.Math.Bot.Main.Utils.ImageHandler.ImageHandler;
import com.Math.Bot.Main.Utils.Sprite.Sprite;
import com.Math.Bot.Main.Utils.TextHandler.TextHandler;

import java.awt.*;

// Optimized Stage 3
public class ButtonMaker {
    public static final byte RECTANGLE = 0;

    private static ButtonFunction getFunctionByButtonTag(String tag, byte state){
        switch (tag.toLowerCase()){
            case "algebrasolver", "algebrabutton" -> {
                switch (state){
                    case 0 -> {
                        return new ButtonFunction(){
                            @Override
                            public void onClick() {
                                TextHandler.bp.cState = TextHandler.bp.ALGEBRA_SOLVER_STATE;
                            }

                            @Override
                            public void onHover() {

                            }

                            @Override
                            public void onExit() {

                            }
                        };
                    }
                }
            }
            case "debugpopup0", "debug0", "popup0" -> {
                return new ButtonFunction() {
                    @Override
                    public void onClick() {

                    }

                    @Override
                    public void onHover() {

                    }

                    @Override
                    public void onExit() {
                    }
                };
            }
        }

        return null;
    }

    private static Button makeButton(String[] info){
        if (info.length != TextHandler.lengthOfTheButtonInformation)
            throw new IllegalArgumentException("The length of the button information can't be" +
                    "lower or greater than " + TextHandler.lengthOfTheButtonInformation);

        short[] pos = {
            Short.parseShort(info[0]), Short.parseShort(info[1])
        };
        short width = Short.parseShort(info[2]), height = Short.parseShort(info[3]);
        byte state = Byte.parseByte(info[4]);
        String tag = info[6];
        Sprite sprite = new Sprite(pos[0], pos[1], tag, width, height);
        return new Button(getFunctionByButtonTag(tag, state), pos, sprite, RECTANGLE);
    }
    public static Button[] getButtonsForState(byte state){
        String[][] buttons = TextHandler.getButtonOfState(state);
        Button[] buttonsInState = new Button[buttons.length];
        for (byte idx = 0; idx < buttons.length; idx++)
            buttonsInState[idx] = makeButton(buttons[idx]);
        return buttonsInState;
    }
}
