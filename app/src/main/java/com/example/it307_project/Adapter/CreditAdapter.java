package com.example.it307_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.CreditModel;
import com.example.it307_project.Model.ItemModel;
import com.example.it307_project.R;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {
    Context context;
    List<CreditModel> creditModel;

    public CreditAdapter(Context c, List<CreditModel> creditModels){
        this.context = c;
        this.creditModel = creditModels;
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
        holder.TVname.setText(creditModel.get(position).getName());
        holder.TVindividualcredit.setText("₱"+String.valueOf(creditModel.get(position).getTotalCredit()));

    }

    @Override
    public int getItemCount() {
        return creditModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVname,TVindividualcredit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TVname = itemView.findViewById(R.id.TVcreditname);
            TVindividualcredit = itemView.findViewById(R.id.TVindividualcredit);

        }
    }
}
