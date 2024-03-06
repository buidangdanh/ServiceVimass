package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectBackUp {
    public String maGD;
    public int activePC;
    public String objBackUp;

    @Override
    public String toString() {
        return new Gson().toJson(this);

    }
}
