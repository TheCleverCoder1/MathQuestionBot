package com.Math.Bot.Main.Utils.Editable;


import com.Math.Bot.Main.Utils.Sprite.Sprite;

import java.awt.*;

// Optimized Stage 2
public abstract class Editable {
    public boolean isSelected;
    public short[] pos;
    public short[] size;
    protected Sprite sprite;

    public abstract void update();
    public abstract void draw();

    public boolean checkIfClicking(Point p){
        Rectangle hitBox = new Rectangle(pos[0], pos[1], size[0], size[1]);
        return hitBox.contains(p);
    }
}
