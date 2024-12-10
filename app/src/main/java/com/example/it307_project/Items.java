package com.example.it307_project;

import android.content.Context;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

public class Items extends AppCompatActivity {

    RecyclerView RVcategory, RVallitem;
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
        setCategoryAdapter();
        setAllItemAdapter();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        RVcategory = findViewById(R.id.RVcategory);
        RVallitem = findViewById(R.id.RVallitem);
    }
    private void setCategoryAdapter() {
        categoryModels.add(new CategoryModel("Canned"));
        categoryModels.add(new CategoryModel("Snacks"));
        categoryModels.add(new CategoryModel("Candy"));

        categoryAdapter = new CategoryAdapter(c, categoryModels);
        RVcategory.setAdapter(categoryAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVcategory.setLayoutManager(layoutManager);
    }

    private void setAllItemAdapter(){
        allItemModels.add(new AllItemModel("Sample Item name",10,100,110,10 * (110-100) ));
        allItemModels.add(new AllItemModel("Sample Item name",20,90,100,20 * (100-90) ));

        allItemAdapter = new AllItemAdapter(c, allItemModels);
        RVallitem.setAdapter(allItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVallitem.setLayoutManager(layoutManager);
    }
}