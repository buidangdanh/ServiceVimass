package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectLichSuRaVaoQuetQr {

    public String maRaVao;
    public String vID;
    public String phone;
    public String accName;
    public String loiRa;
    public String idThietBi;
    public String diaChi;
    public int typeXacThuc;
    public long thoiGianGhiNhan;
    public long timeAuthen;
    public int typeDataAuThen;
    public int phiRaVao;
    public String vpassID;
    public String chucDanh;
    public String thongTinDiem;

    public String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThongTinDiem() {
        return thongTinDiem;
    }

    public void setThongTinDiem(String thongTinDiem) {
        this.thongTinDiem = thongTinDiem;
    }

    public int getTypeDataAuThen() {
        return typeDataAuThen;
    }

    public void setTypeDataAuThen(int typeDataAuThen) {
        this.typeDataAuThen = typeDataAuThen;
    }

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    public String getVpassID() {
        return vpassID;
    }

    public void setVpassID(String vpassID) {
        this.vpassID = vpassID;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public String result;
    public int personNumber;

    public long getTimeAuthen() {
        return timeAuthen;
    }

    public void setTimeAuthen(long timeAuthen) {
        this.timeAuthen = timeAuthen;
    }

    public int getTypeXacThuc() {
        return typeXacThuc;
    }

    public void setTypeXacThuc(int typeXacThuc) {
        this.typeXacThuc = typeXacThuc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhiRaVao() {
        return phiRaVao;
    }

    public void setPhiRaVao(int phiRaVao) {
        this.phiRaVao = phiRaVao;
    }

    public long getThoiGianGhiNhan() {
        return thoiGianGhiNhan;
    }

    public void setThoiGianGhiNhan(long thoiGianGhiNhan) {
        this.thoiGianGhiNhan = thoiGianGhiNhan;
    }


    public String getMaRaVao() {
        return maRaVao;
    }

    public void setMaRaVao(String id) {
        this.maRaVao = id;
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

    public String getLoiRa() {
        return loiRa;
    }

    public void setLoiRa(String loiRa) {
        this.loiRa = loiRa;
    }

    public String getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(String idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public long getThoiGianNghiNhan() {
        return thoiGianGhiNhan;
    }

    public void setThoiGianNghiNhan(long thoiGianNghiNhan) {
        this.thoiGianGhiNhan = thoiGianNghiNhan;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


}
