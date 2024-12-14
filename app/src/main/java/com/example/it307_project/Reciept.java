package com.example.it307_project;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Reciept extends AppCompatActivity {
    
    TextView TVreceiptno, TVreceiptdate, TVreceipttime,TVreceiptchange;
    ImageView backbtn;

    TextInputLayout TLreceipt;
    TextInputEditText ETpayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reciept);
        initialize();
        setTextView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        TVreceiptno = findViewById(R.id.TVreceiptno);
        TVreceiptdate = findViewById(R.id.TVreceiptdate);
        TVreceipttime = findViewById(R.id.TVreceipttime);
        TVreceiptchange = findViewById(R.id.TVreceiptchange);
        TLreceipt = findViewById(R.id.TLreciept);
        ETpayment = findViewById(R.id.ETpayment);
        backbtn = findViewById(R.id.backbtn);

        ETpayment.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {

                TVreceiptchange.setText( ETpayment.getText().toString());

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setTextView(){
        Date currentDate = new Date();
        long timestamp = currentDate.getTime();
        long intTimestamp = timestamp / 1000;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());

        TVreceiptno.setText(String.valueOf(intTimestamp));
        TVreceiptdate.setText(date);
        TVreceipttime.setText(time);


    }
}