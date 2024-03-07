package vn.vimass.service.table.object;

import com.google.gson.Gson;
import vn.vimass.service.BackUp.FingerPrint.Obj.FingerData;

import java.util.ArrayList;

public class ObjectInfoVid {

    public String id; // = idVid + personPosition
    public String idVid ;
    public String uID ;
    public String maSoThue;
    public String diaChi;
    public String dienThoai;
    public String email;
    public String gioiTinh;
    public String hoTen;
    public String ngayCapCCCD;
    public String ngaySinh;
    public String quocTich;
    public String soCanCuoc;
    public String soTheBHYT;
    public String tk;
    public String anhDaiDien;
    public String anhCMNDMatTruoc;
    public String anhCMNDMatSau;
    public String faceData;
    public String personName;
    public String chucDanh;
    public int personPosition;
    public String idQR;
    public String groupID;
    public String mcID;

    public String cksFaceOfVid;

    public ArrayList<FingerData> fingerData;

    public ArrayList<FingerData> getFingerData() {
        return fingerData;
    }

    public void setFingerData(ArrayList<FingerData> fingerData) {
        this.fingerData = fingerData;
    }

    public String getCksFaceOfVid() {
        return cksFaceOfVid;
    }

    public void setCksFaceOfVid(String cksFaceOfVid) {
        this.cksFaceOfVid = cksFaceOfVid;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public String getIdQR() {
        return idQR;
    }

    public void setIdQR(String idQR) {
        this.idQR = idQR;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getListIdDiem() {
        return idQR;
    }

    public void setListIdDiem(String listIdDiem) {
        this.idQR = idQR;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdVid() {
        return idVid;
    }

    public void setIdVid(String idVid) {
        this.idVid = idVid;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgayCapCCCD() {
        return ngayCapCCCD;
    }

    public void setNgayCapCCCD(String ngayCapCCCD) {
        this.ngayCapCCCD = ngayCapCCCD;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getSoCanCuoc() {
        return soCanCuoc;
    }

    public void setSoCanCuoc(String soCanCuoc) {
        this.soCanCuoc = soCanCuoc;
    }

    public String getSoTheBHYT() {
        return soTheBHYT;
    }

    public void setSoTheBHYT(String soTheBHYT) {
        this.soTheBHYT = soTheBHYT;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getAnhCMNDMatTruoc() {
        return anhCMNDMatTruoc;
    }

    public void setAnhCMNDMatTruoc(String anhCMNDMatTruoc) {
        this.anhCMNDMatTruoc = anhCMNDMatTruoc;
    }

    public String getAnhCMNDMatSau() {
        return anhCMNDMatSau;
    }

    public void setAnhCMNDMatSau(String anhCMNDMatSau) {
        this.anhCMNDMatSau = anhCMNDMatSau;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    public int getPersonPosition() {
        return personPosition;
    }

    public void setPersonPosition(int personPosition) {
        this.personPosition = personPosition;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
