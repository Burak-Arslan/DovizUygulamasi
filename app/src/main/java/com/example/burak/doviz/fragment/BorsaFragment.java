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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.burak.doviz.R;
import com.example.burak.doviz.adapter.BorsaAdapter;
import com.example.burak.doviz.adapter.CaprazKurAdapter;
import com.example.burak.doviz.controller.AkaryakitManager;
import com.example.burak.doviz.helper.Connectivity;
import com.example.burak.doviz.helper.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class BorsaFragment extends Fragment {
    View view;
    RecyclerView rvBorsa;
    BorsaAdapter borsaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Init(inflater, container, savedInstanceState);
        InternetSeviyesiKontrol();
        return view;
    }

    public void Init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.activity_borsa, container, false);
            rvBorsa = view.findViewById(R.id.rvBorsa);
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void InternetSeviyesiKontrol() {

        try {
            boolean isNetworkFast = Connectivity.isConnectedFast(Utils.getContext());
            if (isNetworkFast) {
                BorsaBilgileriniGetir();
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

    private void BorsaBilgileriniGetir() {
        try {
            new BorsaBilgileriAsync().execute();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public class BorsaBilgileriAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String htmlBorsa;
        String txtBorsa;
        ArrayList<String> listBorsa = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Utils.getContext(), R.style.MyAlertDialogStyle);
            dialog.setMessage("Veriler getiriliyor");
            dialog.setIndeterminate(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {//Arka plan işlemleri gerçekleştirilir.
            try {
                Document doc = Jsoup.connect(Utils.BORSA_URL).get();
                Elements elements = doc.select("table[id = stocks]");
                Elements columns = elements.select("tr");

                for (int i = 1; i < columns.size(); i++) {
                    Element rows = columns.get(i);
                    htmlBorsa = rows.html();
                    txtBorsa = Jsoup.parse(htmlBorsa).text();
                    listBorsa.add(txtBorsa);
                }
            } catch (IOException e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid)//Arka plan işlemleri bittikten sonra başlık yazdırılır.
        {
            borsaAdapter = new BorsaAdapter(Utils.getContext(), listBorsa);
            rvBorsa.setAdapter(borsaAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Utils.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvBorsa.setLayoutManager(linearLayoutManager);
            Toasty.success(Utils.getContext(), "Bilgiler Getirildi", Toast.LENGTH_SHORT, true).show();
            dialog.dismiss();
        }
    }
}
