package kr.hs.sugong.simplenote.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import kr.hs.sugong.simplenote.R;
import kr.hs.sugong.simplenote.model.NoteItem;

/**
 * Created by Administrator on 2016-03-12.
 */
public class DBNoteAdapter extends CursorAdapter {

    private Context context;

    public DBNoteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//      R.layout.note_item레이아웃에 있는 뷰 전체 불러오기
        View view  = View.inflate(context, R.layout.note_item, null);

//      ViewHolder에 객체를 저장하여 성능이 좋아짐.
        NoteViewHolder viewHolder = new NoteViewHolder();
        viewHolder.icon = (ImageView) view.findViewById(R.id.noteitem_img);
        viewHolder.title = (TextView) view.findViewById(R.id.noteitem_title);
        viewHolder.date = (TextView) view.findViewById(R.id.noteitem_date);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//      ViewHolder객체에 DB에 저장되어있는 내용을 넣는다.
        NoteViewHolder viewHolder = (NoteViewHolder) view.getTag() ;
        viewHolder.icon.setImageDrawable(getDrawable(cursor.getInt(2)));
        viewHolder.title.setText(cursor.getString(1));
        viewHolder.date.setText(cursor.getString(3));
    }

    @SuppressWarnings("deprecation")
    private Drawable getDrawable(int type) { // 스피너의 값에 따라 이미지가 변경되고 보여준다.
        if (type == 1) {
            return context.getResources().getDrawable(R.drawable.idea);
        } else if (type == 2) {
            return context.getResources().getDrawable(R.drawable.person);
        } else if (type == 3) {
            return context.getResources().getDrawable(R.drawable.book);
        } else {
            return context.getResources().getDrawable(R.drawable.note);
        }
    }

    class NoteViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView date;
    }
}
