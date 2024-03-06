package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;

public class FingerData {
    public String emptyID;
    public String name;
    public String data;
    public String idThietBiFP;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
