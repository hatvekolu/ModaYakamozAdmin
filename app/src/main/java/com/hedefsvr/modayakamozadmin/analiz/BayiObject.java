package com.hedefsvr.modayakamozadmin.analiz;

/**
 * Created by Lenovo- on 18.12.2018.
 */

public class BayiObject {

    private int id;
    private String ad,kullanciAdi;


    public BayiObject(int id,String ad,String kullanciAdi){
        this.id=id;

        this.ad=ad;
        this.kullanciAdi=kullanciAdi;


    }
    public int getId(){return id;}
    public String getAd(){return ad;}
    public String getKullanciAdi(){return kullanciAdi;}





}