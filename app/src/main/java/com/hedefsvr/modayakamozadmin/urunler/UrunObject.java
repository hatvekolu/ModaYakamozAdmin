package com.hedefsvr.modayakamozadmin.urunler;

import java.util.List;

/**
 * Created by Lenovo- on 26.7.2018.
 */

public class UrunObject {
    private int id;
    private int kod;
    private int onSiparis;
    private String aciklama;
    private List<ResimObject> resimObject;
    private List<BedenObject> bedenObject;

    public UrunObject(int id,int kod,int onSiparis, String aciklama,List<ResimObject> resimObject,
                      List<BedenObject>bedenObject){
        this.id=id;
        this.kod=kod;
        this.onSiparis=onSiparis;
        this.aciklama=aciklama;
        this.resimObject=resimObject;
        this.bedenObject=bedenObject;
    }
    public int getId(){return id;}
    public int getKod(){return kod;}
    public int getOnSiparis(){return onSiparis;}
    public String getAciklama(){return  aciklama;}
    public List<ResimObject> getResimObject(){return resimObject;}
    public List<BedenObject> getBedenObject(){return bedenObject;}


}