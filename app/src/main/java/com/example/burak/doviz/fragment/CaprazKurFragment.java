package com.example.burak.doviz.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.burak.doviz.R;
import com.example.burak.doviz.adapter.CaprazKurAdapter;
import com.example.burak.doviz.helper.Connectivity;
import com.example.burak.doviz.helper.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class CaprazKurFragment extends Fragment {

    CaprazKurAdapter caprazKurAdapter;
    RecyclerView rvCaprazKur;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Init(inflater, container, savedInstanceState);
        InternetSeviyesiKontrol();
        return view;
    }

    public void Init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.activity_capraz_kur, container, false);
            rvCaprazKur = view.findViewById(R.id.rvCaprazKur);
        } catch (Exception ex) {

        }
    }

    private void InternetSeviyesiKontrol() {
        try {
            boolean isNetworkFast = Connectivity.isConnectedFast(Utils.getContext());
            if (isNetworkFast) {
                CaprazKurBilgileriniGetir();
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

    private void CaprazKurBilgileriniGetir() {
        try {
            new CaprazKurBilgileriniGetir().execute();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public class CaprazKurBilgileriniGetir extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String htmlCaprazKur;
        String txtCaprazKur;
        ArrayList<String> listCaprazKur = new ArrayList<>();


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
                Document doc = Jsoup.connect(Utils.PARITE_URL).get();
                Elements column = doc.select("div[class=listdiv]");
                Elements columns = column.select("ul");
//                Elements row = columns.select("li");

                for (int i = 1; i < columns.size(); i++) {
                    Element rows = columns.get(i);
                    htmlCaprazKur = rows.html();
                    txtCaprazKur = Jsoup.parse(htmlCaprazKur).text();
                    listCaprazKur.add(txtCaprazKur);
                }

            } catch (IOException e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            try {
                caprazKurAdapter = new CaprazKurAdapter(Utils.getContext(), listCaprazKur);
                rvCaprazKur.setAdapter(caprazKurAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Utils.getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvCaprazKur.setLayoutManager(linearLayoutManager);

            } catch (Exception e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        }
    }
}
