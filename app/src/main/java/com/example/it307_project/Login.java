package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    TextInputEditText ETemail, ETpass;
    Button BTNconfirm;
    TextView TVinvalid, TVforgotpass,TVsignup;
    CheckBox rememberMe;
    Context c = this;
    String[][] userPass = {{ "Jeff@email.com","Jeff","1","sample", "12345" },{ "Joan@email.com","Joan","1","sample", "567890" }, { "Dani@email.com","Dani","1","sample", "ASDFGH" }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initialize();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize(){
        ETemail = findViewById(R.id.ETemail);
        ETpass = findViewById(R.id.ETpass);
        loginUser = findViewById(R.id.loginUser);
        loginPass = findViewById(R.id.loginPass);
        BTNconfirm = findViewById(R.id.BTNconfirm);
        TVinvalid = findViewById(R.id.TVinvalid);
        TVforgotpass = findViewById(R.id.TVforgotpass);
        TVsignup = findViewById(R.id.TVsignup);
        rememberMe = findViewById(R.id.rememberMe);


        Intent intent = getIntent();
        String[][] Users = (String[][]) intent.getSerializableExtra("Users");
        if (Users != null) {
            userPass = Users;
        }

        SharedPreferences sharedPref = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String email = sharedPref.getString("email", "");
            String pass = sharedPref.getString("pass", "");
            rememberMe.setChecked(isLoggedIn);
            ETemail.setText(email);
            ETpass.setText(pass);
        }
        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ETemail.getText().toString();
                String pass = ETpass.getText().toString();
                boolean userOK = false;
                int tempCount = 0, userPos = -1;

                if( !validateEmail()){
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    loginUser.setError(null);
                                    loginUser.setErrorEnabled( false );
                                }
                            }, 5000);
                    return;
                }
                for (String[] up : userPass){
                    if (user.equals(up[0]) && pass.equals(up[4])){
                        userOK = true;
                        userPos = tempCount;

                    }else {
                        tempCount++;
                    }

                    if(userOK){
                        if (rememberMe.isChecked()) {
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("email", ETemail.getText().toString());
                            editor.putString("pass", ETpass.getText().toString());
                        } else {
                            editor.putBoolean("isLoggedIn", false);
                        }

                        Toast.makeText(c, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        editor.putString("userName", userPass[userPos][1]);
                        editor.apply();

                        Intent i = new Intent(c, Home.class);
                        startActivity(i);
                        break;

                    }else if(tempCount == userPass.length){
                        TVinvalid.setVisibility(View.VISIBLE);
                        break;
                    }
                }

                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                TVinvalid.setVisibility(View.GONE);
                            }
                        }, 5000);


            }
        });

        TVforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, ForgotPassword.class);
                if(Users != null){
                    i.putExtra("Users", Users);
                    startActivity(i);
                }else{
                    i.putExtra("Users", userPass);
                    startActivity(i);
                }

            }
        });

        TVsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, Register.class);
                if(Users != null ){
                    intent.putExtra("Users", Users);
                    startActivity(intent);
                }else{
                    intent.putExtra("Users", userPass);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateEmail() {
        String val = ETemail.getText().toString();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            loginUser.setError( "Field can not be empty" );
            return false;
        } else if (!val.matches( checkEmail )) {
            loginUser.setError( "Invalid Email" );

            return false;
        } else {
            loginUser.setError(null);
            loginUser.setErrorEnabled( false );
            return true;
        }
    }
}