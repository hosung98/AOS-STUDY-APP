package kr.hs.sugong.explist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016-03-05.
 */
public class BasicExpanderbleAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> grouplist;
    private ArrayList<ArrayList<String>> childlist;
    private LayoutInflater inflater;

    public BasicExpanderbleAdapter(Context context, ArrayList<String> grouplist , ArrayList<ArrayList<String>> childlist) {
        this.inflater = LayoutInflater.from(context);
        this.grouplist = grouplist;
        this.childlist = childlist;
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<String> childs = childlist.get(groupPosition);
        if (childs == null) return 0;
        return childs.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return childlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childlist.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = inflater.inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
        }

        TextView tv = (TextView) v.findViewById(android.R.id.text1);

        if (isExpanded) {
            v.setBackgroundColor(0xFFEA8709);
        } else {
            v.setBackgroundColor(0xFFFFFFFF);
        }

        int  childrens = getChildrenCount(groupPosition);
        String title = grouplist.get(groupPosition) + ((childrens>0)? " (" + childrens + ")" : "");
        tv.setText(title);
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = inflater.inflate(R.layout.chileitem,parent,false);
        }
        String item = (String) getChild(groupPosition, childPosition);
        TextView tv = (TextView) v.findViewById(R.id.childtext);
        tv.setText(item);

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
