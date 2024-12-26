package com.session.demo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CUstomListActivity extends AppCompatActivity {

    ListView listView;
    String[] nameArray = {"Android","iOS","Flutter","React Native"};
    int[] imageArray = {R.drawable.android_icon,R.drawable.ios,R.drawable.flutter,R.drawable.react};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        listView = findViewById(R.id.custom_list);

        CustomListAdapter adapter = new CustomListAdapter(CUstomListActivity.this,nameArray,imageArray);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, customlistdash.class);
            intent.putExtra("name",nameArray[position]);
            intent.putExtra("image",imageArray[position]);
            startActivity(intent);
        });


    }
}