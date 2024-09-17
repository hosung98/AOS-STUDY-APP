package kr.hs.sugong.simplenote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import kr.hs.sugong.simplenote.model.NoteItem;

public class NoteDetailActivity extends BaseDetailActivity  {
    private int position;
    private NoteItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//        Intent로 불러준 내용을 입력받는다.
        item = (NoteItem) intent.getSerializableExtra("item");

//      스피너, 내용, 제목 선택 불가
        sp_type.setClickable(false);
        tv_content.setFocusableInTouchMode(false);
        tv_title.setFocusableInTouchMode(false);

//      버튼이름 변경
        btn_save.setText("되돌아가기");
//      버튼 클릭 시 폼을 종료한다.
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        displayNoteItem();
    }

    private void displayNoteItem() { // 선택한 아이템의 내용을 보여준다.
//      스피너를 저장된 내용에 맞게 선택한다.
        sp_type.setSelection(getTypePosition(item.getType()));
//      제목을 저장된 제목에 맞게 입력한다.
        tv_title.setText(item.getTitle());
//      내용을 저장된 내용에 맞게 입력한다.
        tv_content.setText(item.getContent());
    }

    private int getTypePosition(NoteItem.Type type) {
//      getTypePosition메소드를 이용하여 스피너의 위치를 설정한다.
        if(type == NoteItem.Type.IDEA) return 1;
        else if (type == NoteItem.Type.PERSNAL) return 2;
        else if (type == NoteItem.Type.APPOINTMENT) return 3;
        else return 0;
    }

}
