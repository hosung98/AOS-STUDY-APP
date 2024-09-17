package kr.hs.sugong.dialogwidget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity {

    Button loginbtn;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtn = (Button) findViewById(R.id.login_btn);
        email = (EditText) findViewById(R.id.email);
        email.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getText().toString().equals("")) {
                    loginbtn.setVisibility(View.GONE);
                } else {
                    loginbtn.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        if (savedInstanceState != null ) {
            if (savedInstanceState.getBoolean("LoginBtn")) {
                loginbtn.setVisibility(View.VISIBLE);
                email.setText(savedInstanceState.getString("email"));
            }
        }
    }

    public void loginClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        LoginFragment dialog = new LoginFragment();
        dialog.show(fm,"Login");

    }
}
