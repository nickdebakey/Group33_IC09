package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    }
}
