package com.session.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button login;
    TextView signup;
    EditText email, password;
    SQLiteDatabase db;
    SharedPreferences sp;
    ApiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        db = openOrCreateDatabase("SessionApp.db", MODE_PRIVATE, null);
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
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().equals("")) {
                    email.setError("EMail Id/Contact No. Required");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().trim().length() < 6) {
                    password.setError("Min. 6 Char Password Required");
                } else {
                    //doSqliteLogin();
                    if (new ConnectionDetector(MainActivity.this).networkConnected()) {
                        //new doLogin().execute();
                        pd = new ProgressDialog(MainActivity.this);
                        pd.setMessage("Please Wait...");
                        pd.setCancelable(false);
                        pd.show();
                        doLoginRetrofit();
                    } else {
                        new ConnectionDetector(MainActivity.this).networkDisconnected();
                    }
                }
            }
        });

    }

    private void doLoginRetrofit() {
        Call<GetLoginData> call = apiInterface.doLogin(
                email.getText().toString(),
                password.getText().toString()
        );

        call.enqueue(new Callback<GetLoginData>() {
            @Override
            public void onResponse(Call<GetLoginData> call, Response<GetLoginData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new ToastCommonMethod(MainActivity.this,response.body().message);
                        sp.edit().putString(ConstantSp.USERID, response.body().userData.userId).commit();
                        sp.edit().putString(ConstantSp.FIRSTNAME, response.body().userData.firstName).commit();
                        sp.edit().putString(ConstantSp.LASTNAME, response.body().userData.lastName).commit();
                        sp.edit().putString(ConstantSp.EMAIL, response.body().userData.email).commit();
                        sp.edit().putString(ConstantSp.CONTACT, response.body().userData.contact).commit();
                        sp.edit().putString(ConstantSp.PASSWORD, "").commit();
                        sp.edit().putString(ConstantSp.GENDER, response.body().userData.gender).commit();
                        sp.edit().putString(ConstantSp.CITY, response.body().userData.city).commit();

                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                    else{
                        new ToastCommonMethod(MainActivity.this,response.body().message);
                    }
                }
                else{
                    new ToastCommonMethod(MainActivity.this,ConstantSp.SERVER_ERROR+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetLoginData> call, Throwable t) {
                pd.dismiss();
                new ToastCommonMethod(MainActivity.this,t.getMessage());
            }
        });
    }

    private void doSqliteLogin() {
        String selectQuery = "SELECT * FROM USERS WHERE EMAIL='" + email.getText().toString() + "' AND PASSWORD='" + password.getText().toString() + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                sp.edit().putString(ConstantSp.USERID, cursor.getString(0)).commit();
                sp.edit().putString(ConstantSp.FIRSTNAME, cursor.getString(1)).commit();
                sp.edit().putString(ConstantSp.LASTNAME, cursor.getString(2)).commit();
                sp.edit().putString(ConstantSp.EMAIL, cursor.getString(4)).commit();
                sp.edit().putString(ConstantSp.CONTACT, cursor.getString(3)).commit();
                sp.edit().putString(ConstantSp.PASSWORD, cursor.getString(5)).commit();
                sp.edit().putString(ConstantSp.GENDER, cursor.getString(6)).commit();
                sp.edit().putString(ConstantSp.CITY, cursor.getString(7)).commit();
            }
            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else {
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

    private class doLogin extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("email", email.getText().toString());
            hashMap.put("password", password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantSp.LOGIN_URL, MakeServiceCall.POST, hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("status")) {
                    new ToastCommonMethod(MainActivity.this, object.getString("message"));

                    JSONObject jsonObject = object.getJSONObject("userData");

                    sp.edit().putString(ConstantSp.USERID, jsonObject.getString("userId")).commit();
                    sp.edit().putString(ConstantSp.FIRSTNAME, jsonObject.getString("first_name")).commit();
                    sp.edit().putString(ConstantSp.LASTNAME, jsonObject.getString("last_name")).commit();
                    sp.edit().putString(ConstantSp.EMAIL, jsonObject.getString("email")).commit();
                    sp.edit().putString(ConstantSp.CONTACT, jsonObject.getString("contact")).commit();
                    sp.edit().putString(ConstantSp.PASSWORD, "").commit();
                    sp.edit().putString(ConstantSp.GENDER, jsonObject.getString("gender")).commit();
                    sp.edit().putString(ConstantSp.CITY, jsonObject.getString("city")).commit();

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    new ToastCommonMethod(MainActivity.this, object.getString("message"));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }

}