package vn.vimass.service.entity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjectLichSuRaVaoQuetQrVerMobile {
    public String id;
    public long thoiGianGhiNhan;
    public int deviceID;
    public int typeDataAuThen;
    public String vpassID;
    public String idThietBi;
    public String thongTinDiem;
    public String chucDanh;
    public String sdt;
    public ArrayList<String> dsTinHieuGroup;
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
    public double khoangCach;
    public String avatar;
    public String personName;
    public String position;
    public String diaChi;
    public String noiDungBS1;
    public String noiDungBS2;
    public String messDevice;
    public String moTaPhi;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }



}
