package dev.asikran.lasthit.run;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
    static final long FPS = 10;
    private GameView gameView;
    private boolean running = false;

    public GameLoopThread(GameView gameView) {
        this.gameView = gameView;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = gameView.getHolder().lockCanvas();
                synchronized (gameView.getHolder()) {
                    gameView.update();
                    gameView.onDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    gameView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}  