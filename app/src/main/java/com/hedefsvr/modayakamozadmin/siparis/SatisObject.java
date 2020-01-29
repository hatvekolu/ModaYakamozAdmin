package com.hedefsvr.modayakamozadmin.siparis;

import java.util.List;

/**
 * Created by Lenovo- on 27.11.2017.
 */

public class SatisObject {
    private int id,durum,hazir;
    double hakedis,tutar,kesilen_tutar;
    private String bayi,ad,soyad,tel,il,ilce,adres,aciklama,kargo_no,kargo_firma,tarih,odeme_turu,url,bayi_tel,calisan;
    private List<SatisUrunObject> satisUrunObject;
    public SatisObject(int id,int hazir, double tutar, double kesilen_tutar, int durum, double hakedis, String bayi, String ad, String soyad, String tel,
                       String il, String ilce, String adres, String aciklama, String kargo_no, String kargo_firma, String tarih, String odeme_turu, String url, String bayi_tel,String calisan, List<SatisUrunObject> satisUrunObject){

        this.id=id;
        this.tutar=tutar;
        this.hazir=hazir;
        this.kesilen_tutar=kesilen_tutar;
        this.durum=durum;
        this.hakedis=hakedis;
        this.bayi=bayi;
        this.ad=ad;
        this.soyad=soyad;
        this.tel=tel;
        this.il=il;
        this.ilce=ilce;
        this.adres=adres;
        this.aciklama=aciklama;
        this.kargo_no=kargo_no;
        this.kargo_firma=kargo_firma;
        this.tarih=tarih;
        this.odeme_turu=odeme_turu;
        this.url=url;
        this.bayi_tel=bayi_tel;
        this.calisan=calisan;
        this.satisUrunObject=satisUrunObject;

    }
    public int getId(){return id;}
    public int getHazir(){return hazir;}
    public void setHazir(int hazir){this.hazir = hazir;}
    public double getTutar(){return tutar;}
    public double getKesilen_tutar(){return kesilen_tutar;}
    public int getDurum(){return durum;}
    public double getHakedis(){return hakedis;}
    public String getBayi(){return bayi;}
    public String getAd(){return ad;}
    public String getSoyad(){return soyad;}
    public String getTel(){return tel;}
    public String getIl(){return il;}
    public String getIlce(){return ilce;}
    public String getAdres(){return adres;}
    public String getAciklama(){return aciklama;}
    public String getKargo_no(){return kargo_no;}
    public String getKargo_firma(){return kargo_firma;}
    public String getTarih(){return tarih;}
    public String getOdeme_turu(){return odeme_turu;}
    public String getUrl(){return url;}
    public String getBayi_tel(){return bayi_tel;}
    public String getCalisan(){return calisan;}
    public List<SatisUrunObject> getSatisUrunObject(){return satisUrunObject;}


}
