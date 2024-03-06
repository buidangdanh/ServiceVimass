package vn.vimass.service.entity;

import com.google.gson.Gson;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;

import java.util.ArrayList;

public class ObjectThietBiVPassRes {
    public String ip;
    public String tenMayChu;
    public ArrayList<ObjVpass> devices;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
