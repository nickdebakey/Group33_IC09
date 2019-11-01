package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import javax.security.auth.callback.Callback;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    EditText et_email;
    EditText et_password;
    Button btn_login;
    Button btn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Mailer");

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signUp = findViewById(R.id.btn_signUp);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);

        if (!sharedPref.getString(getString(R.string.preference_file_key), "").equals("")) {
            Intent intent = new Intent(MainActivity.this, InboxActivity.class);
            startActivity(intent);
            finish();
        }


        // clicking on the "Login" button submits the login information for verification
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetLogin().execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/login");
            }
        });

        // clicking on "Sign Up" opens the Sign Up Activity and closes the Login Activity
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class GetLogin extends AsyncTask<String, Void, String>{
        String email = String.valueOf(et_email.getText());
        String password = String.valueOf(et_password.getText());

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            try {
                URL url = new URL(strings[0]);
                RequestBody formBody = new FormBody.Builder()
                                .add("email", email)
                                .add("password", password)
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
        protected void onPostExecute(String string) {
            if (string == null || string.equals("") || string.length() < 6) {
                Toast.makeText(MainActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
            } else {
                String[] stringArray = string.split(",");
                String[] userInfo = new String[7];

                for (int i = 0; i < stringArray.length; i++) {
                    String[] tempArray = stringArray[i].split(":");
                    userInfo[i] = tempArray[1];
                }

                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("token", userInfo[1]);
                editor.putInt("id", Integer.parseInt(userInfo[2].trim()));
                editor.putString("email", userInfo[3]);
                editor.putString("fName", userInfo[4]);
                editor.putString("lName", userInfo[5]);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, InboxActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}