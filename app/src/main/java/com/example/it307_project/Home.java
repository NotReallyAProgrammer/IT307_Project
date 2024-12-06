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

import com.example.it307_project.Adapter.NavAdapter;
import com.example.it307_project.Model.NavModel;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView RVname;
    List<NavModel> navModels = new ArrayList<>();
    NavAdapter navAdapter;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        initialize();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initialize() {
        RVname = findViewById(R.id.RVname);

        navModels.add(new NavModel("Sales", "This is a simple discription."));
        navModels.add(new NavModel("All Items", "This is a simple discription."));
        navModels.add(new NavModel("Credits", "This is a simple discription."));

        navAdapter = new NavAdapter(Home.this,navModels);
        RVname.setAdapter(navAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        RVname.setLayoutManager(layoutManager);
    }
}