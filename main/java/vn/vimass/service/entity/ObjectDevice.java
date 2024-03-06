package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectDevice {
    public String id;
    public String name;
    public int status;

    public String idLoiRaVao;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdLoiRaVao() {
        return idLoiRaVao;
    }

    public void setIdLoiRaVao(String idLoiRaVao) {
        this.idLoiRaVao = idLoiRaVao;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
