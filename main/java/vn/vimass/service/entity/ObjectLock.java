package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectLock {
    public String id;
    public String ip;
    public String portIp;
    public int loaiCua;
    public int chieuRaVaoBarrie;
    public String name;
    public int typePhi;

    public String lenhMoCua;

    public String getLenhMoCua() {
        return lenhMoCua;
    }

    public void setLenhMoCua(String lenhMoCua) {
        this.lenhMoCua = lenhMoCua;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPortIp() {
        return portIp;
    }

    public void setPortIp(String portIp) {
        this.portIp = portIp;
    }

    public int getLoaiCua() {
        return loaiCua;
    }

    public void setLoaiCua(int loaiCua) {
        this.loaiCua = loaiCua;
    }

    public int getChieuRaVaoBarrie() {
        return chieuRaVaoBarrie;
    }

    public void setChieuRaVaoBarrie(int chieuRaVaoBarrie) {
        this.chieuRaVaoBarrie = chieuRaVaoBarrie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypePhi() {
        return typePhi;
    }

    public void setTypePhi(int typePhi) {
        this.typePhi = typePhi;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
