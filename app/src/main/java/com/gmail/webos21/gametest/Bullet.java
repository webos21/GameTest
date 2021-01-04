package com.gmail.webos21.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Bullet {

    public static final int BULLET_PLAYER = -1;
    public static final int BULLET_DUCK = 1;
    public static final int BULLET_FLY = 2;
    public static final int BULLET_BOSS = 3;
    public static final int DIR_UP = -1;
    public static final int DIR_DOWN = 2;
    public static final int DIR_LEFT = 3;
    public static final int DIR_RIGHT = 4;
    public static final int DIR_UP_LEFT = 5;
    public static final int DIR_UP_RIGHT = 6;
    public static final int DIR_DOWN_LEFT = 7;
    public static final int DIR_DOWN_RIGHT = 8;
    public static final int DIR_UP_LEFT2 = 9;
    public static final int DIR_UP_RIGHT2 = 10;
    public static final int DIR_DOWN_Random = 11;
    public static int flag = -1;
    public Bitmap bmpBullet;
    public int bulletX, bulletY;
    public int speed, speed2;
    public int bulletType;
    public boolean isDead;
    private int dir;


    public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType) {
        this.bmpBullet = bmpBullet;
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        this.bulletType = bulletType;

        switch (bulletType) {
            case BULLET_PLAYER:
                speed = 30;
                break;
            case BULLET_DUCK:
                speed = 10;
                break;
            case BULLET_FLY:
                speed = 10;
                break;
            case BULLET_BOSS:
                speed = 3;
                break;
        }
    }

    public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType, int dir) {
        this.bmpBullet = bmpBullet;
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        this.bulletType = bulletType;
        speed = 10;
        if (flag == 1) {
            speed2 = 10;
        } else {
            speed2 = -10;
        }
        this.dir = dir;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bmpBullet, bulletX, bulletY, paint);
    }

    public void logic() {
        switch (bulletType) {
            case BULLET_PLAYER:
                bulletY -= speed;
                if (bulletY < -50) {
                    isDead = true;
                }
                switch (dir) {
                    case DIR_UP_LEFT2:
                        bulletY -= 20;
                        bulletX -= 3;
                        break;
                    case DIR_UP_RIGHT2:
                        bulletY -= 20;
                        bulletX += 3;
                        break;
                }
                break;

            case BULLET_DUCK:
                bulletY += speed;
                if (bulletY > MySurfaceView.screenH) {
                    isDead = true;
                }
                break;

            case BULLET_FLY:
                bulletY += speed;
                if (bulletY > MySurfaceView.screenH) {
                    isDead = true;
                }
                break;

            case BULLET_BOSS:
                switch (dir) {
                    case DIR_UP:
                        bulletY -= speed;
                        break;
                    case DIR_DOWN:
                        bulletY += speed;
                        break;
                    case DIR_LEFT:
                        bulletX -= speed;
                        break;
                    case DIR_RIGHT:
                        bulletX += speed;
                        break;
                    case DIR_UP_LEFT:
                        bulletY -= speed;
                        bulletX -= speed;
                        break;
                    case DIR_UP_RIGHT:
                        bulletX += speed;
                        bulletY -= speed;
                        break;
                    case DIR_DOWN_LEFT:
                        bulletX -= speed;
                        bulletY += speed;
                        break;
                    case DIR_DOWN_RIGHT:
                        bulletY += speed;
                        bulletX += speed;
                        break;
                    case DIR_DOWN_Random:
                        bulletX += speed2;
                        if (bulletX + bmpBullet.getWidth() >= MySurfaceView.screenW) {
                            speed2 = -speed2;
                        } else if (bulletX <= 0) {
                            speed2 = -speed2;
                            if ((bulletX += speed2) < 0)
                                bulletX += speed2;
                        }
                        bulletY += speed;
                        break;
                }
                if (bulletY > MySurfaceView.screenH || bulletY <= -40 || bulletX > MySurfaceView.screenW || bulletX <= -40) {
                    isDead = true;
                }
                break;
        }
    }
}
