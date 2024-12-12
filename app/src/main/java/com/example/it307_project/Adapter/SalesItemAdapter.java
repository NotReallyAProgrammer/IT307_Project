package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.SalesItemModel;
import com.example.it307_project.R;

import java.util.List;

public class SalesItemAdapter extends RecyclerView.Adapter<SalesItemAdapter.ViewHolder> {
    Context context;
    List<SalesItemModel> salesItemModels;
    public SalesItemAdapter(Context c, List<SalesItemModel> salesItemModels){
        this.context = c;
        this.salesItemModels = salesItemModels;
    }


    @NonNull
    @Override
    public SalesItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sales_items_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesItemAdapter.ViewHolder holder, int position) {
        holder.TVsalesname.setText(salesItemModels.get(position).getName());
        holder.TVsalesprice.setText("₱ " + String.valueOf(salesItemModels.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return salesItemModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TVsalesname ,TVsalesprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TVsalesname = itemView.findViewById(R.id.TVsalesname);
            TVsalesprice = itemView.findViewById(R.id.TVsalesprice);
        }
    }
}
