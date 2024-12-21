package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.it307_project.Adapter.SalesItemAdapter;
import com.example.it307_project.Model.AllItemModel;
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
    
    TextView TVreceiptno, TVreceiptdate, TVreceipttime,TVreceiptchange,TVreceiptsubtotal,TVcreditname,TVcredittotal,TVcredititems,TVcreditnew;
    ImageView backbtn;
    Button BTNcredit, BTNpaid;
    RecyclerView RVrecieptitem,RVcreditname;
    LinearLayout LLrecieptcredit,LLcredit,LLcreditselected,LLhidepayment;
    TextInputLayout TLreceipt;
    TextInputEditText ETpayment,ETsearch;
    List<ReceiptModel> receiptModels = new ArrayList<>();
    List<CartModel> cartModels = new ArrayList<>();
    List<CreditModel> creditModels = new ArrayList<>();
    ReceiptAdapter receiptAdapter;
    CreditAdapter creditAdapter;
    Context c = this;

    float totalChange = 0.0f,newTotal;
    int tempCount = 0, userPos = -1;
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
        TVcreditname = findViewById(R.id.TVcreditname);
        TVcredittotal = findViewById(R.id.TVcredittotal);
        TVcredititems = findViewById(R.id.TVcredititems);
        TVcreditnew = findViewById(R.id.TVcreditnew);
        ETpayment = findViewById(R.id.ETpayment);
        ETsearch = findViewById(R.id.ETsearch);
        RVrecieptitem = findViewById(R.id.RVrecieptitem);
        RVcreditname = findViewById(R.id.RVcreditname);
        backbtn = findViewById(R.id.backbtn);
        BTNpaid = findViewById(R.id.BTNpaid);
        BTNcredit = findViewById(R.id.BTNcredit);
        LLrecieptcredit = findViewById(R.id.LLrecieptcredit);
        LLcredit = findViewById(R.id.LLcredit);
        LLcreditselected = findViewById(R.id.LLcreditselected);
        LLhidepayment = findViewById(R.id.LLhidepayment);

        String subtotal="";


        Bundle extras = getIntent().getExtras();
        cartModels = (List<CartModel>) extras.getSerializable("Items");
        String[][] creditArray = (String[][]) extras.getSerializable("Credit");
        //Adapters

        // |Cart
        if(cartModels != null){
             subtotal = String.valueOf(calculateSubtotal());
            TVreceiptsubtotal.setText("₱" + subtotal);
            for (CartModel item : cartModels) {
                receiptModels.add(new ReceiptModel(item.getId(),item.getName(), item.getQty(), item.getTotal()));
            }
        }

        receiptAdapter = new ReceiptAdapter(c,receiptModels);
        RVrecieptitem.setAdapter(receiptAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVrecieptitem.setLayoutManager(layoutManager);


        // |Credit Names
        for (String[] credit:creditArray){
            creditModels.add(new CreditModel(credit[0],Float.parseFloat(credit[1])));
        }

        String finalSubtotal = subtotal;
        creditAdapter = new CreditAdapter(c, creditModels, new CreditAdapter.ClickListener() {
            @Override
            public void onNameClicked(String name) {
                for(CreditModel credit:creditModels){
                    if (credit.getName().equals(name)){
                        name = credit.getName();
                         newTotal =  credit.getTotalCredit() + Float.parseFloat(finalSubtotal);
                        float totalOld = credit.getTotalCredit();
                        LLcreditselected.setVisibility(View.VISIBLE);
                        LLcredit.setVisibility(View.GONE);

                        TVcreditname.setText(name);
                        TVcredittotal.setText("₱"+totalOld);
                        TVcredititems.setText("₱"+finalSubtotal);
                        TVcreditnew.setText("₱"+String.valueOf(newTotal));
                        BTNpaid.setVisibility(View.VISIBLE);
                        BTNpaid.setText("Proceed");
                        BTNcredit.setText("Back");
                        userPos = tempCount;
                        break;
                    }else{
                        tempCount++;
                    }
                }
            }
        });
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

        //Search Credit Name
        ETsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                searchFilter(query);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                Intent i = new Intent(c, Success.class);
                if(BTNpaid.getText().equals("Proceed")){

                    creditArray[userPos][1] = String.valueOf(newTotal);

                    i.putExtra("Total",calculateSubtotal());
                    i.putExtra("User",userPos);
                    i.putExtra("Method","Credit");
                    i.putExtra("Date",setTextView());
                    i.putExtra("Items", (Serializable) cartModels);
                    i.putExtra("Credit",creditArray);
                    startActivity(i);

                }else{
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
                    String val = ETpayment.getText().toString();
                    String input = ETpayment.getText().toString().trim();
                    float payment = Float.parseFloat(input);
                    i.putExtra("Total",calculateSubtotal());
                    i.putExtra("Change",payment - calculateSubtotal());
                    i.putExtra("Cash",payment);
                    i.putExtra("Method","Cash");
                    i.putExtra("Date",setTextView());
                    i.putExtra("Items", (Serializable) cartModels);
                    startActivity(i);
                }

            }
        });

        BTNcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BTNcredit.getText().equals("Close")) {
                    BTNcredit.setText("Credit");
                    BTNpaid.setVisibility(View.VISIBLE);
                    LLrecieptcredit.setVisibility(View.GONE);
                    ETpayment.setEnabled(true);
                    LLhidepayment.setVisibility(View.VISIBLE);
                }else if(BTNcredit.getText().equals("Back")){
                    LLcreditselected.setVisibility(View.GONE);
                    LLcredit.setVisibility(View.VISIBLE);
                    BTNpaid.setVisibility(View.GONE);
                    BTNcredit.setText("Close");
                    ETpayment.setEnabled(false);
                }else{
                    LLrecieptcredit.setVisibility(View.VISIBLE);
                    BTNpaid.setVisibility(View.GONE);
                    BTNcredit.setText("Close");
                    LLhidepayment.setVisibility(View.GONE);
                    ETpayment.setEnabled(false);

                }
            }
        });
    }
    private float calculateSubtotal() {
        float subtotal = 0.0f;
        for (CartModel cartItem : cartModels) {
            subtotal += cartItem.getTotal();
        }
        return Math.round(subtotal * 100.0f) / 100.0f;
    }

    private boolean validatePayment() {
        String val = ETpayment.getText().toString();
        if (val.isEmpty()) {
            TLreceipt.setError("Field cannot be empty");
            return false;
        } else {
            try {
                float payment = Float.parseFloat(val);
                if (payment < calculateSubtotal()) {
                    TLreceipt.setError("Invalid Payment");
                    return false;
                } else {
                    TLreceipt.setError(null);
                    TLreceipt.setErrorEnabled(false);
                    return true;
                }
            } catch (NumberFormatException e) {
                TLreceipt.setError("Invalid number format");
                return false;
            }
        }
    }

    private void searchFilter(String result) {
        List<CreditModel> filterList = new ArrayList<>();
        for (CreditModel credit : creditModels) {
            if (credit.getName().toLowerCase().contains(result.toLowerCase())) {
                filterList.add(credit);
            }
        }
        creditAdapter.setFilterList(filterList);
    }

    private String setTextView(){
        Date currentDate = new Date();
        long timestamp = currentDate.getTime();
        long intTimestamp = timestamp / 1000;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
        SimpleDateFormat setDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        String date = dateFormat.format(calendar.getTime());
        String TVdate = setDate.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());
        String recieptDate = date + " " + time;

        TVreceiptno.setText(String.valueOf(intTimestamp));
        TVreceiptdate.setText(TVdate);
        TVreceipttime.setText(time);

        return recieptDate;

    }

}