package com.example.it307_project.Adapter;


import static com.example.it307_project.Adapter.StringToByte.stringToByteArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.CartModel;
import com.example.it307_project.R;
import com.example.it307_project.Sales;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<CartModel> cartModels;
    private Sales salesActivity;

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

        holder.TVcartname.setText(TextUtils.ellipsize(cartModels.get(holder.getAdapterPosition()).getName(), new TextPaint(), 130, TextUtils.TruncateAt.END).toString());
        holder.TVcartprice.setText("PHP: ₱" +String.valueOf(cartModels.get(holder.getAdapterPosition()).getPrice()));
        holder.TVcarttotal.setText("Total: ₱" +String.valueOf(cartModels.get(holder.getAdapterPosition()).getTotal()));
        holder.TVcartqty.setText(String.valueOf(cartModels.get(holder.getAdapterPosition()).getQty()));


        if (cartModels.get(position).getImageResid() == 0) {
            byte[] byteArrayConverted = stringToByteArray(cartModels.get(position).getImageByte());
            if (byteArrayConverted.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayConverted, 0, byteArrayConverted.length);
                holder.IVcart.setImageBitmap(bitmap);
            }
        } else {
            holder.IVcart.setImageResource(cartModels.get(position).getImageResid());
        }

        //Add and minus quantity
        CartModel cartItem = cartModels.get(holder.getAdapterPosition());


        holder.IBadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxQuantity = cartItem.getAvailableQuantity();
                if (cartItem.getQty() < maxQuantity) {
                    int quantity = cartItem.getQty() + 1;
                    cartItem.setQty(quantity);
                    cartItem.setTotal(cartItem.getPrice() * quantity);
                    notifyItemChanged(holder.getAdapterPosition());
                    salesActivity.refreshCart();
                } else {
                    Toast.makeText(context, "Maximum quantity reached.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.IBminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cartItem.getQty() - 1;
                if (quantity > 0) {
                    cartItem.setQty(quantity);
                    cartItem.setTotal(cartItem.getPrice() * quantity);
                    notifyItemChanged(holder.getAdapterPosition());

                } else {

                    cartModels.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
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
