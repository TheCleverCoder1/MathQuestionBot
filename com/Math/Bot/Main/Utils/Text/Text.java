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
    private boolean isPassword, showPassword;
    private FontMetrics metrics;
    private final short passwordBallRadius, disToPasswordBallY, gapBetweenPasswordBalls;
    private short passwordBallY;

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
    public boolean getIsPassword() {
        return isPassword;
    }
    public boolean getShowPassword() {
        return showPassword;
    }

    // Setters
    public void setPos(float x, float y){
        this.x = x; this.y = y;
        passwordBallY = (short) (y + disToPasswordBallY);
    }

    public Text(StringBuilder text, Font font, float fontSize, boolean isPassword, Color color) {
        this.text = text;
        this.fontSize = fontSize;
        this.font = font.deriveFont(fontSize);
        this.color = color;
        this.isPassword = isPassword; showPassword = false;
        passwordBallRadius = (short) fontSize;
        disToPasswordBallY = (short) (fontSize/2.5);
        gapBetweenPasswordBalls = (short) (fontSize/4);
    }
    public void draw(){
        Graphics2D g = Sprite.g;
        g.setColor(color);
        if (!isPassword || showPassword){
            g.setFont(font);
            g.drawString(getText(), x, y);
            return;
        }
        drawPassword(g);
    }
    private void drawPassword(Graphics2D g){
        for (short i = 0, pX = (short) (x + fontSize); i < getText().length(); i++, pX+=(passwordBallRadius/2 + gapBetweenPasswordBalls)){
            drawCircle(pX, passwordBallY, passwordBallRadius, g);
        }
    }
    private void drawCircle(short x, short y, short radius, Graphics2D g){
        short diameter = (short) (radius/2);
        g.fillOval(x - radius, y - radius, diameter, diameter);
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
