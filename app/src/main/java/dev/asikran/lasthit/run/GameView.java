package dev.asikran.lasthit.run;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import dev.asikran.lasthit.R;
import dev.asikran.lasthit.assets.Creep;

/**
 * Created by JuanErnesto on 07-12-2014.
 */
public class GameView extends SurfaceView {

    private static final int creepResImage = R.drawable.creep;

    private Context context;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private ArrayList<Creep> creeps;
    private Paint scorePaint;
    private long lastClick;
    private int score;
    private int life;

    public GameView(Context context) {
        super(context);

        this.context = context;
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                init();

                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    public void init(){
        creeps = new ArrayList<Creep>();
        creeps.add(createCreep());

        life = 3;

        score = 0;
        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(20);
    }

    public Creep createCreep(){
        Bitmap creepBitmap = BitmapFactory.decodeResource(getResources(), creepResImage);
        return new Creep(getWidth() / 2, 0, 5, 0, 1, 20, 500, creepBitmap);
    }

    public void update(){
        if(life == 0){
            score = 0;
            life = 3;
        }

        for(Creep creep : creeps){
            creep.takeHit(6);
            if(creep.getPosY() >= getHeight() || creep.getCurrentHp() <= 0){
                life--;
                creeps.remove(creep);
                creeps.add(createCreep());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        for(Creep creep : creeps){
            creep.move();
            creep.onDraw(canvas);
        }

        canvas.drawText("SCORE: "+score, 10, 20, scorePaint);
        canvas.drawText("Life: "+life, 10, 60, scorePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 500) {
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()) {
                for(Creep creep : creeps){
                    if (creep.isColliding(event.getX(), event.getY())) {
                        creep.takeHit(100);
                        if(creep.getCurrentHp() <= 0) {
                            score++;
                            creeps.remove(creep);
                            creeps.add(createCreep());
                        }else {
                            creep.resetHP();
                        }

                        break;
                    }
                }
            }
        }
        return true;
    }
}
