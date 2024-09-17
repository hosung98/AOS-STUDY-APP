package com.example.administrator.addressbook;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listview);
        fetchContacts();

    }

    private void fetchContacts() {
        Uri content_uri = ContactsContract.Contacts.CONTENT_URI;
        final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        final String _ID = ContactsContract.Contacts._ID;
        final Uri PhoneContentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        final String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        final ContentResolver resolver = getContentResolver();
        final String Name = ContactsContract.Contacts.DISPLAY_NAME;
        final String PhoneContactID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        cursor = resolver.query(content_uri,null,null,null,null);
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] {Name}
        , new int[] {android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    Cursor phoneCursor = resolver.query(PhoneContentUri, null, PhoneContactID + " = ?", new String[] {contact_id}, null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        callToPhoneNumber(phoneNumber);
                    }

                }
            }
        });
    }
    private void callToPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
}
