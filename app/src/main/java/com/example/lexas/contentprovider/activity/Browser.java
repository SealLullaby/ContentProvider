package com.example.lexas.contentprovider.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.ExploreByTouchHelper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lexas.contentprovider.R;
import com.example.lexas.contentprovider.adapter.BrowserAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lexas on 2018/5/11.
 */
public class Browser extends AppCompatActivity {
    private ArrayList<ExplorerInfo> browserHistory = new ArrayList<>();
    private BrowserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        getBrowserHistory();
        //browserHistory.add(new ExploerInfo("sa","das","dasdasd"));
        ListView listView = (ListView)findViewById(R.id.listview_exploer);
        adapter = new BrowserAdapter(Browser.this,R.layout.simple_brwoser_item,browserHistory);
        listView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), String.valueOf(browserHistory.size()), Toast.LENGTH_LONG).show();
    }
    /**
     * 获取浏览器历史记录    包括date title url
     * @return
     */
    //@SuppressLint("SimpleDateFormat")
    public List<ExplorerInfo> getBrowserHistory(){
        try{
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query( Uri.parse("content://browser/bookmarks"),
                    new String[] { "title", "url", "date" }, "date!=?",
                    new String[] { "null" }, "date desc");
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
            Date date;
            if(cursor != null ){
                while(cursor.moveToNext()) {
                    date = new Date(cursor.getLong(cursor.getColumnIndex("date")));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String url = cursor.getString(cursor.getColumnIndex("url"));
                    String time = sfd.format(date);
                    ExplorerInfo explorerInfo = new ExplorerInfo();
                    explorerInfo.setTime(time);
                    explorerInfo.setTitle(title);
                    explorerInfo.setUrl(url);
                    browserHistory.add(explorerInfo);
                }
                cursor.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  browserHistory;
    }
    public class ExplorerInfo {
        private String time;
        private String title;
        private String url;
        public ExplorerInfo(){}
        public ExplorerInfo(String time,String title,String url){
            this.time = time;
            this.title = title;
            this.url = url;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}

