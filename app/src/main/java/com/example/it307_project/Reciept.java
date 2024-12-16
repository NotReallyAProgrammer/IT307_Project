package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.CreditAdapter;
import com.example.it307_project.Adapter.ReceiptAdapter;
import com.example.it307_project.Model.CreditModel;
import com.example.it307_project.Model.ReceiptModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Reciept extends AppCompatActivity {
    
    TextView TVreceiptno, TVreceiptdate, TVreceipttime,TVreceiptchange;
    ImageView backbtn;
    Button BTNcredit, BTNpaid;
    RecyclerView RVrecieptitem,RVcreditname;
    LinearLayout LLrecieptcredit;
    TextInputLayout TLreceipt;
    TextInputEditText ETpayment;
    List<ReceiptModel> receiptModels = new ArrayList<>();
    List<CreditModel> creditModels = new ArrayList<>();
    ReceiptAdapter receiptAdapter;
    CreditAdapter creditAdapter;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reciept);
        initialize();
        setTextView();
        setReceiptAdapter();
        setCreditAdapter();
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
        RVrecieptitem = findViewById(R.id.RVrecieptitem);
        RVcreditname = findViewById(R.id.RVcreditname);
        backbtn = findViewById(R.id.backbtn);
        BTNpaid = findViewById(R.id.BTNpaid);
        BTNcredit = findViewById(R.id.BTNcredit);
        LLrecieptcredit = findViewById(R.id.LLrecieptcredit);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ETpayment.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {

                TVreceiptchange.setText( ETpayment.getText().toString());

            }
        });

        BTNpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Success.class);
                startActivity(i);
            }
        });

        BTNcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LLrecieptcredit.getVisibility() == View.VISIBLE) {
                    BTNcredit.setText("Credit");
                    LLrecieptcredit.setVisibility(View.GONE);
                }else{
                    LLrecieptcredit.setVisibility(View.VISIBLE);
                    BTNcredit.setText("Close");
                }
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

    private void setReceiptAdapter() {
        receiptModels.add(new ReceiptModel("Sample Name",2,200));
        receiptModels.add(new ReceiptModel("Sample Name2",2,200));
        receiptModels.add(new ReceiptModel("Sample Name3",2,200));

        receiptAdapter = new ReceiptAdapter(c,receiptModels);
        RVrecieptitem.setAdapter(receiptAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVrecieptitem.setLayoutManager(layoutManager);

    }
    private void setCreditAdapter() {
        creditModels.add(new CreditModel("Sample Name",100));
        creditModels.add(new CreditModel("Sample Name",100));

        creditAdapter = new CreditAdapter(c,creditModels);
        RVcreditname.setAdapter(creditAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVcreditname.setLayoutManager(layoutManager);
    }

}