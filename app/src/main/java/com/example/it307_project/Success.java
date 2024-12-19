package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.it307_project.Model.ReceiptModel;

import java.util.List;

public class Success extends AppCompatActivity {
    TextView TVsuccessctr,TVsuccesstotal,TVpaymentmethod,TVpaymenttime,TVpaymentchange,TVpaymentcash;
    Context c= this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_success);

        initialize();
        timerTexview();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        TVsuccessctr = findViewById(R.id.TVsuccessctr);
        TVsuccesstotal = findViewById(R.id.TVsuccesstotal);
        TVpaymentmethod = findViewById(R.id.TVpaymentmethod);
        TVpaymenttime = findViewById(R.id.TVpaymenttime);
        TVpaymentchange = findViewById(R.id.TVpaymentchange);
        TVpaymentcash = findViewById(R.id.TVpaymentcash);


        Bundle extras = getIntent().getExtras();
        List<ReceiptModel> receiptModels = (List<ReceiptModel>) extras.getSerializable("Receipt");
        String date = extras.getString("Date");
        float total = extras.getFloat("Total");
        float cash = extras.getFloat("Cash");
        float change = extras.getFloat("Change");
        String method = extras.getString("Method");


        TVsuccesstotal.setText("₱"+String.valueOf(total));
        TVpaymentmethod.setText(method);
        TVpaymentcash.setText("₱"+String.valueOf(cash));
        TVpaymentchange.setText("₱"+String.valueOf(change));
        TVpaymenttime.setText(date);
    }

    private void timerTexview() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TVsuccessctr.setText(String.valueOf(millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                Intent i = new Intent(c, Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);


            }
        }.start();
    }
}