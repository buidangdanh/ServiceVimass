package vn.vimass.service.crawler.bhd.DongBoVsServer.entity;

import com.google.gson.Gson;

public class ObjectIpPhone {

    public String deviceID;// id thiet bi phone
    public String ip;// thiet bi phone
    public String port;// thiet bi phone
    public String id; // id : diem dinh danh, xac thuc(QR)
    public long currentTime;
    public String cks;// += vid + "_"+ personName +"_"+(5 ky tu dau dac trung khuon mat)) ,item them tung personName
    public String idQR;// idQR : id diem dinh danh ra vao

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getCks() {
        return cks;
    }

    public void setCks(String cks) {
        this.cks = cks;
    }

    public String getIdQR() {
        return idQR;
    }

    public void setIdQR(String idQR) {
        this.idQR = idQR;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
