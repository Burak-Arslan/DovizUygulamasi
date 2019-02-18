package com.example.burak.doviz.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.burak.doviz.adapter.AkaryakitAdapter;
import com.example.burak.doviz.adapter.MainAdapter;
import com.example.burak.doviz.helper.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainManager {

    Context context;
    String htmlAdi;
    String htmlDeger;
    String htmlYuzde;


    public MainManager(Context context) {
        this.context = context;
    }

    public void MainBilgileriGetirAsync(Elements rows, String textAkarYakitGuncelBilgiler, ArrayList<String> listAkaryakitBilgileri) {
        try {
            for (int i = 0; i < rows.size(); i++) {
                Element adi = rows.select("span[class = menu-row1]").get(i);
                Element deger = rows.select("span[class = menu-row2]").get(i);
                Element yuzde = rows.select("span[class = menu-row3]").get(i);
                htmlAdi = adi.html();
                htmlDeger = deger.html();
                htmlYuzde = yuzde.html();
                textAkarYakitGuncelBilgiler = Jsoup.parse(htmlAdi + " " + htmlDeger + " " + htmlYuzde).text();
                listAkaryakitBilgileri.add(textAkarYakitGuncelBilgiler);
            }
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void OnPostExecuteMain(ArrayList<String> listAkaryakitBilgileri, ProgressDialog dialog, MainAdapter adapter, RecyclerView rvAkaryakitBilgileri) {
        try {
            adapter = new MainAdapter(Utils.getContext(), listAkaryakitBilgileri);
            rvAkaryakitBilgileri.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Utils.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvAkaryakitBilgileri.setLayoutManager(linearLayoutManager);
            dialog.dismiss();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

}
