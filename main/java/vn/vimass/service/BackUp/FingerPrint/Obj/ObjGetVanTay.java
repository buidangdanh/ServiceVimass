package vn.vimass.service.BackUp.FingerPrint.Obj;

import com.google.gson.Gson;

public class ObjGetVanTay {
    public String user; // sdt || Vxxx
    public int perNum; // 1,2,3,4 neu la the V

    public String mcID;
    public String cks;// md5: user + deviceID + "ZgVCHxqMd$aNCm54X2YHD" + currentTime + mcID;
    public long currentTime;

    public int deviceID;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
