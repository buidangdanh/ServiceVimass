package vn.vimass.service.table.fingerprint.obj;

import com.google.gson.Gson;

public class ObjectFPbyIdQR {
    public String idfp;
    public String idVid;
    public int positionFP;
    public String idLoiRaVao;
    public String hoTen;
    public String dienThoai;

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

    public void setPositionFP(int positionFP) {
        this.positionFP = positionFP;
    }

    public String getIdLoiRaVao() {
        return idLoiRaVao;
    }

    public void setIdLoiRaVao(String idLoiRaVao) {
        this.idLoiRaVao = idLoiRaVao;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
