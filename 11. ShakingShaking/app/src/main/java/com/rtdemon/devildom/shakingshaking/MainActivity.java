package com.rtdemon.devildom.shakingshaking;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

    private long lastTime;
//    속도
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;

    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 800;


    private SensorManager sensorManager;
    private Sensor accel;

    BounceView ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      센서서비스를 사용
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

//      핸드폰 움직일때 볼객체 생성
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

            ball = (BounceView) findViewById(R.id.ball);

    }

    @Override
    protected void onResume() {
        super.onResume();

//      핸드폰을 움직일때 이벤트 생성
        if (accel != null) {
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        sensorManager은 센서서비스를 사용하기 위한 객체인데 이 값이 없다면 센서를 사용할 수 없다.
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 센서의 값이 가속도계이면
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // 시스템의 현재시간
            long currentTime = System.currentTimeMillis();
            long gapOfTime = (currentTime - lastTime);
            if (gapOfTime > 100) { //0.1초
                lastTime = currentTime;
//              핸드폰을 위아래로 기울였을때
                x = event.values[0];
//               핸드폰을 양 옆으로 기울였을때
                y = event.values[1];
//                핸드폰을 대각으로 기울였을때
                z = event.values[2];

//             속도계산
                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gapOfTime * 10000;
                if (speed > SHAKE_THRESHOLD) { //흔들었음
                    ball.shake();
                }
//                마지막에 위치한 위아래기울기 측정
                lastX = event.values[0];
//                마지막에 위치한 양옆기울기 측정
                lastY = event.values[1];
//                마지막에 위치한 대각기울기 측정
                lastZ = event.values[2];
            }
    }
}

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
