package kr.hs.sugong.simplenote;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.hs.sugong.simplenote.adapter.DBAdapter;
import kr.hs.sugong.simplenote.adapter.DBNoteAdapter;
import kr.hs.sugong.simplenote.adapter.NoteAdapter;
import kr.hs.sugong.simplenote.db.DBHelper;
import kr.hs.sugong.simplenote.model.NoteItem;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listview;
    public static DBNoteAdapter adapter;
    protected DBAdapter dbAdapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      상단에 나타나는 메뉴들
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      Listview객체 생성
        listview = (ListView) findViewById(R.id.notelist);

//      DB를 연결하기 위한 Adapter클래스 객체생성
        dbAdapter = (((MainApplication) getApplicationContext()).getDbAdapter());
        try {
//          ListView에 내용이 들어갈 수 있도록 DBNoteAdapter실행
            adapter = new DBNoteAdapter(this, dbAdapter.queryAllNotes(),true);
            listview.setAdapter(adapter);
//          ListView아이템 클릭하였을 때 이벤트 생성
            listview.setOnItemClickListener(this);
//           리스트뷰를 ContextMenu를 사용하는 뷰로 등록한다.
            registerForContextMenu(listview);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//      리스트의 아이템을 길게 눌렀을 때 나타나는 메뉴들
        if (v.getId() == R.id.notelist) {
            menu.add(0,1,0,"수정");
            menu.add(0,2,0,"삭제");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;


//      adapter를 이용하여 현재 위치를 알아낸다.
        Cursor cursor = adapter.getCursor();
//      cursor가 그 위치에 갈 수 있도록 position을 위치시킨다.
        cursor.moveToPosition(position);

        switch (item.getItemId()) {
            case 1: //수정버튼을 클릭하면 NoteUpdateActivity_T클래스를 실행한다.
                Intent intent = new Intent(this, NoteUpdateActivity_T.class);
                NoteItem nitem = NoteItem.getNoteItem(cursor);
                intent.putExtra("item", nitem);
                startActivityForResult(intent,1);
                break;
            case 2: //삭제버튼을 실행하면 해당 행을 삭제한다.
                int id = cursor.getInt(0);
                dbAdapter.delete(id);
                adapter.changeCursor(dbAdapter.queryAllNotes());
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }
//    menu_main레이아웃의 객체를 Inflater를 통하여 생성한다.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.new_note) {
//            무엇을 하고 싶은 의도 (NoteActivity를 불러오고싶다)
            Intent intent = new Intent(this , NoteActivity.class);

//            시작하고 결과까지 받아옴
            startActivityForResult(intent , 0);

//            시작하고 끝남
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //StartActivityForResult함수를 사용하면 자동으로 실행된다.
        if (resultCode == Activity.RESULT_OK) {
            adapter.changeCursor( dbAdapter.queryAllNotes() );
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 아이템을 선택했을 때
//        상세정보를 나타내는 NoteDetailActivity클래스를 나타낸다.
        Intent intent = new Intent(this, NoteDetailActivity.class);

//        Cursor를 이용하여 아이템을 선택한 위치를 가리킨다.
        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);

//        실행한다.
        NoteItem item = NoteItem.getNoteItem(cursor);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}
