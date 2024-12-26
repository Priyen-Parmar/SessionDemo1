package com.session.demo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class customlistdash extends AppCompatActivity {

    ImageView imageView;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customlistdash);

        imageView = findViewById(R.id.detail_image);
        textView= findViewById(R.id.detail_text);

        int ImageRes= getIntent().getIntExtra("image", 0);
        String nameres= getIntent().getStringExtra("name");

        imageView.setImageResource(ImageRes);
        textView.setText(nameres);
    }
}