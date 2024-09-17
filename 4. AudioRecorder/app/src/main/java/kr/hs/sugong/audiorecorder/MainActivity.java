package kr.hs.sugong.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder mrecorder;
    private MediaPlayer mplayer;
    boolean isRecording , isPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onRecord(View view) {
        if (isRecording) return;
        mrecorder = new MediaRecorder();
        mrecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mrecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mrecorder.setOutputFile(getFileName());
        mrecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mrecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mrecorder.start();
        isRecording = true;
    }

    public void onStopRecord(View view) { // 중지 버튼을 누를때
        if (isRecording) { // 녹음중 일때
            mrecorder.stop(); // 녹음 중인것을 중지
            mrecorder.release(); //
            mrecorder = null; // 변수 초기화
            isRecording = false;
        } else if (isPlaying) { // 재생중 일때
            mplayer.release();
            mplayer = null; // 변수 초기화
            isPlaying = false;
        }
    }

    public void onPlay(View view) {
        if (isPlaying) return; // 플레이 중일때 또 누르면 제어
        mplayer = new MediaPlayer();
        mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onStopRecord(null); // onStopRecord 실행
            }
        });
        try {
            mplayer.setDataSource(getFileName()); // 파일을 저장경로 지정
            mplayer.prepare(); // 플레이 준비
            mplayer.start(); // 플레이 시작
            isPlaying = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName() {
        // "/audiorecord.3gp" 파일로 저장
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecord.3gp";
    }
}

