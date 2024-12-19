package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.AllItemAdapter;
import com.example.it307_project.Adapter.CategoryAdapter;
import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.CategoryModel;
import com.example.it307_project.Model.ItemModel;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Items extends AppCompatActivity {

    RecyclerView RVcategory, RVallitem;
    Button BTNconfirm;
    List<CategoryModel> categoryModels = new ArrayList<>();
    List<AllItemModel> allItemModels = new ArrayList<>();
    AllItemAdapter allItemAdapter;
    CategoryAdapter categoryAdapter;
    Context c = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_items);
        initialize();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        RVcategory = findViewById(R.id.RVcategory);
        RVallitem = findViewById(R.id.RVallitem);
        BTNconfirm = findViewById(R.id.BTNconfirm);

        Intent intent = getIntent();
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");
        String[] categoryArray = intent.getStringArrayExtra("Category");
        DecimalFormat df = new DecimalFormat("#.##");
        // {"00004","Piattos Cheese 40g","Snacks","20","17.05","20.00","R.mipmap.piatos"}
        //Setting Item Adapter
        // |Items
        for (String item[] : itemsArray){
            int resId = 0;
            String itemImgByte = "";

            if (item[6].contains("R")) {
                resId = getResources().getIdentifier(item[6].split("\\.")[2], "mipmap", getPackageName());
            } else {
                itemImgByte = item[6];
            }

            Log.i("Img", "initialize: " + itemImgByte);
            String formattedValue = df.format(Float.parseFloat(item[5]) -  Float.parseFloat(item[4]));
            allItemModels.add(new AllItemModel(
                    item[1], // Item Name
                    item[3], // Quantity
                    Integer.parseInt(item[3]), // Item Quantity
                    Float.parseFloat(item[4]), // Item Price
                    Float.parseFloat(item[5]), // Item SRP
                    Float.parseFloat(formattedValue), // Profit
                    resId, // Image Resource ID
                    itemImgByte // Image Byte String
            ));
        }

        allItemAdapter = new AllItemAdapter(c, allItemModels);
        RVallitem.setAdapter(allItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVallitem.setLayoutManager(layoutManager);

        // |Category
        for (String category : categoryArray){
            categoryModels.add(new CategoryModel(category));
        }

        categoryAdapter = new CategoryAdapter(c, categoryModels, new CategoryAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {

            }
        });
        RVcategory.setAdapter(categoryAdapter);
        LinearLayoutManager catlayoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVcategory.setLayoutManager(catlayoutManager);

        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c,AddItem.class);
                i.putExtra("Category",categoryArray);
                i.putExtra("Items", itemsArray);
                startActivity(i);
            }
        });
    }

}