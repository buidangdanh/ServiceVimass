package vn.vimass.service.crawler.bhd.XuLyLayKhuonMat;

import com.google.gson.Gson;

public class ObjecFace {
    //tb1.id, tb1.chucDanh, tb1.uID, tb1.idVid, tb1.faceData\

    public String id;
    public String chucDanh;
    public String uID;
    public String vID;
    public String dienThoai;
    public String faceData;
    public int personPosition;
    public String personName;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public int getPersonPosition() {
        return personPosition;
    }

    public void setPersonPosition(int personPosition) {
        this.personPosition = personPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
