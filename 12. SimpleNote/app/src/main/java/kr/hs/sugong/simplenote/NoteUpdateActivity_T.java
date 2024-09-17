package kr.hs.sugong.simplenote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

import kr.hs.sugong.simplenote.model.NoteItem;
import kr.hs.sugong.simplenote.util.FileUtil;

/**
 * Created by Administrator on 2016-03-13.
 */
public class NoteUpdateActivity_T extends BaseDetailActivity {

    private NoteItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      다른 Activity를 불러올 수 있도록 한다.
        Intent intent = getIntent();

        item = (NoteItem) intent.getSerializableExtra("item");

//      UI를 수정한다.
        fetchUI();

    }

    private void fetchUI() {
//      저장된 제목을 입력한다.
        tv_title.setText(item.getTitle());
//      저장된 내용을 입력한다.
        tv_content.setText(item.getContent());
//      저장된 타입을 선택한다.
        sp_type.setSelection(NoteItem.getTypeCode(item.getType()));
//      버튼 이름을 수정하기 변경한다.
        btn_save.setText("수정하기");
//      버튼 클릭 시 이벤트 생성
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {
//      제목입력
        item.setTitle(tv_title.getText().toString());
//      내용입력
        item.setContent(tv_content.getText().toString());
//      타입선택
        item.setType(NoteItem.getTypeOfCode(sp_type.getSelectedItemPosition()));
        dbAdapter.update(item, item.getId());

        setResult(RESULT_OK);

        File extstore = Environment.getExternalStorageDirectory();
        try {
            FileUtil.saveObjectFile( extstore.getAbsolutePath() + "/memo_" + item.getId() + ".obj", item);
        } catch (IOException e) {
            e.printStackTrace();
        }
//      UpdateActivity_T 종료
        finish();
    }
}
