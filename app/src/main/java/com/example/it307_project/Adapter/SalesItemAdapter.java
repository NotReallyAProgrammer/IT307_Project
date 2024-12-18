package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.SalesItemModel;
import com.example.it307_project.R;
import java.util.List;

public class SalesItemAdapter extends RecyclerView.Adapter<SalesItemAdapter.ViewHolder> {
    Context context;
    List<SalesItemModel> salesItemModels;
    private final ClickListener listener;
    public SalesItemAdapter(Context c, List<SalesItemModel> salesItemModels, ClickListener listener ){
        this.context = c;
        this.salesItemModels = salesItemModels;
        this.listener = listener;
    }
    public void setFilterList(List<SalesItemModel> filteredList) {

        this.salesItemModels = filteredList;
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
        holder.TVsalesname.setText(salesItemModels.get(position).getName());
        holder.TVsalesprice.setText("₱ " + String.valueOf(salesItemModels.get(position).getPrice()));
        holder.IVsaleimg.setImageResource(salesItemModels.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return salesItemModels.size();
    }

    public interface ClickListener {
        void onPositionClicked(int position);
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
            listener.onPositionClicked(getAdapterPosition());
        }
    }
}
