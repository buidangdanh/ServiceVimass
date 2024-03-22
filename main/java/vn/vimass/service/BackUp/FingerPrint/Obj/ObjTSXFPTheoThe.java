package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;
import vn.vimass.service.table.object.ObjectFPRequest;

import java.util.ArrayList;

public class ObjTSXFPTheoThe {
    public int type; //0: xac thuc 1: dang ky 3: xoa
    public ObjectFPRequest thongTinNguoi;
    public String idFP;
    public ArrayList<FingerData> vanTayThe; //498 byte
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
