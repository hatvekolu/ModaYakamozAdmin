package com.hedefsvr.modayakamozadmin.analiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hedefsvr.modayakamozadmin.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnalizFragment extends Fragment {

    private FragmentTabHost mTabHost;
    Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_analiz, container, false);



        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Bayi Analiz").setContent(intent),
                TemsilciAnalizFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Ürün Analiz").setContent(intent),
                UrunAnalizFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("İllere Göre").setContent(intent),
                IllereGore.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Ulaşma Yüzde").setContent(intent),
                AylaraGoreSatisFragment.class, null);


        TextView x3 = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x3.setTextSize(12);
        TextView x4 = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        x4.setTextSize(12);
        TextView x5 = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        x5.setTextSize(12);
        TextView x6 = (TextView) mTabHost.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
        x6.setTextSize(12);
        return view;
    }

}
