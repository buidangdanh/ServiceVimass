package vn.vimass.service.table.object;

import com.google.gson.Gson;

public class ObjectHienThiXacThucThanhCong {
    public int stt;
    public String accName;
    public String thietBi;
    public String diaDiem;
    public String yeuCau;
    public String ngayGio;
    public String trangThai;

    private String personName;
    private String position;
    private String vID;
    private String mcName;
    private String tenThietBi;
    private String diaChi;
    private String noiDungBS1;
    private String noiDungBS2;
    public int personNumber;

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNoiDungBS1() {
        return noiDungBS1;
    }

    public void setNoiDungBS1(String noiDungBS1) {
        this.noiDungBS1 = noiDungBS1;
    }

    public String getNoiDungBS2() {
        return noiDungBS2;
    }

    public void setNoiDungBS2(String noiDungBS2) {
        this.noiDungBS2 = noiDungBS2;
    }

    public int getStt() {
        return stt;
    }
    public void setStt(int stt) {
        this.stt = stt;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getThietBi() {
        return thietBi;
    }
    public void setThietBi(String thietBi) {
        this.thietBi = thietBi;
    }
    public String getDiaDiem() {
        return diaDiem;
    }
    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }
    public String getYeuCau() {
        return yeuCau;
    }
    public void setYeuCau(String yeuCau) {
        this.yeuCau = yeuCau;
    }
    public String getNgayGio() {
        return ngayGio;
    }
    public void setNgayGio(String ngayGio) {
        this.ngayGio = ngayGio;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
