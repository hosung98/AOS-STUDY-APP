package kr.hs.sugong.simplenote;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import kr.hs.sugong.simplenote.adapter.DBAdapter;
import kr.hs.sugong.simplenote.model.NoteItem;

/**
 * Created by Administrator on 2016-02-28.
 */
public class MainApplication extends Application {

    DBAdapter dbAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
//      dbAdapter을 연다.
        dbAdapter = new DBAdapter(this);
        try {
            dbAdapter.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBAdapter getDbAdapter() {
        return dbAdapter;
    }
}


