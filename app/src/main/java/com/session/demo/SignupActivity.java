package com.session.demo;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SignalStrengthUpdateRequest;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class SignupActivity extends AppCompatActivity {

    Button signup;
    EditText firstName, lastName, contact, email, password, confirm_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup gender;
    CheckBox terms;
    Spinner spinner;
    ArrayList<String> arrayList;
    String sGender, sCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        signup = findViewById(R.id.signup_button);

        firstName = findViewById(R.id.signup_first_name);
        lastName = findViewById(R.id.signup_last_name);
        contact = findViewById(R.id.signup_contact);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirm_password = findViewById(R.id.signup_confirm_password);

        signup.setOnClickListener(new View.OnClickListener() {
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
                    new ToastCommonMethod(SignupActivity.this, "Please Select Gender");
                }
                else if(!terms.isChecked()){
                    new ToastCommonMethod(SignupActivity.this,"Please Accept Terms & Condition");
                }
                else {
                    System.out.println("Signup Successfully");
                    Log.d("RESPONSE", "Signup Successfully");
                    Log.e("RESPONSE", "Signup Successfully");
                    Log.w("RESPONSE", "Signup Successfully");
                    //Toast.makeText(MainActivity.this, "Signup Successfully", Toast.LENGTH_LONG).show();
                    new ToastCommonMethod(SignupActivity.this, "Signup Successfully");

                    //Snackbar.make(v,"Signup Successfully",Snackbar.LENGTH_SHORT).show();
                    new ToastCommonMethod(v, "Signup Successfully");

                    Intent intent = new Intent(SignupActivity.this, DashboardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("FIRST_NAME", firstName.getText().toString());
                    bundle.putString("LAST_NAME", lastName.getText().toString());
                    bundle.putString("CONTACT", contact.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //new ToastCommonMethod(MainActivity.this, DashboardActivity.class);
                }
            }

        });

        gender = findViewById(R.id.signup_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = findViewById(checkedId);
                sGender = button.getText().toString();
                new ToastCommonMethod(SignupActivity.this, sGender);
            }
        });

        terms = findViewById(R.id.signup_terms);


        spinner = findViewById(R.id.signup_city);

        arrayList = new ArrayList<>();
        arrayList.add("Ahmedabad");
        arrayList.add("Jaipur");
        arrayList.add("Rajkot");
        arrayList.add("Kanpur");
        arrayList.add("Baroda");
        arrayList.add("Mumbai");
        arrayList.add("Delhi");
        arrayList.add("Chennai");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignupActivity.this, android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = arrayList.get(position);
                new ToastCommonMethod(SignupActivity.this, sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}