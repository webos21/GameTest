package com.pg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;


/**
 * @author Himi
 */
public class GameMenu {

    private Bitmap bmpMenu;

    private Bitmap bmpButton, bmpButtonPress, bmpstart1, bmpstart2;

    private int btnX, btnY;

    private Boolean isPress;

    public GameMenu(Bitmap bmpMenu, Bitmap bmpButton, Bitmap bmpButtonPress, Bitmap bmpStart1, Bitmap bmpStart2) {
        this.bmpMenu = Bitmap.createScaledBitmap(bmpMenu, MySurfaceView.screenW, MySurfaceView.screenH,  false);
        this.bmpButton = bmpButton;
        this.bmpButtonPress = bmpButtonPress;
        this.bmpstart1 = bmpStart1;
        this.bmpstart2 = bmpStart2;

        btnX = (MySurfaceView.screenW / 2) - (bmpButton.getWidth() / 2);
        btnY = (MySurfaceView.screenH / 2) - (bmpButton.getHeight() / 2);

        isPress = false;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bmpMenu, 0, 0, paint);
        canvas.drawBitmap(bmpstart1, -35, MySurfaceView.screenH / 9, paint);
        if (isPress) {
            canvas.drawBitmap(bmpButtonPress, btnX, btnY, paint);
        } else {
            canvas.drawBitmap(bmpButton, btnX, btnY, paint);
        }
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
                        //MySurfaceView.mediaPlayer2.start();
                    } else {
                        MySurfaceView.mediaPlayer.pause();
                        MySurfaceView.mediaPlayer2.start();
                    }
                    MySurfaceView.gameState = MySurfaceView.GAMEING;
                }
            }
        }
    }

}
