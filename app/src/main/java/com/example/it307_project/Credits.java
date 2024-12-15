package com.example.it307_project;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Adapter.CreditAdapter;
import com.example.it307_project.Model.CreditModel;
import com.example.it307_project.Model.NavModel;

import java.util.ArrayList;
import java.util.List;

public class Credits extends AppCompatActivity {
    RecyclerView RVcredit;
    List<CreditModel> creditModels = new ArrayList<>();
    CreditAdapter creditAdapter;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_credits);
        initialize();
        setCreditAdapter();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize(){
        RVcredit = findViewById(R.id.RVcredit);

        creditModels.add(new CreditModel("Sample Felix",500));
        creditModels.add(new CreditModel("This Felix",500));
        creditModels.add(new CreditModel("Sample Name",500));
        creditModels.add(new CreditModel("Snaa",500));



        creditAdapter = new CreditAdapter(c,creditModels);
        RVcredit.setAdapter(creditAdapter);
      LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false);
       RVcredit.setLayoutManager(layoutManager);
    }

    private void setCreditAdapter(){

    }
}