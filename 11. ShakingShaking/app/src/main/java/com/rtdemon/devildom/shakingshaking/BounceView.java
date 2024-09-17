package com.rtdemon.devildom.shakingshaking;

import android.content.Context;
import android.database.DefaultDatabaseErrorHandler;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Administrator on 2016-03-20.
 */

public class BounceView extends View {
    float radius = 100.0f; // 공의 반지름
    float centerX, centerY; // 공의 x좌표, y좌표
    float width, height; // 너비, 높이
    float cRadius = radius; // 크기가 변경되는 공의 반지름
    float scaleFactor = 1.1f; // 비율
    float maxRadius = 250.0f; // 최대커질수 있는 공의 반지름
    Paint paint; // 공 그리기
    private boolean isRunning; // 동작확인
    ArrayList<Ball> ballList; // 작은공 객체
    private Random random;

    public BounceView(Context context) {
        super(context);
        init();
    }

    public BounceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//       화면크기를 구한다.
        DisplayMetrics disp = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(disp);
        width = disp.widthPixels;
        height = disp.heightPixels;

//      화면 가운데
        centerX = width / 2;
        centerY = height / 2;

//      큰 공색
        paint = new Paint();
        paint.setColor(Color.BLACK);

        handler.sendEmptyMessageDelayed(0, 50); // 타이머에 따라 핸들러의 과정을 반복함
        isRunning = true; // 동작중
        ballList = new ArrayList<Ball>(); // 작은공을 담기위한 변수 생성
        random = new Random(System.currentTimeMillis());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE); // 배경색
        canvas.drawCircle(centerX, centerY, cRadius, paint); // 가운데 큰원인 검은색 공

        // 작은공의 색
        Paint p = new Paint();
        p.setColor(Color.rgb(255,0,0));
        for (Ball ball : ballList) {
            if (ball != null)
            canvas.drawCircle(ball.x, ball.y, 50, p);
        }
    }

    Handler handler = new Handler() { // Handler는 Thread와는 다르게 동작중에도 UI를 변경할 수 있다.
        @Override
        public void handleMessage(Message msg) {
//            큰 공의 비율
            cRadius *= scaleFactor;

//            큰공의 크기가 큰 한계점에 이르면 공이 작아진다.
            if (cRadius > maxRadius) {
                scaleFactor = 0.9f; // 비율
                cRadius = maxRadius;
//            큰공의 크기가 작은 한계점에 이르면 공이 커진다.
            } else if (cRadius < radius) {
                scaleFactor = 1.1f; // 비율
                cRadius = radius;
                maxRadius = 250.0f; // 큰 공의 반지름
            }


            for (int i = 0; i <  ballList.size(); i++) {
                Ball ball = ballList.get(i);
                if (ball != null) {
                    if ((ball.x < 0 || ball.x > width) && (ball.y < 0 || ball.y > height)) { //화면이탈
                        ballList.set(i, null);
                    } else {
                        ball.increment();
                        ball.next(centerX, centerY);
                    }
                }
            }
            invalidate();
            if (isRunning) sendEmptyMessageDelayed(0, 50); // 타이머에 따라 핸들러의 과정을 반복함
        }
    };

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning; // 작동여부
    }

    public void shake() {
        maxRadius = 400.0f; // 가운데 검은공의 최대로 커질수 있는 반지름
        scaleFactor = 1.15f; // 비율
        for (int i = 0; i < 4; i++) {
            Ball b = new Ball(); //생성되는 공 개수

            b.angle = random.nextInt(360);
            // 작은공이 나오는 각도 180 으로 하면 밑으로 만 나옴
            ballList.add(b);
            //b.next(centerX,centerX);
        }
    }

    private class Ball {
        float x, y;
        float distance = 10; // 거리
        int angle; // 각도

        public void next(float centerX, float centerY) { // 작은공의 위치 생성
            y = (float) (Math.sin(angle * Math.PI/180) * distance + centerY);
            x = (float) (Math.cos(angle * Math.PI/180) * distance + centerX);
        }

        public void increment() {
            distance *= 1.1f; // 거리가 멀어지는 속도
        }
    }
}
