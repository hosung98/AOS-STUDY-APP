package kr.hs.sugong.sensorball;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity implements SensorEventListener {

    SensorManager mSensorManager ; // 센서를 사용하기위하여 선언
    Sensor accel , magneto ;
    BallView bv ; // BallView 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀바 없음
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 가속도 센서
        magneto = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) ; // 방향센서
        bv = (BallView) findViewById(R.id.ball);
    }

    @Override
    protected void onResume() {
        /* Activity가 전면에 나타날 때 대부분의 상황에서 호출된다.
        처음 실행했을 때, onCreate() 이후에도 호출된다.*/
       super.onResume();
        // sensor 리스너에 등록, SENSOR_DELAY_UI는 UI 갱신에 필요한 정도의 주기
        mSensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI) ;
        mSensorManager.registerListener(this , magneto , SensorManager.SENSOR_DELAY_UI) ;
    }

    @Override
    protected void onPause() { // Activity가 사용자의 시선에서 가려지는 경우에 호출
        super.onPause();
        /* 어플이 종료 후에도 리스너가 계속 등록되어있으면
         불필요하게 베터리가 소모되기 때문에 리스너를 제거.*/
        mSensorManager.unregisterListener(this);
    }

    float [] mGravity ; // 중력
    float [] mGeomagnetic ; //

    @Override
    public void onSensorChanged(SensorEvent event) { //센서값 변경될 때마다
        if( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ){
            mGravity = event.values ;
        }
        if( event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mGeomagnetic = event.values ;//
        }

        if( mGravity != null && mGeomagnetic != null){ //화면을 움직일때

            float R[] = new float[9] ;
            float I[] = new float[9] ;
            boolean success = SensorManager.getRotationMatrix( R , I , mGravity , mGeomagnetic ) ;
            if( success ){
                float orientation[] = new float[3] ;
                SensorManager.getOrientation(R , orientation) ;
//                float azimut = orientation[0] ;
                float pitch = orientation[1] ;
                float roll = orientation[2] ;
                bv.move( Math.round(roll * 100) , Math.round(pitch * 100 * -1) );
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
