package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.CategoryModel;
import com.example.it307_project.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> categoryModels;
    private final ClickListener listener;

    private int selectedPosition = -1;
    private String mode ="";
    public CategoryAdapter(Context c, List<CategoryModel> categoryModels, ClickListener listener){
        this.context = c;
        this.categoryModels = categoryModels;

        this.listener = listener;
    }

    public void getMode(String mode){
        this.mode = mode;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        holder.TVcatname.setText(categoryModels.get(position).getCategoryName());

        if(mode.equals("Delete")){
            holder.itemView.setBackgroundResource(R.drawable.categorydelete);
        }else if(mode.equals("Select")){
            holder.itemView.setBackgroundResource(R.drawable.categoryborder);  // default background
        }else{
            if (selectedPosition == position) {
                holder.itemView.setBackgroundResource(R.drawable.categoryselected);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.categoryborder);  // default background
            }
        }

    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public interface ClickListener {
        void onPositionClicked(int position);

    }
    public void resetSelection() {
        selectedPosition = -1;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView TVcatname;
        LinearLayout LLcategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVcatname = itemView.findViewById(R.id.TVcatname);
            LLcategory = itemView.findViewById(R.id.LLcategory);
            LLcategory.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            selectedPosition = position;

            notifyDataSetChanged();


            if (listener != null) {
                listener.onPositionClicked(position);
            }

        }
    }
}
