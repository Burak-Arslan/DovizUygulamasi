package com.example.burak.doviz.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.burak.doviz.adapter.MainAdapter;
import com.example.burak.doviz.controller.MainManager;
import com.example.burak.doviz.helper.Connectivity;
import com.example.burak.doviz.helper.Utils;
import com.example.burak.doviz.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    MainManager mainManager;
    RecyclerView rvMain;
    MainAdapter mainAdapter;
    SwipeRefreshLayout refreshLayout;
    Timer timing;
    final static long scheduleTime = 2000;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        Events();
    }

    private void InternetSeviyesiKontrol(boolean showDialog) {

        try {
            boolean isNetworkFast = Connectivity.isConnectedFast(Utils.getContext());
            if (isNetworkFast) {
                SliderBilgileriGetirme(showDialog);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Utils.getContext());
                builder.setTitle("Oops! İnternet Bulunamadı!");
                builder.setIcon(R.drawable.network);

                builder.setPositiveButton("Tekrar Dene", (dialog, which) -> {
                    InternetSeviyesiKontrol(true);
                    dialog.dismiss();
                });
                builder.setNeutralButton("Çıkış", (dialog, which) -> {
                    finish();
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

    public void Init() {
        try {
            Utils.setContext(context);
            mainManager = new MainManager(Utils.getContext());
            rvMain = findViewById(R.id.rvMain);
            refreshLayout = findViewById(R.id.swipeContainer);
            navigationView = findViewById(R.id.bottom_navigation);

            InternetSeviyesiKontrol(true);
            RefreshMainList();

        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void RefreshMainList() {
        try {
            timing = new Timer();

            timing.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        try {
                            InternetSeviyesiKontrol(false);
                        } catch (Exception ex) {
                            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    });
                }
            }, 0, scheduleTime);
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }


    public void Events() {
        try {

            refreshLayout.setOnClickListener(v -> {

            });
            refreshLayout.setOnRefreshListener(() -> {
                try {
                    refreshLayout.setRefreshing(false);
                    SliderBilgileriGetirme(true);
                } catch (Exception ex) {
                    Toasty.success(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            });

            navigationView.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.actionCuzdan:
                        Utils.StartActiviy(Utils.getContext(), MainActivity.class);
                        return true;
                    case R.id.actionPiyasalar:
                        Utils.StartActiviy(Utils.getContext(), MainFragment.class);
                        return true;
                    case R.id.actionCalculator:
                        return true;
                }
                return false;

            });
        } catch (Exception ex) {
            Toasty.success(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    private void SliderBilgileriGetirme(boolean showDialog) {
        try {
            new Baslik(showDialog).execute();
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public class Baslik extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String htmlAkarYakitGuncelBilgiler;
        String textAkarYakitGuncelBilgiler;
        boolean showDialog;
        ArrayList<String> listMainBilgileri = new ArrayList<>();

        public Baslik(boolean showDialog) {
            this.showDialog = showDialog;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Utils.getContext(), R.style.MyAlertDialogStyle);
            if (showDialog) {
                dialog.setMessage("Veriler getiriliyor");
                dialog.setIndeterminate(false);
                dialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {//Arka plan işlemleri gerçekleştirilir.
            try {
                Document doc = Jsoup.connect(Utils.KRIPTO_URL).get();
                Element table = doc.select("div[class = header-doviz]").get(0);
                Elements rows = table.select("ul");
                Elements baslik = rows.select("li");

                mainManager.MainBilgileriGetirAsync(baslik, textAkarYakitGuncelBilgiler, listMainBilgileri);

            } catch (IOException e) {
                Toasty.error(Utils.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            mainManager.OnPostExecuteMain(listMainBilgileri, dialog, mainAdapter, rvMain);
            if (showDialog)
                Toasty.success(Utils.getContext(), "Bilgiler Getirildi", Toast.LENGTH_SHORT, true).show();
            dialog.dismiss();
            dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
