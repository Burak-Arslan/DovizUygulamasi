package com.example.burak.doviz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.burak.doviz.R;

public class CryptoViewHolder extends RecyclerView.ViewHolder {
    TextView txtCryptoTuru;
    TextView txtSaat;
    TextView txtDolarSatis;
    TextView txtTurkLirasiSatis;
    TextView txtHacim;
    TextView txtDegisim;

    public CryptoViewHolder(View itemView) {
        super(itemView);
        try {
            txtCryptoTuru = itemView.findViewById(R.id.txtCryptoTuru);
            txtSaat = itemView.findViewById(R.id.txtSaat);
            txtDolarSatis = itemView.findViewById(R.id.txtDolarSatis);
            txtTurkLirasiSatis = itemView.findViewById(R.id.txtTurkLirasiSatis);
            txtHacim = itemView.findViewById(R.id.txtHacim);
            txtDegisim = itemView.findViewById(R.id.txtDegisim);
        } catch (Exception ex) {
        }
    }

    public void SetData(String sehirBilgileri) {
        String[] splitAkaryakitBilgileri = sehirBilgileri.split(" ");
        String cryptoTamAciklama = splitAkaryakitBilgileri[0];
        String cryptoKisaltma = splitAkaryakitBilgileri[1];
        String saat = splitAkaryakitBilgileri[2];
        String dolarSatis = splitAkaryakitBilgileri[3];
        String turkLirasiSatis = splitAkaryakitBilgileri[4];
        String alis = splitAkaryakitBilgileri[6];
        String degisim = splitAkaryakitBilgileri[7];


        txtCryptoTuru.setText(cryptoTamAciklama + "\n\n" + cryptoKisaltma);
        txtSaat.setText(saat);
        txtDolarSatis.setText(dolarSatis);
        txtTurkLirasiSatis.setText("₺ " +turkLirasiSatis);
        txtHacim.setText(alis);
        txtDegisim.setText(degisim);
    }
}
