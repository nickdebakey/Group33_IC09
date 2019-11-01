package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateEmailActivity extends AppCompatActivity {

    Spinner spnr_users;
    EditText et_createSubject;
    EditText et_createMessage;
    Button btn_createSend;
    Button btn_createCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_email);
        setTitle("Create New Email");

        spnr_users = findViewById(R.id.spnr_users);
        et_createSubject = findViewById(R.id.et_createSubject);
        et_createMessage = findViewById(R.id.et_createMessage);
        btn_createSend = findViewById(R.id.btn_createSend);
        btn_createCancel = findViewById(R.id.btn_createCancel);

        btn_createSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendMessage().execute("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/inbox/add");
            }
        });

        btn_createCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateEmailActivity.this, InboxActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class SendMessage extends AsyncTask<String, Void, String>{
        String subject = String.valueOf(et_createSubject.getText());
        String message = String.valueOf(et_createMessage.getText());

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            try {
                URL url = new URL(strings[0]);
                RequestBody formBody = new FormBody.Builder()
                        // change the receiver id to the spinner value.
                        .add("receiver_id", String.valueOf(6))
                        .add("subject", subject)
                        .add("message", message)
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
    }
}
