package com.hedefsvr.modayakamozadmin.helper;

/**
 * Created by Lenovo- on 20.11.2019.
 */
public class UserObject {

    private String region,ID,kullaniciAdi,sifre;

    public UserObject(String region,String ID,String kullaniciAdi,String sifre){
        this.region=region;
        this.ID=ID;
        this.kullaniciAdi=kullaniciAdi;
        this.sifre=sifre;
    }
    public String getRegion(){return region;}
    public String getID(){return ID;}
    public String getKullaniciAdi(){return kullaniciAdi;}
    public String getSifre(){return  sifre;}
}
