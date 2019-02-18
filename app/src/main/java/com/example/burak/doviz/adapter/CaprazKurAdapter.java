package com.example.burak.doviz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burak.doviz.R;
import com.example.burak.doviz.ViewHolder.AkaryakitViewHolder;
import com.example.burak.doviz.ViewHolder.CaprazKurViewHolder;

import java.util.ArrayList;

public class CaprazKurAdapter extends RecyclerView.Adapter<CaprazKurViewHolder> {
    Context context;
    ArrayList<String> itemList;

    public CaprazKurAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CaprazKurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.capraz_kur_adapter, parent, false);
        CaprazKurViewHolder caprazKurViewHolder = new CaprazKurViewHolder(view);
        return caprazKurViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CaprazKurViewHolder holder, int position) {
        holder.SetData(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
