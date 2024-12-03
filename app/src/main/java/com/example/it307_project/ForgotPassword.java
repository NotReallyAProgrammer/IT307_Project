package com.example.it307_project;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout forgotEmail,menu,forgotAnswer;
    TextInputEditText ETemail,ETanswer;
    AutoCompleteTextView question;
    ImageView backbtn;
    Button BTNconfirm;
    String[] arraySpinner = new String[]{
            "What is your mother's maiden name?",
            "What was the name of your first pet?",
            "What was the name of your elementary school?",
            "In what city were you born?",
            "What is your favorite color?"};
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
        forgotAnswer = findViewById(R.id.forgotAnswer);
        menu = findViewById(R.id.menu);
        ETemail = findViewById(R.id.ETemail);
        ETanswer = findViewById(R.id.ETanswer);
        question = findViewById(R.id.questions);
        backbtn = findViewById(R.id.backbtn);
        BTNconfirm = findViewById(R.id.BTNconfirm);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, R.layout.dropdown_menu_popup_item, arraySpinner);
        question.setAdapter(adapter);

        Intent intent = getIntent();
        String[][] Users = (String[][]) intent.getSerializableExtra("Users");


        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ETemail.getText().toString();
                String ans = ETanswer.getText().toString();
                String selectedQuestion = question.getText().toString();
                boolean userOK = false;
                int tempCount = 0, userPos = -1,selection = 0;


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

                if(!validateEmail()){
                    new Handler(Looper.getMainLooper()).postDelayed(
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
                        userPos = tempCount;

                    }else {
                        tempCount++;
                    }

                    if(userOK){
                        if(parseInt(Users[userPos][2]) == selection && Users[userPos][3].equals(ans)){
                                Intent i = new Intent(c, ChangePassword.class);
                                i.putExtra("Position",userPos);
                                i.putExtra("Users",Users);
                                startActivity(i);

                        }else{
                            menu.setError("Invalid question or answer");
                            forgotAnswer.setError("Invalid question or answer");
                            new Handler(Looper.getMainLooper()).postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            menu.setError(null);
                                            menu.setErrorEnabled( false );
                                            forgotAnswer.setError(null);
                                            forgotAnswer.setErrorEnabled( false );
                                        }
                                    }, 5000);
                            break;
                        }
                        break;
                    }else if(tempCount == Users.length){
                        forgotEmail.setError("Email does not exist");
                        new Handler(Looper.getMainLooper()).postDelayed(
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