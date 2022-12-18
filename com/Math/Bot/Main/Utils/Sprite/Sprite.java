package com.Math.Bot.Main.Utils.Sprite;

import com.Math.Bot.Main.Utils.ImageHandler.ImageHandler;

import java.awt.*;

// Optimized Stage 2
public class Sprite {
    // Some Stuff
    private Image img;
    public static Graphics2D g;
    private short posX, posY;

    // Getter and Setters
    public short getPosX() {
        return posX;
    }
    public void setPosX(short posX) {
        this.posX = posX;
    }
    public short getPosY() {
        return posY;
    }
    public void setPosY(short posY) {
        this.posY = posY;
    }
    public void setPos(short posX, short posY){
        setPosX(posX); setPosY(posY);
    }
    public Image getImg() {
        return img;
    }
    public void setImg(Image img) {
        this.img = img;
    }
    public void setImg(String tag){
        img = ImageHandler.getImageByTagOrName(tag);
    }
    public void setImg(String tag, short width, short height){
        img = ImageHandler.ScaleImage(ImageHandler.getImageByTagOrName(tag), width, height);
    }

    // Constructors
    public Sprite(short x, short y){
        setPos(x, y);
    }
    public Sprite(short x, short y, Image img){
        setPos(x, y); setImg(img);
    }
    public Sprite (short x, short y, String tag, short width, short height){
        setPos(x, y); setImg(tag, width, height);
    }
    public Sprite (short x, short y, String tag){
        setPos(x, y); setImg(tag);
    }
    public Sprite(){
        posX = posY = 0;
    }

    // The method
    public void draw(){
        if (img != null)
            g.drawImage(img, posX, posY, null);
    }
}
