package com.session.demo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    Button editProfile,updateProfile,deleteProfile;
    EditText firstName, lastName, contact, email, password, confirm_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup gender;
    RadioButton male,female,transgender;
    Spinner spinner;
    ArrayList<String> arrayList;
    String sGender, sCity;

    TextView logout;

    CircleImageView profileIv,cameraIv;

    SQLiteDatabase db;
    SharedPreferences sp;
    ApiInterface apiInterface;
    ProgressDialog pd;

    int IMAGE_REQUEST_CODE = 123;

    String[] appPermission = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
    int PERMISSION_REQUEST_CODE = 100;
    String sSelectedPath = "";

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] appPermission33 = {android.Manifest.permission.READ_MEDIA_IMAGES};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        db = openOrCreateDatabase("SessionApp.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME VARCHAR(50),LASTNAME VARCHAR(50),CONTACT BIGINT(10),EMAIL VARCHAR(100),PASSWORD VARCHAR(20),GENDER VARCHAR(10),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        editProfile = findViewById(R.id.profile_edit_button);
        updateProfile = findViewById(R.id.profile_update_button);
        deleteProfile = findViewById(R.id.profile_delete_button);

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Account Delete!");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Are You Sure Want to delete your account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //doSqliteDelete();
                        if(new ConnectionDetector(ProfileActivity.this).networkConnected()){
                            //new doDelete().execute();
                            pd = new ProgressDialog(ProfileActivity.this);
                            pd.setMessage("Please Wait...");
                            pd.setCancelable(false);
                            pd.show();
                            doDeleteRetrofit();
                        }
                        else{
                            new ConnectionDetector(ProfileActivity.this).networkDisconnected();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProfileActivity.this, "Rate Us", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });

        firstName = findViewById(R.id.profile_first_name);
        lastName = findViewById(R.id.profile_last_name);
        contact = findViewById(R.id.profile_contact);
        email = findViewById(R.id.profile_email);
        password = findViewById(R.id.profile_password);
        confirm_password = findViewById(R.id.profile_confirm_password);

        logout = findViewById(R.id.profile_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().clear().commit();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
                    //doSqliteUpdate();
                    if(new ConnectionDetector(ProfileActivity.this).networkConnected()){
                        //new doUpdate().execute();
                        pd = new ProgressDialog(ProfileActivity.this);
                        pd.setMessage("Please Wait...");
                        pd.setCancelable(false);
                        pd.show();
                        if(sSelectedPath.equalsIgnoreCase("")){
                            doUpdateRetrofit();
                        }
                        else{
                            doUpdateImageRetrofit();
                        }
                    }
                    else{
                        new ConnectionDetector(ProfileActivity.this).networkDisconnected();
                    }

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

        cameraIv = findViewById(R.id.profile_camera);
        profileIv = findViewById(R.id.profile_image);

        cameraIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateProfile.getVisibility() == View.VISIBLE) {
                    if (checkAndRequestPermission()) {
                        selectImageData();
                    }
                }
            }
        });

        setData(false);

    }

    public boolean checkAndRequestPermission() {
        List<String> listPermission = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            for (String perm : appPermission33) {
                if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                    listPermission.add(perm);
                }
            }
            if (!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermission.toArray(new String[listPermission.size()]), PERMISSION_REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        } else {
            for (String perm : appPermission) {
                if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                    listPermission.add(perm);
                }
            }
            if (!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermission.toArray(new String[listPermission.size()]), PERMISSION_REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResult = new HashMap<>();
            int deniedCount = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResult.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            if (deniedCount == 0) {
                selectImageData();
            } else {
                for (Map.Entry<String, Integer> entry : permissionResult.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this, permName)) {
                        /*showDialogPermission("", "This App needs Read External Storage And Location permissions to work whithout and problems.",*/
                        showDialogPermission("", "This App needs Read External Storage permissions to work whithout and problems.",
                                "Yes, Grant permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        checkAndRequestPermission();
                                    }
                                },
                                "No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        //finishAffinity();
                                    }
                                }, false);
                    } else {
                        showDialogPermission("", "You have denied some permissions. Allow all permissions at [Setting] > [Permissions]",
                                "Go to Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }, "No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        //finish();
                                    }
                                }, false);
                        break;
                    }
                }
            }
        }
    }

    public AlertDialog showDialogPermission(String title, String msg, String positiveLable, DialogInterface.OnClickListener positiveOnClickListener, String negativeLable, DialogInterface.OnClickListener negativeOnClickListener, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle(title);
        builder.setCancelable(isCancelable);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLable, positiveOnClickListener);
        builder.setNegativeButton(negativeLable, negativeOnClickListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    private void doDeleteRetrofit() {
        Call<GetSignupData> call = apiInterface.doDelete(
                sp.getString(ConstantSp.USERID,"")
        );

        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new ToastCommonMethod(ProfileActivity.this,response.body().message);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        new ToastCommonMethod(ProfileActivity.this,response.body().message);
                    }
                }
                else{
                    new ToastCommonMethod(ProfileActivity.this,ConstantSp.SERVER_ERROR+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                new ToastCommonMethod(ProfileActivity.this,t.getMessage());
            }
        });
    }

    private void doUpdateImageRetrofit() {

        RequestBody idPart = RequestBody.create(MultipartBody.FORM,sp.getString(ConstantSp.USERID,""));
        RequestBody firstNamePart = RequestBody.create(MultipartBody.FORM,firstName.getText().toString());
        RequestBody lastNamePart = RequestBody.create(MultipartBody.FORM,lastName.getText().toString());
        RequestBody contactPart = RequestBody.create(MultipartBody.FORM,contact.getText().toString());
        RequestBody emailPart = RequestBody.create(MultipartBody.FORM,email.getText().toString());
        RequestBody passwordPart = RequestBody.create(MultipartBody.FORM,password.getText().toString());
        RequestBody genderPart = RequestBody.create(MultipartBody.FORM,sGender);
        RequestBody cityPart = RequestBody.create(MultipartBody.FORM,sCity);

        File file = new File(sSelectedPath);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Call<GetUpdateImageData> call = apiInterface.doUpdateImage(
                firstNamePart,
                lastNamePart,
                contactPart,
                emailPart,
                passwordPart,
                genderPart,
                cityPart,
                idPart,
                filePart
        );

        call.enqueue(new Callback<GetUpdateImageData>() {
            @Override
            public void onResponse(Call<GetUpdateImageData> call, Response<GetUpdateImageData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new ToastCommonMethod(ProfileActivity.this,response.body().message);
                        sp.edit().putString(ConstantSp.FIRSTNAME,firstName.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.LASTNAME,lastName.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.EMAIL,email.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.CONTACT,contact.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.PASSWORD,password.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                        sp.edit().putString(ConstantSp.CITY,sCity).commit();
                        sp.edit().putString(ConstantSp.PROFILE,response.body().profile).commit();
                        setData(false);
                    }
                    else{
                        new ToastCommonMethod(ProfileActivity.this,response.body().message);
                    }
                }
                else{
                    new ToastCommonMethod(ProfileActivity.this,ConstantSp.SERVER_ERROR+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetUpdateImageData> call, Throwable t) {
                pd.dismiss();
                new ToastCommonMethod(ProfileActivity.this,t.getMessage());
            }
        });
    }

    private void doUpdateRetrofit() {
        Call<GetSignupData> call = apiInterface.doUpdate(
                firstName.getText().toString(),
                lastName.getText().toString(),
                contact.getText().toString(),
                email.getText().toString(),
                password.getText().toString(),
                sGender,
                sCity,
                sp.getString(ConstantSp.USERID,"")
        );

        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new ToastCommonMethod(ProfileActivity.this,response.body().message);
                        sp.edit().putString(ConstantSp.FIRSTNAME,firstName.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.LASTNAME,lastName.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.EMAIL,email.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.CONTACT,contact.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.PASSWORD,password.getText().toString()).commit();
                        sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                        sp.edit().putString(ConstantSp.CITY,sCity).commit();

                        setData(false);
                    }
                    else{
                        new ToastCommonMethod(ProfileActivity.this,response.body().message);
                    }
                }
                else{
                    new ToastCommonMethod(ProfileActivity.this,ConstantSp.SERVER_ERROR+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                new ToastCommonMethod(ProfileActivity.this,t.getMessage());
            }
        });
    }

    private void doSqliteDelete() {
        String deletQuery = "DELETE FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
        db.execSQL(deletQuery);
        Toast.makeText(ProfileActivity.this, "Profile Deleted Successfully", Toast.LENGTH_SHORT).show();

        sp.edit().clear().commit();
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void doSqliteUpdate() {
        String updateQuery = "UPDATE USERS SET FIRSTNAME='"+firstName.getText().toString()+"',LASTNAME='"+lastName.getText().toString()+"', CONTACT='"+contact.getText().toString()+"',EMAIL='"+email.getText().toString()+"',PASSWORD='"+password.getText().toString()+"',GENDER='"+sGender+"',CITY='"+sCity+"' WHERE USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
        db.execSQL(updateQuery);

        Toast.makeText(ProfileActivity.this, "Profile Update Successfully", Toast.LENGTH_SHORT).show();

        sp.edit().putString(ConstantSp.FIRSTNAME,firstName.getText().toString()).commit();
        sp.edit().putString(ConstantSp.LASTNAME,lastName.getText().toString()).commit();
        sp.edit().putString(ConstantSp.EMAIL,email.getText().toString()).commit();
        sp.edit().putString(ConstantSp.CONTACT,contact.getText().toString()).commit();
        sp.edit().putString(ConstantSp.PASSWORD,password.getText().toString()).commit();
        sp.edit().putString(ConstantSp.GENDER,sGender).commit();
        sp.edit().putString(ConstantSp.CITY,sCity).commit();

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

        if(sp.getString(ConstantSp.PROFILE,"").equalsIgnoreCase("")){
            profileIv.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(ProfileActivity.this).load(sp.getString(ConstantSp.PROFILE,"")).placeholder(R.mipmap.ic_launcher).into(profileIv);
        }

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

    private class doUpdate extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ProfileActivity.this);
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
            hashMap.put("userid",sp.getString(ConstantSp.USERID,""));
            return new MakeServiceCall().MakeServiceCall(ConstantSp.UPDATE_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getBoolean("status")){
                    new ToastCommonMethod(ProfileActivity.this,object.getString("message"));
                    sp.edit().putString(ConstantSp.FIRSTNAME,firstName.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.LASTNAME,lastName.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.EMAIL,email.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.CONTACT,contact.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.PASSWORD,password.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.GENDER,sGender).commit();
                    sp.edit().putString(ConstantSp.CITY,sCity).commit();

                    setData(false);
                }
                else{
                    new ToastCommonMethod(ProfileActivity.this,object.getString("message"));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class doDelete  extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ProfileActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("userid",sp.getString(ConstantSp.USERID,""));
            return new MakeServiceCall().MakeServiceCall(ConstantSp.DELETE_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getBoolean("status")){
                    new ToastCommonMethod(ProfileActivity.this,object.getString("message"));
                    sp.edit().clear().commit();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    new ToastCommonMethod(ProfileActivity.this,object.getString("message"));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void selectImageData() {
        FishBun.with(ProfileActivity.this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .isStartInAllView(false)
                .setIsUseDetailView(false)
                .setReachLimitAutomaticClose(true)
                .setSelectCircleStrokeColor(android.R.color.transparent)
                .setActionBarColor(Color.parseColor("#F44336"), Color.parseColor("#F44336"), false)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .startAlbumWithOnActivityResult(IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            //mSelected = Matisse.obtainResult(data);
            List<Uri> mSelected = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
            Log.d("RESPONSE_IMAGE_URI",mSelected.get(0).toString());
            profileIv.setImageURI(mSelected.get(0));
            sSelectedPath = getImage(mSelected.get(0));
            Log.d("RESPONSE_IMAGE_PATH",sSelectedPath);
            /*sChangeImage = "Yes";*/
        }
    }

    private String getImage(Uri uri) {
        if (uri != null) {
            String path = null;
            String[] s_array = {MediaStore.Images.Media.DATA};
            Cursor c = managedQuery(uri, s_array, null, null, null);
            int id = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (c.moveToFirst()) {
                do {
                    path = c.getString(id);
                }
                while (c.moveToNext());
                //c.close();
                if (path != null) {
                    return path;
                }
            }
        }
        return "";
    }

}