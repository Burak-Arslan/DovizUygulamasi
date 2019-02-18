package com.example.burak.doviz.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burak.doviz.R;
import com.example.burak.doviz.ViewHolder.MainViewHolder;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    Context context;
    ArrayList<String> itemList;

    public MainAdapter(Context context, ArrayList<String> listMainBilgileri) {
        this.context = context;
        this.itemList = listMainBilgileri;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_adapter, parent, false);
        MainViewHolder akaryakitViewHolderItem = new MainViewHolder(view);
        return akaryakitViewHolderItem;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.SetData(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
