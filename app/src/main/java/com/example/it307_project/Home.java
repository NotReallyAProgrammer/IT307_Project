package com.example.it307_project;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    TextView TVname;
    RecyclerView RVname,RVitem;
    List<NavModel> navModels = new ArrayList<>();
    List<ItemModel> itemModels = new ArrayList<>();
    ItemAdapter itemadapter;
    NavAdapter navAdapter;
    Context c = this;

    //Items Array
    String[][] itemsArray = {{"00001", "Kopiko Black Twin","Beverage","10","12.75","16.00","R.mipmap.kopiko"},
                            {"00002","Lucky Me Chicken 55g","Noodle","20","9.30","12.00","R.mipmap.luckyme"},
                            {"00003","Argentina Corned Beef 150g","Canned","15","36.40","38.00","R.mipmap.argentina"},
                            {"00004","Piattos Cheese 40g","Snacks","20","17.05","20.00","R.mipmap.piatos"}};

    String[] categoryArray = {"Beverage", "Canned", "Noodle","Snacks"};
    String[][] creditInfoArray = {{"Miguel","200"},{"Felix","500"},{"Mia","230"},{"Janeth","550"}};


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
        TVname = findViewById(R.id.TVname);


        SharedPreferences sharedPref = getSharedPreferences("user_session", MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "defaultName");

        TVname.setText("Welcome " + userName);
    }

    private void setNavAdapter(){
        navModels.add(new NavModel("Sales", "For sales and credit.", "View Sales",R.mipmap.sales_nav));
        navModels.add(new NavModel("All Items", "Managing items and category.","View Inventory",R.mipmap.item_nav));
        navModels.add(new NavModel("Credits", "Adding names paying tabs.","View Credits",R.mipmap.credit_nav));

        navAdapter = new NavAdapter(c,navModels, new NavAdapter.ClickListener() {
            @Override
            public int onPositionClicked(int position) {
                if(position == 0){
                    Intent i = new Intent(c,Sales.class);
                    i.putExtra("Items", itemsArray);
                    i.putExtra("Category", categoryArray);
                    startActivity(i);
               }else if(position == 1){
                    Intent i = new Intent(c, Inventory.class);
                    i.putExtra("Items", itemsArray);
                    i.putExtra("Category", categoryArray);
                    startActivity(i);
                }else if (position == 2) {
                    Intent i = new Intent(c, Credits.class);
                    startActivity(i);
                }
                return position;
            }
        });

        RVname.setAdapter(navAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVname.setLayoutManager(layoutManager);

    }

    private void setItemAdapter(){

        for (String item[] : itemsArray){

            int resId = getResources().getIdentifier(item[6].split("\\.")[2], "mipmap",getPackageName());
            Log.i(TAG,item[6].split("\\.")[1] );
            itemModels.add(new ItemModel(Float.parseFloat(item[5]), Integer.parseInt(item[3]),item[2],item[1],resId));
        }


        itemadapter = new ItemAdapter(c,itemModels);
        RVitem.setAdapter(itemadapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 2);
        RVitem.setLayoutManager(mGridLayoutManager);
    }
}