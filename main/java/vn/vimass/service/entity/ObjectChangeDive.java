package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectChangeDive {
    public String idLoiRaVao;
    public String idVpass;
    public int typeDangKy; //1: dang ky 0: khong lam gi

    public String getIdLoiRaVao() {
        return idLoiRaVao;
    }

    public void setIdLoiRaVao(String idLoiRaVao) {
        this.idLoiRaVao = idLoiRaVao;
    }

    public String getIdVpass() {
        return idVpass;
    }

    public void setIdVpass(String idVpass) {
        this.idVpass = idVpass;
    }

    public int getTypeDangKy() {
        return typeDangKy;
    }

    public void setTypeDangKy(int typeDangKy) {
        this.typeDangKy = typeDangKy;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
