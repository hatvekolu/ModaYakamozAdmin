package com.hedefsvr.modayakamozadmin.siparis;

import com.hedefsvr.modayakamozadmin.urunler.BedenObject;
import com.hedefsvr.modayakamozadmin.urunler.ResimObject;

import java.util.List;

/**
 * Created by Lenovo- on 27.11.2017.
 */

public class SatisUrunObject {
    private double tl;
    private int kategori;
    private String marka,id,yer;
    private List<ResimObject>resimObject;
    private BedenObject bedenObject;
    public SatisUrunObject(double tl,int kategori,String marka,String id,String yer, List<ResimObject> resimObject, BedenObject bedenObject){
        this.tl=tl;
        this.kategori=kategori;
        this.marka=marka;
        this.yer=yer;
        this.id=id;
        this.resimObject=resimObject;
        this.bedenObject=bedenObject;
    }
    public double getTl(){return tl;}
    public int getKategori(){return kategori;}
    public String getMarka(){return  marka;}
    public String getYer(){return yer;}
    public String getId(){return  id;}
    public List<ResimObject>getResimObject(){return resimObject;}
    public BedenObject getBedenObject(){return bedenObject;}

}
