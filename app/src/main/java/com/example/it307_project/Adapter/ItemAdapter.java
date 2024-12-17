package com.example.it307_project.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.ItemModel;

import com.example.it307_project.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    List<ItemModel> itemModels;

    public ItemAdapter(Context c, List<ItemModel> itemModels){
        this.context = c;
        this.itemModels = itemModels;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.TVitemname.setText(itemModels.get(position).getItemName());
        holder.TVitemcat.setText(itemModels.get(position).getItemCategory());
        holder.TVqty.setText(String.valueOf(itemModels.get(position).getItemQty()));
        holder.TVprice.setText("₱"+String.valueOf(itemModels.get(position).getItemPrice()));
        holder.IVitemimg.setImageResource(itemModels.get(position).getItemImg());

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TVitemname, TVitemcat,TVqty,TVprice;
        ImageView IVitemimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TVitemname = itemView.findViewById(R.id.TVitemname);
            TVitemcat = itemView.findViewById(R.id.TVitemcat);
            TVqty = itemView.findViewById(R.id.TVqty);
            TVprice = itemView.findViewById(R.id.TVprice);
            IVitemimg = itemView.findViewById(R.id.IVitemimg);
        }
    }
}
