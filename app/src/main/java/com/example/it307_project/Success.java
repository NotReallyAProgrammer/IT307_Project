package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.it307_project.Model.CartModel;
import com.example.it307_project.Model.ReceiptModel;

import java.io.Serializable;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class Success extends AppCompatActivity {
    TextView TVsuccessctr,TVsuccesstotal,TVpaymentmethod,
            TVpaymenttime,TVpaymentchange,TVpaymentcash,
            TVcreditname,TVcredittotal;

    LinearLayout LLpaidcash,LLcreditname,LLpaidchange,LLcredittotal;
    Button BTNbacktohome;
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
        TVcreditname = findViewById(R.id.TVcreditname);
        TVcredittotal = findViewById(R.id.TVcredittotal);
        LLpaidcash = findViewById(R.id.LLpaidcash);
        LLcreditname =findViewById(R.id.LLcreditname);
        LLpaidchange = findViewById(R.id.LLpaidchange);
        LLcredittotal = findViewById(R.id.LLcredittotal);
        BTNbacktohome = findViewById(R.id.BTNbacktohome);

        Bundle extras = getIntent().getExtras();
        String date = extras.getString("Date");
        float total = extras.getFloat("Total");
        String method = extras.getString("Method");


        if (method.equals("Cash")){
            LLpaidcash.setVisibility(View.VISIBLE);
            LLpaidchange.setVisibility(View.VISIBLE);
            LLcreditname.setVisibility(View.GONE);
            LLcredittotal.setVisibility(View.GONE);
            float cash = extras.getFloat("Cash");
            float change = extras.getFloat("Change");
            TVpaymentcash.setText("₱"+String.valueOf(cash));
            TVpaymentchange.setText("₱"+String.valueOf(change));
        }else{

            String[][] creditArray = (String[][])extras.getSerializable("Credit");
            if(creditArray!=null){
                LLpaidcash.setVisibility(View.GONE);
                LLpaidchange.setVisibility(View.GONE);
                LLcreditname.setVisibility(View.VISIBLE);
                LLcredittotal.setVisibility(View.VISIBLE);
                int userPos = extras.getInt("User");
                TVcreditname.setText(creditArray[userPos][0]);
                TVcredittotal.setText(creditArray[userPos][1]);
            }

        }


        TVsuccesstotal.setText("₱"+String.valueOf(total));
        TVpaymentmethod.setText(method);
        TVpaymenttime.setText(date);
    }

    private void timerTexview() {
        Bundle extras = getIntent().getExtras();
        String[][] creditArray = (String[][])extras.getSerializable("Credit");
        String[][] itemsArray = (String[][]) extras.getSerializable("Items");
        String[] cartegoryArray = extras.getStringArray("Category");
        List<CartModel> cartModels = (List<CartModel>) extras.getSerializable("Cart");
        Intent i = new Intent(c, Home.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("Credit",creditArray);
        i.putExtra("Receipt", (Serializable) cartModels);
        i.putExtra("Items",itemsArray);
        i.putExtra("Category",cartegoryArray);

        CountDownTimer time =  new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TVsuccessctr.setText(String.valueOf(millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                startActivity(i);
            }
        }.start();

        BTNbacktohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.cancel();

                startActivity(i);
            }
        });
    }
}