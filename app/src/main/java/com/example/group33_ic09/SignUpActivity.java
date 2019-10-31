package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

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
                try {
                    new SignUp().execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/signup");
                } catch (Exception e) {
                    e.printStackTrace();
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

    private class SignUp extends AsyncTask<String, Void, String>{
        String fName = String.valueOf(et_signUpFirstName.getText());
        String lName = String.valueOf(et_signUpLastName.getText());
        String email = String.valueOf(et_signUpEmail.getText());
        String pw = String.valueOf(et_signUpPassword1.getText());
        String pw2 = String.valueOf(et_signUpPassword2.getText());

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            try {
                URL url = new URL(strings[0]);
                RequestBody formBody = new FormBody.Builder()
                        .add("email", email)
                        .add("password", pw)
                        .add("fname", fName)
                        .add("lname", lName)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        return response.body().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (pw.equals(pw2)) {

                String[] stringArray = s.split(",");
                String[] userInfo = new String[7];

                for (int i = 0; i < stringArray.length; i++) {
                    String[] tempArray = stringArray[i].split(":");
                    userInfo[i] = tempArray[1];
                }

                Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();


                SharedPreferences sharedPref = SignUpActivity.this.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token", userInfo[1]);
                editor.putInt("id", Integer.parseInt(userInfo[2].trim()));
                editor.putString("email", userInfo[3]);
                editor.putString("fName", userInfo[4]);
                editor.putString("lName", userInfo[5]);
                editor.commit();
                Intent intent = new Intent(SignUpActivity.this, InboxActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SignUpActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
