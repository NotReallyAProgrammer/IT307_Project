package com.example.it307_project;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.CartAdapter;
import com.example.it307_project.Adapter.CategoryAdapter;
import com.example.it307_project.Adapter.SalesItemAdapter;
import com.example.it307_project.Model.CartModel;
import com.example.it307_project.Model.CategoryModel;
import com.example.it307_project.Model.ItemModel;
import com.example.it307_project.Model.SalesItemModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sales extends AppCompatActivity {
    RecyclerView RVsalesitem, RVsalescategory, RVsalescart;
    ImageView backbtn;
    TextView TVheader, TVclear, TVsubtotal;
    TextInputEditText ETsearch;
    Button BTNcart;

    LinearLayout LLnoitem, LLcart;

    List<SalesItemModel> salesItemModels = new ArrayList<>();
    List<CategoryModel> categoryModels = new ArrayList<>();
    List<CartModel> cartModels = new ArrayList<>();
    SalesItemAdapter salesItemAdapter;
    CategoryAdapter categoryAdapter;
    CartAdapter cartAdapter;

    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales);
        initialize();
        calculateSubtotal();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        LLcart = findViewById(R.id.LLcart);
        LLnoitem = findViewById(R.id.Llnoitem);
        RVsalesitem = findViewById(R.id.RVsalesitem);
        RVsalescategory = findViewById(R.id.RVsalescategory);
        RVsalescart = findViewById(R.id.RVsalescart);
        TVheader = findViewById(R.id.TVheader);
        TVclear = findViewById(R.id.TVclear);
        TVsubtotal = findViewById(R.id.TVsubtotal);
        ETsearch = findViewById(R.id.ETsearch);
        backbtn = findViewById(R.id.backbtn);
        BTNcart = findViewById(R.id.BTNcart);

        Intent intent = getIntent();
        String[] categoryArray = intent.getStringArrayExtra("Category");
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");

        // Setting Adapters
        // Items
        for (String[] item : itemsArray) {
            int resId = getResources().getIdentifier(item[6].split("\\.")[2], "mipmap", getPackageName());
            salesItemModels.add(new SalesItemModel(item[1], item[2], Float.parseFloat(item[5]), resId));
        }

        salesItemAdapter = new SalesItemAdapter(c, salesItemModels, new SalesItemAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                setCartAdapter(position);
                calculateSubtotal();
                updateCart();
            }
        });
        RVsalesitem.setAdapter(salesItemAdapter);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 3);
        RVsalesitem.setLayoutManager(mGridLayoutManager);

        // Cart
        cartAdapter = new CartAdapter(c, cartModels, this);
        RVsalescart.setAdapter(cartAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVsalescart.setLayoutManager(layoutManager);

        // Category
        for (String category : categoryArray) {
            categoryModels.add(new CategoryModel(category));
        }
        categoryAdapter = new CategoryAdapter(c, categoryModels, new CategoryAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                String result = categoryArray[position];
                catFilter(result);
                TVclear.setVisibility(View.VISIBLE);
                TVheader.setText(result);
            }
        });
        RVsalescategory.setAdapter(categoryAdapter);
        LinearLayoutManager cartlayoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVsalescategory.setLayoutManager(cartlayoutManager);

        // Search
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


        // Click Listeners
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TVclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
                TVheader.setText("All Items");
                TVclear.setVisibility(View.GONE);
            }
        });

        BTNcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cartModels.isEmpty()){
                    Intent i = new Intent(c, Reciept.class);
                    i.putExtra("Items", (Serializable) cartModels);
                    Log.d("SendingActivity", "CartList: " + cartModels.get(0).getName());
                    startActivity(i);

                }
            }
        });

    }

    private void setCartAdapter(int position) {
        String name = salesItemModels.get(position).getName();
        float price = salesItemModels.get(position).getPrice();
        int resId = salesItemModels.get(position).getImg();
        int quantity = 1;

        boolean itemExists = false;
        for (CartModel cartItem : cartModels) {
            if (cartItem.getName().equals(name)) {
                cartItem.setQty(cartItem.getQty() + 1);
                cartItem.setTotal(cartItem.getQty() * price);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            float total = price * quantity;
            cartModels.add(new CartModel(name, price, total, quantity, resId));
        }
        cartAdapter.notifyDataSetChanged();
    }

    private float calculateSubtotal() {
        float subtotal = 0.0f;
        for (CartModel cartItem : cartModels) {
            subtotal += cartItem.getTotal();
        }

        return subtotal;
    }

    private void updateCart() {
        float subtotal = calculateSubtotal();
        if (cartModels.isEmpty() || subtotal <= 0) {

            LLnoitem.setVisibility(View.VISIBLE);
            LLcart.setVisibility(View.GONE);
        } else {

            LLcart.setVisibility(View.VISIBLE);
            LLnoitem.setVisibility(View.GONE);
        }


        TVsubtotal.setText("₱" + String.format("%.2f", subtotal));
    }

    public void refreshCart() {
        calculateSubtotal();
        updateCart();
    }

    private void searchFilter(String result) {
        List<SalesItemModel> filterList = new ArrayList<>();
        for (SalesItemModel item : salesItemModels) {
            if (item.getName().toLowerCase().contains(result.toLowerCase())) {
                filterList.add(item);
            }
        }
        salesItemAdapter.setFilterList(filterList);
    }

    private void clearFilter() {
        categoryAdapter.resetSelection();
        salesItemAdapter.setFilterList(salesItemModels);
    }

    private void catFilter(String result) {
        List<SalesItemModel> filterList = new ArrayList<>();
        for (SalesItemModel item : salesItemModels) {
            if (item.getCategory().toLowerCase().equals(result.toLowerCase())) {
                filterList.add(item);
            }
        }
        salesItemAdapter.setFilterList(filterList);
    }
}
