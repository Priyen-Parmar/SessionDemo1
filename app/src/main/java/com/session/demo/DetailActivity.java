package com.session.demo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.detail_image);
        textView = findViewById(R.id.detail_text);

        int imageRes = getIntent().getIntExtra("image",0);
        String name = getIntent().getStringExtra("name");

        // Set the image and text
        imageView.setImageResource(imageRes);
        textView.setText(name);

    }
}