package com.example.it307_project;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.SalesItemAdapter;
import com.example.it307_project.Model.SalesItemModel;

import java.util.ArrayList;
import java.util.List;

public class Sales extends AppCompatActivity {
    RecyclerView RVsalesitem;
    ImageView backbtn;
    List<SalesItemModel> salesItemModels = new ArrayList();
    SalesItemAdapter salesItemAdapter;

    Context c= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales);
        initialize();
        setSalesItemAdapter();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        RVsalesitem = findViewById(R.id.RVsalesitem);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}