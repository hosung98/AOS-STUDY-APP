package kr.hs.sugong.simplenote.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kr.hs.sugong.simplenote.db.DBHelper;
import kr.hs.sugong.simplenote.model.NoteItem;

/**
 * Created by Administrator on 2016-03-12.
 */

public class DBAdapter {

    private DBHelper helper;
    private Context context;

    private final int VERSION = 1;

//  DB저장 파일 제목
    private final String DB_FILE_NAME = "notedb.sqlite";
//  쿼리구문을 사용하기 위해
    private SQLiteDatabase db;


    public DBAdapter( Context context ) {

        this.context = context;
//      DB생성
        this.helper = new DBHelper(context , DB_FILE_NAME, null, VERSION);

    }

    public void open() throws Exception { // DB열기
        db = helper.getWritableDatabase();
    }

    public Cursor queryAllNotes() {  // SQL구문 실행
        return db.query(DBHelper.TABLE,DBHelper.COLUMNS,null,null,null,null,"_id desc",null);
    }

    public boolean insert(NoteItem item) { // 데이터를 입력할때 사용하는 SQL
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMNS[1], item.getTitle()); // 제목을 넣는다.
        cv.put(DBHelper.COLUMNS[2], NoteItem.getTypeCode(item.getType())); // 선택한 타입 넣는다.
        cv.put(DBHelper.COLUMNS[3], getDate()); // 날짜를 넣는다.
        cv.put(DBHelper.COLUMNS[4], item.getContent()); // 내용을 넣는다.

        return db.insert(DBHelper.TABLE,null,cv) > 0;
    }

    public boolean delete(int id) { // 데이터를 삭제할때 사용하는 SQL
        return db.delete(DBHelper.TABLE,"_id = " + id,null) > 0;
    }

    public boolean update(NoteItem item, int id) { // 데이터를 수정할때 사용하는 SQL
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMNS[1], item.getTitle());// 제목을 넣는다.
        cv.put(DBHelper.COLUMNS[2], NoteItem.getTypeCode(item.getType())); // 선택한 타입 넣는다.
        cv.put(DBHelper.COLUMNS[3], getDate());// 날짜를 넣는다.
        cv.put(DBHelper.COLUMNS[4], item.getContent());// 내용을 넣는다.

        return db.update(DBHelper.TABLE,cv,"_id = " + id, null) > 0;
    }

    public String getDate() { // 현재날짜 얻음
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(Calendar.getInstance().getTime());
    }

}
