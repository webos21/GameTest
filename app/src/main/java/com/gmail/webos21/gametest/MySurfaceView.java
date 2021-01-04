package com.gmail.webos21.gametest;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Vector;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

    public static final String TAG = "MySurface";

    public static final int GAME_MENU = 0;
    public static final int GAMEING = 1;
    public static final int GAME_WIN = 2;
    public static final int GAME_LOST = 3;
    public static final int GAME_PAUSE = -1;
    public static int screenW, screenH;
    public static int Pause_flag = 0;
    public static int gameState = GAME_MENU;
    public static boolean soundFlag = true;
    public static Bitmap bmpPause_canvas;
    public static Bitmap bmpPause_canvas2;
    public static int playerWeaponLevel = 1;
    public static Bitmap bmpBullet, bmpBullet2;
    public static Bitmap bmpEnemyBullet;
    public static Bitmap bmpBossBullet;
    public static SoundIcon soundIcon;
    public static int enemyArrayIndex;
    public static Vector<Bullet> vcBulletBoss;
    public static SoundPool sp;
    public static int soundId_long, shoot, enemy_shoot;
    public static MediaPlayer mediaPlayer;
    public static MediaPlayer mediaPlayer2;
    private SurfaceHolder sfh;
    private Paint paint;
    private Paint paint2;
    private Thread th;
    private boolean flag;
    private Canvas canvas;
    private Resources res = this.getResources();
    private Bitmap bmpBackGround;
    private Bitmap bmpStart1;
    private Bitmap bmpStart2;
    private Bitmap bmpSound, bmpSound2;
    private Bitmap bmpGamePause;
    private Bitmap bmpPause_bg;
    private Bitmap bmpPause_back;
    private Bitmap bmpPause_continue;
    private Bitmap bmpPause_exit;
    private Bitmap bmpBoom;
    private Bitmap bmpBoosBoom;
    private Bitmap bmpButton;
    private Bitmap bmpButtonPress;
    private Bitmap bmpEnemyDuck;
    private Bitmap bmpEnemyFly;
    private Bitmap bmpEnemyBoss;
    private Bitmap bmpEnemyNew;
    private Bitmap bmpEnemyWeapon;
    private Bitmap bmpGameWin;
    private Bitmap bmpGameLost;
    private Bitmap bmpPlayer;
    private Bitmap bmpPlayerHp;
    private Bitmap bmpMenu;
    private GameMenu gameMenu;
    private GameBg backGround;
    private GamePause gamePause;
    private Player player;
    private Vector<Enemy> vcEnemy;
    private int createEnemyTime = 150;
    private int count;
    private int enemyArray[][] = {{1, 2, 3, 4}, {2, 1, 4, 2}, {2, 3, 2, 3}, {1, 1, 1}, {5, 2}, {2, 1, 5}, {-1}};
    private boolean isBoss;
    private Random random;
    private Vector<Bullet> vcBullet;
    private int countEnemyBullet;
    private Vector<Bullet> vcBulletPlayer;
    private int countPlayerBullet;
    private Vector<Boom> vcBoom;
    private Boss boss;

    private Bitmap bmpclip;
    private Canvas canvasclip;


    public MySurfaceView(Context context) {
        super(context);
        initView(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setAntiAlias(true);

        setFocusable(true);
        setFocusableInTouchMode(true);

        if (!isInEditMode()) {
            sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
            soundId_long = sp.load(context, R.raw.boom, 1);
            shoot = sp.load(context, R.raw.shoot, 1);
            enemy_shoot = sp.load(context, R.raw.enemy_shoot, 1);
        }

        this.setKeepScreenOn(true);
    }

    void initGame() {
        if (gameState == GAME_MENU) {

            bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.bg3);
            bmpStart1 = BitmapFactory.decodeResource(res, R.drawable.start1);
            bmpStart1 = BitmapFactory.decodeResource(res, R.drawable.start2);

            bmpSound = BitmapFactory.decodeResource(res, R.drawable.sound);
            bmpSound2 = BitmapFactory.decodeResource(res, R.drawable.sound2);

            bmpGamePause = BitmapFactory.decodeResource(res, R.drawable.gamepause);
            bmpPause_bg = BitmapFactory.decodeResource(res, R.drawable.pause_bg);
            bmpPause_back = BitmapFactory.decodeResource(res, R.drawable.pause_back);
            bmpPause_continue = BitmapFactory.decodeResource(res, R.drawable.pause_continue);
            bmpPause_exit = BitmapFactory.decodeResource(res, R.drawable.pause_exit);

            bmpPause_canvas = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);

            bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
            bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
            bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
            bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
            bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck2);
            bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly2);
            bmpEnemyBoss = BitmapFactory.decodeResource(res, R.drawable.enemy_boss);

            bmpEnemyNew = BitmapFactory.decodeResource(res, R.drawable.enemy_new);
            bmpEnemyWeapon = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);


            bmpGameWin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.gamewin), screenW, screenH, false);
            bmpGameLost = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.gamelost), screenW, screenH, false);
            bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
            bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
            bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
            bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
            bmpBullet2 = BitmapFactory.decodeResource(res, R.drawable.bullet2);

            bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
            bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);

            vcBoom = new Vector<>();

            vcBullet = new Vector<>();

            vcBulletPlayer = new Vector<>();

            gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress, bmpStart1, bmpStart2);
            gamePause = new GamePause(bmpPause_bg, bmpPause_back, bmpPause_continue, bmpPause_exit);
            soundIcon = new SoundIcon(bmpSound, bmpSound2);

            backGround = new GameBg(bmpBackGround, bmpGamePause);
            player = new Player(bmpPlayer, bmpPlayerHp);
            vcEnemy = new Vector<>();
            random = new Random();

            boss = new Boss(bmpEnemyBoss);

            vcBulletBoss = new Vector<>();
        }
    }

    public void myDraw() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);

                switch (gameState) {

                    case GAME_MENU:
                        if (Pause_flag == 1) {
                            initGame();

                            isBoss = false;
                            playerWeaponLevel = 1;
                            enemyArrayIndex = 0;
                            Pause_flag = 0;
                        }
                        if (!soundFlag) {
                            mediaPlayer.start();
                        }

                        gameMenu.draw(canvas, paint);
                        soundIcon.draw(canvas, paint);
                        break;

                    case GAMEING:
                        if (!soundFlag) {
                            mediaPlayer2.start();
                        }

                        backGround.draw(canvas, paint);
                        backGround.draw(canvasclip, paint);

                        player.draw(canvas, paint);
                        player.draw(canvasclip, paint);

                        if (!isBoss) {
                            for (int i = 0; i < vcEnemy.size(); i++) {
                                vcEnemy.elementAt(i).draw(canvas, paint);
                                vcEnemy.elementAt(i).draw(canvasclip, paint);
                            }
                            for (int i = 0; i < vcBullet.size(); i++) {
                                vcBullet.elementAt(i).draw(canvas, paint);
                                vcBullet.elementAt(i).draw(canvasclip, paint);
                            }
                        } else {
                            boss.draw(canvas, paint);
                            boss.draw(canvasclip, paint);

                            for (int i = 0; i < vcBulletBoss.size(); i++) {
                                vcBulletBoss.elementAt(i).draw(canvas, paint);
                                vcBulletBoss.elementAt(i).draw(canvasclip, paint);
                            }
                        }

                        for (int i = 0; i < vcBulletPlayer.size(); i++) {
                            vcBulletPlayer.elementAt(i).draw(canvas, paint);
                            vcBulletPlayer.elementAt(i).draw(canvasclip, paint);
                        }

                        for (int i = 0; i < vcBoom.size(); i++) {
                            vcBoom.elementAt(i).draw(canvas, paint);
                            vcBoom.elementAt(i).draw(canvasclip, paint);
                        }
                        break;

                    case GAME_PAUSE:

                        paint2.setAlpha(245);
                        bmpPause_canvas2 = fastblur(bmpclip, 4);
                        canvas.drawBitmap(bmpPause_canvas2, 0, 0, paint);

                        gamePause.draw(canvas, paint2);

                        soundIcon.draw(canvas, paint);
                        break;

                    case GAME_WIN:
                        canvas.drawBitmap(bmpGameWin, 0, 0, paint);
                        break;

                    case GAME_LOST:
                        canvas.drawBitmap(bmpGameLost, 0, 0, paint);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (gameState) {
            case GAME_MENU:
                gameMenu.onTouchEvent(event);
                soundIcon.onTouchEvent(event);
                break;
            case GAMEING:
                player.onTouchEvent(event);
                backGround.onTouchEvent(event);
                break;
            case GAME_PAUSE:
                soundIcon.onTouchEvent(event);
                gamePause.onTouchEvent(event);
                break;
            case GAME_WIN:
            case GAME_LOST:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
                Log.d(TAG, "BackButton on NOT MENU....");
                gameState = GAME_MENU;
                isBoss = false;
                initGame();
                enemyArrayIndex = 0;
            } else if (gameState == GAME_MENU) {
                Log.d(TAG, "BackButton on MENU....");
                MainActivity.instance.finish();
                System.exit(0);
            }
            return true;
        }

        switch (gameState) {
            case GAMEING:
                player.onKeyDown(keyCode, event);
                break;
            case GAME_MENU:
            case GAME_PAUSE:
            case GAME_WIN:
            case GAME_LOST:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
                gameState = GAME_MENU;
            }
            return true;
        }

        switch (gameState) {
            case GAMEING:
                player.onKeyUp(keyCode, event);
                break;
            case GAME_MENU:
            case GAME_PAUSE:
            case GAME_WIN:
            case GAME_LOST:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logic() {
        switch (gameState) {
            case GAME_MENU:
                break;
            case GAMEING:
                backGround.logic();
                player.logic();
                if (!isBoss) {
                    for (int i = 0; i < vcEnemy.size(); i++) {
                        Enemy en = vcEnemy.elementAt(i);
                        if (en.isDead) {
                            vcEnemy.removeElementAt(i);
                        } else {
                            en.logic();
                        }
                    }

                    count++;
                    if (count % createEnemyTime == 0) {
                        for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
                            if (enemyArray[enemyArrayIndex][i] == 1) {
                                int x = random.nextInt(screenW - 100) + 50;
                                vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
                            } else if (enemyArray[enemyArrayIndex][i] == 2) {
                                int y = random.nextInt(30);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50, y));
                            } else if (enemyArray[enemyArrayIndex][i] == 3) {
                                int y = random.nextInt(45);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3, screenW + 50, y));
                            } else if (enemyArray[enemyArrayIndex][i] == 4) {
                                int x = random.nextInt(screenW - 50);
                                vcEnemy.addElement(new Enemy(bmpEnemyNew, 4, x, -50));
                            } else if (enemyArray[enemyArrayIndex][i] == 5) {
                                int x = random.nextInt(screenW - 100) + 50;
                                vcEnemy.addElement(new Enemy(bmpEnemyWeapon, 5, x, -50));
                            }
                        }
                        if (enemyArrayIndex == enemyArray.length - 1) {
                            isBoss = true;
                        } else {
                            enemyArrayIndex++;
                        }
                    }

                    for (int i = 0; i < vcEnemy.size(); i++) {
                        if (player.isCollsionWith(vcEnemy.elementAt(i))) {
                            if (vcEnemy.elementAt(i).type == 5) {
                                if (playerWeaponLevel <= 3)
                                    playerWeaponLevel++;
                            } else {
                                player.setPlayerHp(player.getPlayerHp() - 1);
                                if (playerWeaponLevel > 1)
                                    playerWeaponLevel--;
                                if (!soundFlag) {
                                    sp.play(soundId_long, 1f, 1f, 0, 0, 1);
                                }
                            }

                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }

                    countEnemyBullet++;
                    if (countEnemyBullet % 15 == 0) {
                        for (int i = 0; i < vcEnemy.size(); i++) {
                            Enemy en = vcEnemy.elementAt(i);

                            int bulletType;
                            switch (en.type) {
                                case Enemy.TYPE_FLY:
                                    bulletType = Bullet.BULLET_FLY;
                                    vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
                                    if (!soundFlag) {
                                        sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
                                    }
                                    break;

                                case Enemy.TYPE_DUCKL:
                                    bulletType = Bullet.BULLET_DUCK;
                                    vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
                                    if (!soundFlag) {
                                        sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
                                    }
                                    break;

                                case Enemy.TYPE_DUCKR:
                                    bulletType = Bullet.BULLET_DUCK;
                                    vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
                                    if (!soundFlag) {
                                        sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
                                    }
                                    break;

                                case Enemy.TYPE_NEW:
                                    bulletType = Bullet.BULLET_BOSS;
                                    vcBullet.add(new Bullet(bmpBossBullet, en.x + 10, en.y + 20, bulletType, Bullet.DIR_DOWN_LEFT));
                                    if (!soundFlag) {
                                        sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
                                    }
                                    vcBullet.add(new Bullet(bmpBossBullet, en.x + 10, en.y + 20, bulletType, Bullet.DIR_DOWN));
                                    if (!soundFlag) {
                                        sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
                                    }
                                    vcBullet.add(new Bullet(bmpBossBullet, en.x + 10, en.y + 20, bulletType, Bullet.DIR_DOWN_RIGHT));
                                    if (!soundFlag) {
                                        sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
                                    }
                                    break;
                                case Enemy.TYPE_WEAPON:
                                    break;
                            }

                        }
                    }

                    for (int i = 0; i < vcBullet.size(); i++) {
                        Bullet b = vcBullet.elementAt(i);
                        if (b.isDead) {
                            vcBullet.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }

                    for (int i = 0; i < vcBullet.size(); i++) {
                        if (player.isCollsionWith(vcBullet.elementAt(i))) {
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            if (playerWeaponLevel > 1) {
                                playerWeaponLevel--;
                            }
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }

                    for (int i = 0; i < vcBulletPlayer.size(); i++) {
                        Bullet blPlayer = vcBulletPlayer.elementAt(i);
                        for (int j = 0; j < vcEnemy.size(); j++) {
                            if (vcEnemy.elementAt(j).isCollsionWith(blPlayer)) {
                                vcBoom.add(new Boom(bmpBoom, vcEnemy.elementAt(j).x, vcEnemy.elementAt(j).y, 7));
                                if (!soundFlag) {
                                    sp.play(soundId_long, 1f, 1f, 0, 0, 1);
                                }
                            }
                        }
                    }
                } else {
                    boss.logic();
                    if (boss.hp >= 67 && countPlayerBullet % 13 == 0) {
                        vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 50, boss.y + 30, Bullet.BULLET_FLY));
                        if (!soundFlag) {
                            sp.play(enemy_shoot, 1f, 1f, 0, 0, 1);
                        }
                    } else if (67 > boss.hp && boss.hp >= 34 && countPlayerBullet % 8 == 0) {
                        vcBulletBoss.add(new Bullet(bmpEnemyBullet, boss.x + 50, boss.y + 30, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_Random));
                        if (!MySurfaceView.soundFlag) {
                            MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);
                        }
                    } else if (34 > boss.hp && countPlayerBullet % 13 == 0) {
                        vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 50, boss.y + 10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN));
                        vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 50, boss.y + 10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_LEFT));
                        vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 50, boss.y + 10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_RIGHT));
                        if (!MySurfaceView.soundFlag) {
                            MySurfaceView.sp.play(MySurfaceView.enemy_shoot, 1f, 0.5f, 0, 0, 1);
                        }
                    }
                    if (countPlayerBullet % 100 == 0) {
                        Bullet.flag = -Bullet.flag;
                    }

                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        Bullet b = vcBulletBoss.elementAt(i);
                        if (b.isDead) {
                            vcBulletBoss.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }

                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            if (playerWeaponLevel > 1) {
                                playerWeaponLevel--;
                            }
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }

                    for (int i = 0; i < vcBulletPlayer.size(); i++) {
                        Bullet b = vcBulletPlayer.elementAt(i);
                        if (boss.isCollsionWith(b)) {
                            if (!soundFlag) {
                                sp.play(soundId_long, 1f, 1f, 0, 0, 1);
                            }
                            if (boss.hp <= 0) {
                                gameState = GAME_WIN;
                            } else {
                                b.isDead = true;
                                boss.setHp(boss.hp - 1);
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 15, boss.y + 25, 5));
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 35, boss.y + 40, 5));
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 65, boss.y + 20, 5));
                            }
                        }
                    }
                }
                countPlayerBullet++;
                if (countPlayerBullet % 10 == 0) {
                    if (playerWeaponLevel == 1)
                        vcBulletPlayer.add(new Bullet(bmpBullet, player.x - bmpPlayer.getWidth() / 5, player.y - 15, Bullet.BULLET_PLAYER));
                    else if (playerWeaponLevel == 2) {
                        vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth() / 5, player.y - 15, Bullet.BULLET_PLAYER, Bullet.DIR_UP_LEFT2));
                        vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth() / 5, player.y - 15, Bullet.BULLET_PLAYER, Bullet.DIR_UP_RIGHT2));
                    } else if (playerWeaponLevel == 3) {
                        vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth() / 5, player.y - 15, Bullet.BULLET_PLAYER, Bullet.DIR_UP_LEFT2));
                        vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth() / 5, player.y - 15, Bullet.BULLET_PLAYER));
                        vcBulletPlayer.add(new Bullet(bmpBullet2, player.x - bmpPlayer.getWidth() / 5, player.y - 15, Bullet.BULLET_PLAYER, Bullet.DIR_UP_RIGHT2));
                    }
                    if (!soundFlag) {
                        sp.setVolume(shoot, 0.5f, 0.5f);
                        sp.play(shoot, 0.8f, 0.8f, 0, 0, 1);
                    }
                }

                for (int i = 0; i < vcBulletPlayer.size(); i++) {
                    Bullet b = vcBulletPlayer.elementAt(i);
                    if (b.isDead) {
                        vcBulletPlayer.removeElement(b);
                    } else {
                        b.logic();
                    }
                }

                for (int i = 0; i < vcBoom.size(); i++) {
                    Boom boom = vcBoom.elementAt(i);
                    if (boom.playEnd) {
                        vcBoom.removeElementAt(i);
                    } else {
                        vcBoom.elementAt(i).logic();
                    }
                }
                break;
            case GAME_PAUSE:

                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 17) {
                    Thread.sleep(17 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap fastblur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        if (radius < 1) {
            return (null);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }
        yw = yi = 0;
        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;
        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                sir = stack[i + radius];
                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];
                rbs = r1 - Math.abs(i);
                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];
                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.bg1);
        mediaPlayer2 = MediaPlayer.create(getContext(), R.raw.bg2);
        mediaPlayer.setLooping(true);
        mediaPlayer2.setLooping(true);

        bmpclip = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Config.ARGB_8888);
        canvasclip = new Canvas(bmpclip);
        canvasclip.drawColor(Color.WHITE);

        initGame();
        flag = true;

        th = new Thread(this);
        th.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }


}
