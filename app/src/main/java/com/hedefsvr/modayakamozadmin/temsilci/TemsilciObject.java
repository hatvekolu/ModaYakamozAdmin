package com.hedefsvr.modayakamozadmin.temsilci;

/**
 * Created by Lenovo- on 27.7.2018.
 */

public class TemsilciObject {

    private int id,toplamSatis;
    private String ad,soyad,kullanciAdi,sifre,banka,iban,tel;
    private int komOrani,komEk;

    public TemsilciObject(int id,int toplamSatis,String ad,String soyad,String kullanciAdi,String sifre,String banka,String iban,String tel,int komOrani,int komEk){
        this.id=id;
        this.toplamSatis=toplamSatis;
        this.ad=ad;
        this.soyad=soyad;
        this.kullanciAdi=kullanciAdi;
        this.sifre=sifre;
        this.banka=banka;
        this.iban=iban;
        this.tel=tel;
        this.komOrani=komOrani;
        this.komEk=komEk;

    }
    public int getId(){return id;}
    public int getToplamSatis(){return toplamSatis;}
    public String getAd(){return ad;}
    public String getSoyad(){return soyad;}
    public String getKullanciAdi(){return kullanciAdi;}
    public String getSifre(){return sifre;}
    public String getBanka(){return banka;}
    public String getIban(){return iban;}
    public String getTel(){return tel;}
    public int getKomOrani(){return komOrani;}
    public int getKomEk(){return komEk;}




}
