package com.hedefsvr.modayakamozadmin.genel;

/**
 * Created by Lenovo- on 25.7.2018.
 */

public class AylarObject {
    private int yil;
    private String ay;
    private int adet;
    private double tutar;

    public AylarObject(int yil,String ay,int adet,double tutar){
        this.yil=yil;
        this.ay=ay;
        this.adet=adet;
        this.tutar=tutar;

    }
    public int getYil(){return yil;}
    public String getAy(){return ay;}
    public int getAdet(){return  adet;}
    public double getTutar(){return tutar;}



}
