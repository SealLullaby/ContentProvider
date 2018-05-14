package com.example.lexas.contentprovider.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lexas.contentprovider.R;
import com.example.lexas.contentprovider.activity.Browser;

import java.util.List;

/**
 * Created by Lexas on 2018/5/13.
 */
public class BrowserAdapter extends ArrayAdapter<Browser.ExplorerInfo> {
    private int resourceId;
    public BrowserAdapter(Context context, int textViewResourceId, List<Browser.ExplorerInfo> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Browser.ExplorerInfo explorerInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.simple_brwoser_item,parent,false);
        TextView time_textView = (TextView)view.findViewById(R.id.time);
        TextView title_textView = (TextView)view.findViewById(R.id.title);
        TextView url_textView =(TextView)view.findViewById(R.id.url);
        time_textView.setText(explorerInfo.getTime());
        title_textView.setText(explorerInfo.getTitle());
        url_textView.setText(explorerInfo.getUrl());
        return view;
    }
}

