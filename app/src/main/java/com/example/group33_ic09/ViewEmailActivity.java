package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ViewEmailActivity extends AppCompatActivity {

    TextView tv_viewSender;
    TextView tv_viewSubject;
    TextView tv_viewMessage;
    TextView tv_viewDate;
    Button btn_viewClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_email);
        // set title to subject or sender

        tv_viewSender = findViewById(R.id.tv_viewSender);
        tv_viewSubject = findViewById(R.id.tv_viewSubject);
        tv_viewMessage = findViewById(R.id.tv_viewMessage);
        tv_viewDate = findViewById(R.id.tv_viewDate);
        btn_viewClose = findViewById(R.id.btn_viewClose);
    }
}
