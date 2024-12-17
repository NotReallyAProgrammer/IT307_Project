package com.example.it307_project.Adapter;


import static android.content.ContentValues.TAG;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it307_project.Model.NavModel;
import com.example.it307_project.R;
import com.example.it307_project.Sales;

import java.lang.ref.WeakReference;
import java.util.List;


public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder>{

    Context context;
    List<NavModel> navModel;
    private final ClickListener listener;



    public NavAdapter(Context c, List<NavModel> navModels, ClickListener listener){
        this.context = c;
        this.navModel = navModels;
        this.listener = listener;
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
        holder.BTNnavview.setText(navModel.get(position).getButtonName());
        holder.IVimg.setImageResource(navModel.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return navModel.size();
    }

    public interface ClickListener {
        void onPositionClicked(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         TextView TVnavtitle, TVnavdisc, BTNnavview;
         ImageView IVimg;
         WeakReference<ClickListener> listenerRef;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            TVnavtitle = itemView.findViewById(R.id.TVnavtitle);
            TVnavdisc = itemView.findViewById(R.id.TVnavdisc);
            BTNnavview = itemView.findViewById(R.id.BTNnavview);
            IVimg = itemView.findViewById(R.id.IVimg);

           BTNnavview.setOnClickListener(this);
        }

        public void onClick(View v){
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }

}
