package com.example.it307_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Items extends AppCompatActivity {

    RecyclerView RVcategory, RVallitem;
    Button BTNconfirm;
    ImageView backbtn;
    ImageButton IBaddbutton,IBremovebutton,IBclose;
    TextView TVheader, TVclear,TVcategory,TVcategorymode;
    TextInputLayout itemcat;
    TextInputEditText ETsearch,ETaddcat;
    LinearLayout LLaddcategory;
    List<CategoryModel> categoryModels = new ArrayList<>();
    List<AllItemModel> allItemModels = new ArrayList<>();
    AllItemAdapter allItemAdapter;
    CategoryAdapter categoryAdapter;

    String[] category;
    Context c = this;

    DecimalFormat df = new DecimalFormat("#.##");
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
        LLaddcategory = findViewById(R.id.LLaddcategory);
        RVcategory = findViewById(R.id.RVcategory);
        RVallitem = findViewById(R.id.RVallitem);
        TVheader = findViewById(R.id.TVheader);
        TVclear = findViewById(R.id.TVclear);
        TVcategory = findViewById(R.id.TVcategory);
        TVcategorymode = findViewById(R.id.TVcategorymode);
        itemcat = findViewById(R.id.itemcat);
        ETsearch = findViewById(R.id.ETsearch);
        ETaddcat = findViewById(R.id.ETaddcat);
        BTNconfirm = findViewById(R.id.BTNconfirm);
        IBaddbutton = findViewById(R.id.IBaddbutton);
        IBremovebutton = findViewById(R.id.IBremovebutton);
        IBclose = findViewById(R.id.IBclose);
        backbtn = findViewById(R.id.backbtn);

        Intent intent = getIntent();
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");
        category = intent.getStringArrayExtra("Category");


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

        allItemAdapter = new AllItemAdapter(c, allItemModels, new AllItemAdapter.ClickListener() {
            @Override
            public void onIdCLick(String id) {
                    Intent i = new Intent(c, AddItem.class);
                    i.putExtra("Mode","Edit");
                    i.putExtra("Id",id);
                    i.putExtra("Items",itemsArray);
                    i.putExtra("Category",category);
                    startActivity(i);
            }
        });
        RVallitem.setAdapter(allItemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
        RVallitem.setLayoutManager(layoutManager);

        // |Category
        for (String category : category){
            categoryModels.add(new CategoryModel(category));
        }

        categoryAdapter = new CategoryAdapter(c, categoryModels, new CategoryAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                String mode = TVcategorymode.getText().toString();
                if(mode.equals("Delete Category")){
                    deleteCat(position);
                }else{
                    String result = category[position];
                    catFilter(result);
                    TVclear.setVisibility(View.VISIBLE);
                    TVheader.setText(result);
                    if(ETsearch.isFocused()){
                        ETsearch.setText(null);
                        ETsearch.clearFocus();
                    }
                }

            }
        });
        RVcategory.setAdapter(categoryAdapter);
        LinearLayoutManager catlayoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVcategory.setLayoutManager(catlayoutManager);

        //Search
        ETsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0){
                    clearFilter();
                    TVheader.setText("All Items");
                    TVclear.setVisibility(View.GONE);
                }else{
                    String query = s.toString();
                    searchFilter(query);
                    TVclear.setVisibility(View.VISIBLE);
                    categoryAdapter.resetSelection();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Click Listener
        IBaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BTNconfirm.setText("Add Category");
                LLaddcategory.setVisibility(View.VISIBLE);


            }
        });

        IBremovebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TVcategorymode.getText().equals("Categories")){
                    categoryAdapter.getMode("Delete");
                    TVcategorymode.setText("Delete Category");
                }else{
                    categoryAdapter.getMode("Select");
                    TVcategorymode.setText("Categories");
                }

            }
        });

        IBclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLaddcategory.setVisibility(View.GONE);
                BTNconfirm.setText("Add Item");
            }
        });

        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = BTNconfirm.getText().toString();
                if(btn.equals("Add Item")){
                    Intent i = new Intent(c,AddItem.class);
                    i.putExtra("Category",category);
                    i.putExtra("Items", itemsArray);
                    i.putExtra("Mode","Add");
                    startActivity(i);
                    finish();
                }else if(btn.equals("Add Category")){
                    String cat = ETaddcat.getText().toString();

                    if(!validateCat()){
                        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                new Runnable() {
                                    public void run() {
                                        itemcat.setError(null);
                                        itemcat.setErrorEnabled( false );
                                    }
                                }, 5000);
                       return;
                    }

                    categoryModels.add(new CategoryModel(cat));
                    category = new String[categoryModels.size()];
                    for (int i = 0; i < categoryModels.size(); i++) {
                        category[i] = categoryModels.get(i).getCategoryName();
                    }
                    Toast.makeText(c, "Category Added", Toast.LENGTH_SHORT).show();
                    categoryAdapter.notifyDataSetChanged();
                    ETaddcat.setText("");
                    LLaddcategory.setVisibility(View.GONE);
                    BTNconfirm.setText("Add Item");
                }

            }
        });

        TVclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter();
                TVheader.setText("All Items");
                TVclear.setVisibility(View.GONE);
                ETsearch.clearFocus();
                ETsearch.setText(null);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Inventory.class);
                i.putExtra("Category",category);
                i.putExtra("Items",itemsArray);
                startActivity(i);
            }
        });

    }

    private void deleteCat(int position){

        AlertDialog.Builder remove = new AlertDialog.Builder(c);
        remove.setTitle("Delete Category")
                .setMessage("Are you sure you want to delete: " + categoryModels.get(position).getCategoryName()).setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (position != -1) {

                            categoryModels.remove(position);
                            category = new String[categoryModels.size()];
                            for (int i = 0; i < categoryModels.size(); i++) {
                                category[i] = categoryModels.get(i).getCategoryName();
                            }

                            categoryAdapter.notifyDataSetChanged();
                            categoryAdapter.resetSelection();
                            TVheader.setText("All Categories");
                        }
                        categoryAdapter.getMode("Select");
                        TVcategorymode.setText("Categories");
                        Toast.makeText(c, "Category Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = remove.create();
        dialog.show();



    }

    private void searchFilter(String result) {
        List<AllItemModel> filterList = new ArrayList<>();
        for (AllItemModel item : allItemModels) {
            if (item.getItemName().toLowerCase().contains(result.toLowerCase())) {
                filterList.add(item);
            }
        }

        allItemAdapter.setFilterList(filterList);
    }
    private void catFilter(String result) {
        List<AllItemModel> filterList = new ArrayList<>();
        for (AllItemModel item : allItemModels) {
            if (item.getCategory().toLowerCase().equals(result.toLowerCase())) {
                filterList.add(item);
            }
        }
        allItemAdapter.setFilterList(filterList);
    }
    private void clearFilter() {
        categoryAdapter.resetSelection();
        allItemAdapter.setFilterList(allItemModels);
    }
    private boolean validateCat() {
        String val = ETaddcat.getText().toString();

        if (val.isEmpty()) {
            itemcat.setError( "Field can not be empty" );
            return false;
        }else {
            itemcat.setError(null);
            itemcat.setErrorEnabled( false );
            return true;
        }
    }

}