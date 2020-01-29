package com.hedefsvr.modayakamozadmin.analiz;

import com.hedefsvr.modayakamozadmin.urunler.BedenObject;
import com.hedefsvr.modayakamozadmin.urunler.ResimObject;

import java.util.List;

/**
 * Created by Lenovo- on 19.12.2018.
 */

public class EnCokUrunObject {
    private int id;
    private int kod,toplam;
    private List<ResimObject> resimObject;
    private List<BedenObject> bedenObjects;

    public EnCokUrunObject(int id,int kod,int toplam,List<ResimObject> resimObject,List<BedenObject> bedenObjects){
        this.id=id;
        this.kod=kod;
        this.toplam=toplam;
        this.resimObject=resimObject;
        this.bedenObjects=bedenObjects;
    }
    public int getId(){return id;}
    public int getKod(){return kod;}
    public int getToplam(){return toplam;}
    public List<ResimObject> getResimObject(){return resimObject;}
    public List<BedenObject> getBedenObjects(){return bedenObjects;}
}
