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

    RecyclerView recyclerView, recyclerView_second, recyclerView_third;
    String[] nameArray = {"Cloth","Sports","Food","Jewelery"};
    int[] imageArray = {R.drawable.cloth,R.drawable.sports,R.drawable.food,R.drawable.jewelery};
    ArrayList<CustomList> arrayList;



    String[] itemArray2 = {"Camera","Compass","Airplane","Map","Camping","Luggage", "Cloth", "Food"};
    int[] ImgArray2 = {R.drawable.camera,R.drawable.compass,R.drawable.airplane, R.drawable.map, R.drawable.camping, R.drawable.luggage,R.drawable.cloth,R.drawable.food};
    ArrayList<CustomList> arrayList2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_demo);

        recyclerView = findViewById(R.id.recyclerview);
        //recyclerView.setLayoutManager(new LinearLayoutManager(RecylerDemoActivity.this));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setNestedScrollingEnabled(false);

        arrayList = new ArrayList<>();
        for(int i=0;i< nameArray.length;i++){
            CustomList list = new CustomList();
            list.setCatName(nameArray[i]);
            list.setCatImage(imageArray[i]);
            arrayList.add(list);
        }
        RecyclerDemoAdapter adapter = new RecyclerDemoAdapter(RecylerDemoActivity.this,arrayList);
        recyclerView.setAdapter(adapter);



        // Second Recycler View
        recyclerView_second = findViewById(R.id.recyclerview_second);
        recyclerView_second.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));

        arrayList2 = new ArrayList<>();
        for(int i=0; i < itemArray2.length; i++){
            CustomList list = new CustomList();
            list.setCatImage(ImgArray2[i]);
            list.setCatName(itemArray2[i]);
            arrayList2.add(list);
        }

        RecyclerDemoAdapter adapter2 = new RecyclerDemoAdapter(RecylerDemoActivity.this, arrayList2);
        recyclerView_second.setAdapter(adapter2);




        // Third RecyclerView
        recyclerView_third = findViewById(R.id.recyclerview_third);
        recyclerView_third.setLayoutManager(new LinearLayoutManager(RecylerDemoActivity.this));

        RecyclerDemoAdapter adapter3 = new RecyclerDemoAdapter(this, arrayList2);
        recyclerView_third.setAdapter(adapter3);


    }
}