package com.session.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSecondAdapter extends BaseAdapter {

    Context context;
    ArrayList<CustomList> arrayList;

    public CustomSecondAdapter(Context context, ArrayList<CustomList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_second_list,null);
        ImageView imageView = view.findViewById(R.id.custom_second_list_image);
        TextView name = view.findViewById(R.id.custom_second_list_name);

        name.setText(arrayList.get(position).getCatName());
        imageView.setImageResource(arrayList.get(position).getCatImage());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("NAME",arrayList.get(position).getCatName());
                bundle.putInt("IMAGE",arrayList.get(position).getCatImage());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
