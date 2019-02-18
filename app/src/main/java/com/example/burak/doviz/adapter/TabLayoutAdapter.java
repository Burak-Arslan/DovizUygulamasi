package com.example.burak.doviz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.burak.doviz.R;
import com.example.burak.doviz.fragment.AkaryakitFragment;
import com.example.burak.doviz.fragment.BorsaFragment;
import com.example.burak.doviz.fragment.CaprazKurFragment;
import com.example.burak.doviz.fragment.CryptoFragment;
import com.example.burak.doviz.fragment.DovizFragment;
import com.example.burak.doviz.fragment.EmtiaFragment;
import com.example.burak.doviz.fragment.GoldFragment;
import com.example.burak.doviz.fragment.KrediFragment;
import com.example.burak.doviz.helper.Utils;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 8;
    Fragment fragment;

    public TabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = new AkaryakitFragment();
                break;
            case 1:
                fragment = new CaprazKurFragment();
                break;
            case 2:
                fragment = new DovizFragment();
                break;
            case 3:
                fragment = new BorsaFragment();
                break;
            case 4:
                fragment = new EmtiaFragment();
                break;
            case 5:
                fragment = new GoldFragment();
                break;
            case 6:
                fragment = new KrediFragment();
                break;
            case 7:
                fragment = new CryptoFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String result = null;
        switch (position) {
            case 0:
                result = Utils.getContext().getResources().getString(R.string.akaryakitBilgileri);
                break;
            case 1:
                result = Utils.getContext().getResources().getString(R.string.caprazKurBilgileri);
                break;
            case 2:
                result = Utils.getContext().getResources().getString(R.string.dovizBilgileri);
                break;
            case 3:
                result = Utils.getContext().getResources().getString(R.string.borsaBilgileri);
                break;
            case 4:
                result = Utils.getContext().getResources().getString(R.string.emtiaBilgileri);
                break;
            case 5:
                result = Utils.getContext().getResources().getString(R.string.altinBilgileri);
                break;
            case 6:
                result = Utils.getContext().getResources().getString(R.string.krediBilgileri);
                break;
            case 7:
                result = Utils.getContext().getResources().getString(R.string.ctyptoBilgileri);
                break;
        }

        return result;
    }
}
