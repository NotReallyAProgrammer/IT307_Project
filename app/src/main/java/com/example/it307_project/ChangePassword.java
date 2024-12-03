package com.example.it307_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.util.Arrays;

public class ChangePassword extends AppCompatActivity {

    TextInputLayout  changePass, newCnfrmPass;

    TextInputEditText  ETpass, ETcfrmpass;
    TextView TVplaceholder;

    Button BTNconfirm;

    Context c=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        initialize();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize(){

        changePass = findViewById(R.id.changePass);
        newCnfrmPass = findViewById(R.id.newCnfrmPass);
        ETpass = findViewById(R.id.ETpass);
        ETcfrmpass = findViewById(R.id.ETcnfrmpass);
        TVplaceholder = findViewById(R.id.TVplaceholder);
        BTNconfirm = findViewById(R.id.BTNconfirm);

        Intent intent = getIntent();
        String[][] Users = (String[][]) intent.getSerializableExtra("Users");
        int Userpos = intent.getIntExtra("Position",0);
        String email =Users[Userpos][0];

        TVplaceholder.setText(email);

        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = ETpass.getText().toString();


                if( !validatePassword() | !validateReEnter() )
                {
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {


                                    changePass.setError(null);
                                    changePass.setErrorEnabled( false );

                                    newCnfrmPass.setError(null);
                                    newCnfrmPass.setErrorEnabled( false );
                                }
                            }, 10000);
                    return;
                }

                Users[Userpos][4] = pass;
                Intent i = new Intent(c, Login.class);
                i.putExtra("Users",Users);

                startActivity(i);
                Toast.makeText(c, "Password change successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validatePassword() {
        String val = ETpass.getText().toString().trim();
        final String checkPassword = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{6,}$";


        if (val.isEmpty()) {
            changePass.setError( "Field can not be empty" );
            return false;
        }
        else if(val.length() < 6)
        {
            changePass.setError( "Password need to contain 6 character!" );

            return false;
        }
        else if(!val.matches(checkPassword))
        {
            changePass.setError( "Invalid Password must contain Uppercase and Digits!" );
            return false;
        }
        else {
            changePass.setError(null);
            changePass.setErrorEnabled( false );
            return true;
        }
    }

    private boolean validateReEnter() {
        String val = ETpass.getText().toString();
        String val2 = ETcfrmpass.getText().toString();


        if(!val.equals(val2))
        {
            newCnfrmPass.setError( "Password Doesn't Match" );
            return false;
        }
        else if (val2.isEmpty()) {
            newCnfrmPass.setError( "Field can not be empty" );
            return false;
        } else {
            newCnfrmPass.setError(null);
            newCnfrmPass.setErrorEnabled( false );
            return true;
        }
    }
}