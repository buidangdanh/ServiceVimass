package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;
import vn.vimass.service.table.NhomThietBiDiem.entity.ListDiem;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;

import java.util.ArrayList;

public class ObjFP {
    public String id;
    public String mcID;
    public String idDonVi;
    public String nameF;
    public int totalF;
    public int currentF;
    public long timeTao;
    public long timeSua;
    public int type;
    public ArrayList<ListDiem> listDiem;
    public ObjVpass deviceV;
    public String port;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
