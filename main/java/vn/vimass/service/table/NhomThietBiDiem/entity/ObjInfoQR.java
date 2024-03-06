package vn.vimass.service.table.NhomThietBiDiem.entity;

import com.google.gson.Gson;

public class ObjInfoQR {

    public String id;
    public String tenCuaHang;
    public String diaChi;
    public Double lat;
    public Double lng;
    public String textQRRaVao;
    public String textQRBack;
    public String theDaNangLK;
    public String dsPathURLMayDieuKhien;
    public int status;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTenCuaHang() {
        return tenCuaHang;
    }
    public void setTenCuaHang(String tenCuaHang) {
        this.tenCuaHang = tenCuaHang;
    }
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public String getTextQRRaVao() {
        return textQRRaVao;
    }
    public void setTextQRRaVao(String textQRRaVao) {
        this.textQRRaVao = textQRRaVao;
    }
    public String getTextQRBack() {
        return textQRBack;
    }
    public void setTextQRBack(String textQRBack) {
        this.textQRBack = textQRBack;
    }
    public String getTheDaNangLK() {
        return theDaNangLK;
    }
    public void setTheDaNangLK(String theDaNangLK) {
        this.theDaNangLK = theDaNangLK;
    }
    public String getDsPathURLMayDieuKhien() {
        return dsPathURLMayDieuKhien;
    }
    public void setDsPathURLMayDieuKhien(String dsPathURLMayDieuKhien) {
        this.dsPathURLMayDieuKhien = dsPathURLMayDieuKhien;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
