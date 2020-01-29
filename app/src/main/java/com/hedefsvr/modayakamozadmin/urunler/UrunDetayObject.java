package com.hedefsvr.modayakamozadmin.urunler;

import java.util.List;


public class UrunDetayObject {
    private int id;
    private int kod;
    private int onSiparis;
    private String aciklama;
    private String sutun;
    private String raf;
    private String firma;
    private String calisan;
    private List<ResimObject> resimObject;
    private List<BedenObject> bedenObject;
    private List<BedenObject> siparisObject;

    public UrunDetayObject(int id,int kod,int onSiparis, String aciklama,String sutun,String raf, String firma, String calisan,List<ResimObject> resimObject,
                           List<BedenObject>bedenObject, List<BedenObject>siparisObject){
        this.id=id;
        this.kod=kod;
        this.onSiparis=onSiparis;
        this.aciklama=aciklama;
        this.sutun=sutun;
        this.raf=raf;
        this.firma=firma;
        this.calisan=calisan;
        this.resimObject=resimObject;
        this.bedenObject=bedenObject;
        this.siparisObject=siparisObject;
    }
    public int getId(){return id;}
    public int getKod(){return kod;}
    public int getOnSiparis(){return onSiparis;}
    public String getAciklama(){return  aciklama;}
    public String getSutun(){return  sutun;}
    public String getRaf(){return  raf;}
    public String getFirma(){return  firma;}
    public String getCalisan(){return  calisan;}
    public List<ResimObject> getResimObject(){return resimObject;}
    public List<BedenObject> getBedenObject(){return bedenObject;}
    public List<BedenObject> getSiparisObject(){return siparisObject;}
}
