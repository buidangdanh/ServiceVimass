package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjectNguoiNhom {
    public String idNguoi;
    public ArrayList<String> listNhom = new ArrayList<>();

    public String getIdNguoi() {
        return idNguoi;
    }

    public void setIdNguoi(String idNguoi) {
        this.idNguoi = idNguoi;
    }

    public ArrayList<String> getListNhom() {
        return listNhom;
    }

    public void setListNhom(ArrayList<String> listNhom) {
        this.listNhom = listNhom;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
