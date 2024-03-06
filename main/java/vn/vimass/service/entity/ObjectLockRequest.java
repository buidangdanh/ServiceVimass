package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectLockRequest {
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
