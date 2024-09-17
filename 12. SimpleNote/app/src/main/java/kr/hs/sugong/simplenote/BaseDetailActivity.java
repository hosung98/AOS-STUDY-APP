package kr.hs.sugong.simplenote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import kr.hs.sugong.simplenote.adapter.DBAdapter;
import kr.hs.sugong.simplenote.model.NoteItem;

/**
 * Created by Administrator on 2016-02-28.
 */
public class BaseDetailActivity extends AppCompatActivity {
//  제목입력 텍스트
    protected EditText tv_title ;
//  내용입력 텍스트
    protected EditText tv_content ;
//  타입 스피너
    protected Spinner sp_type;
//  저장버튼
    protected Button btn_save;
//  DB연결자
    protected DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      DB를 연결하기 위한 어댑터
        dbAdapter = (((MainApplication) getApplicationContext()).getDbAdapter());

        tv_title = (EditText) findViewById(R.id.newnote_title);
        tv_content = (EditText) findViewById(R.id.newnote_content);
        sp_type = (Spinner) findViewById(R.id.newnote_type);
        btn_save = (Button) findViewById(R.id.newnote_btn);
    }
}




