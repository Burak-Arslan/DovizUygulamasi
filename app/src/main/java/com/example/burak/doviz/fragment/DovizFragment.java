package com.example.burak.doviz.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.burak.doviz.R;
import com.example.burak.doviz.helper.Connectivity;
import com.example.burak.doviz.helper.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class DovizFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        InternetSeviyesiKontrol();
        return inflater.inflate(R.layout.activity_doviz, container, false);
    }
    private void InternetSeviyesiKontrol() {
        try {
            boolean isNetworkFast = Connectivity.isConnectedFast(Utils.getContext());
            if (isNetworkFast) {
                DovizBilgileriGetir();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
                builder.setTitle("Oops! İnternet Bulunamadı!");
                builder.setIcon(R.drawable.network);

                builder.setPositiveButton("Tekrar Dene", (dialog, which) -> {
                    InternetSeviyesiKontrol();
                    dialog.dismiss();
                });
                builder.setNeutralButton("Çıkış", (dialog, which) -> {

                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.darkPrimary));
                alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.cikis));
                return;
            }
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void DovizBilgileriGetir() {
        try {
            new DovizBilgileri().execute();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public class DovizBilgileri extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String htmlDoviz;
        String txtDoviz;
        ArrayList<String> listDovizBilgileri = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                dialog = new ProgressDialog(Utils.getContext(), R.style.MyAlertDialogStyle);
                dialog.setMessage("Veriler getiriliyor");
                dialog.setIndeterminate(false);
                dialog.show();
            } catch (Exception e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(Utils.DOVIZ_URL).get();
                Elements column = doc.select("div[class=listdiv]");
                Elements columns = column.select("ul");
//                Elements row = columns.select("li");

                for (int i = 1; i < columns.size(); i++) {
                    Element rows = columns.get(i);
                    htmlDoviz = rows.html();
                    txtDoviz = Jsoup.parse(htmlDoviz).text();
                    listDovizBilgileri.add(txtDoviz);
                }

            } catch (IOException e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            try {
                listDovizBilgileri.get(0);
                Toasty.success(Utils.getContext(), "Bilgiler Getirildi", Toast.LENGTH_SHORT, true).show();
            } catch (Exception e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        }
    }
}
