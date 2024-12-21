package com.example.it307_project.Adapter;


import static com.example.it307_project.Adapter.StringToByte.stringToByteArray;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.R;
import java.util.List;

public class SalesItemAdapter extends RecyclerView.Adapter<SalesItemAdapter.ViewHolder> {
    Context context;
    List<AllItemModel> allItemModels;
    private final ClickListener listener;
    public SalesItemAdapter(Context c, List<AllItemModel> allItemModels, ClickListener listener ){
        this.context = c;
        this.allItemModels = allItemModels;
        this.listener = listener;
    }
    public void setFilterList(List<AllItemModel> filteredList) {
        this.allItemModels = filteredList;
        notifyDataSetChanged();
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

        AllItemModel item = allItemModels.get(position);
        holder.TVsalesname.setText(item.getItemName());
        holder.TVsalesprice.setText("₱ " + String.valueOf(item.getItemPrice()));

        if (item.getImageResId() == 0) {
            byte[] byteArrayConverted = stringToByteArray(item.getItemImage());
            if (byteArrayConverted.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayConverted, 0, byteArrayConverted.length);
                holder.IVsaleimg.setImageBitmap(bitmap);
            } else {

                holder.IVsaleimg.setImageResource(R.mipmap.no_image);
            }

        } else {
            holder.IVsaleimg.setImageResource(item.getImageResId());
        }
    }

    @Override
    public int getItemCount() {
        return allItemModels.size();
    }

    public interface ClickListener {
        void onIdCLick(String id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView TVsalesname ,TVsalesprice;

        LinearLayout LLitem;
        ImageView IVsaleimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TVsalesname = itemView.findViewById(R.id.TVsalesname);
            TVsalesprice = itemView.findViewById(R.id.TVsalesprice);
            IVsaleimg = itemView.findViewById(R.id.IVsalesimg);
            LLitem = itemView.findViewById(R.id.LLitem);

            LLitem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onIdCLick(allItemModels.get(getAdapterPosition()).getItemId());
        }
    }
}
