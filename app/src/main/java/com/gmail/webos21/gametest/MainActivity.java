package com.gmail.webos21.gametest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    public static MainActivity instance;

    private MySurfaceView myView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.main);

        myView = findViewById(R.id.gameview);
    }

    @Override
    public void onBackPressed() {
        myView.onKeyDown(KeyEvent.KEYCODE_BACK, null);
    }

}