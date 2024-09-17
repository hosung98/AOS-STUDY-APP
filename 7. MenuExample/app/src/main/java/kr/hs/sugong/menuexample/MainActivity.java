package kr.hs.sugong.menuexample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  { //AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    ImageView iv_brush ;
    Button btn_lines;

    EditText input;

    ArrayList<String> items;

    private final int MENU_EXIT = 0;
    private final int MENU_TOAST = 1;

    ArrayAdapter<String> adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        listview = (ListView) findViewById(R.id.listView);

//        ArrayList를 이용한 어뎁터 넣기
        items = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.cities)));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listview.setAdapter(adapter);

//        구분선 설정
        listview.setDivider(new ColorDrawable(Color.BLUE));
        listview.setDividerHeight(4);
//
        input = (EditText) findViewById(R.id.input);
        findViewById(R.id.btn_add).setOnClickListener(listener);

//        배열을 이용한 어뎁터 넣기
//        String[] cities = getResources().getStringArray(R.array.cities);
//        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

//        스피너(콤보박스)용
//        listview.setOnItemSelectedListener(this);

//        리스트뷰용
//        listview.setOnItemClickListener(this);

//        컨텍스트 메뉴, 옵션메뉴
//        iv_brush = (ImageView) findViewById(R.id.iv_brush);
//        btn_lines = (Button) findViewById(R.id.btn_lines);

//        컨텍스트 적용
//        registerForContextMenu(iv_brush);
//        registerForContextMenu(btn_lines);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = input.getText().toString();
            if (!text.trim().equals("")) {
                items.add(text);
                adapter.notifyDataSetChanged();
                input.setText("");
                input.requestFocus();
                listview.setSelection(items.size()-1);
            }
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v == iv_brush) {
            menu.setHeaderTitle("브러시 색상");
            menu.add(0, 1, 1, "빨강");
            menu.add(0, 2, 2, "파랑");
            menu.add(0, 3, 3, "회색");
        } else if (v == btn_lines) {
            menu.setHeaderTitle("선의 두께");
            menu.add(1, 10, 1, "1픽셀");
            menu.add(1,11,2,"2픽셀");
            menu.add(1,12,3,"3픽셀");
            menu.add(1,13,4,"4픽셀");
            menu.add(1,14,5,"5픽셀");
            menu.add(1,15,6,"6픽셀");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        xml로 하는 방법
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        코드로 하는 방법
//        MenuItem menu1 = menu.add(0, MENU_EXIT, 0, "종료");
//        menu1.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
//        MenuItem menu2 = menu.add(0, MENU_TOAST, 1, "토스트");
//        menu2.setIcon(android.R.drawable.ic_dialog_alert);

        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:Toast.makeText(this,"빨강색 선택",Toast.LENGTH_SHORT).show(); break;
            case 2:Toast.makeText(this,"파랑색 선택",Toast.LENGTH_SHORT).show(); break;
            case 3:Toast.makeText(this,"회색 선택",Toast.LENGTH_SHORT).show(); break;

            case 10:Toast.makeText(this,"1픽셀 선택",Toast.LENGTH_SHORT).show(); break;
            case 11:Toast.makeText(this,"2픽셀 선택",Toast.LENGTH_SHORT).show(); break;
            case 12:Toast.makeText(this,"3픽셀 선택",Toast.LENGTH_SHORT).show(); break;
            case 13:Toast.makeText(this,"4픽셀 선택",Toast.LENGTH_SHORT).show(); break;
            case 14:Toast.makeText(this,"5픽셀 선택",Toast.LENGTH_SHORT).show(); break;
            case 15:Toast.makeText(this,"6픽셀 선택",Toast.LENGTH_SHORT).show(); break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:finish();return true;
            case R.id.action_toast:
                Snackbar.make(findViewById(R.id.text), "하단에 스낵바로 나타나는 내용입니다.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Toast.makeText(this,"메뉴 항목 선택됨",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
//     스피너용
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        String item = (String) listview.getAdapter().getItem(position);
//        Toast.makeText(this,item + "가 선택됨",Toast.LENGTH_SHORT).show();

//    }
//      스피너용
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        Toast.makeText(this, "선택 해제됨",Toast.LENGTH_SHORT).show();
//    }

//      리스트용
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String item = (String) listview.getAdapter().getItem(position);
//        Toast.makeText(this,item + "가 클릭됨",Toast.LENGTH_SHORT).show();
//    }
}
