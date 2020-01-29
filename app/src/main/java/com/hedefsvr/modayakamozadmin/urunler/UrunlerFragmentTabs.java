package com.hedefsvr.modayakamozadmin.urunler;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;
import com.hedefsvr.modayakamozadmin.siparis.SiparisiBekleyenUrunler;


/**
 * A simple {@link Fragment} subclass.
 */
public class UrunlerFragmentTabs extends Fragment {


    private FragmentTabHost mTabHost;
    Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_urunler_fragment_tabs, container, false);
        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("ELBİSE").setContent(intent),
                UrunlerFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("ÇANTA").setContent(intent),
                CantaFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator("TESETTÜR").setContent(intent),
                TesetturFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("AYAKKABI").setContent(intent),
                AyakkabiFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("SİPARİŞ ÜRÜN").setContent(intent),
                SiparisiBekleyenUrunler.class, null);


        TextView x3 = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x3.setTextSize(12);
        TextView x4 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        x4.setTextSize(12);
        TextView x5 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        x5.setTextSize(12);
        TextView x6 = (TextView) mTabHost.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
        x6.setTextSize(12);
        TextView x7 = (TextView) mTabHost.getTabWidget().getChildAt(4).findViewById(android.R.id.title);
        x7.setTextSize(12);
        return view;
    }

}
