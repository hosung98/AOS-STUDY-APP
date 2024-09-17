package kr.hs.sugong.explist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ExpandableListView listview;
    private ArrayList<String> grouplist;
    private ArrayList<ArrayList<String>> childlist;
    private BasicExpanderbleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initAdapter();
    }

    private void initAdapter() {

        listview = (ExpandableListView) findViewById(R.id.listview);

        grouplist = new ArrayList<String>();
        childlist = new ArrayList<ArrayList<String>>();

        grouplist.add("Appetizer");
        grouplist.add("Steak");
        grouplist.add("Salad");
        grouplist.add("Drink");
        grouplist.add("Pay");

        ArrayList<String> sub1 = new ArrayList<String>() ;
        sub1.add("ClamChowder Soup");sub1.add("Mushroom Soup");
        childlist.add(sub1);

        ArrayList<String> sub2 = new ArrayList<String>();
        sub2.add("NewYork Steak");sub2.add("T bone Steak");
        sub2.add("Backbone Steak");sub2.add("FilletRinon Steak");
        sub2.add("Beef Steak");
        childlist.add(sub2);

        ArrayList<String> sub3 = new ArrayList<String>();
        sub3.add("Oriental Salad");sub3.add("Intalian Salad");
        sub3.add("KJun Salad");sub3.add("Chicken Salad");
        childlist.add(sub3);

        ArrayList<String> sub4 = new ArrayList<String>();
        sub4.add("Coke");sub4.add("Soda");
        sub4.add("Coffee");sub4.add("Tea");
        childlist.add(sub4);

        ArrayList<String> sub5 = new ArrayList<String>();
        childlist.add(null);

        adapter = new BasicExpanderbleAdapter(this,grouplist,childlist);
        listview.setAdapter(adapter);

        addListEvents();

    }

    private void addListEvents() {

        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (childlist.get(groupPosition) == null) {
                    Toast.makeText(getBaseContext(), grouplist.get(groupPosition) + "Click", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

        });

        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getBaseContext(), childlist.get(groupPosition).get(childPosition) + "Click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        listview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getBaseContext(), grouplist.get(groupPosition) + "Expand", Toast.LENGTH_SHORT).show();
            }
        });

        listview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getBaseContext(), grouplist.get(groupPosition) + "Collapse", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
