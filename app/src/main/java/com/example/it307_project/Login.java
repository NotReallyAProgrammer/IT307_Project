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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.it307_project.Connection.Constant;
import com.example.it307_project.Connection.RequestHandler;
import com.example.it307_project.Connection.SharedPreference;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputLayout loginUser, loginPass;
    TextInputEditText ETemail, ETpass;
    Button BTNconfirm;
    TextView TVinvalid, TVforgotpass,TVsignup;
    CheckBox rememberMe;
    Context c = this;


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


                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        Constant.URL_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject obj = new JSONObject(response);
                                    if (!obj.getBoolean("error")) {
                                        JSONObject userJson = obj.getJSONObject("user");

                                        int id = userJson.getInt("id");
                                        String username = userJson.getString("username");
                                        String email = userJson.getString("email");

                                        SharedPreference.getInstance(getApplicationContext()).userLogin(id, username, email);
                                        Toast.makeText(c, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String errorMsg = obj.getString("message");
                                        TVinvalid.setText(errorMsg);
                                        TVinvalid.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(c, Home.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String user = ETemail.getText().toString();
                        String pass = ETpass.getText().toString();
                        Map<String,String> params = new HashMap<>();
                        params.put("email",user);
                        params.put("password",pass);
                        return params;
                    }
                };

                RequestHandler.getInstance(c).addToRequestQueue(stringRequest);

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
                    startActivity(i);
            }
        });

        TVsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, Register.class);
                    startActivity(intent);
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