package com.session.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class AddPaymentActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    EditText amount;
    Button payNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        amount = findViewById(R.id.add_payment_amount);
        payNow = findViewById(R.id.add_payment_now);

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().trim().equals("")){
                    amount.setError("Amount Required");
                }
                else if(amount.getText().toString().trim().equals("0")){
                    amount.setError("Valid Amount Required");
                }
                else{
                    startPaymentMethod();
                }
            }
        });

    }

    private void startPaymentMethod() {
        final Activity activity = this;
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_xsiOz9lYtWKHgF");
        /**
         * Set your logo here
         */
        //checkout.setImage(R.drawable.watermelon);

        /**
         * Reference to current activity
         */

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Purchase Deal From " + getResources().getString(R.string.app_name));
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.mipmap.ic_launcher);
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Integer.parseInt(amount.getText().toString()) * 100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "khatrisagar2@gmail.com");
            preFill.put("contact", "7878232386");
            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("RESPONSE", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(this, "Transaction Id : "+s, Toast.LENGTH_SHORT).show();
        Log.d("RESPONSE_SUCCESS","Transaction Id : "+s);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Failed : "+s, Toast.LENGTH_SHORT).show();
        Log.d("RESPONSE_SUCCESS","Payment Failed : "+s);
    }
}