package com.example.burak.doviz.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.burak.doviz.adapter.AkaryakitAdapter;
import com.example.burak.doviz.helper.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AkaryakitManager {

    Context context;

    public AkaryakitManager(Context context) {
        this.context = context;
    }

    public void AkaryakitBilgileriniGetirAsync(Elements rows, String htmlAkarYakitGuncelBilgiler, String textAkarYakitGuncelBilgiler, ArrayList<String> listAkaryakitBilgileri) {
        try {
            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cols = row.select("td");
                htmlAkarYakitGuncelBilgiler = cols.html();
                textAkarYakitGuncelBilgiler = Jsoup.parse(htmlAkarYakitGuncelBilgiler).text();
                listAkaryakitBilgileri.add(textAkarYakitGuncelBilgiler);
            }
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void AkaryakitBasliklariniGetirAsync(Elements baslik, String htmlBaslik, String textBaslik, ArrayList<String> listBaslik) {
        try {
            for (int index = 1; index < baslik.size(); index++) {
                htmlBaslik = baslik.html();
                textBaslik = Jsoup.parse(htmlBaslik).text();
                listBaslik.add(textBaslik);
            }
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void OnPostExecuteAkaryakit(ArrayList<String> listAkaryakitBilgileri, ProgressDialog dialog, AkaryakitAdapter adapter, RecyclerView rvAkaryakitBilgileri) {
        try {
            adapter = new AkaryakitAdapter(Utils.getContext(), listAkaryakitBilgileri);
            rvAkaryakitBilgileri.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Utils.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvAkaryakitBilgileri.setLayoutManager(linearLayoutManager);
            Toasty.success(Utils.getContext(), "Bilgiler Getirildi", Toast.LENGTH_SHORT, true).show();
            dialog.dismiss();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public boolean GirilenSehirVarMi(String sehirKodu) {
        boolean result = false;
        try {
            if (Integer.parseInt(sehirKodu) < 1 || Integer.parseInt(sehirKodu) > 81) {
                result = false;
                Toasty.warning(Utils.getContext(), "Girilen Şehir Kodu Mevcut Değil", Toast.LENGTH_SHORT, true).show();
            } else {
                result = true;
            }
        } catch (Exception ex) {
            result = false;
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
        return result;
    }
}
