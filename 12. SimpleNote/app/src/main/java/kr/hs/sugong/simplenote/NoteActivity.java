package kr.hs.sugong.simplenote;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import kr.hs.sugong.simplenote.model.NoteItem;

public class NoteActivity extends BaseDetailActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewNote();
            }
        });

    }

    private void addnewNote() {
//      제목객체를 불러와 변수에 저장한다.
        String title = ((EditText) findViewById(R.id.newnote_title)).getText().toString();
        if (title.trim().equals("")) {
            showSimpleAlert("제목을 입력하세요");
            return;
        }

//      내용객체를 불러와 변수에 저장한다.
        String content = ((EditText) findViewById(R.id.newnote_content)).getText().toString();
        if (content.trim().equals("")) {
            showSimpleAlert("내용을 입력하세요");
            return;
        }

//      item객체에 제목, 내용, 시간, 타입을 저장한다.
        NoteItem item = new NoteItem();
        item.setTitle(title);
        item.setContent(content);
        item.setDate(Calendar.getInstance().getTime());
        item.setType(getNoteType());

//      DB에 입력한 내용을 저장한다.
        dbAdapter.insert(item);

//      핸드폰을 종료한다.
        setResult(Activity.RESULT_OK);
        finish();
    }

    private NoteItem.Type getNoteType() {
//      스피너를 통해 타입을 가져와 돌려준다.
        Spinner spinner = (Spinner) findViewById(R.id.newnote_type);
        String itemtype = spinner.getSelectedItem().toString();

        if (itemtype.equals("아이디어")) {
            return NoteItem.Type.IDEA;
        } else if (itemtype.equals("개인")) {
            return NoteItem.Type.PERSNAL;
        } else if (itemtype.equals("약속")) {
            return NoteItem.Type.APPOINTMENT;
        } else {
            return NoteItem.Type.NORMAL;
        }
    }

    private void showSimpleAlert(String s) {
//      잘못입력했을 때 경고메시지를 나타낸다.
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("경고");
        ab.setMessage(s);
        ab.setPositiveButton("확인", null);
        ab.show();
    }
}
