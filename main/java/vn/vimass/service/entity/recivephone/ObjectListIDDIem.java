package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;
import vn.vimass.service.table.object.ObjectQr;

import java.util.ArrayList;

public class ObjectListIDDIem {
    public String idNguoi;
    public ArrayList<ObjectQr> listNhom = new ArrayList<>();

    public String getIdNguoi() {
        return idNguoi;
    }

    public void setIdNguoi(String idNguoi) {
        this.idNguoi = idNguoi;
    }

    public ArrayList<ObjectQr> getListNhom() {
        return listNhom;
    }

    public void setListNhom(ArrayList<ObjectQr> listNhom) {
        this.listNhom = listNhom;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
