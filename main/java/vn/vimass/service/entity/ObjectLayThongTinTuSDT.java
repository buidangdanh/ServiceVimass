package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectLayThongTinTuSDT {
    public String phone;

    public String vID;

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);

    }
}
