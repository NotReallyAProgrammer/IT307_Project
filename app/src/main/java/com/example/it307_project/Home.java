package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.ItemAdapter;
import com.example.it307_project.Adapter.NavAdapter;
import com.example.it307_project.Model.ItemModel;
import com.example.it307_project.Model.NavModel;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView RVname,RVitem;
    List<NavModel> navModels = new ArrayList<>();
    List<ItemModel> itemModels = new ArrayList<>();
    ItemAdapter itemadapter;
    NavAdapter navAdapter;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        initialize();
        setNavAdapter();
        setItemAdapter();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        RVname = findViewById(R.id.RVname);
        RVitem = findViewById(R.id.RVitem);


    }

    private void setNavAdapter(){
        navModels.add(new NavModel("Sales", "This is a simple discription.", "View Sales"));
        navModels.add(new NavModel("All Items", "This is a simple discription.","View Items"));
        navModels.add(new NavModel("Credits", "This is a simple discription.","View Credits"));

        navAdapter = new NavAdapter(c,navModels, new NavAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                if(position == 0){
                    Intent i = new Intent(c,Sales.class);
                    startActivity(i);
               }else if(position == 1){
//                    Intent i = new Intent(c, Items.class);
//                    startActivity(i);
                }else if (position == 2) {
                    Intent i = new Intent(c, Credits.class);
                    startActivity(i);
                }
            }



        });

        RVname.setAdapter(navAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVname.setLayoutManager(layoutManager);

    }



    private void setItemAdapter(){
        itemModels.add(new ItemModel("₱100","20","Sample","Sample Item Name"));
        itemModels.add(new ItemModel("₱100","20","Sample","Sample Item Name2"));
        itemModels.add(new ItemModel("₱100","20","Sample","Sample Item Name3"));
        itemModels.add(new ItemModel("₱100","20","Sample","Sample Item Name4"));

        itemadapter = new ItemAdapter(c,itemModels);
        RVitem.setAdapter(itemadapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 2);
        RVitem.setLayoutManager(mGridLayoutManager);
    }
}