package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText et_signUpFirstName;
    EditText et_signUpLastName;
    EditText et_signUpEmail;
    EditText et_signUpPassword1;
    EditText et_signUpPassword2;
    Button btn_signUpConfirm;
    Button btn_signUpCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        et_signUpFirstName = findViewById(R.id.et_signUpFirstName);
        et_signUpLastName = findViewById(R.id.et_signUpLastName);
        et_signUpEmail = findViewById(R.id.et_signUpEmail);
        et_signUpPassword1 = findViewById(R.id.et_signUpPassword1);
        et_signUpPassword2 = findViewById(R.id.et_signUpPassword2);
        btn_signUpConfirm = findViewById(R.id.btn_signUpConfirm);
        btn_signUpCancel = findViewById(R.id.btn_signUpCancel);

        btn_signUpConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_signUpPassword1.equals(et_signUpPassword2)){
                    try {
                        run();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_signUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("email", String.valueOf(et_signUpEmail))
                .add("password", String.valueOf(et_signUpPassword1))
                .add("fname", String.valueOf(et_signUpFirstName.getText()))
                .add("lname", String.valueOf(et_signUpLastName.getText()))
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }
}
