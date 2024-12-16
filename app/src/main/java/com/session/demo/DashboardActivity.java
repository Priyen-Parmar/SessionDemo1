package com.session.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    TextView firstName,lastName,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String sFirstName = bundle.getString("FIRST_NAME");
        String sLastName = bundle.getString("LAST_NAME");
        String sContact = bundle.getString("CONTACT");

        Log.d("RESPONSE",sFirstName+"\n"+sLastName+"\n"+sContact);

        firstName = findViewById(R.id.dashboard_firstname);
        lastName = findViewById(R.id.dashboard_lastname);
        contact = findViewById(R.id.dashboard_contact);

        firstName.setText(sFirstName);
        lastName.setText(sLastName);
        contact.setText(sContact);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}