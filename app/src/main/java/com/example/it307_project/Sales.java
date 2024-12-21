package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.CartModel;
import com.example.it307_project.Model.CategoryModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Sales extends AppCompatActivity {
    RecyclerView RVsalesitem, RVsalescategory, RVsalescart;
    ImageView backbtn;
    TextView TVheader, TVclear, TVsubtotal;
    TextInputEditText ETsearch;
    Button BTNcart;
    LinearLayout LLnoitem, LLcart;
    List<AllItemModel> allItemModels = new ArrayList<>();
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
        String[][] creditArray =  (String[][]) intent.getSerializableExtra("Credit");
        DecimalFormat df = new DecimalFormat("#.##");
        // Setting Adapters
        // Items
        for (String[] item : itemsArray) {
            int resId = 0;

            String itemImgByte = "";

            if (item[6].contains("R")) {
                resId = getResources().getIdentifier(item[6].split("\\.")[2], "mipmap", getPackageName());
            } else {
                itemImgByte = item[6];
            }

            //
            String formattedValue = df.format(Float.parseFloat(item[5]) -  Float.parseFloat(item[4]));
            allItemModels.add(new AllItemModel(
                    item[0],//Item ID
                    item[1], //Item Name
                    item[2], //Item Category
                    Integer.parseInt(item[3]), // Item Quantity
                    Float.parseFloat(item[5]), // Item Price
                    Float.parseFloat(item[4]), // Item SRP
                    Float.parseFloat(formattedValue), // Profit
                    resId, // Image Resource ID
                    itemImgByte // Image Byte String
            ));
        }

        salesItemAdapter = new SalesItemAdapter(c, allItemModels, new SalesItemAdapter.ClickListener() {
            @Override
            public void onIdCLick(String id) {
                for(AllItemModel item : allItemModels){
                    if(item.getItemQuantity() <= 0){
                        Toast.makeText(c, "No item left", Toast.LENGTH_SHORT).show();
                    }else{
                        setCartAdapter(id);
                        calculateSubtotal();
                        updateCart();

                    }
                    break;
                }

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
                if(ETsearch.isFocused()){
                    ETsearch.setText(null);
                    ETsearch.clearFocus();
                }
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
                if (count == 0){
                    clearFilter();
                    TVheader.setText("All Items");
                    TVclear.setVisibility(View.GONE);
                }else{
                    String query = s.toString();
                    searchFilter(query);
                    TVclear.setVisibility(View.VISIBLE);
                    categoryAdapter.resetSelection();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // Click Listeners
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Home.class);
                i.putExtra("Credit",creditArray);
                i.putExtra("Category",categoryArray);
                i.putExtra("Items",itemsArray);
                startActivity(i);
                finish();
            }
        });

        TVclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
                TVheader.setText("All Items");
                TVclear.setVisibility(View.GONE);
                ETsearch.clearFocus();
                ETsearch.setText(null);
            }
        });

        BTNcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cartModels.isEmpty()){
                    Intent i = new Intent(c, Reciept.class);
                    i.putExtra("Cart", (Serializable) cartModels);
                    i.putExtra("Credit",creditArray);
                    i.putExtra("Category",categoryArray);
                    i.putExtra("Items",itemsArray);
                    startActivity(i);

                }
            }
        });

    }

    private void setCartAdapter(String id) {
        String cartID = "";
        String cartName = "";
        float cartPrice = 0;
        int resId = 0;
        String itemImgByte = null;
        int availableQuantity = 0;

        for (AllItemModel item : allItemModels) {
            if (id.equals(item.getItemId())) {
                cartID = item.getItemId();
                cartName = item.getItemName();
                cartPrice = item.getItemPrice();
                resId = item.getImageResId();
                itemImgByte = item.getItemImage();
                availableQuantity = item.getItemQuantity();
                break;
            }
        }

        int quantity = 1;
        boolean itemExists = false;


        for (CartModel cartItem : cartModels) {
            if (cartID.equals(cartItem.getId())) {
                if (cartItem.getQty() < availableQuantity) {
                    cartItem.setQty(cartItem.getQty() + 1);
                    cartItem.setTotal(cartItem.getPrice() * cartItem.getQty());
                    itemExists = true;
                    break;
                } else {
                    Toast.makeText(c, "Maximum quantity for this item reached", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }



        if (!itemExists) {
            float total = cartPrice * quantity;
            cartModels.add(new CartModel(cartID, cartName, cartPrice, total, quantity, resId, itemImgByte,availableQuantity));
        }


        cartAdapter.notifyDataSetChanged();
        updateCart();
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
        List<AllItemModel> filterList = new ArrayList<>();
        for (AllItemModel item : allItemModels) {
            if (item.getItemName().toLowerCase().contains(result.toLowerCase())) {
                filterList.add(item);
            }
        }

        // Pass the filtered list to the adapter
        salesItemAdapter.setFilterList(filterList);
    }
    private void clearFilter() {
        categoryAdapter.resetSelection();
        salesItemAdapter.setFilterList(allItemModels);
    }
    private void catFilter(String result) {
        List<AllItemModel> filterList = new ArrayList<>();
        for (AllItemModel item : allItemModels) {
            if (item.getCategory().toLowerCase().equals(result.toLowerCase())) {
                filterList.add(item);
            }
        }
        salesItemAdapter.setFilterList(filterList);
    }
}
