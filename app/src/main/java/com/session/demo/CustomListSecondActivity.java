package com.session.demo;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CustomListSecondActivity extends AppCompatActivity {

    ListView listView;
    String[] nameArray = {"Cloth","Sports","Food","Jewelery"};
    int[] imageArray = {R.drawable.cloth,R.drawable.sports,R.drawable.food,R.drawable.jewelery};
    ArrayList<CustomList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_second);

        listView = findViewById(R.id.custom_list_second);

        arrayList = new ArrayList<>();
        for(int i=0;i< nameArray.length;i++){
            CustomList list = new CustomList();
            list.setCatName(nameArray[i]);
            list.setCatImage(imageArray[i]);
            arrayList.add(list);
        }
        CustomSecondAdapter adapter = new CustomSecondAdapter(CustomListSecondActivity.this,arrayList);
        listView.setAdapter(adapter);
    }
}