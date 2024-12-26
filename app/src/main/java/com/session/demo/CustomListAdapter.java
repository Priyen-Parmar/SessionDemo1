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

public class CustomListAdapter extends BaseAdapter {

    Context context;
    String[] nameArray;
    int[] imageArray;

    public CustomListAdapter(Context context, String[] nameArray, int[] imageArray) {
        this.context = context;
        this.nameArray = nameArray;
        this.imageArray = imageArray;
    }


    @Override
    public int getCount() {
        return nameArray.length;
    }

    @Override
    public Object getItem(int position) {
        return nameArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_list,null);
        TextView name = view.findViewById(R.id.custom_list_name);
        ImageView image = view.findViewById(R.id.custom_list_image);

        name.setText(nameArray[position]);
        image.setImageResource(imageArray[position]);

        /*name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomListAdapter.this, customlistdash);
                Bundle bundle = new Bundle();
            }
        });*/

        return view;
    }
}
