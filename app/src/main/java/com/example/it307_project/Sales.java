package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
import com.example.it307_project.Model.SalesItemModel;

import java.util.ArrayList;
import java.util.List;

public class Sales extends AppCompatActivity {
    RecyclerView RVsalesitem, RVsalescategory,RVsalescart;
    ImageView backbtn;
    Button BTNcart;
    List<SalesItemModel> salesItemModels = new ArrayList();
    List<CategoryModel> categoryModels = new ArrayList<>();
    List<CartModel> cartModels = new ArrayList<>();

    SalesItemAdapter salesItemAdapter;
    CategoryAdapter categoryAdapter;
    CartAdapter cartAdapter;

    Context c= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales);
        initialize();
        setSalesItemAdapter();
        setCategoryAdapter();
        setCartAdapter();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initialize() {
        RVsalesitem = findViewById(R.id.RVsalesitem);
        RVsalescategory = findViewById(R.id.RVsalescategory);
        RVsalescart = findViewById(R.id.RVsalescart);
        backbtn = findViewById(R.id.backbtn);
        BTNcart = findViewById(R.id.BTNcart);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BTNcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Reciept.class);
                startActivity(i);
            }
        });


    }

    private void setSalesItemAdapter() {
        salesItemModels.add(new SalesItemModel("Sample Name",100));
        salesItemModels.add(new SalesItemModel("Sample Name2",50));
        salesItemModels.add(new SalesItemModel("Sample Name3",10));
        salesItemModels.add(new SalesItemModel("Sample Name3",20));

        salesItemAdapter = new SalesItemAdapter(c,salesItemModels);
        RVsalesitem.setAdapter(salesItemAdapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 3);
        RVsalesitem.setLayoutManager(mGridLayoutManager);


    }

    private void setCategoryAdapter() {
        categoryModels.add(new CategoryModel("Sample Name"));
        categoryModels.add(new CategoryModel("Sample Name"));
        categoryModels.add(new CategoryModel("Sample Name"));
        categoryModels.add(new CategoryModel("Sample Name"));
        categoryModels.add(new CategoryModel("Sample Name"));

        categoryAdapter = new CategoryAdapter(c, categoryModels);
        RVsalescategory.setAdapter(categoryAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVsalescategory.setLayoutManager(layoutManager);
    }

    private void setCartAdapter() {
        cartModels.add(new CartModel("Sample Name",100,200));
        cartModels.add(new CartModel("Sample Name",100,200));
        cartModels.add(new CartModel("Sample Name",100,200));

        cartAdapter = new CartAdapter(c, cartModels);
        RVsalescart.setAdapter(cartAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVsalescart.setLayoutManager(layoutManager);
    }

}