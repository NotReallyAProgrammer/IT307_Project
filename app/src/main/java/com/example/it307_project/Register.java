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
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class Register extends AppCompatActivity {

    TextInputLayout regName, regEmail, regPass, regCfrmPass,regAnswer,menu;
    TextInputEditText ETname, ETemail, ETpass, ETcfrmpass, ETanswer;
    Button BTNconfirm;
    ImageView backbtn;
    AutoCompleteTextView question;
    String[] arraySpinner = new String[]{
            "What is your mother's maiden name?",
            "What was the name of your first pet?",
            "What was the name of your elementary school?",
            "In what city were you born?",
            "What is your favorite color?"};

    Context c=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        initialize();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize(){

        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        regCfrmPass = findViewById(R.id.regCnfrmPass);
        regAnswer = findViewById(R.id.regAnswer);
        ETname = findViewById(R.id.ETName);
        ETemail = findViewById(R.id.ETemail);
        ETpass = findViewById(R.id.ETpass);
        ETcfrmpass = findViewById(R.id.ETcnfrmpass);
        ETanswer = findViewById(R.id.ETanswer);
        question = findViewById(R.id.questions);
        menu = findViewById(R.id.menu);
        BTNconfirm = findViewById(R.id.BTNconfirm);
        backbtn = findViewById(R.id.backbtn);


        //Setting selection in spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, R.layout.dropdown_menu_popup_item, arraySpinner);
        question.setAdapter(adapter);

        Intent intent = getIntent();
        String[][] Users = (String[][]) intent.getSerializableExtra("Users");
        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ETname.getText().toString();
                String email = ETemail.getText().toString();
                String pass = ETpass.getText().toString();
                String ans = ETanswer.getText().toString();

                String selectedQuestion = question.getText().toString();
                int selection = 0;

                if (selectedQuestion.equals("What is your mother's maiden name?")){
                    selection = 1;
                }else if(selectedQuestion.equals("What was the name of your first pet?")){
                    selection = 2;
                }else if(selectedQuestion.equals("What was the name of your elementary school?")){
                    selection = 3;
                }else if(selectedQuestion.equals("In what city were you born?")){
                    selection = 4;
                }else if(selectedQuestion.equals("What is your favorite color?")){
                    selection = 5;
                }

                if(!validateFullName() | !validateEmail() | !validatePassword() | !validateReEnter() | !validateQuestion() | !validateAnswer() )
                {
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {

                                    regName.setError(null);
                                    regName.setErrorEnabled( false );

                                    regEmail.setError(null);
                                    regEmail.setErrorEnabled( false );

                                    regPass.setError(null);
                                    regPass.setErrorEnabled( false );

                                    regCfrmPass.setError(null);
                                    regCfrmPass.setErrorEnabled( false );

                                    menu.setError(null);


                                    regAnswer.setError(null);
                                    regAnswer.setErrorEnabled( false );
                                }
                            }, 10000);
                    return;
                }


                String[] newUser = {email,name,String.valueOf(selection),ans,pass};
                String[][] newUserPass = Arrays.copyOf(Users, Users.length + 1);
                newUserPass[Users.length] = newUser;


                Intent i = new Intent(c,Login.class);
                i.putExtra("Users",newUserPass);
                startActivity(i);
                Toast.makeText(c, "Register successfully", Toast.LENGTH_SHORT).show();

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Error Validation
    private boolean validateFullName() {
        String val = ETname.getText().toString();
        if (val.isEmpty()) {
            regName.setError( "Field can not be empty" );
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled( false );
            return true;
        }
    }

    private boolean validateEmail() {
        String val = ETemail.getText().toString();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError( "Field can not be empty" );
            return false;
        } else if (!val.matches( checkEmail )) {
            regEmail.setError( "Invalid Email" );

            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled( false );
            return true;
        }
    }

    private boolean validatePassword() {
        String val = ETpass.getText().toString();
        final String checkPassword = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{6,}$";


        //Validate Password
                /*"^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";*/

        if (val.isEmpty()) {
            regPass.setError( "Field can not be empty" );
            return false;
        }
        else if(val.length() < 6)
        {
            regPass.setError( "Password need to contain 6 character!" );

            return false;
        }
        else if(!val.matches(checkPassword))
        {
            regPass.setError( "Invalid Password must contain Uppercase and Digits!" );
            return false;
        }
        else {
            regPass.setError(null);
            regPass.setErrorEnabled( false );
            return true;
        }
    }

    private boolean validateReEnter() {
        String val = ETpass.getText().toString();
        String val2 = ETcfrmpass.getText().toString();


        if(!val.equals(val2))
        {
            regCfrmPass.setError( "Password Doesn't Match" );
            return false;
        }
        else if (val2.isEmpty()) {
            regCfrmPass.setError( "Field can not be empty" );
            return false;
        } else {
            regCfrmPass.setError(null);
            regCfrmPass.setErrorEnabled( false );
            return true;
        }
    }

    private boolean validateQuestion(){
        String val = question.getText().toString();
        if (val.isEmpty()) {
            menu.setError( "Please select a question" );
            return false;
        } else {
            menu.setError(null);
            return true;
        }
    }

    private boolean validateAnswer() {
        String val = ETanswer.getText().toString();

        if (val.isEmpty()) {
            regAnswer.setError( "Field can not be empty" );
            return false;
        }

        else {
            regAnswer.setError(null);
            regAnswer.setErrorEnabled( false );
            return true;
        }
    }

}