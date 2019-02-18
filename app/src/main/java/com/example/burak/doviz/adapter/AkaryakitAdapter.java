package com.example.burak.doviz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burak.doviz.R;
import com.example.burak.doviz.ViewHolder.AkaryakitViewHolder;

import java.util.ArrayList;

public class AkaryakitAdapter extends RecyclerView.Adapter<AkaryakitViewHolder> {
    Context context;
    ArrayList<String> itemList;

    public AkaryakitAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public AkaryakitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.akaryakit_adapter, parent, false);
        AkaryakitViewHolder akaryakitViewHolderItem = new AkaryakitViewHolder(view);
        return akaryakitViewHolderItem;
    }

    @Override
    public void onBindViewHolder(AkaryakitViewHolder holder, int position) {
        holder.SetData(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
