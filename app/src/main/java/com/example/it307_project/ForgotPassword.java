package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout forgotEmail;
    TextInputEditText ETemail;
    ImageView backbtn;
    Button BTNconfirm;

    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        initialize();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize(){
        forgotEmail = findViewById(R.id.forgotEmail);
        ETemail = findViewById(R.id.ETemail);
        backbtn = findViewById(R.id.backbtn);
        BTNconfirm = findViewById(R.id.BTNconfirm);

        Intent intent = getIntent();
        String[][] Users = (String[][]) intent.getSerializableExtra("Users");



        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ETemail.getText().toString();
                boolean userOK = false;
                int tempCount = 0;

                if(!validateEmail()){
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    forgotEmail.setError(null);
                                    forgotEmail.setErrorEnabled( false );
                                }
                            }, 5000);
                    return;
                }

                for (String[] up : Users){
                    if (user.equals(up[0]) ){
                        userOK = true;

                    }else {
                        tempCount++;
                    }

                    if(userOK){
                        Toast.makeText(c, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        break;
                    }else if(tempCount == Users.length){
                        forgotEmail.setError("Email does not exist");
                        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                new Runnable() {
                                    public void run() {
                                        forgotEmail.setError(null);
                                        forgotEmail.setErrorEnabled( false );
                                    }
                                }, 5000);
                        break;
                    }
                }


            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validateEmail() {
        String val = ETemail.getText().toString();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            forgotEmail.setError( "Field can not be empty" );
            return false;
        } else if (!val.matches( checkEmail )) {
            forgotEmail.setError( "Invalid Email" );

            return false;
        } else {
            forgotEmail.setError(null);
            forgotEmail.setErrorEnabled( false );
            return true;
        }
    }
}