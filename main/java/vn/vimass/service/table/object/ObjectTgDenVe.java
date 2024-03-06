package vn.vimass.service.table.object;

import com.google.gson.Gson;

public class ObjectTgDenVe {

    public String maGD;
    public String vID;
    public String accName;
    public long thoiGianDen;
    public long thoiGianVe;
    public String soGioLam;
    public String ngay;

    public String getSoGioLam() {
        return soGioLam;
    }
    public void setSoGioLam(String soGioLam) {
        this.soGioLam = soGioLam;
    }
    public String getMaGD() {
        return maGD;
    }
    public void setMaGD(String maGD) {
        this.maGD = maGD;
    }

    public String getvID() {
        return vID;
    }
    public void setvID(String vID) {
        this.vID = vID;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public long getThoiGianDen() {
        return thoiGianDen;
    }
    public void setThoiGianDen(long thoiGianDen) {
        this.thoiGianDen = thoiGianDen;
    }
    public long getThoiGianVe() {
        return thoiGianVe;
    }
    public void setThoiGianVe(long thoiGianVe) {
        this.thoiGianVe = thoiGianVe;
    }

    public String getNgay() {
        return ngay;
    }
    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
