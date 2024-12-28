package com.session.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    TextView firstName,lastName,contact;

    //RadioButton male,female;
    RadioGroup gender;
    CheckBox sport,game,dance,reading;
    Button show;

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

        /*male = findViewById(R.id.dashboard_male);
        female = findViewById(R.id.dashboard_female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToastCommonMethod(DashboardActivity.this,male.getText().toString());
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToastCommonMethod(DashboardActivity.this,female.getText().toString());
            }
        });*/

        gender = findViewById(R.id.dashboard_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = findViewById(checkedId);
                new ToastCommonMethod(DashboardActivity.this,button.getText().toString());
            }
        });

        sport = findViewById(R.id.dashboard_sport);
        game = findViewById(R.id.dashboard_game);
        dance = findViewById(R.id.dashboard_dance);
        reading = findViewById(R.id.dashboard_reading);

//        sport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    new ToastCommonMethod(DashboardActivity.this,sport.getText().toString());
//                }
//            }
//        });

        /*game.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    new ToastCommonMethod(DashboardActivity.this,game.getText().toString());
                }
            }
        });

        dance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    new ToastCommonMethod(DashboardActivity.this,dance.getText().toString());
                }
            }
        });

        reading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    new ToastCommonMethod(DashboardActivity.this,reading.getText().toString());
                }
            }
        });*/

        show = findViewById(R.id.dashboard_show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                if(sport.isChecked()){
                    //new ToastCommonMethod(DashboardActivity.this,sport.getText().toString());
                    builder.append(sport.getText().toString()+"\n");
                }
                if(game.isChecked()){
                    //new ToastCommonMethod(DashboardActivity.this,game.getText().toString());
                    builder.append(game.getText().toString()+"\n");
                }
                if(dance.isChecked()){
                    builder.append(dance.getText().toString()+"\n");
                    //new ToastCommonMethod(DashboardActivity.this,dance.getText().toString());
                }
                if(reading.isChecked()){
                    builder.append(reading.getText().toString()+"\n");
                    //new ToastCommonMethod(DashboardActivity.this,reading.getText().toString());
                }

                if(builder.toString().equals("")){

                }
                else{
                    new ToastCommonMethod(DashboardActivity.this,builder.toString().trim());
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}