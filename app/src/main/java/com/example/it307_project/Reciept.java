package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.it307_project.Model.CartModel;
import com.example.it307_project.Model.CreditModel;
import com.example.it307_project.Model.ReceiptModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Reciept extends AppCompatActivity {
    
    TextView TVreceiptno, TVreceiptdate, TVreceipttime,TVreceiptchange,TVreceiptsubtotal;
    ImageView backbtn;
    Button BTNcredit, BTNpaid;
    RecyclerView RVrecieptitem,RVcreditname;
    LinearLayout LLrecieptcredit;
    TextInputLayout TLreceipt;
    TextInputEditText ETpayment;
    List<ReceiptModel> receiptModels = new ArrayList<>();
    List<CartModel> cartModels = new ArrayList<>();
    List<CreditModel> creditModels = new ArrayList<>();
    ReceiptAdapter receiptAdapter;
    CreditAdapter creditAdapter;
    Context c = this;

    float totalChange = 0.0f;
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
        TVreceiptsubtotal = findViewById(R.id.TVreceiptsubtotal);
        ETpayment = findViewById(R.id.ETpayment);
        RVrecieptitem = findViewById(R.id.RVrecieptitem);
        RVcreditname = findViewById(R.id.RVcreditname);
        backbtn = findViewById(R.id.backbtn);
        BTNpaid = findViewById(R.id.BTNpaid);
        BTNcredit = findViewById(R.id.BTNcredit);
        LLrecieptcredit = findViewById(R.id.LLrecieptcredit);

        String subtotal;

        Bundle extras = getIntent().getExtras();
         cartModels = (List<CartModel>) extras.getSerializable("Items");
        //Adapters

        // |Cart
        if(cartModels != null){
             subtotal = String.valueOf(calculateSubtotal());
            TVreceiptsubtotal.setText("₱" + subtotal);
            for (CartModel item : cartModels) {
                receiptModels.add(new ReceiptModel(item.getName(), item.getQty(), item.getTotal()));
            }
        }

        receiptAdapter = new ReceiptAdapter(c,receiptModels);
        RVrecieptitem.setAdapter(receiptAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVrecieptitem.setLayoutManager(layoutManager);


        // |Credit Names
        creditModels.add(new CreditModel("Sample Name",100));
        creditModels.add(new CreditModel("Sample Name",100));

        creditAdapter = new CreditAdapter(c,creditModels);
        RVcreditname.setAdapter(creditAdapter);

        LinearLayoutManager creditLayoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVcreditname.setLayoutManager(creditLayoutManager);


        //Payment Input

        ETpayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    String input = ETpayment.getText().toString().trim();

                    if (!input.isEmpty()) {
                        try {
                            float payment = Float.parseFloat(input);
                            if (payment >= calculateSubtotal()) {
                                totalChange = payment - calculateSubtotal();
                                TVreceiptchange.setText("₱" + totalChange);

                            }
                        } catch (NumberFormatException e) {

                            TVreceiptchange.setText("₱0.00");
                        }
                    } else {

                        TVreceiptchange.setText("₱0.00");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = ETpayment.getText().toString().trim();

                if (!input.isEmpty()) {
                    try {
                        float payment = Float.parseFloat(input);
                        if (payment >= calculateSubtotal()) {
                            totalChange = payment - calculateSubtotal();
                            TVreceiptchange.setText("₱" + totalChange);

                        }
                    } catch (NumberFormatException e) {
                        TVreceiptchange.setText("₱0.00");
                    }
                } else {
                    TVreceiptchange.setText("₱0.00");
                }
            }
        });


        //Click Listener
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BTNpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = ETpayment.getText().toString().trim();
                float payment = Float.parseFloat(input);
                if(!validatePayment()){
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    TLreceipt.setError(null);
                                    TLreceipt.setErrorEnabled( false );
                                }
                            }, 10000);
                    return;
                }
                Intent i = new Intent(c, Success.class);
                i.putExtra("Total",calculateSubtotal());
                i.putExtra("Change",payment - calculateSubtotal());
                i.putExtra("Cash",payment);
                i.putExtra("Method","Cash");
                i.putExtra("Date",setTextView());
                i.putExtra("Receipt", (Serializable) receiptModels);
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
    private float calculateSubtotal() {
        float subtotal = 0.0f;
        for (CartModel cartItem : cartModels) {
            subtotal += cartItem.getTotal();
        }

        return subtotal;
    }

    private boolean validatePayment() {
        String val = ETpayment.getText().toString();
        float payment = Float.parseFloat(val);
        if (val.isEmpty()) {
            TLreceipt.setError( "Field can not be empty" );
            return false;
        }else if(payment < calculateSubtotal()){
            TLreceipt.setError( "Invalid Payment" );
            return false;
        }else {
            TLreceipt.setError(null);
            TLreceipt.setErrorEnabled( false );
            return true;
        }
    }

    private String setTextView(){
        Date currentDate = new Date();
        long timestamp = currentDate.getTime();
        long intTimestamp = timestamp / 1000;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());
        String recieptDate = date + " " + time;

        TVreceiptno.setText(String.valueOf(intTimestamp));
        TVreceiptdate.setText(date);
        TVreceipttime.setText(time);

        return recieptDate;

    }


}