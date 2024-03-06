package vn.vimass.service.crawler.bhd.updateServer;

import com.google.gson.Gson;

public class ObjUploadXacThucClient {
    public String sdtXacThuc; // sdtXacThuc nếu là sđt

    public String vID;  // vID và personNumber nếu là thẻ
    public int personNumber; // 1||2||3||4

    public int deviceID; //Androi 1 | IOS 2 | cpt3
    public int typeDataAuThen; //class TypeAuthenData
    public String cks; // md5: key + typeDataAuThen + timeInit + deviceID + userLTVPush; // key theo userLTVPush
    public long timeInit;
    public String hinhThuc; // "QR" || "UHF"
    public String idDiemRaVao;

    // choOFFLine commit
    public String typeVi; // SDT||VCARD
    public String userLTVPush;
    public String idLocal; // ATLC + time(long) + Random3 ky tu

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
