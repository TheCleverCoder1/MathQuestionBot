package com.Math.Bot.Main.Utils.Text;

import com.Math.Bot.Main.Utils.Sprite.Sprite;
import java.awt.*;

public class Text {
    public String getText() {
        return text.toString();
    }
    private float x, y;
    public void setPos(float x, float y){
        this.x = x; this.y = y;
    }

    public Text(StringBuilder text, Font font, float fontSize, boolean isPassword, Color color) {
        this.text = text;
        this.fontSize = fontSize;
        this.font = font.deriveFont(fontSize);
        this.color = color;
        this.isPassword = isPassword;
    }

    private StringBuilder text;
    private Font font;
    private float fontSize;
    private Color color;
    private boolean isPassword;

    public void draw(){
        Graphics2D g = Sprite.g;
        g.setColor(color);
        if (!isPassword){
            g.setFont(font);
            g.drawString(getText(), x, y);
        }
    }

    @Override public String toString(){
        return "Text: \"" + text + '"';
    }
}
