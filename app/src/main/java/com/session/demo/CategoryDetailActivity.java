package com.session.demo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CategoryDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        imageView = findViewById(R.id.category_detail_image);
        name = findViewById(R.id.category_detail_name);

        Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString("NAME"));
        imageView.setImageResource(bundle.getInt("IMAGE"));

    }
}