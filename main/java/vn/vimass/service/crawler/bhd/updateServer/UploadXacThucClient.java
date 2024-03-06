package vn.vimass.service.crawler.bhd.updateServer;

import vn.vimass.service.connect.PostBody;
import vn.vimass.service.log.Log;
import vn.vimass.service.utils.ServivceCommon;

public class UploadXacThucClient {
    private static String key = "SafNLitbSafNLitxinYbFPMPKCjauZFPMPKCjauZ";
    private static String URL_UP_LOAD_XAC_THUC_CLIENT = "http://210.245.8.7:12318/vimass/services/VUHF/uploadXacThucTuClient";
    public static void upLoad(String idXacThuc, String vid,String phone, int personNumber, int deviceID,
                              int typeDataAuThen, long time ,String idThietBi, String typeVi) {

        ObjUploadXacThucClient obj = new ObjUploadXacThucClient();
        obj.sdtXacThuc = phone; // sdtXacThuc nếu là sđt

        obj.vID = vid;  // vID và personNumber nếu là thẻ
        obj.personNumber = personNumber; // 1||2||3||4 // khac
        obj.deviceID = deviceID; //Androi 1 | IOS 2 | cpt3
        obj.typeDataAuThen = typeDataAuThen; //class TypeAuthenData
        obj.timeInit = time;
        obj.hinhThuc = "QR"; // "QR" || "UHF"
        obj.idDiemRaVao = idThietBi;

        // choOFFLine commit
        obj.typeVi = typeVi; // SDT||VCARD
        obj.userLTVPush = "0981455707";
        obj.idLocal = idXacThuc; // ATLC + time(long) + Random3 ky tu

        obj.cks = checkSum(key,obj.typeDataAuThen,obj.timeInit,obj.deviceID, obj.userLTVPush ); // md5: key + typeDataAuThen + timeInit + deviceID + userLTVPush; // key theo userLTVPush
        Log.logServices("respone xac thuc client input : " + obj.toString());
        String strJson = PostBody.callService(URL_UP_LOAD_XAC_THUC_CLIENT, obj.toString());

        //Log.logServices("respone xac thuc client: " + strJson);
    }

    public static String checkSum(String key, int typeDataAuThen, long timeInit, int deviceID,String userLTVPush) {

        String cksTemp =  key + typeDataAuThen + timeInit + deviceID + userLTVPush;
        Log.logServices("after cks : " + cksTemp);
        cksTemp = ServivceCommon.bamMD5(cksTemp);
        Log.logServices("befor cks : " + cksTemp);
        return cksTemp;

    }

}
