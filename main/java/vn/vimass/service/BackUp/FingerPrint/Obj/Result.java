package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;

public class Result {
    public String type;
    public String value;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
