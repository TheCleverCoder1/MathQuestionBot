package com.Math.Bot.Main.Utils.Text;

import com.Math.Bot.Main.Utils.Sprite.Sprite;
import java.awt.*;

// Optimized Stage 3
public class Text {
    // Vars
    private float x, y, fontSize, fontHeight, baseLineHeight;
    private StringBuilder text;
    private Font font;
    private Color color;
    private boolean isPassword;
    private FontMetrics metrics;

    // Getters
    public String getText() {
        return text.toString();
    }
    public Font getFont() { return font; }
    public float getFontSize() {return fontSize;}
    public float getFontHeight() {
        return fontHeight;
    }
    public float getBaseLineHeight() {
        return baseLineHeight;
    }
    public FontMetrics getMetrics() {
        return metrics;
    }

    // Setters
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

    public void draw(){
        Graphics2D g = Sprite.g;
        g.setColor(color);
        if (!isPassword){
            g.setFont(font);
            g.drawString(getText(), x, y);
        }
    }
    public void initializeMetrics(Graphics2D g){
        metrics = g.getFontMetrics(getFont());
        fontHeight = (short) metrics.getHeight();
        baseLineHeight = (short) (metrics.getAscent() - (getFontSize() * 0.5));
    }
    @Override public String toString(){
        return "Text: \"" + text + '"';
    }
}
