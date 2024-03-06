package vn.vimass.service.table.NhomThietBiDiem.entity;

import com.google.gson.Gson;

public class ObjectCheckTypeGr {

    public String groupID;
    public int groupLevel;
    public String listGr;
    public String idQR;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
    }

    public String getListGr() {
        return listGr;
    }

    public void setListGr(String listGr) {
        this.listGr = listGr;
    }

    public String getIdQR() {
        return idQR;
    }

    public void setIdQR(String idQR) {
        this.idQR = idQR;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
