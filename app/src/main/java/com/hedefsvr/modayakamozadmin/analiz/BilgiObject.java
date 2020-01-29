package com.hedefsvr.modayakamozadmin.analiz;

import com.hedefsvr.modayakamozadmin.genel.AylarObject;

import java.util.List;

/**
 * Created by Lenovo- on 19.12.2018.
 */

public class BilgiObject {
    private String komisyoncu,ulasan,donen,yolda,bekleyen,hakedis,kesilen_tutar,cekilen_tutar,yuzde,bakiye;
    private List<AylarObject> aylarObjects;
    public BilgiObject(String komisyoncu,String ulasan,String donen,String yolda,String bekleyen,String hakedis
            ,String kesilen_tutar,String cekilen_tutar,String yuzde,String bakiye,List<AylarObject>aylarObjects){
        this.komisyoncu=komisyoncu;
        this.ulasan=ulasan;
        this.donen=donen;
        this.yolda=yolda;
        this.bekleyen=bekleyen;
        this.hakedis=hakedis;
        this.kesilen_tutar=kesilen_tutar;
        this.cekilen_tutar=cekilen_tutar;
        this.yuzde=yuzde;
        this.bakiye=bakiye;
        this.aylarObjects=aylarObjects;
    }


    public  String getKomisyoncu(){return  komisyoncu;}
    public  String getUlasan(){return  ulasan;}
    public  String getDonen(){return  donen;}
    public  String getYolda(){return  yolda;}
    public  String getBekleyen(){return  bekleyen;}
    public  String getHakedis(){return  hakedis;}
    public  String getKesilen_tutar(){return  kesilen_tutar;}
    public  String getCekilen_tutar(){return  cekilen_tutar;}
    public  String getYuzde(){return  yuzde;}
    public  String getBakiye(){return  bakiye;}
    public List<AylarObject> getAylarObjects(){return aylarObjects;}




}
