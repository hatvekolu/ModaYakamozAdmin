package com.hedefsvr.modayakamozadmin.analiz;

/**
 * Created by Lenovo- on 31.12.2018.
 */

public class AylaraGoreObject {
    private String yil,ay;
    private int bekleyen,yolda,donen,ulasan;
    double yuzde;

    public AylaraGoreObject(String yil,String ay,int bekleyen,int yolda,int donen,int ulasan,double yuzde){
        this.yil=yil;
        this.ay=ay;
        this.bekleyen=bekleyen;
        this.yolda=yolda;
        this.donen=donen;
        this.ulasan=ulasan;
        this.yuzde=yuzde;

    }
    public String getYil(){return yil;}
    public String getAy(){return ay;}
    public int getBekleyen(){return  bekleyen;}
    public int getYolda(){return  yolda;}
    public int getDonen(){return  donen;}
    public int getUlasan(){return  ulasan;}
    public double getYuzde(){return yuzde;}
}
