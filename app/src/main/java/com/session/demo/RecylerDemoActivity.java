package com.session.demo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class RecylerDemoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] nameArray = {"Cloth","Sports","Food","Jewelery"};
    int[] imageArray = {R.drawable.cloth,R.drawable.sports,R.drawable.food,R.drawable.jewelery};
    ArrayList<CustomList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_demo);

        recyclerView = findViewById(R.id.recyclerview);
        //recyclerView.setLayoutManager(new LinearLayoutManager(RecylerDemoActivity.this));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.setNestedScrollingEnabled(false);

        arrayList = new ArrayList<>();
        for(int i=0;i< nameArray.length;i++){
            CustomList list = new CustomList();
            list.setCatName(nameArray[i]);
            list.setCatImage(imageArray[i]);
            arrayList.add(list);
        }
        RecyclerDemoAdapter adapter = new RecyclerDemoAdapter(RecylerDemoActivity.this,arrayList);
        recyclerView.setAdapter(adapter);
    }
}