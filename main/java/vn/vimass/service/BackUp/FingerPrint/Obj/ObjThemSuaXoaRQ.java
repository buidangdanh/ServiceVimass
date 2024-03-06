package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;
import vn.vimass.service.table.object.ObjectInfoVid;

public class ObjThemSuaXoaRQ {
    public int type;
    public ObjectInfoVid thongTinNguoi;

    public String idFP;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
