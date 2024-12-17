package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.SalesItemModel;
import com.example.it307_project.R;

import java.util.ArrayList;
import java.util.List;

public class AllItemAdapter extends RecyclerView.Adapter<AllItemAdapter.ViewHolder> {
    Context context;
    List<AllItemModel> allItemModels;

    public AllItemAdapter(Context c, List<AllItemModel> allItemModels){
        this.context = c;
        this.allItemModels = allItemModels;
    }
    public void setFilterList(List<AllItemModel> filterList){
        this.allItemModels = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.all_items_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllItemAdapter.ViewHolder holder, int position) {
        holder.TVallitemname.setText(allItemModels.get(position).getItemName());
        holder.TVallitemqty.setText("Quantity: " + String.valueOf(allItemModels.get(position).getItemQuantity()));
        holder.TVallitemsrp.setText("Srp: " + "₱" + String.valueOf(allItemModels.get(position).getItemSrp()));
        holder.TVallitemprice.setText("Price: " + "₱" + String.valueOf(allItemModels.get(position).getItemPrice()));
        holder.TVallitemprofit.setText( "Profit: " + "₱" + String.valueOf(allItemModels.get(position).getItemProfit()));
        holder.IVallitemimg.setImageResource(allItemModels.get(position).getItemImg());
    }

    @Override
    public int getItemCount() {
        return allItemModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVallitemname, TVallitemqty, TVallitemprice, TVallitemsrp, TVallitemprofit;
        ImageView IVallitemimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVallitemname = itemView.findViewById(R.id.TVallitemname);
            TVallitemqty = itemView.findViewById(R.id.TVallitemqty);
            TVallitemsrp = itemView.findViewById(R.id.TVallitemsrp);
            TVallitemprice = itemView.findViewById(R.id.TVallitemprice);
            TVallitemprofit = itemView.findViewById(R.id.TVallitemprofit);
            IVallitemimg = itemView.findViewById(R.id.IVallitemimg);
        }
    }
}
