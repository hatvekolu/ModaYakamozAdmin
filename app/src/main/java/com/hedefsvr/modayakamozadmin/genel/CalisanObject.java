package com.hedefsvr.modayakamozadmin.genel;


public class CalisanObject {
    private String id,kullanciAdi,sifre,yetki,durum,siparis,bekleyen,hazir,tarih,odeme;
    public CalisanObject(String id,String kullanciAdi,String sifre,String yetki,String durum,String siparis,String bekleyen,String hazir
            ,String tarih,String odeme){
        this.id=id;
        this.kullanciAdi=kullanciAdi;
        this.sifre=sifre;
        this.yetki=yetki;
        this.durum=durum;
        this.siparis=siparis;
        this.bekleyen=bekleyen;
        this.hazir=hazir;
        this.tarih=tarih;
        this.odeme=odeme;
    }
    public String getId(){return id;}
    public String getKullanciAdi(){return kullanciAdi;}
    public String getSifre(){return  sifre;}
    public String getYetki(){return yetki;}
    public String getDurum(){return  durum;}
    public String getSiparis(){return  siparis;}
    public String getBekleyen(){return  bekleyen;}
    public String getHazir(){return  hazir;}
    public String getTarih(){return  tarih;}
    public String getOdeme(){return  odeme;}
}
