package com.example.it307_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.CreditAdapter;
import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.CategoryModel;
import com.example.it307_project.Model.CreditModel;
import com.example.it307_project.Model.NavModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Credits extends AppCompatActivity {
    RecyclerView RVcredit;
    TextInputEditText ETpayment,ETsearch;
    TextInputLayout creditPayment;
    CreditAdapter creditAdapter;
    LinearLayout LLnewtab;
    ImageView backbtn;
    TextView TVtotalcredit, TVtotalperson, TVcreditpaymentname;
    Button BTNclose, BTNconfirm, BTNaddname;
    Context c = this;

    List<CreditModel> creditModels = new ArrayList<>();
    String[][] creditArray;
    String[][] categoryArray;
    String[][] itemsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_credits);
        initialize();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        RVcredit = findViewById(R.id.RVcredit);
        backbtn = findViewById(R.id.backbtn);
        BTNconfirm = findViewById(R.id.BTNconfirm);
        BTNclose = findViewById(R.id.BTNclose);
        BTNaddname = findViewById(R.id.BTNaddname);
        TVtotalcredit = findViewById(R.id.TVtotalcredit);
        TVtotalperson = findViewById(R.id.TVtotalperson);
        TVcreditpaymentname = findViewById(R.id.TVcreditpaymentname);
        LLnewtab = findViewById(R.id.LLnewtab);
        ETpayment = findViewById(R.id.ETpayment);
        ETsearch = findViewById(R.id.ETsearch);
        creditPayment = findViewById(R.id.creditPayment);

        Bundle extras = getIntent().getExtras();
        creditArray = (String[][]) extras.getSerializable("Credit");
        itemsArray = (String[][]) extras.getSerializable("Items");
        categoryArray = (String[][]) extras.getSerializable("Category");

        float grandTotal = 0;

        for (String[] credit : creditArray) {
            creditModels.add(new CreditModel(credit[0], Float.parseFloat(credit[1])));

            float value = Float.parseFloat(credit[1]);
            grandTotal += value;
        }

        TVtotalcredit.setText("₱" + grandTotal);
        TVtotalperson.setText(String.valueOf(creditArray.length));

        creditAdapter = new CreditAdapter(c, creditModels, new CreditAdapter.ClickListener() {
            @Override
            public void onNameClicked(String name) {

                for (String[] credit : creditArray) {
                    if (credit[0].equals(name)) {
                        float totalCredit = Float.parseFloat(credit[1]);
                        if (totalCredit == 0) {
                            Toast.makeText(c, "This has no balance", Toast.LENGTH_SHORT).show();
                        } else {
                            LLnewtab.setVisibility(View.VISIBLE);
                            ETpayment.setInputType(InputType.TYPE_CLASS_NUMBER);

                            payCredit(name);
                        }
                        break;
                    }
                }
            }
        });
        RVcredit.setAdapter(creditAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVcredit.setLayoutManager(layoutManager);

        ETsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0){
                    clearFilter();
                }else{
                    String query = s.toString();
                    searchFilter(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                searchFilter(query);
            }
        });

        BTNaddname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLnewtab.setVisibility(View.VISIBLE);
                TVcreditpaymentname.setText("Add New Name");
                creditPayment.setHint("Enter new name here");
                ETpayment.setInputType(InputType.TYPE_CLASS_TEXT);
                addName();
            }
        });

        BTNclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLnewtab.setVisibility(View.GONE);
                ETpayment.setText("");
                ETpayment.clearFocus();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Home.class);
                i.putExtra("Credit", creditArray);
                i.putExtra("Items", itemsArray);
                i.putExtra("Category", categoryArray);
                startActivity(i);
                finish();
            }
        });
    }

    private void payCredit(String name) {

        for (String[] credit : creditArray) {
            if (name.equals(credit[0])) {
                TVcreditpaymentname.setText("Tab Name: " + name);
                creditPayment.setHint("Enter payment here");
            }
        }

        ETpayment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    creditPayment.setError("");
                    creditPayment.setErrorEnabled(false);
                }
            }
        });

        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String[] credit : creditArray) {
                    if (credit[0].equals(name)) {
                        String val = ETpayment.getText().toString();
                        boolean isValidPayment = validatePayment(val, Float.parseFloat(credit[1]));
                        if (isValidPayment) {
                            float creditVal = Float.parseFloat(credit[1]);
                            float payment = Float.parseFloat(val);
                            float newTotal = creditVal - payment;

                            AlertDialog.Builder alertCredit = new AlertDialog.Builder(c);
                            alertCredit.setTitle("Credit Payment")
                                    .setMessage("Tab Name: " + credit[0] + "\n" +
                                            "Total Credit: " + "₱" + credit[1] + "\n" +
                                            "Payment: " + "₱" + payment + "\n" +
                                            "New Total Credit: " + "₱" + newTotal)
                                    .setCancelable(false)
                                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            credit[1] = String.valueOf(newTotal);

                                            for (CreditModel model : creditModels) {
                                                if (model.getName().equals(credit[0])) {
                                                    model.setTotalCredit(newTotal);
                                                    break;
                                                }
                                            }

                                            creditAdapter.notifyDataSetChanged();

                                            Toast.makeText(c, "Payment Success", Toast.LENGTH_SHORT).show();

                                            float grandTotal = 0;

                                            grandTotal += Float.parseFloat(credit[1]);

                                            TVtotalcredit.setText("₱" + grandTotal);

                                            LLnewtab.setVisibility(View.GONE);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            AlertDialog alert = alertCredit.create();
                            alert.show();
                        }
                    }
                }

            }
        });
    }

    private void addName() {
        if (TVcreditpaymentname.getText().toString().equals("Add New Name")) {

            BTNconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = ETpayment.getText().toString();
                    if (!name.isEmpty()) {

                        AlertDialog.Builder nameDiag = new AlertDialog.Builder(c);
                        nameDiag.setTitle("Add New Name").setMessage("Name: " + name).setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                creditModels.add(new CreditModel(name, 0));
                                creditArray = new String[creditModels.size()][2];
                                for (int i = 0; i < creditModels.size(); i++) {
                                    creditArray[i] = new String[]{creditModels.get(i).getName(), String.valueOf(creditModels.get(i).getTotalCredit())};
                                }
                                Toast.makeText(c, "New Name Added", Toast.LENGTH_SHORT).show();
                                creditAdapter.notifyDataSetChanged();
                                ETpayment.setText("");
                                LLnewtab.setVisibility(View.GONE);
                                TVtotalperson.setText(String.valueOf(creditArray.length));
                            }
                        });
                       AlertDialog builder = nameDiag.create();
                       builder.show();
                    } else {
                        validatePayment("", 0);
                    }
                }
            });
        }
    }

    private void searchFilter(String result) {
        List<CreditModel> filterList = new ArrayList<>();
        for (CreditModel name : creditModels) {
            if (name.getName().toLowerCase().contains(result.toLowerCase())) {
                filterList.add(name);
            }
        }
        creditAdapter.setFilterList(filterList);
    }

    private void clearFilter() {
        creditAdapter.setFilterList(creditModels);
    }

    private boolean validatePayment(String val, float creditVal) {
        if (val.isEmpty()) {
            creditPayment.setError("Input cannot be empty.");
            creditPayment.setErrorEnabled(true);
            return false;
        }
        if (!TVcreditpaymentname.getText().toString().equals("Add New Name")) {
            float payment = Float.parseFloat(val);
            if (payment <= creditVal) {
                return true;
            } else {
                creditPayment.setError("Payment cannot exceed total credit.");
                creditPayment.setErrorEnabled(true);
                return false;
            }
        }
        return true;
    }
}
