package com.example.burak.doviz.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.burak.doviz.R;
import com.example.burak.doviz.activity.MainFragment;
import com.example.burak.doviz.adapter.AkaryakitAdapter;
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

public class AkaryakitFragment extends Fragment {

    TextInputEditText txtInputSehirKodu;
    Button btnTamam;
    String sehirKodu;
    AkaryakitAdapter adapter;
    RecyclerView rvAkaryakitBilgileri;
    AkaryakitManager akaryakitManager;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Init(inflater, container, savedInstanceState);
        Events();
        return view;
    }


    private void Events() {
        try {
            btnTamam.setOnClickListener(v -> {
                sehirKodu = txtInputSehirKodu.getText().toString();
                if (!sehirKodu.equals("") && sehirKodu != null) {
                    boolean girilenSehirVarMi = akaryakitManager.GirilenSehirVarMi(sehirKodu);
                    if (girilenSehirVarMi) {
                        InternetSeviyesiKontrol();
                    }
                } else {
                    Toasty.warning(Utils.getContext(), "Şehir Kodu Boş Geçilemez!", Toast.LENGTH_SHORT, true).show();
                }
            });
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void Init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {

            view = inflater.inflate(R.layout.activity_akaryakit, container, false);
            txtInputSehirKodu = view.findViewById(R.id.txtInputSehirKodu);
            rvAkaryakitBilgileri = view.findViewById(R.id.rvAkaryakitBilgileri);
            btnTamam = view.findViewById(R.id.btnTamam);
            akaryakitManager = new AkaryakitManager(Utils.getContext());
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void InternetSeviyesiKontrol() {
        try {
            boolean isNetworkFast = Connectivity.isConnectedFast(Utils.getContext());
            if (isNetworkFast) {
                AkaryakitBilgileriniGetirme();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
                builder.setTitle("Oops! İnternet Bulunamadı!");
                builder.setIcon(R.drawable.network);

                builder.setPositiveButton("Tekrar Dene", (dialog, which) -> {
                    InternetSeviyesiKontrol();
                    dialog.dismiss();
                });
                builder.setNeutralButton("Çıkış", (dialog, which) -> {
                    Utils.StartActiviy(Utils.getContext(), MainFragment.class);
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

    private void AkaryakitBilgileriniGetirme() {
        try {
            new AkaryakitGuncelBilgileriGetir().execute();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public class AkaryakitGuncelBilgileriGetir extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String htmlAkarYakitGuncelBilgiler;
        String textAkarYakitGuncelBilgiler;
        String htmlBaslik;
        String textBaslik;
        ArrayList<String> listAkaryakitBilgileri = new ArrayList<>();
        ArrayList<String> listBaslik = new ArrayList<>();


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
                Document doc = Jsoup.connect(Utils.AKARYAKIT + sehirKodu).get();
                Element table = doc.select("table").get(0);
                Elements rows = doc.select("tr");
                Elements baslik = doc.select("th");

                akaryakitManager.AkaryakitBasliklariniGetirAsync(baslik, htmlBaslik, textBaslik, listBaslik);

                akaryakitManager.AkaryakitBilgileriniGetirAsync(rows, htmlAkarYakitGuncelBilgiler, textAkarYakitGuncelBilgiler, listAkaryakitBilgileri);

            } catch (IOException e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            try {
                akaryakitManager.OnPostExecuteAkaryakit(listAkaryakitBilgileri, dialog, adapter, rvAkaryakitBilgileri);
            } catch (Exception e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
        }
    }
}
