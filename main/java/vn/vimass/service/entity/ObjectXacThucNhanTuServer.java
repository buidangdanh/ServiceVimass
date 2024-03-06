package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectXacThucNhanTuServer {
    public String id;

    public long thoiGianGhiNhan;

    public int deviceID;

    public int typeDataAuThen;

    public String idThietBi;

    public String sdt;

    public String[] dsTinHieuGroup;

    public long thoiGianXacThuc;

    public int trangThai;

    public String idL;

    public String accName;

    public String mcID;

    public String mcName;

    public String tenThietBi;

    public String vID;

    public String uID;

    public int personNumber;

    public int typeXacThuc;

    public String position;

    public String diaChi;

    public String noiDungBS1;

    public String noiDungBS2;

    public String messDevice;
    private String personName;

    public String toJson(){
        return new Gson().toJson(this);
    }

}
