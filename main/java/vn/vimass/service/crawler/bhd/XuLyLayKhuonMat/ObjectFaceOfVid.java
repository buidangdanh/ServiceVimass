package vn.vimass.service.crawler.bhd.XuLyLayKhuonMat;

import com.google.gson.Gson;

import java.util.GregorianCalendar;

public class ObjectFaceOfVid {

    public String id;
    public String hoTen;
    public String chucDanh;
    public String idVid;
    public String uID;
    public int personPosition;
    public String personName;
    public String faceData;
    public String groupID;
    public String idQR;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdVid() {
        return idVid;
    }

    public void setIdVid(String idVid) {
        this.idVid = idVid;
    }

    public int getPersonPosition() {
        return personPosition;
    }

    public void setPersonPosition(int personPosition) {
        this.personPosition = personPosition;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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


    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getIdQR() {
        return idQR;
    }

    public void setIdQR(String idQR) {
        this.idQR = idQR;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
