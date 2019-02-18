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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.burak.doviz.R;
import com.example.burak.doviz.controller.KrediManager;
import com.example.burak.doviz.entitiy.KrediEntity;
import com.example.burak.doviz.helper.Connectivity;
import com.example.burak.doviz.helper.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class KrediFragment extends Fragment {

    Spinner spnAy;
    Button btnTamam;
    KrediManager krediManager;
    RadioButton rbKonut, rbIhtiyac, rbTasit;
    KrediEntity krediEntity;
    EditText edtKrediTutari;
    String inputURL;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Init(inflater, container, savedInstanceState);
        Events();
        return view;
    }

    public void Init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.activity_kredi, container, false);
            krediManager = new KrediManager(Utils.getContext());
            spnAy = view.findViewById(R.id.spnAy);
            btnTamam = view.findViewById(R.id.btnTamam);
            rbKonut = view.findViewById(R.id.rbKonut);
            rbIhtiyac = view.findViewById(R.id.rbIhtiyac);
            rbTasit = view.findViewById(R.id.rbTasit);
            edtKrediTutari = view.findViewById(R.id.edtKrediTutari);

            KrediAyBilgileriniDoldur();
            krediEntity = new KrediEntity();

            krediEntity.setRbIhtiyac(rbIhtiyac);
            krediEntity.setRbKonut(rbKonut);
            krediEntity.setRbTasit(rbTasit);

        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void KrediAyBilgileriniDoldur() {
        try {
            String[] arraySpinner = new String[]{
                    "3", "4", "5", "6", "9", "12", "18",
                    "24", "30", "36"
            };
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Utils.getContext(),
                    android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnAy.setAdapter(adapter);
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void Events() {
        try {
            btnTamam.setOnClickListener(v -> {
                String secilenAyBilgisi = spnAy.getSelectedItem().toString();
                String secilenKrediTuru = krediManager.KrediHesapla(krediEntity);
                String krediTutari = edtKrediTutari.getText().toString();

                if (!Utils.IsNullOrEmpty(secilenAyBilgisi) && !Utils.IsNullOrEmpty(secilenKrediTuru) && !Utils.IsNullOrEmpty(krediTutari)) {
                    inputURL = secilenKrediTuru + "?term=" + secilenAyBilgisi + "&amount=" + krediTutari;
                    InternetSeviyesiKontrol();
                } else {
                    Toasty.warning(Utils.getContext(), "Lütfen İlgili Alanları Doldurunuz.", Toast.LENGTH_SHORT, true).show();

                }
            });
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void InternetSeviyesiKontrol() {

        try {
            boolean isNetworkFast = Connectivity.isConnectedFast(Utils.getContext());
            if (isNetworkFast) {
                KrediBilgileriniGetir();
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

    private void KrediBilgileriniGetir() {
        try {
            new KrediBilgileriniAsync().execute();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public class KrediBilgileriniAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String htmlKredi;
        String txtKredi;
        ArrayList<String> listKredi = new ArrayList<>();

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
                Document doc = Jsoup.connect(Utils.KREDI_URL + inputURL).get();
                Elements elements = doc.select("div[class = table]");
                Elements columns = elements.select("tr");

                for (int i = 1; i < columns.size(); i++) {
                    Element rows = columns.get(i);
                    htmlKredi = rows.html();
                    txtKredi = Jsoup.parse(htmlKredi).text();
                    listKredi.add(txtKredi);
                }
            } catch (IOException e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid)//Arka plan işlemleri bittikten sonra başlık yazdırılır.
        {
            Toasty.success(Utils.getContext(), "Bilgiler Getirildi", Toast.LENGTH_SHORT, true).show();
            dialog.dismiss();
        }
    }
}
