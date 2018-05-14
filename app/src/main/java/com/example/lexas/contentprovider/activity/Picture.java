package com.example.lexas.contentprovider.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.lexas.contentprovider.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lexas on 2018/4/22.
 */
public class Picture extends AppCompatActivity {
    Button PictureCatch;
    ListView show;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> descs = new ArrayList<String>();
    ArrayList<String> fileNames = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_show);
//        PictureCatch = (Button) findViewById(R.id.PictureCatch);
        show = (ListView) findViewById(R.id.show);

//        PictureCatch.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {

                names.clear();
                descs.clear();
                fileNames.clear();

                Cursor cursor = getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
                while(cursor.moveToNext())
                {

                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));

                    String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));

                    byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    names.add(name);

                    descs.add(desc);

                    fileNames.add(new String(data,0,data.length-1));
                }

                List<Map<String, Object>> listItems=new ArrayList<Map<String, Object>>();

                for (int i=0;i<names.size();i++)
                {
                    Map<String, Object> listItem = new HashMap<String, Object>();
                    listItem.put("name", names.get(i));
                    listItem.put("desc", descs.get(i));
                    listItems.add(listItem);
                }

                SimpleAdapter simpleAdapter = new SimpleAdapter(
                        Picture.this,listItems,R.layout.picture_line,new String[]{ "name", "desc" },new int[]{R.id.name,R.id.desc});

                show.setAdapter(simpleAdapter);
//            }
//        });

        show.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View source
                    , int position, long id)
            {

                View viewDialog = getLayoutInflater().inflate(
                        R.layout.picture_view, null);

                ImageView image = (ImageView) viewDialog
                        .findViewById(R.id.image);

                image.setImageBitmap(BitmapFactory
                        .decodeFile(fileNames.get(position)));

                new AlertDialog.Builder(Picture.this)
                        .setView(viewDialog)
                        .setPositiveButton("确定",null)
                        .show();
            }
        });
    }
}
