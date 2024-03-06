package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;

public class ObjectLayDiemCoNhom {

    public String user; // V123xx || 096652xxxxx
    public String mcID;
    public long currentTime;
    public String cks; // md5: "Y99JAuGfmYaBYYyycsLy26" + mcID + currentTime;

    public String catID; // "" || null lay tat ca
    public boolean theLK; // loc nhung diem cua don vi co lk voi the da nang

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getCks() {
        return cks;
    }

    public void setCks(String cks) {
        this.cks = cks;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public boolean isTheLK() {
        return theLK;
    }

    public void setTheLK(boolean theLK) {
        this.theLK = theLK;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
