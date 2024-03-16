package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;

public class RequestTTFP {
    public String idFP = "";
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
