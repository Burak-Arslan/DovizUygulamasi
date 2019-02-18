package com.example.burak.doviz.ViewHolder;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.burak.doviz.R;
import com.example.burak.doviz.helper.Utils;

import es.dmoral.toasty.Toasty;

public class MainViewHolder extends RecyclerView.ViewHolder {
    TextView txtBilgi;
    TextView txtDegeri;
    TextView txtYuzde;
    ImageView imgTip;

    public MainViewHolder(View itemView) {
        super(itemView);
        try {
            txtBilgi = itemView.findViewById(R.id.txtAdi);
            txtDegeri = itemView.findViewById(R.id.txtDegeri);
            txtYuzde = itemView.findViewById(R.id.txtYuzde);
            imgTip = itemView.findViewById(R.id.imgTip);
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void SetData(String sehirBilgileri) {
        try {
            // Kodlar düzenlenecek
            String[] splitEnlemBoylam = sehirBilgileri.split(" ");
            String deger = splitEnlemBoylam[0];
            String deger2 = splitEnlemBoylam[1];
            String deger3 = splitEnlemBoylam[2];
            String deger4 = splitEnlemBoylam[3];
            String deger5 = "";
            if (deger.contains("GRAM")) {
                deger5 = splitEnlemBoylam[4];
                txtBilgi.setText(deger + " " + deger2);
                txtDegeri.setText(deger3);
                txtYuzde.setText(deger4 + " " + deger5);
                if (deger5.contains("-")) {
                    imgTip.setImageDrawable(Utils.getContext().getDrawable(R.drawable.down32));
                } else {
                    imgTip.setImageDrawable(Utils.getContext().getDrawable(R.drawable.up23));
                }
            } else {
                txtBilgi.setText(deger);
                txtDegeri.setText(deger2);
                txtYuzde.setText(deger3 + " " + deger4);
                if (deger4.contains("-")) {
                    imgTip.setImageDrawable(Utils.getContext().getDrawable(R.drawable.down32));
                } else {
                    imgTip.setImageDrawable(Utils.getContext().getDrawable(R.drawable.up23));
                }
            }


        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }
}
