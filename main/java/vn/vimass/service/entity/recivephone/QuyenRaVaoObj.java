package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;

public class QuyenRaVaoObj {
    public String id;// danh cho type == 3 xoa

    public int perNum;
    public String uID;
    public String vID;

    public String name;
    public String chucDanh;

    public String sdt;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
