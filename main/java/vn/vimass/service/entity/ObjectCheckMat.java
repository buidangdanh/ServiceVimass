package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectCheckMat {
    public String textSearch;
    public int perNum;
    public String vID;

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public int getPerNum() {
        return perNum;
    }

    public void setPerNum(int perNum) {
        this.perNum = perNum;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
