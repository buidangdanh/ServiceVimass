package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;

public class FingerInfo {
    public String id;
    public String name;
    public String port;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
