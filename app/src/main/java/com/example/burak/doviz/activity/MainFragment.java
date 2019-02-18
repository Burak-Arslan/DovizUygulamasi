package com.example.burak.doviz.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.example.burak.doviz.R;
import com.example.burak.doviz.adapter.TabLayoutAdapter;
import com.example.burak.doviz.helper.Utils;

import es.dmoral.toasty.Toasty;

public class MainFragment extends AppCompatActivity {

    private TabLayoutAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    Toolbar tabToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment);
        Init();
//        InternetVarMi();
    }

    private void InternetVarMi() {
        try {
            if (getIntent().getBooleanExtra("finish", false)) {
                finish();
            }
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void Init() {
        try {
            tabToolbar = findViewById(R.id.tabtoolbar);
            setSupportActionBar(tabToolbar);
            mSectionsPagerAdapter = new TabLayoutAdapter(getSupportFragmentManager());

            mViewPager = findViewById(R.id.container);
            tabLayout = findViewById(R.id.tabs);

            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setPageTransformer(true, new ScaleInOutTransformer());
            tabLayout.setupWithViewPager(mViewPager);
        } catch (Exception ex) {
            Toasty.error(Utils.getContext(), ex.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }
}
