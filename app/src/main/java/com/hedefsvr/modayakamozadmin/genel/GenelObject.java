package com.hedefsvr.modayakamozadmin.genel;

import java.util.List;

/**
 * Created by Lenovo- on 25.7.2018.
 */

public class GenelObject {
    private int toplam;
    private int bekleyen;
    private int yolda;
    private int ulasan;
    private int donen;
    private double yuzde;
    private String version;
    private int enCokSatanUrun,enCokSatis;
    private String enCokSatisYapan,resimUrl;
    private List<AylarObject> aylarObjects;


    public GenelObject(int toplam,int bekleyen, int yolda, int ulasan, int donen, double yuzde,String version,int enCokSatanUrun,int enCokSatis,
                       String enCokSatisYapan,String resimUrl,List<AylarObject>aylarObjects){
        this.toplam=toplam;
        this.bekleyen=bekleyen;
        this.yolda=yolda;
        this.ulasan=ulasan;
        this.donen=donen;
        this.yuzde=yuzde;
        this.version=version;
        this.enCokSatanUrun=enCokSatanUrun;
        this.enCokSatis=enCokSatis;
        this.enCokSatisYapan=enCokSatisYapan;
        this.resimUrl=resimUrl;
        this.aylarObjects=aylarObjects;
    }
    public int getToplam(){return toplam;}
    public int getBekleyen(){return bekleyen;}
    public int getYolda(){return  yolda;}
    public int getUlasan(){return ulasan;}
    public int getDonen(){return donen;}
    public double getYuzde(){return yuzde;}
    public String getVersion(){return version;}
    public int getEnCokSatanUrun(){return enCokSatanUrun;}
    public int getEncokSatis(){return enCokSatis;}
    public String getEnCokSatisYapan(){return enCokSatisYapan;}
    public String getResimUrl(){return resimUrl;}
    public List<AylarObject> getAylarObjects(){return aylarObjects;}
}