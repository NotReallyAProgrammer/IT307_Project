package com.example.it307_project.Adapter;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.CartModel;
import com.example.it307_project.R;
import com.example.it307_project.Sales;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<CartModel> cartModels;
    private Sales salesActivity;  // Reference to the activity to call refres

    public CartAdapter(Context c, List<CartModel>cartModels, Sales sales){
        this.context = c;
        this.cartModels = cartModels;
        this.salesActivity =sales;
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


        holder.TVcartname.setText(TextUtils.ellipsize(cartModels.get(position).getName(), new TextPaint(), 130, TextUtils.TruncateAt.END).toString());
        holder.TVcartprice.setText("PHP: ₱" +String.valueOf(cartModels.get(position).getPrice()));
        holder.TVcarttotal.setText("Total: ₱" +String.valueOf(cartModels.get(position).getTotal()));
        holder.TVcartqty.setText(String.valueOf(cartModels.get(position).getQty()));
        holder.IVcart.setImageResource(cartModels.get(position).getImage());

        CartModel cartItem = cartModels.get(position);

        holder.IBadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getQty() + 1;  // Increase quantity
                cartItem.setQty(quantity);  // Update the quantity
                cartItem.setTotal(cartItem.getPrice() * quantity);  // Update the total
                notifyItemChanged(position);  // Notify the adapter that data has changed for this position
                salesActivity.refreshCart();
            }
        });

        holder.IBminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getQty() - 1;
                if (quantity > 0) {
                    cartItem.setQty(quantity);
                    cartItem.setTotal(cartItem.getPrice() * quantity);
                    notifyItemChanged(position);

                } else {

                    cartModels.remove(position);
                    notifyItemRemoved(position);
                }
                salesActivity.refreshCart();
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVcartname,TVcartprice,TVcarttotal,TVcartqty;
        ImageView IVcart;
        ImageButton IBadd,IBminus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVcartname = itemView.findViewById(R.id.TVcartname);
            TVcartprice = itemView.findViewById(R.id.TVcartprice);
            TVcarttotal = itemView.findViewById(R.id.TVcarttotal);
            TVcartqty = itemView.findViewById(R.id.TVcartqty);
            IVcart = itemView.findViewById(R.id.IVcart);
            IBadd = itemView.findViewById(R.id.IBadd);
            IBminus = itemView.findViewById(R.id.IBminus);
        }
    }
}
