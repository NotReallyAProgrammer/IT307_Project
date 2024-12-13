package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.CartModel;
import com.example.it307_project.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<CartModel> cartModels;

    public CartAdapter(Context c, List<CartModel>cartModels){
        this.context = c;
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.TVcartname.setText(cartModels.get(position).getName());
        holder.TVcartprice.setText("PHP: ₱" +String.valueOf(cartModels.get(position).getPrice()));
        holder.TVcartprice.setText("Total: ₱" +String.valueOf(cartModels.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVcartname,TVcartprice,TVcarttotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVcartname = itemView.findViewById(R.id.TVcartname);
            TVcartprice = itemView.findViewById(R.id.TVcartprice);
            TVcarttotal = itemView.findViewById(R.id.TVcarttotal);
        }
    }
}
