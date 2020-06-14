package com.example.adeelsultan.phcproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComplaintToPatientActivity extends AppCompatActivity {

    EditText To, Subject, Message;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_to_patient);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complaint here");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        To = findViewById(R.id.to);
        Subject = findViewById(R.id.subject);
        Message = findViewById(R.id.message);
        button = findViewById(R.id.sendButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW
                        ,Uri.parse("mailto:" + To.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT, Subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, Message.getText().toString());
                startActivity(intent);
            }
        });
    }
}
