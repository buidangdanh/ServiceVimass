package vn.vimass.service.table.SendData.entity;

import com.google.gson.Gson;

public class ObjectData {
    public String id;
    public String ipPC;
    public String data;
    public String personFace;
    public int type;

    public String getPersonFace() {
        return personFace;
    }

    public void setPersonFace(String personFace) {
        this.personFace = personFace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpPC() {
        return ipPC;
    }

    public void setIpPC(String ipPC) {
        this.ipPC = ipPC;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }

}
