package com.rtdemon.devildom.smssender;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendSMS(View view) {
        SmsManager sms = SmsManager.getDefault();
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText msg = (EditText) findViewById(R.id.msg);

        sms.sendTextMessage(phone.getText().toString(), null, msg.getText().toString(), null, null);

    }

}
