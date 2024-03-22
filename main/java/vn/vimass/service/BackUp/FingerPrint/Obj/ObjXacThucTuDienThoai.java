package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;

public class ObjXacThucTuDienThoai {
    public int type; // 1: xac thuc , 2: chua nghi ra
    public String idFP;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
