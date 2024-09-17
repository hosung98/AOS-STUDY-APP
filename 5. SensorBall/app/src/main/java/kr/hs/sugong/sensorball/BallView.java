package kr.hs.sugong.sensorball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import android.view.View;
import android.view.WindowManager;

public class BallView extends View{

    float radius = 50.0f ; // 반지름
    float x , y ; // 공의 x축, 공의 y축
    float screenW , screenH ; // 핸드폰의 너비, 핸드폰의 높이
    Paint mPaint = new Paint() ; // 공의 색을 넣기위해 선언

    public BallView(Context context) {
        super(context);
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intit() ;
    }

    private void intit(){
        // 핸드폰의 너비 높이를 구한다.
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE); //
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels ; // 핸드폰의 너비
        screenH = metrics.heightPixels ; // 핸드폰의 높이
        mPaint.setColor (Color.RED) ; // 공의 색깔
    }

    public void move(float deltaX , float deltaY){ // 공 움직이기
        // 공이 화면밖에 빠져나가지 않도록 제어
        x += deltaX ; y += deltaY ;
        if( x < radius ) x = radius ;
        if( x > screenW - radius ) x = screenW - radius ;

        if( y < radius) y = radius ;
        if( y > screenH - radius*2 ) y = screenH - radius*2 ;
        invalidate();// 화면 갱신
    }

    @Override
    protected void onDraw(Canvas canvas) { // 공 디자인
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE); // 배경색
        canvas.drawCircle(x , y , radius , mPaint); // 공그리기
    }
}
