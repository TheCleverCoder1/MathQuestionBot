package com.Math.Bot.Main.Utils.Editable;


import com.Math.Bot.Main.Utils.Sprite.Sprite;

import java.awt.*;

// Optimized Stage 3
public abstract class Editable {
    // Is selected when making gui in debug mode.
    public boolean isSelected;
    public short[] pos;
    public short[] size;
    protected Sprite sprite;

    public abstract void update();
    public abstract void draw();

    public boolean checkIfClicking(Point p){
        // Creating a rectangle (hitbox) and checking if the point is in the rectangle (hitbox)
        return new Rectangle(pos[0], pos[1], size[0], size[1]).contains(p);
    }
}
