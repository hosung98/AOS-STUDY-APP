package kr.hs.sugong.simplenote.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import kr.hs.sugong.simplenote.R;
import kr.hs.sugong.simplenote.model.NoteItem;
import kr.hs.sugong.simplenote.model.NoteItem.Type;

/**
 * Created by Administrator on 2016-02-28.
 */
public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<NoteItem> items; // item을 넣는 리스트 변수 생성

    public NoteAdapter(Context context, List<NoteItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//      NoteAdapter가 실행되면 자동으로 getView메소드가 실행된다.

        NoteViewHolder viewHolder ;
        if (convertView == null) {
//          Inflater는 Layout에서 디자인한 객체를 가져오기 위한 것이다.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.note_item , parent, false);

//          findViewById를 이용하여 객체를 변수에 넣을 때 성능을 높이기 위하여 viewHolder를 사용
            viewHolder = new NoteViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.noteitem_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.noteitem_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.noteitem_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NoteViewHolder) convertView.getTag();
        }

//      기존에 입력한 내용들을 각각의 텍스트에 맞게 저장한다.
        NoteItem item = items.get(position);
        viewHolder.icon.setImageDrawable(getDrawable(item.getType()));
        viewHolder.title.setText(item.getTitle());

        // 기존에 입력한 날짜를 date텍스트에 저장한다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.date.setText(sdf.format(item.getDate()));

        return convertView;
    }

    @SuppressWarnings("deprecation")
    private Drawable getDrawable(Type type) { // 타입에 따라 리턴하는 이미지를 다르게 해준다.
        if (type == Type.IDEA) {
            return context.getResources().getDrawable(R.drawable.idea);
        } else if (type == Type.PERSNAL) {
            return context.getResources().getDrawable(R.drawable.person);
        } else if (type == Type.APPOINTMENT) {
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
