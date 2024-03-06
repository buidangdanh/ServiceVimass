package vn.vimass.service.crawler.bhd.DongBoVsServer.entity;

import com.google.gson.Gson;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjFP;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjGroup;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjLockDevice;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjQR;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;

import java.util.ArrayList;

public class TypeThongBao {
    public int type;
    public ArrayList<ObjGroup> listGr;
    public ArrayList<ObjLockDevice> listDevice;
    public ArrayList<ObjQR> listDiem;

    public ArrayList<ObjVpass> listVpass;
    public ArrayList<ObjFP> listFP;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<ObjGroup> getListGr() {
        return listGr;
    }

    public void setListGr(ArrayList<ObjGroup> listGr) {
        this.listGr = listGr;
    }

    public ArrayList<ObjLockDevice> getListDevice() {
        return listDevice;
    }

    public void setListDevice(ArrayList<ObjLockDevice> listDevice) {
        this.listDevice = listDevice;
    }

    public ArrayList<ObjQR> getListDiem() {
        return listDiem;
    }

    public void setListDiem(ArrayList<ObjQR> listDiem) {
        this.listDiem = listDiem;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
