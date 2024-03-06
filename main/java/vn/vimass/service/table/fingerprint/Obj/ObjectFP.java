package vn.vimass.service.table.fingerprint.obj;

import com.google.gson.Gson;

public class ObjectFP {
    public String idfp;
    public String idVid;
    public int positionFP;

    public String idLoiRaVao;

    public String getIdfp() {
        return idfp;
    }

    public void setIdfp(String idfp) {
        this.idfp = idfp;
    }

    public String getIdVid() {
        return idVid;
    }

    public void setIdVid(String idVid) {
        this.idVid = idVid;
    }

    public int getPositionFP() {
        return positionFP;
    }

    public void setPositionFP(String positionFP) {
        this.positionFP = Integer.parseInt(positionFP);
    }

    public String getIdLoiRaVao() {
        return idLoiRaVao;
    }

    public void setIdLoiRaVao(String idLoiRaVao) {
        this.idLoiRaVao = idLoiRaVao;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
