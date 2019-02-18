package com.example.burak.doviz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burak.doviz.R;
import com.example.burak.doviz.ViewHolder.BorsaViewHolder;

import java.util.ArrayList;

public class BorsaAdapter extends RecyclerView.Adapter<BorsaViewHolder> {

    Context context;
    ArrayList<String> itemList;

    public BorsaAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BorsaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.borsa_adapter, parent, false);
        BorsaViewHolder borsaViewHolder = new BorsaViewHolder(view);
        return borsaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BorsaViewHolder holder, int position) {
        holder.SetData(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
