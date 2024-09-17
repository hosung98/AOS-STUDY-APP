package kr.hs.sugong.drawexample.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by Administrator on 2016-02-21.
 */
public class FreeLine extends View{

    public ArrayList<Vertex> arVertex;
    public Paint paint;
    public static int color_value = Color.BLACK;
    public static int bold_value = 3;

    public FreeLine(Context context) {super(context);}

    public FreeLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        arVertex = new ArrayList<Vertex>();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    // 정점 하나에 대한 정보를가지는 클래스
    public class Vertex {
        float x, y;
        boolean draw;
        int color, bold;
        Vertex(float ax, float ay, boolean ad, int color_num, int bold_num) {
            x = ax;
            y = ay;
            draw = ad;
            color = color_num;
            bold = bold_num;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawColor(0xffffffff);
// 정점을 순회하면서 선분으로 잇는다.
        for (int cnt=0;cnt<arVertex.size();cnt++) {
            if (arVertex.get(cnt).draw) {
                paint.setColor(arVertex.get(cnt).color);
                paint.setStrokeWidth(arVertex.get(cnt).bold);
                canvas.drawLine(arVertex.get(cnt-1).x, arVertex.get(cnt-1).y, arVertex.get(cnt).x, arVertex.get(cnt).y, paint);

            }
        }
    }
    // 터치 이동시마다 정점들을 추가한다.
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            arVertex.add(new Vertex(event.getX(), event.getY(), false, color_value,bold_value));
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            arVertex.add(new Vertex(event.getX(), event.getY(), true,color_value,bold_value));
            invalidate();
            return true;
        }
        return false;
    }



}
