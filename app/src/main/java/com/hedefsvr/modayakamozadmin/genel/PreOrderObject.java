package com.hedefsvr.modayakamozadmin.genel;

import com.hedefsvr.modayakamozadmin.urunler.BedenObject;
import com.hedefsvr.modayakamozadmin.urunler.ResimObject;

import java.util.List;

/**
 * Created by Lenovo- on 19.11.2019.
 */

public class PreOrderObject {

    private String id,urunKodu,cikisTL,onsiparis,marka,preOrder,seri;
    private List<ResimObject> resimObject;
    private List<BedenObject> bedenObject;
    public PreOrderObject(String id,String urunKodu,String cikisTL,String onsiparis,String marka,String preOrder,String seri, List<ResimObject> resimObject, List<BedenObject> bedenObject){
        this.id=id;
        this.urunKodu=urunKodu;
        this.cikisTL=cikisTL;
        this.onsiparis=onsiparis;
        this.marka=marka;
        this.preOrder=preOrder;
        this.seri=seri;
        this.resimObject=resimObject;
        this.bedenObject=bedenObject;
    }
    public String getId(){return id;}
    public String getUrunKodu(){return urunKodu;}
    public String getCikisTL(){return  cikisTL;}
    public String getOnsiparis(){return onsiparis;}
    public String getMarka(){return  marka;}
    public String getPreOrder(){return  preOrder;}
    public String getSeri(){return  seri;}
    public List<ResimObject>getResimObject(){return resimObject;}
    public List<BedenObject> getBedenObject(){return bedenObject;}
}
