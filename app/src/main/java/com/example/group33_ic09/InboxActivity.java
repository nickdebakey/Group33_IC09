package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.IOException;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InboxActivity extends AppCompatActivity {

    ImageView iv_new;
    ImageView iv_logout;
    TextView tv_name;
    ListView lv_emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle("Inbox");

        iv_new = findViewById(R.id.iv_new);
        iv_logout = findViewById(R.id.iv_logout);
        tv_name = findViewById(R.id.tv_name);
        lv_emails = findViewById(R.id.lv_emails);

        iv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InboxActivity.this, CreateEmailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSharedPreferences(String.valueOf(R.string.preference_file_key));
                Intent intent = new Intent(InboxActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sharedPref = InboxActivity.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String fName = sharedPref.getString("fName", "");
        String lName = sharedPref.getString("lName", "");
        String fullName = (fName.replaceAll("\"", "") + " " + lName.replaceAll("\"", ""));
        tv_name.setText(fullName);

        new GetEmails().execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox/");

       // InboxAdapter inboxAdapter = new InboxAdapter(InboxActivity.this, R.layout.inbox, emails);
    }

    private class GetEmails extends AsyncTask<String, Void, String>{
        SharedPreferences sharedPref = InboxActivity.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Authorization", sharedPref.getString("token", "").replaceAll("\"", ""))
                    .url(strings[0])
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
