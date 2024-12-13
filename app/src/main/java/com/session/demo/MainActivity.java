package com.session.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button signup;
    EditText firstName,lastName,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.main_signup);

        firstName = findViewById(R.id.main_first_name);
        lastName = findViewById(R.id.main_last_name);
        contact = findViewById(R.id.main_contact);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText().toString().trim().equals("")){
                    firstName.setError("First Name Required");
                }
                else if(lastName.getText().toString().trim().equals("")){
                    lastName.setError("Last Name Required");
                }
                else if(contact.getText().toString().trim().equals("")){
                    contact.setError("Contact No. Required");
                }
                else if(contact.getText().toString().trim().length()<10){
                    contact.setError("Valid Contact No. Required");
                }
                else{
                    System.out.println("Signup Successfully");
                    Log.d("RESPONSE","Signup Successfully");
                    Log.e("RESPONSE","Signup Successfully");
                    Log.w("RESPONSE","Signup Successfully");
                    Toast.makeText(MainActivity.this, "Signup Successfully", Toast.LENGTH_LONG).show();
                    Snackbar.make(v,"Signup Successfully",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

}