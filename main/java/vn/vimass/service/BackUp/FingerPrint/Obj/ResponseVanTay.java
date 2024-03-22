package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;

public class ResponseVanTay {
    public int msgCode;
    public int funcId;
    public Result result;
    public String msgContent;
    public int totalAll;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
