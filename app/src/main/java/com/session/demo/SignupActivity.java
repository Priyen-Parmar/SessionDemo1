package com.session.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    Button signup;
    EditText firstName, lastName, contact, email, password, confirm_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup gender;
    CheckBox terms;
    Spinner spinner;
    ArrayList<String> arrayList;
    String sGender, sCity;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        db = openOrCreateDatabase("SessionApp.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME VARCHAR(50),LASTNAME VARCHAR(50),CONTACT BIGINT(10),EMAIL VARCHAR(100),PASSWORD VARCHAR(20),GENDER VARCHAR(10),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

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
                    //doSqliteSignup();
                    if(new ConnectionDetector(SignupActivity.this).networkConnected()){
                        new doSignup().execute();
                    }
                    else{
                        new ConnectionDetector(SignupActivity.this).networkDisconnected();
                    }


                    /*System.out.println("Signup Successfully");
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
                    startActivity(intent);*/

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

    private void doSqliteSignup() {
        String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"' OR CONTACT='"+contact.getText().toString()+"' ";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            new ToastCommonMethod(SignupActivity.this, "User Already Exists");
            onBackPressed();
        }
        else{
            String insertQuery = "INSERT INTO USERS VALUES (NULL,'"+firstName.getText().toString()+"','"+lastName.getText().toString()+"','"+contact.getText().toString()+"','"+email.getText().toString()+"','"+password.getText().toString()+"','"+sGender+"','"+sCity+"')";
            db.execSQL(insertQuery);
            new ToastCommonMethod(SignupActivity.this, "Signup Successfully");
        }
    }

    private class doSignup extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SignupActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("firstname",firstName.getText().toString());
            hashMap.put("lastname",lastName.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            hashMap.put("gender",sGender);
            hashMap.put("city",sCity);
            return new MakeServiceCall().MakeServiceCall(ConstantSp.SIGNUP_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getBoolean("status")){
                    new ToastCommonMethod(SignupActivity.this,object.getString("message"));
                    onBackPressed();
                }
                else{
                    new ToastCommonMethod(SignupActivity.this,object.getString("message"));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }
}