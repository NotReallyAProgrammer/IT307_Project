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
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.ItemAdapter;
import com.example.it307_project.Model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends AppCompatActivity {
    RecyclerView RVitem;
    Button BTNviewall;
    ImageView IVback;
    List<ItemModel> itemModels = new ArrayList<>();
    ItemAdapter itemadapter;
    Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory);
        initialize();
        setItemAdapter();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        RVitem = findViewById(R.id.RVitem);
        BTNviewall = findViewById(R.id.BTNviewall);
        IVback = findViewById(R.id.backbtn);

        IVback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        BTNviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Items.class);
                startActivity(i);
            }
        });
    }

    private void setItemAdapter(){
//        itemModels.add(new ItemModel(10,20,"Sample",));
//        itemModels.add(new ItemModel(20,20,"Sample","Sample Item Name2"));
//        itemModels.add(new ItemModel(100,20,"Sample","Sample Item Name3"));
//        itemModels.add(new ItemModel(100,20,"Sample","Sample Item Name4"));

        itemadapter = new ItemAdapter(c,itemModels);
        RVitem.setAdapter(itemadapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 2);
        RVitem.setLayoutManager(mGridLayoutManager);
    }
}