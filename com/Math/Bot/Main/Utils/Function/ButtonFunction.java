package com.Math.Bot.Main.Utils.Function;

// Optimized Stage 3
public abstract class ButtonFunction extends Function {
    public abstract void onClick();
    public abstract void onHover();
    public abstract void onExit();

    @Override
    public void func() {
        System.out.println("ButtonFunction is not allowed to do func!!");
    }
}
