package vn.vimass.service.entity.thongkeoffline;

import com.google.gson.Gson;
import vn.vimass.service.entity.ObjectLichSuRaVaoQuetQr;

import java.util.ArrayList;

public class ObjectThongKeResponse {
    public String maGD;
    public String vID;
    public String loiRa;
    public String diaChi;
    public String phone;
    public String accName;
    public String idQR;
    public long thoiGianDen;
    public long thoiGianVe;
    public String tongThoiGian;
    public int typeXacThuc;


    public String getTongThoiGian() {
        return tongThoiGian;
    }

    public void setTongThoiGian(String tongThoiGian) {
        this.tongThoiGian = tongThoiGian;
    }

    public ArrayList<ObjectLichSuRaVaoQuetQr> hisOnDay;

    public int getTypeXacThuc() {
        return typeXacThuc;
    }

    public void setTypeXacThuc(int typeXacThuc) {
        this.typeXacThuc = typeXacThuc;
    }

    public String getIdQR() {
        return idQR;
    }

    public void setIdQR(String idQR) {
        this.idQR = idQR;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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

    public String getLoiRa() {
        return loiRa;
    }

    public void setLoiRa(String loiRa) {
        this.loiRa = loiRa;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public ArrayList<ObjectLichSuRaVaoQuetQr> getHisOnDay() {
        return hisOnDay;
    }

    public void setHisOnDay(ArrayList<ObjectLichSuRaVaoQuetQr> hisOnDay) {
        this.hisOnDay = hisOnDay;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
