package com.example.burak.doviz.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.burak.doviz.entitiy.KrediEntity;
import com.example.burak.doviz.helper.Utils;

import es.dmoral.toasty.Toasty;

public class KrediManager {
    Context context;

    public KrediManager(Context context) {
        this.context = context;
    }

    public String KrediHesapla(KrediEntity krediEntity) {
        String result = "";
        try {
            if (krediEntity.getRbIhtiyac().isChecked()) {
                result = "ihtiyac-kredisi";
            } else if (krediEntity.getRbTasit().isChecked()) {
                result = "tasit-kredisi";

            } else if (krediEntity.getRbKonut().isChecked()) {
                result = "konut-kredisi";
            }
        } catch (Exception ex) {
            result = "";
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
        return result;
    }
}
