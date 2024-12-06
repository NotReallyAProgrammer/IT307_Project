package com.example.it307_project.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.NavModel;
import com.example.it307_project.R;

import java.util.List;


public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder>{

    Context context;
    List<NavModel> navModel;

    public NavAdapter(Context c, List<NavModel> navModels){
        this.context = c;
        this.navModel = navModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.navigation_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TVnavtitle.setText(navModel.get(position).getTitle());
        holder.TVnavdisc.setText(navModel.get(position).getDisc());
    }

    @Override
    public int getItemCount() {
        return navModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView TVnavtitle, TVnavdisc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TVnavtitle = itemView.findViewById(R.id.TVnavtitle);
            TVnavdisc = itemView.findViewById(R.id.TVnavdisc);
        }
    }

}
