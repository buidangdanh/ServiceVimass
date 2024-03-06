package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjectGoupRequest {

    public String user; // sdt || Vxxx
    public String mcID;
    public String cks; //md5: user + deviceID + "ZgVCHxqMd$aNCm54X2YHD" + currentTime + mcID
    public long currentTime;

    public int deviceID; //Androi 1 | IOS 2 |

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
