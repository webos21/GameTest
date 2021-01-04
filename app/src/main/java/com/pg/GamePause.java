package com.pg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @author Himi
 */
public class GamePause {

    private Bitmap bmpPause_bg;

    private Bitmap bmpButton, bmpButtonPress, bmpPause_continue, bmpPause_exit;

    private int btnX, btnY, btn_continue_x, btn_continue_y, btn_exit_x, btn_exit_y;

    private Boolean isPress, flag;

    public GamePause(Bitmap bmpPause_bg, Bitmap bmpPause_back, Bitmap bmpPause_continue, Bitmap bmpPause_exit) {
        this.bmpPause_bg = Bitmap.createScaledBitmap(bmpPause_bg, MySurfaceView.screenW, MySurfaceView.screenH,  false);
        this.bmpButton = bmpPause_back;
        this.bmpPause_continue = bmpPause_continue;
        this.bmpPause_exit = bmpPause_exit;

        btn_continue_x = MySurfaceView.screenW / 2 - bmpPause_continue.getWidth() / 2;
        btn_continue_y = MySurfaceView.screenH * 2 / 5 - bmpPause_continue.getHeight();

        btnX = MySurfaceView.screenW / 2 - bmpButton.getWidth() / 2;
        btnY = MySurfaceView.screenH * 4 / 6 - bmpButton.getHeight();


        btn_exit_x = MySurfaceView.screenW / 2 - bmpPause_exit.getWidth() / 2;
        btn_exit_y = MySurfaceView.screenH * 5 / 6 - bmpPause_exit.getHeight();

        isPress = false;
    }

    public void draw(Canvas canvas, Paint paint) {

        canvas.drawBitmap(bmpPause_bg, 0, 0, paint);

        if (isPress) {
            canvas.drawBitmap(bmpButtonPress, btnX, btnY, paint);
        } else {
            canvas.drawBitmap(bmpButton, btnX, btnY, paint);
        }
        canvas.drawBitmap(bmpPause_continue, btn_continue_x, btn_continue_y, paint);
        canvas.drawBitmap(bmpPause_exit, btn_exit_x, btn_exit_y, paint);
    }

    public void onTouchEvent(MotionEvent event) {

        int pointX = (int) event.getX();
        int pointY = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {

            if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
                if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
                    isPress = true;
                } else {
                    isPress = false;
                }
            } else {
                isPress = false;
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
                if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
                    isPress = false;
                    if (MySurfaceView.soundFlag) {
                        MySurfaceView.mediaPlayer.pause();
                        MySurfaceView.mediaPlayer2.pause();
                    } else {
                        MySurfaceView.mediaPlayer2.pause();
                        MySurfaceView.mediaPlayer.start();
                    }
                    MySurfaceView.Pause_flag = 1;
                    MySurfaceView.gameState = MySurfaceView.GAME_MENU;
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (pointX > btn_exit_x && pointX < btn_exit_x + bmpPause_exit.getWidth()) {
                if (pointY > btn_exit_y && pointY < btn_exit_y + bmpPause_exit.getHeight()) {
                    isPress = true;
                } else {
                    isPress = false;
                }
            } else {
                isPress = false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (pointX > btn_exit_x && pointX < btn_exit_x + bmpPause_exit.getWidth()) {
                if (pointY > btn_exit_y && pointY < btn_exit_y + bmpPause_exit.getHeight()) {
                    isPress = false;
                    MainActivity.instance.finish();
                    System.exit(0);
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (pointX > btn_continue_x && pointX < btn_continue_x + bmpPause_continue.getWidth()) {
                if (pointY > btn_continue_y && pointY < btn_continue_y + bmpPause_continue.getHeight()) {
                    isPress = true;
                } else {
                    isPress = false;
                }
            } else {
                isPress = false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (pointX > btn_continue_x && pointX < btn_continue_x + bmpPause_continue.getWidth()) {
                if (pointY > btn_continue_y && pointY < btn_continue_y + bmpPause_continue.getHeight()) {
                    isPress = false;
                    MySurfaceView.gameState = MySurfaceView.GAMEING;
                }
            }
        }
    }
}
