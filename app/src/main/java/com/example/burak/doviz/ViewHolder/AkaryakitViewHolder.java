package com.example.burak.doviz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.burak.doviz.R;

public class AkaryakitViewHolder extends RecyclerView.ViewHolder {
    TextView txtIlce;
    TextView txtKursunsuzBenzin;
    TextView txtGazYagi;
    TextView txtMotorin;
    TextView txtKaloriferYagi;
    TextView txtFuelOil;
    TextView txtYKFuelOil;

    public AkaryakitViewHolder(View itemView) {
        super(itemView);
        try {

            txtIlce = itemView.findViewById(R.id.txtIlce);
            txtKursunsuzBenzin = itemView.findViewById(R.id.txtKursunuzBenzin);
            txtGazYagi = itemView.findViewById(R.id.txtGazYagi);
            txtMotorin = itemView.findViewById(R.id.txtMotorin);
            txtKaloriferYagi = itemView.findViewById(R.id.txtKaloriferYagi);
            txtFuelOil = itemView.findViewById(R.id.txtFuelOil);
            txtYKFuelOil = itemView.findViewById(R.id.txtYKFuelOil);
        } catch (Exception ex) {
        }
    }

    public void SetData(String sehirBilgileri) {
        String[] splitAkaryakitBilgileri = sehirBilgileri.split(" ");
        String ilce = splitAkaryakitBilgileri[0];
        String kursunsuzBenzin = splitAkaryakitBilgileri[1];
        String gazYagi = splitAkaryakitBilgileri[2];
        String motorin = splitAkaryakitBilgileri[4];
        String kaloriferYagi = splitAkaryakitBilgileri[5];
        String fuelOil = splitAkaryakitBilgileri[6];
        String ykFuelOil = splitAkaryakitBilgileri[7];

        txtIlce.setText(ilce);
        txtKursunsuzBenzin.setText(kursunsuzBenzin);
        txtGazYagi.setText(gazYagi);
        txtMotorin.setText(motorin);
        txtKaloriferYagi.setText(kaloriferYagi);
        txtFuelOil.setText(fuelOil);
        txtYKFuelOil.setText(ykFuelOil);
    }
}
