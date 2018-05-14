package com.example.lexas.contentprovider.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lexas.contentprovider.R;
import com.example.lexas.contentprovider.adapter.MessageAdapter;
import com.example.lexas.contentprovider.adapter.PhoneAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by Lexas on 2018/4/2.
 */
public class MessageCatch extends AppCompatActivity{
/*展示短信的ListView*/
private static final String TAG = "MessageCatch";

    private ListView listView;
    private List<Map<String,String>> dataList;
    private MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        dataList = queryMessageLog();
        adapter = new MessageAdapter(MessageCatch.this,R.layout.simple_message_item,dataList);
        listView= (ListView)findViewById(R.id.lv_show);
        listView.setAdapter(adapter);
        };


    @TargetApi(21)
private List<Map<String,String>> queryMessageLog() {
        //动态申请权限
        if (ActivityCompat.checkSelfPermission(MessageCatch.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MessageCatch.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 101);
            return null;
        }
        //动态申请权限
        if (ActivityCompat.checkSelfPermission(MessageCatch.this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MessageCatch.this,
                    new String[]{Manifest.permission.READ_SMS}, 101);
            return null;
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, new String[]{
                Telephony.Sms.ADDRESS,   //
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms.READ,
                Telephony.Sms.STATUS,
                Telephony.Sms.TYPE,
        }, null, null, "date DESC limit 2");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SMSMessage message = new SMSMessage();
                message.address = cursor.getString(0);
                message.body = cursor.getString(1);
                message.date = cursor.getLong(2);
                message.read = getMessageRead(cursor.getInt(3));
                message.status = getMessageStatus(cursor.getInt(4));
                message.type = getMessageType(cursor.getInt(5));
                message.person = getPerson(message.address);
                Log.i(TAG, "message : " + message.toString());
                Map<String, String> map = new HashMap<String, String>();
                map.put("body", message.body);
                map.put("date", String.valueOf(message.date));
                map.put("address", message.address);
                map.put("person", message.person);
                map.put("type", message.type);
                map.put("read", message.read);
                map.put("status", message.status);
                list.add(map);


            }

        }
        cursor.close();
        return list;
    }



    private String getMessageRead(int anInt) {
        if (1 == anInt) {
            return "已读";
        }
        if (0 == anInt) {
            return "未读";
        }
        return null;
    }
    private String getMessageType(int anInt) {
        if (1 == anInt) {
            return "收到的";
        }
        if (2 == anInt) {
            return "已发出";
        }
        return null;
    }
    private String getMessageStatus(int anInt) {
        switch (anInt) {
            case -1:
                return "接收";
            case 0:
                return "complete";
            case 64:
                return "pending";
            case 128:
                return "failed";
            default:
                break;
        }
        return null;
    }
    private String getPerson(String address) {
        try {
            ContentResolver resolver = getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
            Cursor cursor;
            cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        String name = cursor.getString(0);
                        return name;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    class SMSMessage {
        long date;
        String address;
        String body;
        String person;
        String read;
        String status;
        String type;

        @Override
        public String toString() {
            return "SMSMessage{" +
                    "date=" + date  +
                    ", address='" + address + '\'' +
                    ", body='" + body + '\'' +
                    ", person='" + person + '\'' +
                    ", read='" + read + '\'' +
                    ", status='" + status + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
