package com.session.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    TextView signup;
    EditText email,password;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("SessionApp.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME VARCHAR(50),LASTNAME VARCHAR(50),CONTACT BIGINT(10),EMAIL VARCHAR(100),PASSWORD VARCHAR(20),GENDER VARCHAR(10),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        getSupportActionBar().hide();

        signup = findViewById(R.id.main_signup);

        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);
        login = findViewById(R.id.main_login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("EMail Id/Contact No. Required");
                }
                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password Required");
                }
                else if(password.getText().toString().trim().length()<6){
                    password.setError("Min. 6 Char Password Required");
                }
                else{
                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"' AND PASSWORD='"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){
                        while (cursor.moveToNext()){
                            sp.edit().putString(ConstantSp.USERID,cursor.getString(0)).commit();
                            sp.edit().putString(ConstantSp.FIRSTNAME,cursor.getString(1)).commit();
                            sp.edit().putString(ConstantSp.LASTNAME,cursor.getString(2)).commit();
                            sp.edit().putString(ConstantSp.EMAIL,cursor.getString(4)).commit();
                            sp.edit().putString(ConstantSp.CONTACT,cursor.getString(3)).commit();
                            sp.edit().putString(ConstantSp.PASSWORD,cursor.getString(5)).commit();
                            sp.edit().putString(ConstantSp.GENDER,cursor.getString(6)).commit();
                            sp.edit().putString(ConstantSp.CITY,cursor.getString(7)).commit();
                        }
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                    }
                    /*System.out.println("Signup Successfully");
                    Log.d("RESPONSE","Signup Successfully");
                    Log.e("RESPONSE","Signup Successfully");
                    Log.w("RESPONSE","Signup Successfully");
                    //Toast.makeText(MainActivity.this, "Signup Successfully", Toast.LENGTH_LONG).show();
                    new ToastCommonMethod(MainActivity.this, "Signup Successfully");

                    //Snackbar.make(v,"Signup Successfully",Snackbar.LENGTH_SHORT).show();
                    new ToastCommonMethod(v,"Signup Successfully");

                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("FIRST_NAME",firstName.getText().toString());
                    bundle.putString("LAST_NAME",lastName.getText().toString());
                    bundle.putString("CONTACT",contact.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);*/

                    //new ToastCommonMethod(MainActivity.this, DashboardActivity.class);

                }
            }
        });

    }

}