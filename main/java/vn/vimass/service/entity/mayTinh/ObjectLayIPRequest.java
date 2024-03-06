package vn.vimass.service.entity.mayTinh;

import com.google.gson.Gson;

public class ObjectLayIPRequest {
    public String id;
    public String ip;
    public String moTa;


    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
