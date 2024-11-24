package com.example.it307_project;



import android.content.Context;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    Button BTNconfirm;
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
        BTNconfirm = findViewById(R.id.BTNconfirm);
        question = findViewById(R.id.questions);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, R.layout.dropdown_menu_popup_item, arraySpinner);
        question.setAdapter(adapter);

        question.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BTNconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedQuestion;
                selectedQuestion = question.getText().toString();
                Toast.makeText(c, selectedQuestion, Toast.LENGTH_SHORT).show();
            }
        });
    }
}