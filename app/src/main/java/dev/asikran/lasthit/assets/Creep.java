package dev.asikran.lasthit.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

import dev.asikran.lasthit.R;

/**
 * Created by JuanErnesto on 07-12-2014.
 */
public class Creep {

    private float posX;
    private float posY;

    private float vel;

    private float dirX;
    private float dirY;

    private int hp;
    private int currentHp;

    private float size;

    private Bitmap image;

    private float width;
    private float heigth;

    private Paint hpBarPaint = new Paint();

    public Creep(float posX, float posY, float vel, float dirX, float dirY, float size, int hp, Bitmap image) {
        this.posX = posX - (image.getWidth()/2);
        this.posY = posY;
        this.vel = vel;
        this.dirX = dirX;
        this.dirY = dirY;
        this.size = size;
        this.hp = hp;
        this.currentHp = hp;
        this.image = image;
        this.width = image.getWidth();
        this.heigth = image.getHeight();
        this.hpBarPaint.setColor(Color.GREEN);
    }

    public Creep(float width, Bitmap image) {
        this.posX = width/2 - (image.getWidth()/2);
        this.posY = 0;
        this.vel = 10;
        this.dirX = 0;
        this.dirY = -1;
        this.hp = 100;
        this.currentHp = this.hp;
        this.size = 20;
        this.image = image;
        this.width = image.getWidth();
        this.heigth = image.getHeight();
        this.hpBarPaint.setColor(Color.GREEN);
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setVel(float vel) {
        this.vel = vel;
    }

    public void setDirX(float dirX) {
        this.dirX = dirX;
    }

    public void setDirY(float dirY) {
        this.dirY = dirY;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getVel() {
        return vel;
    }

    public float getDirX() {
        return dirX;
    }

    public float getDirY() {
        return dirY;
    }

    public int getHp() {
        return hp;
    }
    public int getCurrentHp() {
        return currentHp;
    }

    public float getSize() {
        return size;
    }

    public Bitmap getImage() {
        return image;
    }

    public void move(){
        posX += vel * dirX;
        posY += vel * dirY;
    }

    public void resetHP(){
        this.currentHp = hp;
    }

    public void takeHit(int damage){
        this.currentHp -= damage;
    }

    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(image, posX , posY, null);

        canvas.drawRect(new Rect((int) posX, (int) posY - 10, (int) (posX + (width * (currentHp / 500f))), (int) posY - 5), hpBarPaint);
    }

    public boolean isColliding(float x, float y) {
        return x > posX && x < posX + width && y > posY && y < posY + heigth;
    }
}
