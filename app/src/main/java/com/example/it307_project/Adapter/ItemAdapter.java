package com.example.it307_project.Adapter;

import static com.example.it307_project.Adapter.StringToByte.stringToByteArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    Context context;
    List<AllItemModel> allItemModels;

    public ItemAdapter(Context c, List<AllItemModel> allItemModels){
        this.context = c;
        this.allItemModels = allItemModels;
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
        holder.TVitemname.setText(allItemModels.get(position).getItemName());
        holder.TVitemcat.setText(allItemModels.get(position).getCategory());
        holder.TVqty.setText(String.valueOf(allItemModels.get(position).getItemQuantity()));
        holder.TVprice.setText("₱"+String.valueOf(allItemModels.get(position).getItemPrice()));

        if (allItemModels.get(position).getImageResId() == 0) {
            byte[] byteArrayConverted = stringToByteArray(allItemModels.get(position).getItemImage());
            if (byteArrayConverted.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayConverted, 0, byteArrayConverted.length);
                holder.IVitemimg.setImageBitmap(bitmap);
            } else {

                holder.IVitemimg.setImageResource(R.mipmap.no_image);
            }

        } else {
            holder.IVitemimg.setImageResource(allItemModels.get(position).getImageResId());
        }
    }

    @Override
    public int getItemCount() {
        return allItemModels.size();
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
