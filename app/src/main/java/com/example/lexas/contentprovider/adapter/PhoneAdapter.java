package com.example.lexas.contentprovider.adapter;

/**
 * Created by Lexas on 2018/4/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lexas.contentprovider.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/15.
 */

public class PhoneAdapter extends ArrayAdapter<Map<String,String>> {
    private int SourceId;
    // public PhoneAdapter(){};
    public PhoneAdapter(Context context,int textViewResourceId,List<Map<String,String>> objects){
        super(context,textViewResourceId,objects);
        SourceId = textViewResourceId;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Map<String,String> map = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(SourceId, parent, false);
        TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
        TextView tv_number = (TextView)view.findViewById(R.id.tv_number);
        TextView tv_date = (TextView)view.findViewById(R.id.tv_date);
        TextView tv_duration = (TextView)view.findViewById(R.id.tv_duration);
        TextView tv_type = (TextView)view.findViewById(R.id.tv_type);
        tv_name.setText(map.get("name"));
        tv_number.setText(map.get("number"));
        tv_date.setText(map.get("date"));
        tv_duration.setText(map.get("duration"));
        tv_type.setText(map.get("type"));
        return view;
    }
}

