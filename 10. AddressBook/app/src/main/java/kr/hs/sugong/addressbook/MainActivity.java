package kr.hs.sugong.addressbook;

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

import com.example.administrator.addressbook.R;

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
        final ContentResolver resolver = getContentResolver();
        final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        final String _ID = ContactsContract.Contacts._ID;
        final Uri PhoneContentURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        final String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        final String Name = ContactsContract.Contacts.DISPLAY_NAME;
        final String PhoneContactId = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
//      Contracts테이블의 정보는 ContactsContract.Contacts에서
//      하위 테이블의 정보는 ContactsContract.CommonDataKinds에서

        cursor = resolver.query(content_uri, null, null, null, null);

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[]{Name},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cursor.moveToPosition(position);
                int hasphonenumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                String name = cursor.getString(cursor.getColumnIndex(Name));
                if (hasphonenumber > 0) {
                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    Cursor phonecursor = resolver.query(PhoneContentURI, null, PhoneContactId + " = ?" , new String[]{contact_id}, null);
                    if (phonecursor.moveToNext()) {
                        String phonenumber = phonecursor.getString(phonecursor.getColumnIndex(NUMBER));
                        callToPhoneNumber(phonenumber);
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
