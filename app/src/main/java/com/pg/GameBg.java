package com.pg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @author Himi
 */
public class GameBg {

    private Bitmap bmpBackGround1;
    private Bitmap bmpBackGround2;
    private Bitmap bmpGamePause;

    private int bg1x, bg1y, bg2x, bg2y;
    private int btn_x, btn_y;

    private boolean isPress;

    private int speed = 3;

    public GameBg(Bitmap bmpBackGround, Bitmap bmpGamePause) {
        this.bmpBackGround1 = bmpBackGround;
        this.bmpBackGround2 = bmpBackGround;
        this.bmpGamePause = bmpGamePause;

        bg1y = -Math.abs(bmpBackGround1.getHeight() - MySurfaceView.screenH);
        bg2y = bg1y - bmpBackGround1.getHeight();

        btn_x = (int) (MySurfaceView.screenW * 0.9);
        btn_y = (int) (MySurfaceView.screenH * 0.01);

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bmpBackGround1, bg1x, bg1y, paint);
        canvas.drawBitmap(bmpBackGround2, bg2x, bg2y, paint);
        canvas.drawBitmap(bmpGamePause, btn_x, btn_y, paint);

    }

    public void onTouchEvent(MotionEvent event) {
        int pointX = (int) event.getX();
        int pointY = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (pointX > btn_x && pointX < btn_x + bmpGamePause.getWidth()) {
                if (pointY > btn_y && pointY < btn_y + bmpGamePause.getHeight()) {
                    isPress = true;
                } else {
                    isPress = false;
                }
            } else {
                isPress = false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (pointX > btn_x && pointX < btn_x + bmpGamePause.getWidth()) {
                if (pointY > btn_y && pointY < btn_y + bmpGamePause.getHeight()) {
                    isPress = false;
                    MySurfaceView.gameState = MySurfaceView.GAME_PAUSE;
                }
            }
        }
    }

    public void logic() {
        bg1y += speed;
        bg2y += speed;

        if (bg1y > MySurfaceView.screenH) {
            bg1y = bg2y - bmpBackGround1.getHeight();
        }

        if (bg2y > MySurfaceView.screenH) {
            bg2y = bg1y - bmpBackGround1.getHeight();
        }
    }
}
