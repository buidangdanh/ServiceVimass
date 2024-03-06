package vn.vimass.service.crawler.bhd.DongBoVsServer.entity;

import com.google.gson.Gson;

public class ObjectIpPhone {

    public String IP;// thiet bi phone
    public String port;// thiet bi phone
    public String id; // id : diem dinh danh, xac thuc(QR)

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
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
