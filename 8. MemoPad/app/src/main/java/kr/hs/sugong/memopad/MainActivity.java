package kr.hs.sugong.memopad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText textfield ;
    private static final String FILENAME = "memo.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textfield = (EditText) findViewById(R.id.textarea);



    }

    public void onSave(View view) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter( openFileOutput(FILENAME, MODE_PRIVATE)));

            String text = textfield.getText().toString();
            bw.write(text);
            bw.flush();
            Toast.makeText(this, "파일 저장 완료",Toast.LENGTH_SHORT).show();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLoad(View view) {
        try {
            BufferedReader br = new BufferedReader( new InputStreamReader( openFileInput(FILENAME)));
            StringBuffer sb = new StringBuffer();
            
            boolean isFirst = true;
            String line = null ;
            while((line = br.readLine()) != null) {
                if (!isFirst) sb.append("\n");
                isFirst = false;
                sb.append(line);
            }
            textfield.setText(sb.toString());
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClear(View view) {

        textfield.setText("");

    }

}
