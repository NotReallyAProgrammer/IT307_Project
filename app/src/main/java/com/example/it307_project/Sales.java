package com.example.it307_project;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sales extends AppCompatActivity {
    RecyclerView RVsalesitem, RVsalescategory,RVsalescart;
    ImageView backbtn;
    TextView TVheader,TVclear;
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
        TVheader = findViewById(R.id.TVheader);
        TVclear = findViewById(R.id.TVclear);
        backbtn = findViewById(R.id.backbtn);
        BTNcart = findViewById(R.id.BTNcart);

        Intent intent = getIntent();
        String[] categoryArray = intent.getStringArrayExtra("Category");
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");

        //Setting Adapters


        // |Items
        for (String item[] : itemsArray){
            int resId = getResources().getIdentifier(item[6].split("\\.")[2], "mipmap",getPackageName());
            salesItemModels.add(new SalesItemModel(item[1],item[2],Float.parseFloat(item[5]),resId));
        }

        salesItemAdapter = new SalesItemAdapter(c,salesItemModels);
        RVsalesitem.setAdapter(salesItemAdapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 3);
        RVsalesitem.setLayoutManager(mGridLayoutManager);


        // |Category
        for (String category : categoryArray){
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVsalescategory.setLayoutManager(layoutManager);


        //Click Listeners
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
                Intent i = new Intent(c, Reciept.class);
                startActivity(i);
            }
        });
    }


    private void catFilter(String result) {
        List<SalesItemModel> filterList = new ArrayList<>();
        for(SalesItemModel item : salesItemModels){
            if(item.getCategory().toLowerCase().equals(result.toLowerCase())) {
                filterList.add(item);
            }
            salesItemAdapter.setFilterList(filterList);

        }
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
    private void clearFilter(){
        categoryAdapter.resetSelection();
        salesItemAdapter.setFilterList(salesItemModels);

    }
}