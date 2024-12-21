package com.example.it307_project;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.ItemAdapter;
import com.example.it307_project.Adapter.NavAdapter;
import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.CartModel;
import com.example.it307_project.Model.CreditModel;
import com.example.it307_project.Model.ItemModel;
import com.example.it307_project.Model.NavModel;
import com.example.it307_project.Model.ReceiptModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Home extends AppCompatActivity {

    TextView TVname;
    RecyclerView RVname,RVitem;
    ImageButton IBlogout;
    List<NavModel> navModels = new ArrayList<>();
    List<AllItemModel> allItemModels = new ArrayList<>();
    ItemAdapter itemadapter;
    NavAdapter navAdapter;
    Context c = this;

    //Array
    String[][] itemsArray = {{"00001", "Kopiko Black Twin","Beverage","10","12.75","16.00","R.mipmap.kopiko"},
                            {"00002","Lucky Me Chicken 55g","Noodle","20","9.30","12.00","R.mipmap.luckyme"},
                            {"00003","Argentina Meat Loaf 150g","Canned","15","21.75","25.00","R.mipmap.argentina"},
                            {"00004","Piattos Cheese 40g","Snacks","20","17.05","20.00","R.mipmap.piatos"}};

    String[] categoryArray = {"Beverage", "Canned", "Noodle","Snacks"};
    String[][] creditInfoArray = {{"Miguel","1"},{"Felix","1"},{"Mia","1"},{"Janeth","1"}};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        initialize();

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
        IBlogout = findViewById(R.id.IBlogout);

        SharedPreferences sharedPref = getSharedPreferences("user_session", MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "defaultName");

        Bundle intent = getIntent().getExtras();

         if (intent != null){
             String[][] newItemsArray = (String[][]) intent.getSerializable("Items");
             String[] newCategoryArray = intent.getStringArray("Category");
             String[][] newCredit = (String[][]) intent.getSerializable("Credit");
             List<CartModel> cartModels = (List<CartModel>) intent.getSerializable("Receipt");


                if (newItemsArray != null){
                    itemsArray = newItemsArray;
                 }
                 if (newCategoryArray != null ){
                    categoryArray = newCategoryArray;
                 }
                 if (newCredit != null ){
                     creditInfoArray = newCredit;
                 }
                 if (cartModels != null) {

                     for (String[] items : itemsArray) {
                         for (CartModel cart : cartModels) {
                             if (items[0].equals(cart.getId())) {

                                     int currentQuantity = Integer.parseInt(items[3]);
                                     int newQuantity = currentQuantity - cart.getQty();

                                     items[3] = String.valueOf(newQuantity);

                                 break;
                             }
                         }

                     }
              }
        }


        TVname.setText("Welcome " + userName);

        //Adapter
        navModels.add(new NavModel("All Items", "Managing items and category..", "View Inventory",R.mipmap.item_nav));
        navModels.add(new NavModel("Sales", "For sales and credit.","View Sales",R.mipmap.sales_nav));
        navModels.add(new NavModel("Credits", "Adding names paying tabs.","View Credits",R.mipmap.credit_nav));

        navAdapter = new NavAdapter(c,navModels, new NavAdapter.ClickListener() {
            @Override
            public int onPositionClicked(int position) {
                if(position == 0){
                    Intent i = new Intent(c,Inventory.class);
                    i.putExtra("Items", itemsArray);
                    i.putExtra("Category", categoryArray);
                    i.putExtra("Credit", creditInfoArray);
                    startActivity(i);
                    finish();
                }else if(position == 1){
                    Intent i = new Intent(c, Sales.class);
                    i.putExtra("Items", itemsArray);
                    i.putExtra("Category", categoryArray);
                    i.putExtra("Credit", creditInfoArray);
                    startActivity(i);
                    finish();
                }else if (position == 2) {
                    Intent i = new Intent(c, Credits.class);
                    i.putExtra("Credit", creditInfoArray);
                    startActivity(i);
                    finish();
                }
                return position;
            }
        });

        RVname.setAdapter(navAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVname.setLayoutManager(layoutManager);


        // |Items
        DecimalFormat df = new DecimalFormat("#.##");
        for (String item[] : itemsArray){
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
                    item[1], // Item Name
                    item[2], //Category
                    Integer.parseInt(item[3]), // Item Quantity
                    Float.parseFloat(item[5]), // Item Price
                    Float.parseFloat(item[4]), // Item SRP
                    Float.parseFloat(formattedValue), // Profit
                    resId, // Image Resource ID
                    itemImgByte // Image Byte String
            ));
        }

        Collections.reverse(allItemModels);

        itemadapter = new ItemAdapter(c,allItemModels);
        RVitem.setAdapter(itemadapter);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(c, 2);
        RVitem.setLayoutManager(mGridLayoutManager);

        IBlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logout = new AlertDialog.Builder(c);
                logout.setTitle("Log out!").setMessage("Are you sure you want to log out?").setCancelable(true).setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();

                        Intent intent = new Intent(c, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

                AlertDialog alert = logout.create();
                alert.show();

            }
        });

    }
}