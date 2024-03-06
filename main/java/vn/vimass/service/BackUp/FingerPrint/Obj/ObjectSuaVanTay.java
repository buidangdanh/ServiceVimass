package vn.vimass.service.BackUp.FingerPrint.Obj;

import java.util.ArrayList;

public class ObjectSuaVanTay {
    public String user; // sdt || Vxxx
    public int perNum; // 1,2,3,4 neu la the V

    public String cks; // md5:  user  + "ZgVCHxqMd$aNkk54X2YHD" + currentTime ;
    public long currentTime;
    public int deviceID; // //Androi 1 | IOS 2 |

    public String mcID;

    public ArrayList<ObjFPSua> listItem;
}
