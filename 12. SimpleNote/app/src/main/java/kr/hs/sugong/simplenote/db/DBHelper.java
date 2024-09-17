package kr.hs.sugong.simplenote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016-03-12.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String [] COLUMNS = {"_id","_title","_type","_date", "_content"}; // DB 테이블 컬럼
    public static final String TABLE = "notes"; // DB 테이블 명


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + TABLE + " (" + COLUMNS[0] + " integer primary key autoincrement, "
                     + COLUMNS[1] + " text not null ," + COLUMNS[2] + " integer," + COLUMNS[3] + " text,"
                     + COLUMNS[4] + " text)"; // 테이블 만드는 SQL 구문
        db.execSQL(sql); // 구문 실행

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("drop table " + TABLE); // 버전이 새로운 것이면 전 버전의 테이블을 지우고 재생성
            onCreate(db);
        }
    }
}

