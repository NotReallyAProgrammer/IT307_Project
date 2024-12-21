package com.example.it307_project;

import static com.example.it307_project.Adapter.StringToByte.stringToByteArray;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.CategoryModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItem extends AppCompatActivity {

    TextInputLayout itemName,itemQty,itemPrice,itemSelling,menu;
    TextInputEditText ETname,ETqty,ETprice,ETselling;
    AutoCompleteTextView category;
    Button BTNconfirm;
    ImageButton IBaddimg;
    TextView TVtotalprice,TVtotalselling;
    ImageView IVitemimg,backbtn;

    DecimalFormat df = new DecimalFormat("#.##");

    Context c = this;
    byte[] byteArray = null;
    boolean clicked = false;

    private static final int REQ_CODE_TAKE_PHOTO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        initialize();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize(){
        itemName = findViewById(R.id.itemName);
        itemQty = findViewById(R.id.itemQty);
        itemPrice = findViewById(R.id.itemPrice);
        itemSelling = findViewById(R.id.itemSelling);
        menu = findViewById(R.id.menu);
        ETname = findViewById(R.id.ETName);
        ETqty = findViewById(R.id.ETQty);
        ETprice = findViewById(R.id.ETPrice);
        ETselling = findViewById(R.id.ETSelling);
        IVitemimg = findViewById(R.id.IVitemimg);
        category = findViewById(R.id.category);
        TVtotalprice = findViewById(R.id.TVtotalprice);
        TVtotalselling = findViewById(R.id.TVtotalselling);
        BTNconfirm = findViewById(R.id.BTNconfirm);
        backbtn = findViewById(R.id.backbtn);
        IBaddimg = findViewById(R.id.IBaddimg);

        Intent intent = getIntent();
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");
        String[] categoryArray = intent.getStringArrayExtra("Category");
        String mode = intent.getStringExtra("Mode");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, R.layout.dropdown_menu_popup_item, categoryArray);
        category.setAdapter(adapter);

        if(mode.equals("Edit")){
            String id = intent.getStringExtra("Id");
            //{"00004","Piattos Cheese 40g","Snacks","20","17.05","20.00","R.mipmap.piatos"}};
            for (String[] item : itemsArray){
                if (item[0].equals(id)){
                    ETname.setText(item[1]);
                    ETqty.setText(item[3]);
                    ETprice.setText(item[4]);
                    ETselling.setText(item[5]);

                    category.setText(item[2]);

                    if(item[6].contains("R")){
                        int resId = getResources().getIdentifier(item[6].split("\\.")[2], "mipmap", getPackageName());
                        IVitemimg.setImageResource(resId);
                    }else{
                        byte[] byteArrayConverted = stringToByteArray(item[6]);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayConverted, 0, byteArrayConverted.length);
                        IVitemimg.setImageBitmap(bitmap);
                    }


                    int quantity = Integer.parseInt(item[3]);
                    float price = Float.parseFloat(item[4]);
                    float sellingPrice = Float.parseFloat(item[5]);
                    float profit = (quantity*sellingPrice) - (quantity*price);

                    TVtotalprice.setText("₱"+ String.valueOf(quantity*price));
                    TVtotalselling.setText("₱" + String.valueOf(profit));
                    break;
                }
            }
            BTNconfirm.setText("Edit Item");
        }


        //Edit Text
        ETprice.addTextChangedListener(new TextWatcher()  {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    calculateTotal();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                    calculateTotal();
                }

        });

        ETselling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                  calculateProfit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateProfit();
            }
        });

        ETqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0){
                    calculateProfit();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                    calculateProfit();
            }

        });

        //Click Listener
        IBaddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, REQ_CODE_TAKE_PHOTO);

            }
        });

        IVitemimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("IMG", "onClick: " + Arrays.toString(byteArray));
                if (byteArray !=null){
                    if (IBaddimg.getVisibility() == View.VISIBLE){
                        IBaddimg.setVisibility(View.GONE);
                    }else{
                        IBaddimg.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // {"00004","Piattos Cheese 40g","Snacks","20","17.05","20.00","R.mipmap.piatos"}
                if (mode.equals("Edit")){
                    EditItem();
                }else{
                    AddItem();
                }

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(c, Items.class);
                    i.putExtra("Category",categoryArray);
                    i.putExtra("Items",itemsArray);
                    startActivity(i);
                    finish();
            }
        });

    }
    private void calculateTotal(){
        String price = ETprice.getText().toString().trim();
        String qty = ETqty.getText().toString();
        String sell = ETselling.getText().toString();
        if (!price.isEmpty()) {

            float payment = Float.parseFloat(price);

            int quantity = Integer.parseInt(qty);

            float total = payment * quantity;
            TVtotalprice.setText("₱" + String.valueOf(df.format(total)));
            if(!sell.isEmpty()){
                float selling = Float.parseFloat(sell);
                float profit = (quantity*selling) - (quantity*payment);
                TVtotalselling.setText("₱" + String.valueOf(df.format(profit)));
            }

        } else {
            TVtotalprice.setText("₱0.00");
        }
    }
    private void calculateProfit(){
        String price = ETprice.getText().toString().trim();
        String qty = ETqty.getText().toString();
        String sell = ETselling.getText().toString();

        if (!price.isEmpty()) {

            float payment = Float.parseFloat(price);
            int quantity = Integer.parseInt(qty);
            float total = payment * quantity;

            TVtotalprice.setText("₱" + String.valueOf(df.format(total)));
            if(!sell.isEmpty()){
                float selling = Float.parseFloat(sell);
                float profit = (quantity*selling) - (quantity*payment);
                TVtotalselling.setText("₱" + String.valueOf(df.format(profit)));
            }

        } else {
            TVtotalprice.setText("₱0.00");
        }
    }

    private void EditItem(){
        Intent intent = getIntent();
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");
        String[] categoryArray = intent.getStringArrayExtra("Category");
        String id = intent.getStringExtra("Id");

        for (String[] item : itemsArray){
            if (item[0].equals(id)){

                String name = ETname.getText().toString();
                String qty = ETqty.getText().toString();
                String selected = category.getText().toString();
                String price = ETprice.getText().toString();
                String selling = ETselling.getText().toString();
                String imgByte = "";

                if(!validateName() | !validateQty() | !validatePrice() | !validateSelling() | !validateCategory()){

                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {

                                    itemName.setError(null);
                                    itemName.setErrorEnabled( false );

                                    itemQty.setError(null);
                                    itemQty.setErrorEnabled( false );

                                    itemPrice.setError(null);
                                    itemPrice.setErrorEnabled( false );

                                    itemSelling.setError(null);
                                    itemSelling.setErrorEnabled( false );

                                    menu.setError(null);
                                }
                            }, 10000);
                    return;
                }

                if(clicked){
                    imgByte = Arrays.toString(byteArray);
                }else{
                    imgByte = item[6];
                }

                //{"00004","Piattos Cheese 40g","Snacks","20","17.05","20.00","R.mipmap.piatos"}};
                item[1] = name;
                item[2] = selected;
                item[3] = qty;
                item[4] = price;
                item[5] = selling;
                item[6] = imgByte;

                Intent i = new Intent(c, Items.class);
                i.putExtra("Items",itemsArray);
                i.putExtra("Category",categoryArray);
                Log.i("Item", Arrays.deepToString(itemsArray));
                startActivity(i);
                finish();
                break;
            }
        }
    }
    private void AddItem(){

        Intent intent = getIntent();
        String[][] itemsArray = (String[][]) intent.getSerializableExtra("Items");
        String[] categoryArray = intent.getStringArrayExtra("Category");

        String lastId = itemsArray[itemsArray.length - 1][0];
        int newId = Integer.parseInt(lastId) + 1;
        String newItemId = String.format("%05d", newId);
        String name = ETname.getText().toString();
        String qty = ETqty.getText().toString();
        String selected = category.getText().toString();
        String price = ETprice.getText().toString();
        String selling = ETselling.getText().toString();
        String imgByte = Arrays.toString(byteArray);

        if(!validateName() | !validateQty() | !validatePrice() | !validateSelling() | !validateCategory()){

            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {

                            itemName.setError(null);
                            itemName.setErrorEnabled( false );

                            itemQty.setError(null);
                            itemQty.setErrorEnabled( false );

                            itemPrice.setError(null);
                            itemPrice.setErrorEnabled( false );

                            itemSelling.setError(null);
                            itemSelling.setErrorEnabled( false );

                            menu.setError(null);
                        }
                    }, 10000);
            return;
        }

        String[] addItem = {newItemId,name,selected,qty,price,selling,imgByte};
        String[][] newItem = Arrays.copyOf(itemsArray, itemsArray.length + 1);
        newItem[itemsArray.length] = addItem;

        Intent i = new Intent(c, Items.class);
        i.putExtra("Items",newItem);
        i.putExtra("Category",categoryArray);
        startActivity(i);
        finish();
    }

    //VALIDATION
    private boolean validateName() {
        String val = ETname.getText().toString();
        if (val.isEmpty()) {
            itemName.setError( "Field can not be empty" );
            return false;
        } else {
            itemName.setError(null);
            itemName.setErrorEnabled( false );
            return true;
        }
    }
    private boolean validateQty() {
        String val = ETqty.getText().toString();
        if (val.isEmpty()) {
            ETqty.setError( "Field can not be empty" );
            return false;
        } else {
            itemQty.setError(null);
            itemQty.setErrorEnabled( false );
            return true;
        }
    }
    private boolean validatePrice() {
        String val = ETprice.getText().toString();
        if (val.isEmpty()) {
            ETprice.setError( "Field can not be empty" );
            return false;
        } else {
            itemPrice.setError(null);
            itemPrice.setErrorEnabled( false );
            return true;
        }
    }
    private boolean validateSelling() {
        String val = ETselling.getText().toString();
        if (val.isEmpty()) {
            ETselling.setError( "Field can not be empty" );
            return false;
        } else {
            itemSelling.setError(null);
            itemSelling.setErrorEnabled( false );
            return true;
        }
    }
    private boolean validateCategory(){
        String val = category.getText().toString();
        if (val.isEmpty()) {
            menu.setError( "Please select a category" );
            return false;
        } else {
            menu.setError(null);
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_TAKE_PHOTO){
            clicked = true;
            IBaddimg.setVisibility(View.GONE);
            Bundle extras = data.getExtras();
            Bitmap imgBit = (Bitmap) extras.get("data");
            IVitemimg.setImageBitmap(imgBit);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imgBit.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
    }
}