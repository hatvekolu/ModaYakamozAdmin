package com.hedefsvr.modayakamozadmin.helper;

/**
 * Created by Lenovo- on 20.11.2019.
 */

public class DurumObject {
    private String id,yetki,kullanciAdi,sifre;

    public DurumObject(String id,String yetki,String kullanciAdi,String sifre){
        this.id=id;
        this.yetki=yetki;
        this.kullanciAdi=kullanciAdi;
        this.sifre=sifre;
    }
    public String getId(){return id;}
    public String getYetki(){return yetki;}
    public String getKullanciAdi(){return kullanciAdi;}
    public String getSifre(){return sifre;}
}
