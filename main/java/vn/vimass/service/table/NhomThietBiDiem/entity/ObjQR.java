package vn.vimass.service.table.NhomThietBiDiem.entity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjQR {

    public String id; // id Diem dinh danh, diem thanh toan
    public String mcID;
    public long timeTao;
    public int catID;
    public String theDaNangLK;
    public ObjInfoQR infor; // thong tin diem ra vao
    public String LockDeviceID;
    public ArrayList<String> listIDLockDevice;

    public ArrayList<ObjGroup> listGroup;

    public ArrayList<ObjGroup> getListGroup() {
        return listGroup;
    }

    public void setListGroup(ArrayList<ObjGroup> listGroup) {
        this.listGroup = listGroup;
    }

    public ArrayList<String> getListIDLockDevice() {
        return listIDLockDevice;
    }

    public void setListIDLockDevice(ArrayList<String> listIDLockDevice) {
        this.listIDLockDevice = listIDLockDevice;
    }


    public String getLockDeviceID() {
        return LockDeviceID;
    }
    public void setLockDeviceID(String lockDeviceID) {
        LockDeviceID = lockDeviceID;
    }

    public int type; // 2 sua, 3 xoa

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

    public long getTimeTao() {
        return timeTao;
    }

    public void setTimeTao(long timeTao) {
        this.timeTao = timeTao;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public String getTheDaNangLK() {
        return theDaNangLK;
    }

    public void setTheDaNangLK(String theDaNangLK) {
        this.theDaNangLK = theDaNangLK;
    }

    public ObjInfoQR getInfor() {
        return infor;
    }

    public void setInfor(ObjInfoQR infor) {
        this.infor = infor;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
