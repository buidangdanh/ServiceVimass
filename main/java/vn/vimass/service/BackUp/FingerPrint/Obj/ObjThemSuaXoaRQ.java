package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;
import vn.vimass.service.table.object.ObjectFPRequest;
import vn.vimass.service.table.object.ObjectInfoVid;

public class ObjThemSuaXoaRQ {
    public int type;
    public ObjectFPRequest thongTinNguoi;

    public String idFP;

    public String nameFP;

    public String emptyID;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
