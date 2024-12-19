package com.example.it307_project.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.CartModel;
import com.example.it307_project.Model.SalesItemModel;
import com.example.it307_project.R;

import java.util.ArrayList;
import java.util.Arrays;
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
        Log.d("Adapter", "itemImgByte: " + allItemModels.get(position).getItemImgByte());
        if (allItemModels.get(position).getItemImg() == 0) {
            byte[] byteArrayConverted = stringToByteArray(allItemModels.get(position).getItemImgByte());
            if (byteArrayConverted.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayConverted, 0, byteArrayConverted.length);
                holder.IVallitemimg.setImageBitmap(bitmap);
            } else {
                // If byte array is empty, use fallback image
                holder.IVallitemimg.setImageResource(R.mipmap.no_image);  // Provide a fallback image resource
            }

        } else {
            holder.IVallitemimg.setImageResource(allItemModels.get(position).getItemImg());
        }


    }

    public static byte[] stringToByteArray(String str) {
        // Check if the string is empty or null
        if (str == null || str.isEmpty()) {
            return new byte[0];  // return an empty array if invalid
        }

        // Remove unwanted characters (brackets, spaces, etc.)
        str = str.replace("[", "").replace("]", "").replace(" ", "");

        // Split the string by commas to get individual byte values
        String[] byteStrings = str.split(",");

        // Check if we get any valid bytes
        if (byteStrings.length == 0) {
            return new byte[0];  // return empty byte array if no valid bytes found
        }

        byte[] byteArray = new byte[byteStrings.length];

        // Convert each string to a byte
        for (int i = 0; i < byteStrings.length; i++) {
            try {
                byteArray[i] = Byte.parseByte(byteStrings[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle parsing errors, return empty array in case of invalid format
                return new byte[0];
            }
        }

        return byteArray;
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
