package com.session.demo;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    Button editProfile,updateProfile;
    EditText firstName, lastName, contact, email, password, confirm_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup gender;
    RadioButton male,female,transgender;
    Spinner spinner;
    ArrayList<String> arrayList;
    String sGender, sCity;

    SQLiteDatabase db;
    SharedPreferences sp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("SessionApp.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME VARCHAR(50),LASTNAME VARCHAR(50),CONTACT BIGINT(10),EMAIL VARCHAR(100),PASSWORD VARCHAR(20),GENDER VARCHAR(10),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        editProfile = findViewById(R.id.profile_edit_button);
        updateProfile = findViewById(R.id.profile_update_button);

        firstName = findViewById(R.id.profile_first_name);
        lastName = findViewById(R.id.profile_last_name);
        contact = findViewById(R.id.profile_contact);
        email = findViewById(R.id.profile_email);
        password = findViewById(R.id.profile_password);
        confirm_password = findViewById(R.id.profile_confirm_password);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(true);
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstName.getText().toString().trim().equals("")) {
                    firstName.setError("First Name Required");
                } else if (lastName.getText().toString().trim().equals("")) {
                    lastName.setError("Last Name Required");
                } else if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Valid Email Id Required");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().trim().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().trim().length() < 6) {
                    password.setError("Min. 6 Char Password Required");
                } else if (confirm_password.getText().toString().trim().equals("")) {
                    confirm_password.setError("Confirm Password Required");
                } else if (confirm_password.getText().toString().trim().length() < 6) {
                    confirm_password.setError("Min. 6 Char Confirm Password Required");
                } else if (!password.getText().toString().trim().matches(confirm_password.getText().toString().trim())) {
                    confirm_password.setError("Password Does Not Match");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    new ToastCommonMethod(ProfileActivity.this, "Please Select Gender");
                }
                else {
                    setData(false);
                    /*String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"' OR CONTACT='"+contact.getText().toString()+"' ";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){
                        new ToastCommonMethod(ProfileActivity.this, "User Already Exists");
                        onBackPressed();
                    }
                    else{
                        String insertQuery = "INSERT INTO USERS VALUES (NULL,'"+firstName.getText().toString()+"','"+lastName.getText().toString()+"','"+contact.getText().toString()+"','"+email.getText().toString()+"','"+password.getText().toString()+"','"+sGender+"','"+sCity+"')";
                        db.execSQL(insertQuery);
                        new ToastCommonMethod(ProfileActivity.this, "Signup Successfully");
                    }*/
                }
            }

        });

        gender = findViewById(R.id.profile_gender);
        male = findViewById(R.id.profile_male);
        female = findViewById(R.id.profile_female);
        transgender = findViewById(R.id.profile_transgender);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = findViewById(checkedId);
                sGender = button.getText().toString();
                new ToastCommonMethod(ProfileActivity.this, sGender);
            }
        });

        spinner = findViewById(R.id.profile_city);

        arrayList = new ArrayList<>();
        arrayList.add("Ahmedabad");
        arrayList.add("Jaipur");
        arrayList.add("Rajkot");
        arrayList.add("Kanpur");
        arrayList.add("Baroda");
        arrayList.add("Mumbai");
        arrayList.add("Delhi");
        arrayList.add("Chennai");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ProfileActivity.this, android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = arrayList.get(position);
                new ToastCommonMethod(ProfileActivity.this, sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setData(false);

    }

    private void setData(boolean b) {
        if(b){
           confirm_password.setVisibility(View.VISIBLE);
           editProfile.setVisibility(View.GONE);
           updateProfile.setVisibility(View.VISIBLE);
        }
        else{
            confirm_password.setVisibility(View.GONE);
            editProfile.setVisibility(View.VISIBLE);
            updateProfile.setVisibility(View.GONE);
        }

        firstName.setEnabled(b);
        lastName.setEnabled(b);
        email.setEnabled(b);
        contact.setEnabled(b);
        password.setEnabled(b);
        spinner.setEnabled(b);
        male.setEnabled(b);
        female.setEnabled(b);
        transgender.setEnabled(b);

        firstName.setText(sp.getString(ConstantSp.FIRSTNAME,""));
        lastName.setText(sp.getString(ConstantSp.LASTNAME,""));
        email.setText(sp.getString(ConstantSp.EMAIL,""));
        contact.setText(sp.getString(ConstantSp.CONTACT,""));
        password.setText(sp.getString(ConstantSp.PASSWORD,""));
        confirm_password.setText(sp.getString(ConstantSp.PASSWORD,""));

        if(sp.getString(ConstantSp.GENDER,"").equalsIgnoreCase("Male")){
            male.setChecked(true);
        }
        else if(sp.getString(ConstantSp.GENDER,"").equalsIgnoreCase("Female")){
            female.setChecked(true);
        }
        else if(sp.getString(ConstantSp.GENDER,"").equalsIgnoreCase("Transgender")){
            transgender.setChecked(true);
        }
        else{

        }

        int iCityPosition = 0;
        for(int i=0;i<arrayList.size();i++){
            if(sp.getString(ConstantSp.CITY,"").equalsIgnoreCase(arrayList.get(i))){
                iCityPosition = i;
                break;
            }
        }
        spinner.setSelection(iCityPosition);
    }
}