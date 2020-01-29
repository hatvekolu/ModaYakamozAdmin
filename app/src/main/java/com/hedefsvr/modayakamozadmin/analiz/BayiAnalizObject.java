package com.hedefsvr.modayakamozadmin.analiz;

/**
 * Created by Lenovo- on 23.7.2019.
 */

public class BayiAnalizObject {

    private String komisyoncu,id,toplamSatis;
    public BayiAnalizObject(String komisyoncu,String id,String toplamSatis){
        this.komisyoncu=komisyoncu;
        this.id=id;
        this.toplamSatis=toplamSatis;

    }


    public  String getKomisyoncu(){return  komisyoncu;}
    public  String getId(){return  id;}
    public  String getToplamSatis(){return  toplamSatis;}

}
