package com.example.lexas.contentprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lexas.contentprovider.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Lexas on 2018/4/17.
 */
public class MessageAdapter extends ArrayAdapter<Map<String,String>> {
    private int SourceId;
    public MessageAdapter(Context context,int textViewResourceId,List<Map<String,String>> objects){
        super(context,textViewResourceId,objects);
        SourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String,String> map = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(SourceId, parent, false);
        TextView tv_body = (TextView)view.findViewById(R.id.tv_body);
        TextView tv_date = (TextView)view.findViewById(R.id.tv_date);
        TextView tv_address = (TextView)view.findViewById(R.id.tv_address);
        TextView tv_person = (TextView)view.findViewById(R.id.tv_person );
        TextView tv_type = (TextView)view.findViewById(R.id.tv_type);
        TextView tv_read = (TextView)view.findViewById(R.id.tv_read);
        TextView tv_status = (TextView)view.findViewById(R.id.tv_status);
        tv_body.setText(map.get("body"));
        tv_date.setText(map.get("date"));
        tv_address.setText(map.get("address"));
        tv_person.setText(map.get("person"));
        tv_type.setText(map.get("type"));
        tv_read.setText(map.get("read"));
        tv_status.setText(map.get("status"));
        return view;
    }
}
