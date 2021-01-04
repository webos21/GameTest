/**
 *
 */
package com.gmail.webos21.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Boss
 * @author Himi
 *
 */
public class Boss {

    public int hp = 100;
    public int x, y;
    public int frameW, frameH;
    private Bitmap bmpBoss;
    private int frameIndex;

    private int speed = 6;

    private boolean isCrazy;

    private int crazyTime = 150;

    private int crazyTime2 = 50;

    private int count;

    public Boss(Bitmap bmpBoss) {
        this.bmpBoss = bmpBoss;
        frameW = bmpBoss.getWidth() / 10;
        frameH = bmpBoss.getHeight();

        x = MySurfaceView.screenW / 2 - frameW / 2;
        y = 0;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(x, y, x + frameW, y + frameH);
        canvas.drawBitmap(bmpBoss, x - frameIndex * frameW, y, paint);
        canvas.restore();
    }

    public void logic() {
        frameIndex++;
        if (frameIndex >= 10) {
            frameIndex = 0;
        }
        if (isCrazy == false) {
            if (hp >= 34 && hp < 67) {
                if (x + frameW / 2 > MySurfaceView.screenW / 2) {
                    x--;
                } else {
                    x++;
                }
            } else {
                x += speed;
                if (x + frameW * 3 / 4 >= MySurfaceView.screenW) {
                    speed = -speed;
                } else if (x + frameW / 4 <= 0) {
                    speed = -speed;
                }
            }
            count++;
            if (hp < 34 && count % crazyTime == 0) {
                isCrazy = true;
                speed = 24;

            }

        } else {
            speed -= 1;

            if (speed == 0) {
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_UP));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_RIGHT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_UP_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_UP_RIGHT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x + 40, y + 10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_RIGHT));
                if (!MySurfaceView.soundFlag) {
                    MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);
                    MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 0.5f, 1f, 0, 0, 1);
                    MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);
                }

            }
            y += speed + 3;
            if (y <= 0) {
                isCrazy = false;
                speed = 5;
            }
        }
    }

    public boolean isCollsionWith(Bullet bullet) {
        int x2 = bullet.bulletX;
        int y2 = bullet.bulletY;
        int w2 = bullet.bmpBullet.getWidth();
        int h2 = bullet.bmpBullet.getHeight();
        if (x >= x2 && x >= x2 + w2) {
            return false;
        } else if (x <= x2 && x + frameW <= x2) {
            return false;
        } else if (y >= y2 && y >= y2 + h2) {
            return false;
        } else if (y <= y2 && y + frameH <= y2) {
            return false;
        }
        return true;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
