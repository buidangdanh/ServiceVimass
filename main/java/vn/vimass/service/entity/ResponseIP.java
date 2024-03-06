package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ResponseIP {

    public int funcId;
    public int device; // 0-ios, 1-android, 2-pc
    public long currentime;

    public String data;
    public String listIdDiem;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
