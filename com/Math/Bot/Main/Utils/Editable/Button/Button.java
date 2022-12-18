package com.Math.Bot.Main.Utils.Editable.Button;

import com.Math.Bot.Main.BotPanel;
import com.Math.Bot.Main.Utils.Editable.Editable;
import com.Math.Bot.Main.Utils.FontHandler.FontHandler;
import com.Math.Bot.Main.Utils.Function.ButtonFunction;
import com.Math.Bot.Main.Utils.Sprite.Sprite;

import java.awt.*;
import java.util.Arrays;

// Optimized Stage 3
public class Button extends Editable {
    private final ButtonFunction func;
    private final byte shape;
    private Rectangle rect;

    public boolean hovering = false;

    public Button(ButtonFunction func, short[] pos, Sprite sprite, byte shape){
        if (pos.length != 2)
            throw new IllegalArgumentException("Pos needs to be have two short but it is: " + Arrays.toString(pos));
        this.func = func;
        this.pos = pos;
        this.shape = shape;
        this.sprite = sprite;
        size = new short[]{
                (short) sprite.getImg().getWidth(null), // First is width
                (short) sprite.getImg().getHeight(null) // Second is height
        };
        rect = new Rectangle(pos[0], pos[1], size[0], size[1]);
    }
    public Button(ButtonFunction func, short[] pos, Image img, byte shape){
        if (pos.length != 2)
            throw new IllegalArgumentException("Pos needs to be have two short but it is: " + Arrays.toString(pos));
        this.func = func;
        this.pos = pos;
        this.shape = shape;
        this.sprite = new Sprite(pos[0], pos[1], img);
        size = new short[]{
                (short) sprite.getImg().getWidth(null), // First is width
                (short) sprite.getImg().getHeight(null) // Second is height
        };
        rect = new Rectangle(pos[0], pos[1], size[0], size[1]);
    }

    public void updateRect(){
        rect = new Rectangle(pos[0], pos[1], size[0], size[1]);
    }

    public void onClick(){
        if (hovering) func.onClick();
    }
    public void onHover(Point p){
        hovering = false;
        if (shape == ButtonMaker.RECTANGLE) {
            if (rect.contains(p)){
                hovering = true;
                func.onHover();
            } else func.onExit();
        }
    }

    @Override public void update() {}

    @Override public void draw(){
        sprite.draw();
    }

    public void drawInfo() {
        Graphics2D g = Sprite.g;
        g.setFont(FontHandler.getFonts()[0].deriveFont(25f));
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(pos[0], pos[1], size[0], size[1]);
        g.setColor(Color.BLACK);
        g.drawString("Pos: " + Arrays.toString(pos), pos[0] + 10, pos[1] + 10);
        g.drawString("Size: " + Arrays.toString(size), pos[0] + 10, pos[1] + 60);
    }
}
