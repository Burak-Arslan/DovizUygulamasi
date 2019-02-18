package com.example.burak.doviz.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.burak.doviz.R;

import es.dmoral.toasty.Toasty;

public class Utils {

    public static String KRIPTO_URL = "https://www.doviz.com/kripto-paralar";
    public static String ALTIN_URL = "https://canlidoviz.com/altin-fiyatlari/";
    public static String PARITE_URL = "https://canlidoviz.com/pariteler";
    public static String DOVIZ_URL = "https://canlidoviz.com/doviz/serbest-piyasa";
    public static String AKARYAKIT = "https://www.tppd.com.tr/tr/akaryakit-fiyatlari?id=";
    public static String BORSA_URL = "https://borsa.doviz.com/hisseler";
    public static String EMTIA_URL = "https://tr.investing.com/commodities/real-time-futures";
    public static String KREDI_URL = "https://www.doviz.com/kredi/";
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Utils.context = context;
    }

    public static void StartActiviy(Context context, Class<?> activity) {
        try {
            Intent myIntent = new Intent(context, activity);
            myIntent.putExtra("finish", true);
            Utils.getContext().startActivity(myIntent);
            ((Activity) context).overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public static boolean IsNullOrEmpty(String value) {
        boolean result = false;

        try {
            if (value == null || value.isEmpty()) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }

        return result;
    }
}
