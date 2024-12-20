package com.example.it307_project.Adapter;

import static com.example.it307_project.Adapter.StringToByte.stringToByteArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.R;


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
        holder.TVallitemsrp.setText("Srp: " + "₱" + String.valueOf(allItemModels.get(position).getItemSRP()));
        holder.TVallitemprice.setText("Price: " + "₱" + String.valueOf(allItemModels.get(position).getItemPrice()));
        holder.TVallitemprofit.setText( "Profit: " + "₱" + String.valueOf(allItemModels.get(position).getProfit()));


        if (allItemModels.get(position).getImageResId() == 0) {
            byte[] byteArrayConverted = stringToByteArray(allItemModels.get(position).getItemImage());
            if (byteArrayConverted.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayConverted, 0, byteArrayConverted.length);
                holder.IVallitemimg.setImageBitmap(bitmap);
            } else {

                holder.IVallitemimg.setImageResource(R.mipmap.no_image);
            }

        } else {
            holder.IVallitemimg.setImageResource(allItemModels.get(position).getImageResId());
        }


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
