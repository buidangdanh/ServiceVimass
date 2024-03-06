package vn.vimass.service.table.NhomThietBiDiem.entity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjVpass {
    public String id;
    public String mcID;
    public String desDevice;
    public double storage;
    public int typeDevice;
    public String portD;
    public int function;
    public String ip;
    public ArrayList<ListDiem> listDiem;
    public String deviceID;
    public int stt;

    public int type; // 1 them khong co deviceID, 2 sua, 3 xoa  , 11 đăng ký thiết bị có deviceID (deviceID không bỏ trống )

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
