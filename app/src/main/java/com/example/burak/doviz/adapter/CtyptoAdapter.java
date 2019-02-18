package com.example.burak.doviz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burak.doviz.R;
import com.example.burak.doviz.ViewHolder.BorsaViewHolder;
import com.example.burak.doviz.ViewHolder.CryptoViewHolder;

import java.util.ArrayList;

public class CtyptoAdapter extends RecyclerView.Adapter<CryptoViewHolder> {
    Context context;
    ArrayList<String> itemList;

    public CtyptoAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crypto_adapter, parent, false);
        CryptoViewHolder cryptoViewHolder = new CryptoViewHolder(view);
        return cryptoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        holder.SetData(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
