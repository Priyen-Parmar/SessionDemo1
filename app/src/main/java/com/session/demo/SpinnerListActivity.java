package com.session.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
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

    AutoCompleteTextView autotxt;
    ArrayList<String> arrLanguage = new ArrayList<>();

    MultiAutoCompleteTextView multiAutoCompleteTextView;
    GridView listView;

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
        arrayList.add("Php");

        arrayList.remove(5);
        arrayList.set(5,"Python");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrayList);
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


        autotxt = findViewById(R.id.autotxt);

        //AutoCompleteTextView

        arrLanguage.add("C++");
        arrLanguage.add("C");
        arrLanguage.add("C#");
        arrLanguage.add("Objective C");
        arrLanguage.add("Java");
        arrLanguage.add("JavaScript");
        arrLanguage.add("Kotlin");
        arrLanguage.add("Python");
        arrLanguage.add("PHP");
        arrLanguage.add("Swift");
        arrLanguage.add("Laravel");

        ArrayAdapter<String> autotxtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrLanguage);
        autotxt.setAdapter(autotxtAdapter);
        autotxt.setThreshold(1);

        multiAutoCompleteTextView = findViewById(R.id.multiautotxt);
        ArrayAdapter multiTextAdapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrLanguage);
        multiAutoCompleteTextView.setAdapter(multiTextAdapter);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        listView = findViewById(R.id.listview);
        ArrayAdapter listAdapter = new ArrayAdapter(SpinnerListActivity.this, android.R.layout.simple_list_item_1,arrLanguage);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ToastCommonMethod(SpinnerListActivity.this,arrLanguage.get(position));
            }
        });

    }
}