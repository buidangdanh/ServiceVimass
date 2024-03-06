package vn.vimass.service.crawler.bhd.DongBoVsServer;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.QuyenRaVaoObj;

import java.util.ArrayList;

public class XoaQuyenUserTrongDonVi {

    public String user; // sdt || Vxxx
    public int perNum; // 1,2,3,4 neu la the V

    public String cks;
    public long currentTime;
    public int deviceID;

    public String mcID;
    public ArrayList<QuyenRaVaoObj> listPerDelete;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPerNum() {
        return perNum;
    }

    public void setPerNum(int perNum) {
        this.perNum = perNum;
    }

    public String getCks() {
        return cks;
    }

    public void setCks(String cks) {
        this.cks = cks;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public ArrayList<QuyenRaVaoObj> getListPerDelete() {
        return listPerDelete;
    }

    public void setListPerDelete(ArrayList<QuyenRaVaoObj> listPerDelete) {
        this.listPerDelete = listPerDelete;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
