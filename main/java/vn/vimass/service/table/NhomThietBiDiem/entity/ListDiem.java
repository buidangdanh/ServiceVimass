package vn.vimass.service.table.NhomThietBiDiem.entity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ListDiem {
    public String id;
    public String mcID;
    public Object timeTao;
    public String catID;
    public String theDaNangLK;
    public Infor infor;
    public ArrayList<ObjLockDevice> listLockDevice;
    public ArrayList<ObjGroup> listGroup;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public Object getTimeTao() {
        return timeTao;
    }

    public void setTimeTao(Object timeTao) {
        this.timeTao = timeTao;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getTheDaNangLK() {
        return theDaNangLK;
    }

    public void setTheDaNangLK(String theDaNangLK) {
        this.theDaNangLK = theDaNangLK;
    }

    public Infor getInfor() {
        return infor;
    }

    public void setInfor(Infor infor) {
        this.infor = infor;
    }

    public ArrayList<ObjLockDevice> getListLockDevice() {
        return listLockDevice;
    }

    public void setListLockDevice(ArrayList<ObjLockDevice> listLockDevice) {
        this.listLockDevice = listLockDevice;
    }

    public ArrayList<ObjGroup> getListGroup() {
        return listGroup;
    }

    public void setListGroup(ArrayList<ObjGroup> listGroup) {
        this.listGroup = listGroup;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
