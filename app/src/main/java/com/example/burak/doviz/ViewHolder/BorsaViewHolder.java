package com.example.burak.doviz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.burak.doviz.R;

public class BorsaViewHolder extends RecyclerView.ViewHolder {
    TextView txtKisaltmaAd;
    TextView txtSaat;
    TextView txtSon;
    TextView txtYuzde;
    TextView txtHacimTL;
    TextView txtHacimLOT;
    TextView txtYKFuelOil;

    public BorsaViewHolder(View itemView) {
        super(itemView);
        try {

            txtKisaltmaAd = itemView.findViewById(R.id.txtKisaltmaAd);
            txtSaat = itemView.findViewById(R.id.txtSaat);
            txtSon = itemView.findViewById(R.id.txtSon);
            txtYuzde = itemView.findViewById(R.id.txtYuzde);
            txtHacimTL = itemView.findViewById(R.id.txtHacimTL);
            txtHacimLOT = itemView.findViewById(R.id.txtHacimLOT);
        } catch (Exception ex) {
        }
    }

    public void SetData(String sehirBilgileri) {
        String[] splitAkaryakitBilgileri = sehirBilgileri.split(" ");
        String kisaltma = splitAkaryakitBilgileri[0];
        String saat = splitAkaryakitBilgileri[1];
        String son = splitAkaryakitBilgileri[2];
        String yuzde = splitAkaryakitBilgileri[4];
        String hacim = splitAkaryakitBilgileri[5];
        String hacimLOT = splitAkaryakitBilgileri[6];
        String sirketAcilimi = splitAkaryakitBilgileri[7];

        txtKisaltmaAd.setText(kisaltma + "\n\n" + sirketAcilimi);
        txtSaat.setText(saat);
        txtSon.setText(son);
        txtYuzde.setText("% " + yuzde);
        txtHacimTL.setText(hacim);
        txtHacimLOT.setText(hacimLOT);
    }
}
