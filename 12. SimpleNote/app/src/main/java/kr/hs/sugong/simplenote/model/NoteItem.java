package kr.hs.sugong.simplenote.model;

import android.database.Cursor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016-02-28.
 */
public class NoteItem implements Serializable {

//    직렬화하는 크기가 커지면 Parcleable을 사용
//    작으면 Serializable사용

    public static enum Type { // enum 타입변수
        NORMAL , IDEA , PERSNAL , APPOINTMENT
    }



//    Enum을 한꺼번에 돌릴수 있는 코드도 있음
    public static int getTypeCode( Type type ) { // 스피너의 값을 가지고 리턴값을 정해준다.
        if (type == Type.IDEA) return 1;
        if (type == Type.PERSNAL) return 2;
        if (type == Type.APPOINTMENT) return 3;
        return 0;
    }

    public static Type getTypeOfCode(int code) {// 스피너의 값을 가지고 리턴값을 정해준다.

        if (code == 1) return Type.IDEA;
        else if (code == 2) return Type.PERSNAL;
        else if (code == 3) return Type.APPOINTMENT;
        return Type.NORMAL;
    }

    private static Date parseDate(String date) throws Exception{  // 날짜 형식 변경해서 값을 돌려줌
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.parse(date);
    }

    public static NoteItem getNoteItem(Cursor cursor) {
//      NoteItem클래스 객체생성을 한다.
        NoteItem item = new NoteItem();

//       Listview에서 선택한 위치를 가져와 그 값을 저장한다.
        item.setId(cursor.getInt(0));
        item.setTitle(cursor.getString(1));
        item.setType(getTypeOfCode(cursor.getInt(2)));
        try {
            item.setDate(parseDate(cursor.getString(3)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        item.setContent(cursor.getString(4));
        return item;
    }

    private int id;

    private String title;
    private String content;
    private Date date;
    private Type type;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

}
