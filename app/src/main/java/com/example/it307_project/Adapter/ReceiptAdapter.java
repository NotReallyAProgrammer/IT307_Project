package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.ReceiptModel;
import com.example.it307_project.R;

import java.util.List;
public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    Context context;
    List<ReceiptModel> receiptModels;
    public ReceiptAdapter(Context c, List<ReceiptModel> receiptModels){
        this.context = c;
        this.receiptModels = receiptModels;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reciept_cardview,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TVreceiptname.setText(receiptModels.get(position).getName());
        holder.TVreceiptqty.setText("Qty: "+ String.valueOf(receiptModels.get(position).getQty()));
        holder.TVreceiptprice.setText("₱"+ String.valueOf(receiptModels.get(position).getPrice()));
    }
    @Override
    public int getItemCount() {
        return receiptModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVreceiptname,TVreceiptqty,TVreceiptprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVreceiptname = itemView.findViewById(R.id.TVreceiptname);
            TVreceiptqty = itemView.findViewById(R.id.TVreceiptqty);
            TVreceiptprice = itemView.findViewById(R.id.TVreceiptprice);
        }
    }
}