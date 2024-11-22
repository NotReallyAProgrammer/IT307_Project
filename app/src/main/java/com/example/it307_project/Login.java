package com.example.it307_project;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextInputLayout loginUser, loginPass;
    TextInputEditText ETuser, ETpass;
    Button BTNconfirm;
    TextView TVinvalid;
    Context c = this; String[][] userPass = {{ "Jeff", "12345" },{ "Joan", "567890" }, { "Dani", "ASDFGH" }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initialiaze();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialiaze(){
        ETuser = findViewById(R.id.ETuser);
        ETpass = findViewById(R.id.ETpass);
        BTNconfirm = findViewById(R.id.BTNconfirm);
        TVinvalid = findViewById(R.id.TVinvalid);




        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ETuser.getText().toString();
                String pass = ETpass.getText().toString();
                boolean userOK = false;
                int tempCount = 0;


                for (String[] up : userPass){
                    if (user.equals(up[0]) && pass.equals(up[1])){
                        userOK = true;

                    }else {
                        tempCount++;
                    }

                    if(userOK){
                        Toast.makeText(c, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        break;
                    }else if(tempCount == userPass.length){
                        TVinvalid.setVisibility(View.VISIBLE);
                        break;
                    }
                }


                        //TIMER FOR ERROR MESSAGE TO DISAPPEAR
                        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                TVinvalid.setVisibility(View.GONE);
                            }
                        }, 5000);
            }
        });
    }
}