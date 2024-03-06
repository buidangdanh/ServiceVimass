package vn.vimass.service.table.SendData.entity;

import com.google.gson.Gson;

public class ObjecIpPC {

    public int id;
    public String ip;
    public String nameServer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



    @Override
    public String toString(){

        return new Gson().toJson(this);

    }
}
