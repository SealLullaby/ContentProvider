package com.example.lexas.contentprovider.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lexas.contentprovider.R;
import com.example.lexas.contentprovider.adapter.PhoneAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lexas on 2018/4/17.
 */
public class PhoneActivity extends AppCompatActivity {
    /**
     * 展示通话记录的listview
     */
    private ListView listView;
    /**
     * 保存通话记录的list
     */
    private List<Map<String,String>> dataList;
    /**
     *适配器
     */
    private PhoneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        //请求权限

        if (ContextCompat.checkSelfPermission(PhoneActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PhoneActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        } else {
            dataList = getDataList();
        }
        adapter = new PhoneAdapter(PhoneActivity.this,R.layout.simple_calllog_item,dataList);
        listView= (ListView)findViewById(R.id.lv_show);
        listView.setAdapter(adapter);
       /* Intent intent = new Intent(PhoneActivity.this, AutogetPhone.class);
        startService(intent);*/
    }
    /**
     * 获取所需的dataList
     * @return
     */
    // @TargetApi(23)
    private List<Map<String,String>> getDataList(){
        try {
            //获得contentResolver
            ContentResolver resolver = getContentResolver();
            /**
             * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
             * @param projection 需要查询的字段
             * @param selection sql语句where之后的语句
             * @param selectionArgs ?占位符代表的数据
             * @param sortOrder 排序方式
             *
             */
            Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                    new String[]{CallLog.Calls.CACHED_NAME// 通话记录的联系人
                            , CallLog.Calls.NUMBER// 通话记录的电话号码
                            , CallLog.Calls.DATE// 通话记录的日期
                            , CallLog.Calls.DURATION// 通话时长
                            , CallLog.Calls.TYPE}// 通话类型
                    , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
            );
            // 3.通过Cursor获得数据
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                String date = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date(dateLong));
                int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
                int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String typeString = "";
                switch (type) {
                    case CallLog.Calls.INCOMING_TYPE:
                        typeString = "打入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        typeString = "打出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        typeString = "未接";
                        break;
                    case CallLog.Calls.REJECTED_TYPE:
                        typeString = "拒接";
                        break;
                    default: break;
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", (name == null) ? "未备注联系人" : name);
                map.put("number", number);
                map.put("date", date);
                map.put("duration", (duration / 60) + "分钟");
                map.put("type", typeString);
                list.add(map);
            }
            return list;
        }catch (SecurityException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    dataList = getDataList();
                }else{
                    Toast.makeText(this, "you denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default: break;
        }
    }
}
