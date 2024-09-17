package kr.hs.sugong.drawexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import kr.hs.sugong.drawexample.widget.FreeLine;

public class MainActivity extends AppCompatActivity {

    private final int CONTEXT_RED = 1;
    private final int CONTEXT_BLUE = 2;
    private final int CONTEXT_BLACK = 3;
    private final int CONTEXT_BOLD = 10;
    private final int CONTEXT_UNBOLD = 11;

    private final int OPTION_EXIT = 1;
    private final int OPTION_CLEAR = 2;

    Button bold_btn, color_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bold_btn = (Button) findViewById(R.id.boldbtn);
        color_btn = (Button) findViewById(R.id.colorbtn);

        registerForContextMenu(bold_btn);
        registerForContextMenu(color_btn);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v == color_btn ){
            menu.setHeaderTitle("색상");
            menu.add(1, CONTEXT_RED, 1, "빨강");
            menu.add(1,CONTEXT_BLUE,2,"파랑");
            menu.add(1,CONTEXT_BLACK,3,"검정");
        } else if (v== bold_btn){
            menu.setHeaderTitle("굵기");
            menu.add(1, CONTEXT_BOLD, 1, "굵게");
            menu.add(1,CONTEXT_UNBOLD,2,"보통");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case CONTEXT_RED:
                FreeLine.color_value= Color.RED;
                break;
            case CONTEXT_BLUE:
                FreeLine.color_value= Color.BLUE;
                break;
            case CONTEXT_BLACK:
                FreeLine.color_value= Color.BLACK;
                break;
            case CONTEXT_BOLD:
                FreeLine.bold_value= 6;
                break;
            case CONTEXT_UNBOLD:
                FreeLine.bold_value= 3;
                break;
        }
        return false;
    }
}
