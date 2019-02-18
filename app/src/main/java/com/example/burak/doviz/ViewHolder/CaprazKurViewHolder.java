package com.example.burak.doviz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.burak.doviz.R;

public class CaprazKurViewHolder extends RecyclerView.ViewHolder {
    TextView txtBilgi;
    TextView txtSaat;
    TextView txtYuzde;
    TextView txtAlis;
    TextView txtSatis;

    public CaprazKurViewHolder(View itemView) {
        super(itemView);
        try {

            txtBilgi = itemView.findViewById(R.id.txtBilgi);
            txtSaat = itemView.findViewById(R.id.txtSaat);
            txtYuzde = itemView.findViewById(R.id.txtYuzde);
            txtAlis = itemView.findViewById(R.id.txtAlis);
            txtSatis = itemView.findViewById(R.id.txtSatis);
        } catch (Exception ex) {
        }
    }

    public void SetData(String sehirBilgileri) {
        String[] splitAkaryakitBilgileri = sehirBilgileri.split(" ");
        String bilgiLeft = splitAkaryakitBilgileri[0];
        String bilgiSlash = splitAkaryakitBilgileri[1];
        String bilgiRight = splitAkaryakitBilgileri[2];
        String saat = splitAkaryakitBilgileri[3];
        String yuzde = splitAkaryakitBilgileri[4];
        String yuzdeDeger = splitAkaryakitBilgileri[5];
        String alis = splitAkaryakitBilgileri[6];
        String satis = splitAkaryakitBilgileri[7];

        txtBilgi.setText(bilgiLeft + " " + bilgiSlash + " " + bilgiRight);
        txtSaat.setText(saat);
        txtYuzde.setText(yuzde + " " + yuzdeDeger);
        txtAlis.setText(alis);
        txtSatis.setText(satis);
    }
}
