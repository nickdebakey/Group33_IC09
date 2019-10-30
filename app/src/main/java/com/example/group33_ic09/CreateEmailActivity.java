package com.example.group33_ic09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    }
}
