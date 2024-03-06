package vn.vimass.service.crawler.bhd.XuLyLayKhuonMat;

import com.google.gson.Gson;

public class ObjecPerOfGr {

    public String id;
    public String userName;
    public String chucDanh;
    public String uID;
    public String vID;
    public int perNum;
    public String groupID;
    public String idQR;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getPerNum() {
        return perNum;
    }

    public void setPerNum(int perNum) {
        this.perNum = perNum;
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
