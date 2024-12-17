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
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.ItemAdapter;
import com.example.it307_project.Model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends AppCompatActivity {

    TextView TVtotalval,TVtotalprofit,TVtotalitems,TVtotalcat;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        TVtotalval = findViewById(R.id.TVtotalval);
        TVtotalprofit = findViewById(R.id.TVtotalprofit);
        TVtotalitems = findViewById(R.id.TVtotalitems);
        TVtotalcat = findViewById(R.id.TVtotalcat);
        RVitem = findViewById(R.id.RVitem);
        BTNviewall = findViewById(R.id.BTNviewall);
        IVback = findViewById(R.id.backbtn);
        Intent intent = getIntent();
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");
        String[] categoryArray = intent.getStringArrayExtra("Category");

        float totalVal = 0;
        float profit= 0;

        //Setting Item Adapter
        for (String item[] : itemsArray){
            int resId = getResources().getIdentifier(item[6].split("\\.")[2], "mipmap",getPackageName());
            itemModels.add(new ItemModel(Float.parseFloat(item[5]), Integer.parseInt(item[3]),item[2],item[1],resId));

            //
            int quantity = Integer.parseInt(item[3]);
            float pricePerItem = Float.parseFloat(item[4]);
            float itemTotal = quantity * pricePerItem;

            float sellperitem = Float.parseFloat(item[5]);
            float sellingTotal = quantity * sellperitem;

            totalVal += itemTotal;
            profit += sellingTotal;
        }

        itemadapter = new ItemAdapter(c,itemModels);
        RVitem.setAdapter(itemadapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 2);
        RVitem.setLayoutManager(mGridLayoutManager);


        //Setting TextView
        TVtotalval.setText("₱" + totalVal);
        TVtotalcat.setText(String.valueOf(categoryArray.length));
        TVtotalitems.setText(String.valueOf(itemsArray.length));
        TVtotalprofit.setText("₱" + String.valueOf(profit - totalVal));


        //Click Listener
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
                i.putExtra("Items",itemsArray);
                i.putExtra("Category",categoryArray);
                startActivity(i);
            }
        });
    }

}