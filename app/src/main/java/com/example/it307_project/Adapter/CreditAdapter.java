package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.AllItemModel;
import com.example.it307_project.Model.CreditModel;
import com.example.it307_project.Model.ItemModel;
import com.example.it307_project.R;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {
    Context context;
    List<CreditModel> creditModels;
    private final ClickListener listener;
    public CreditAdapter(Context c, List<CreditModel> creditModels,ClickListener listener){
        this.context = c;
        this.creditModels = creditModels;
        this.listener = listener;
    }

    public void setFilterList(List<CreditModel> filteredList) {
        this.creditModels = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.credits_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.ViewHolder holder, int position) {
        holder.TVname.setText(creditModels.get(position).getName());
        holder.TVindividualcredit.setText("₱"+String.valueOf(creditModels.get(position).getTotalCredit()));

    }

    @Override
    public int getItemCount() {
        return creditModels.size();
    }

    public interface ClickListener {
        void onNameClicked(String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView TVname,TVindividualcredit;
        RelativeLayout RLcredit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVname = itemView.findViewById(R.id.TVcreditname);
            TVindividualcredit = itemView.findViewById(R.id.TVindividualcredit);
            RLcredit = itemView.findViewById(R.id.RLcredit);

            RLcredit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onNameClicked(creditModels.get(getAdapterPosition()).getName());
        }
    }
}
