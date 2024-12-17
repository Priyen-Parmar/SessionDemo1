package com.session.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SpinnerListActivity extends AppCompatActivity {

    Spinner spinner;
    //String[] technologyArray = {"Android","Java","Php","iOS","Flutter","Python"};
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_list);

        spinner = findViewById(R.id.spinner);

        arrayList = new ArrayList<>();
        arrayList.add("Android");
        arrayList.add("Java");
        arrayList.add("iOS");
        arrayList.add("Flutter");
        arrayList.add("React");
        arrayList.add("Demo");
        arrayList.add("Pythn");

        arrayList.remove(5);
        arrayList.set(5,"Python");

        ArrayAdapter adapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new ToastCommonMethod(SpinnerListActivity.this,arrayList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}