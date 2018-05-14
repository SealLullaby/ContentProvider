package com.example.lexas.contentprovider.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lexas.contentprovider.R;

public class MainActivity extends AppCompatActivity {
    //通话记录的button
    private ImageView iv_iphone;
    //信息的button
    private ImageView iv_message;
    //图片的button
    private ImageView iv_picture;
    //浏览器的button
    private ImageView iv_exploer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test7);
        initView();
        iv_iphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_phone = new Intent(MainActivity.this, PhoneActivity.class);
                startActivity(intent_phone);
            }
        });
        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_message = new Intent(MainActivity.this, MessageCatch.class);
                startActivity(intent_message);
            }
        });
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_message = new Intent(MainActivity.this, Picture.class);
                startActivity(intent_message);
            }
        });
        iv_exploer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_message = new Intent(MainActivity.this, Browser.class);
                startActivity(intent_message);
            }
        });

    }

    private void initView() {
       iv_exploer = (ImageView)findViewById(R.id.iv_exploer);
        iv_iphone = (ImageView)findViewById(R.id.iv_iphone);
        iv_message = (ImageView)findViewById(R.id.iv_message);
        iv_picture = (ImageView)findViewById(R.id.iv_picture);
    }
}
