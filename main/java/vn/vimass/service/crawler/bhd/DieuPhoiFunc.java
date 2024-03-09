package vn.vimass.service.crawler.bhd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.BackUp.BackUpControllerDataBaseVer2;
import vn.vimass.service.crawler.bhd.SendData.SendDataController;
import vn.vimass.service.crawler.bhd.XuLyXacThuc.DinhDanhXacThuc;
import vn.vimass.service.entity.*;
import vn.vimass.service.entity.group.UpdateGroupRequest;
import vn.vimass.service.log.Log;
import vn.vimass.service.log.ServicesData;
import vn.vimass.service.table.*;
import vn.vimass.service.table.NhomThietBiDiem.Nhom;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjGroup;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjPerson;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;
import vn.vimass.service.table.fingerprint.Fingerprint;
import vn.vimass.service.table.fingerprint.obj.ObjectFP;
import vn.vimass.service.table.fingerprint.obj.ObjectFPbyIdQR;
import vn.vimass.service.table.object.*;
import vn.vimass.service.utils.ServivceCommon;
import vn.vimass.service.utils.VimassData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static vn.vimass.service.BackUp.BackUpControllerDataBase.*;
import static vn.vimass.service.BackUp.BackUpFunCVer2.goiDenMayHai;
import static vn.vimass.service.BackUp.BackUpFunction.*;
import static vn.vimass.service.BackUp.BackUpRoutes.*;

public class DieuPhoiFunc {

    public static ResponseMessage1 geValueResponseMessage1(int funcId, int msgCode, String msgContent, String result) {

        ResponseMessage1 res = new ResponseMessage1();
        res.funcId = funcId;
//      res.device = device;
        res.result = result;
        res.msgContent = msgContent;
        res.msgCode = msgCode;
        return res;
    }

    //------------------------dich vu xac thuc-----------------------------------
    public static ResponseMessage1 funcAuthenticInOut(int funcId, String input) {
        ResponseMessage1 res = new ResponseMessage1();
        System.out.println("ObjecDiaDiemXacThucThanhCong ==========> 1" + input);
        String result = "";
        ObjectXacThuc obj = new Gson().fromJson(input, ObjectXacThuc.class);
        System.out.println("funcAuthenticInOut ObjectXacThuc: " + obj.toString());
        String checkSum = obj.device + obj.idQR + obj.phone + obj.vID + obj.thoiGianGhiNhan;
        System.out.println("funcAuthenticInOut befor checkSum: " + checkSum);
        String cksService = ServivceCommon.bamMD5(checkSum);
        System.out.println("funcAuthenticInOut after checkSum: " + cksService);

        try {
            String id = obj.thoiGianGhiNhan + ServicesData.generateSessionKeyLowestCase(5);
//            if (cksService.equals(obj.cks)) {
                ObjecDiaDiemXacThucThanhCong item = getValueObjecDiaDiemXacThucThanhCong(id, obj);
                System.out.println("ObjecDiaDiemXacThucThanhCong ==========> 2" + item);
                boolean checkInOut = DinhDanhXacThuc.checkQR(item);
                String content = "";
                if(checkInOut){
                    content = Tool.setBase64("Người dùng được phép ra vào");
                    result = Tool.setBase64(item.toString());
                    return geValueResponseMessage1(funcId, 1, content, result);
                }else{
                    content = Tool.setBase64("Người dùng không được phép ra vào");
                    result ="";
                    return geValueResponseMessage1(funcId, 0, content, result);
                }
//            } else {
//                System.out.println("funcAuthenticInOut error cks");
//                return geValueResponseMessage1(funcId, 0, "error cks", "");
//            }
        } catch (Exception e) {
            System.out.println("funcAuthenticInOut Exception: " + e.getMessage());
            System.out.println("funcAuthenticInOut Exception: " + e.getMessage());
        }
        return res;

    }

    private static ObjecDiaDiemXacThucThanhCong getValueObjecDiaDiemXacThucThanhCong(String id, ObjectXacThuc obj) {
        ObjecDiaDiemXacThucThanhCong item = new ObjecDiaDiemXacThucThanhCong();
        try{
            item.id = id;
            item.idQR = obj.idQR;
            item.nameQR = obj.nameQR;
            item.addressQR = obj.addressQR;
            item.device = obj.device;
            item.phone = obj.phone;
            item.vID = obj.vID;
            item.personNumber = obj.personNumber;
            item.accName = obj.accName;
            item.thoiGianGhiNhan = obj.thoiGianGhiNhan;
            item.typeXacThuc = obj.typeXacThuc;
            item.vpassID = obj.vpassID;
            item.trangThai = 1;
            item.timeAuthen = obj.timeAuthen;
            item.typeDataAuThen = obj.typeDataAuThen;


            Log.logServices("funcAuthenticInOut insert: " + item.toString());
        }catch (Exception ex){
            Log.logServices("getValueObjecDiaDiemXacThucThanhCong Exception: " + ex.getMessage());

        }



        return item;
    }

    //--------------------dich vu lay tat cac diem ra vao-----------------------------------------
    public static ResponseMessage1 getlistAddressAll(int funcId) {
        ArrayList<ObjectIpAddress> list = getInfoAddRest();
        String result = Tool.setBase64(list.toString());

        Log.logServices("getlistAddressAll aesEncrypt : " + result);
        String content = "successfully";
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, result);
        return res;
    }

    //--------------------dich vu lay chi thong tin 1 diem-----------------------------------------
    public static ResponseMessage1 getDetail(int funcId, String input) {

        ObjectIpAddress item = IPaddress.getThongTinDiaDiem(input);
//      String result = Tool.aesEncrypt(item.toString(),key);
        String result = "";
//        if(item.idLoiRaVao.length() > 0){
//            result = Tool.setBase64(item.toString());
//        }
        result = Tool.setBase64(item.toString());
        Log.logServices("getDetail aesEncrypt : " + result);
        String content = "successfully";

        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, result);

        return res;
    }

    //--------------------dich vu insert thong tin the -----------------------------------------
    public static ResponseMessage1 funcInsertInfoVid(int funcId, String input) {
        String content = "";
        Log.logServices("funcInsertInfoVid: " + input);
        String result = "";

        Type collectionType = new TypeToken<List<ObjectInfoVid>>() {
        }.getType();
        List<ObjectInfoVid> list = new Gson().fromJson(input, collectionType);

//        ObjectInfoVid obj =  new Gson().fromJson(input, ObjectInfoVid.class);

        for (int i = 0; i < list.size(); i++) {

            String key = list.get(i).idVid;
            int per = list.get(i).personPosition;
            Log.logServices("funcInsertInfoVid input key: " + key);
            ObjectInfoVid item = InforVid.getInfoVid(key);
            ObjectQr objectDiem = new ObjectQr();
            if (item.idVid != null) {
                if (!key.equals(item.idVid) && per != item.personPosition) {
                    InforVid.InsertData(list.get(i));
                    result = VimassData.ContentResult ;
                    content = "successfully";

                } else {

                    InforVid.updateData(list.get(i).idVid, per);
                    // cap nhat diem ra vao cua 1 ban ghi

                    result = VimassData.ContentResult;
                    content = "successfully";
                }
            } else {
                InforVid.InsertData(list.get(i));
                result = VimassData.ContentResult;
                content = "successfully";

            }
        }

        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, result);
        return res;
    }

    public static ResponseMessage1 funcInsertInfoVidOnly(int funcId, String input) {
//        if(May1){
//            goiDenMayHai(funcId, input, 2, idDiem);
//        }
        System.out.println("funcInsertInfoVidOnly: " + input);
        ResponseMessage1 response = new ResponseMessage1();
        response.funcId = funcId;
        try {

            ObjectInfoVid objInfoVid = new Gson().fromJson(input, ObjectInfoVid.class);

            boolean checkFaceDataExist = checkFace(objInfoVid.idVid, objInfoVid.cksFaceOfVid);
            if(!checkFaceDataExist){
                boolean checkExist = InforVid.checkExist(objInfoVid.idVid, objInfoVid.personName);
                System.out.println("checkExist: " + checkExist);
                if(checkExist){
                    if (objInfoVid.id != null && !objInfoVid.id.equals("")) {

                        if (capNhatVaoInfoVid(objInfoVid)) {
                            System.out.println("Update FaceData true");
                            response = StatusResponse(1);
                        } else {
                            System.out.println("Update FaceData false");
                            response = StatusResponse(2);
                        }
                    }
                }else{

                    System.out.println("Insert FaceData true");
                    InforVid.InsertData(objInfoVid);
                    response.result = VimassData.ContentResult;
                    response.msgCode = VimassData.typeResult;

                }
            }else{
                response = StatusResponse(4);
            }

        } catch (Exception ex) {
            Log.logServices("funcInsertInfoVidOnly Exception: " + ex.getMessage());
            response = StatusResponse(3);
        }
        return response;
    }

    private static boolean checkFace(String key, String cksFace){

        boolean exist = false;
        ArrayList<ObjectInfoVid> list = InforVid.getlistDataFaceofVid(key);
        String strCheckSum = key;
        for(ObjectInfoVid item: list){

            strCheckSum += "_"+item.personName+"_"+item.faceData.substring(0, Math.min(item.faceData.length(), 5));;

        }
       String checkSum = ServivceCommon.bamMD5(strCheckSum);

        if(checkSum.equals(cksFace)){
            exist = true;
        }

        Log.logServices("checkFaceDate : "+exist +"\t"+ strCheckSum +"\t"+checkSum +"\t cksFace" +cksFace);
        System.out.println("checkFaceDate : "+exist+ "\t"+ strCheckSum +"\t"+checkSum +"\t cksFace" +cksFace);
        return exist;

    }

    private static boolean exists = false;
    private static List<ObjectQr> updateListDiem(String listDiem, String idDiemMoi) {

        Type collectionType = new TypeToken<List<ObjectQr>>() {
        }.getType();
        List<ObjectQr> list = new Gson().fromJson(listDiem, collectionType);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Log.logServices("idDiemMoi: " + list.get(i).idQR);
                //!list.contains(idDiemMoi)
                if (list.get(i).idQR.equals(idDiemMoi)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                Log.logServices("Phần tử không tồn tại trong danh sách.");
                ObjectQr objectDiem = new ObjectQr();
                objectDiem.idQR = idDiemMoi;
                list.add(objectDiem);

            } else {
                Log.logServices("Phần tử tồn tại trong danh sách.");
            }
            exists = false;

        } else {
            ObjectQr objectDiem = new ObjectQr();
            objectDiem.idQR = idDiemMoi;
            list.add(objectDiem);
        }

        return list;
    }
    //--------------------xoa diem ra vao cua 1 chu the-----------------------------------------

    private static boolean ckeckIdQR = false;

//    public static ResponseMessage1 deleteIdDiemInfoVid(int funcId, String input, String idDiem) {
//        if(May1){
//            goiDenMayHai(funcId, input, 2, idDiem);
//        }
//        String content = "successfully";
//        String[] arrStr = input.split("-");
//        ObjectInfoVid item = InforVid.getInfoVid(arrStr[0], Integer.parseInt(arrStr[1]));
//        // ArrayList<ObjectQr> list = deleteIdDiem(item.listIdDiem,idDiem);
//
//        Log.logServices("list befor deleteIdDiem: " + item.listIdDiem);
//        Type collectionType = new TypeToken<List<ObjectQr>>() {
//        }.getType();
//        ArrayList<ObjectQr> list = new Gson().fromJson(item.listIdDiem, collectionType);
//
//        for (int i = 0; i < list.size(); i++) {
//
//            if (idDiem.equals(list.get(i).idQR)) {
//                Log.logServices("deleteIdDiem item: " + list.get(i).idQR);
//                list.remove(i);
//                ckeckIdQR = true;
//            }
//        }
//        String result = "";
//        int msgCode = 0;
//        if (ckeckIdQR) {
//            result = VimassData.ContentResult + "-" + list.toString();
//            InforVid.updateData(arrStr[0], Integer.parseInt(arrStr[1]), list.toString());
//            ckeckIdQR = false;
//            msgCode = 1;
//
//        } else {
//            result = "Entry and exit points are not included in the list";
//            msgCode = 10;// null
//        }
//
//        ResponseMessage1 res = geValueResponseMessage1(funcId, msgCode, content, result);
//        return res;
//    }

    //--------------------dich vu lay danh sach thong thong tin the ra vao diem-----------------------------------------

    public static ResponseMessage1 getListInfoVid(int funcId, String input) {
        ResponseMessage1 res = null;
        try {
            ObjectOffsetLimit objLimit = new Gson().fromJson(input, ObjectOffsetLimit.class);
            ArrayList<ObjectInfoVid> list = getListInFoVid(objLimit.idQRgreat,objLimit.offset, objLimit.limit, objLimit.textSearch).records;

            String result = Tool.setBase64(list.toString());
            String content = "successfully";
            res = geValueResponseMessage1(funcId, 1, content, result);
        }catch (Exception e){
            System.out.println("Exception getListInfoVid: " + e.getMessage());
        }

        return res;
    }

    public static ResponseMessage1 getListInfoVidLimit(int funcId, String input) {
        if(May1){
            goiDenMayHai(funcId, input, 2, "");
        }
        ResponseMessage1 res = new ResponseMessage1();
        res.funcId = 10601;
        try {
            ObjectOffsetLimit objLimit = new Gson().fromJson(input, ObjectOffsetLimit.class);
            ObjectRecordsAndTotalObjectInfoVid obj = getListInFoVid(objLimit.idQRgreat ,objLimit.offset, objLimit.limit, objLimit.textSearch);
            ArrayList<ObjectInfoVid> list = obj.records;
            String result = Tool.setBase64(list.toString());

            String content = "successfully";
            res = geValueResponseMessage1(funcId, 1, content, result);
            res.totalAll = obj.total;
        } catch (Exception ex) {
            res.msgCode = 2;
            res.msgContent = "Check the data fields again";
            Log.logServices("getListInfoVidLimit Exception: " + ex.getMessage());
        }

        return res;
    }

    private static ObjectRecordsAndTotalObjectInfoVid getListInFoVid(String idQR,int offset, int limit, String key) {
        ObjectRecordsAndTotalObjectInfoVid obj = null;
        try {
            obj = getRecordsAndTotalinfo_vid(idQR, limit, offset, key);
            return obj;
        } catch (Exception ex) {
           // ObjectRecordsAndTotalObjectInfoVid obj = getRecordsAndTotalinfo_vid(limit, offset,  key);
            Log.logServices("getListInFoVid Exception: " + ex.getMessage());
        }
        return obj;
    }

    //--------------------dich vu delete thong tin the -----------------------------------------
    public static ResponseMessage1 deleteInfoVid(int funcId, String input) {
        String[] arrStr = input.split("-");

        InforVid.delete(arrStr[0], "");
        String content = "successfully";
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, VimassData.ContentResult);
        return res;
    }


    public static ResponseMessage1 getSaoKe(int funcId, String input) {
        ResponseMessage1 item = layDuLieuQrTheoSoThe(input);
//      String result = Tool.aesEncrypt(item.toString(),key);
        String result = Tool.setBase64(item.toString());
        Log.logServices("getDetail aesEncrypt : " + result);
        String content = "successfully";
      //  ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, result);
        item.result = Tool.setBase64(item.toString());
        return item;
    }

    //------------------------dich vu send data toi cac pc khac va nguoc lai-----------------------------------
    public static ResponseMessage1 sendData(int funcId, String input) {
        String content = "successfully";
        //  String getbase64 = Tool.getBase64(input);
        ObjectLichSuRaVaoQuetQr item = new Gson().fromJson(input, ObjectLichSuRaVaoQuetQr.class);
        LichSuRaVaoQuetQR.taoDuLieu(item);
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, item.toString());

        return res;
    }

    public static ResponseMessage1 getInfoLock(int funcId, String input) {
        ResponseMessage1 item = layDuLieuKhoa(input);
        item.result = Tool.setBase64(item.result.toString());
        return item;
    }

    public static ResponseMessage1 getInfoDevice(int funcId, String input) {
        ResponseMessage1 item = layDuLieuThietBi(input);
        item.result = Tool.setBase64(item.result.toString());
        return item;
    }

    public static ResponseMessage1 changeInfoDevice(int funcId, String input, int deviceID) {
        ResponseMessage1 item = SuaDuLieuThietBi(input, deviceID);
        item.result = Tool.setBase64(item.result.toString());
        return item;
    }

    public static ResponseMessage1 getInfoFromInfo_vidSDTorVID(int funcId, String input, int deviceID) {
        ResponseMessage1 item = getInfoFrominfo_vid(input, deviceID);
        item.result = Tool.setBase64(item.result.toString());
        return item;
    }
    //---------------Dich thiet lap dieu khien khoa cua Vpass--------------------
    public static ResponseMessage1 ControllerVpass(int funcId, String input) {
        if(May1){
            goiDenMayHai(funcId, input, 2, "");
        }
        Log.logServices("getbase64 ControllerVpass input : " + input);
        String getbase64 = Tool.getBase64(input);
        Log.logServices("getbase64 ControllerVpass: " + getbase64);
        ObjectControllerVpass obj = new Gson().fromJson(getbase64, ObjectControllerVpass.class);

        if (funcId == 115) {
            ControllerVpass.insertDB(obj);
        }
        if (funcId == 11501) {
            ControllerVpass.update(obj);
        }
        if (funcId == 11502) {
            ControllerVpass.detele(obj);
        }

        String content = "successfully";
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, VimassData.ContentResult, "");
        return res;
    }

    public static ResponseMessage1 listControllerVpass(int funcId) {
        if(May1){
            goiDenMayHai(funcId, "", 2, "");
        }
        List<ObjectControllerVpass> list = ControllerVpass.getListVpassControllerVpass();
        String getbase64 = Tool.setBase64(list.toString());
        String content = "successfully";
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, getbase64);
        return res;
    }

    public static ResponseMessage1 VPassDeviceInfo(int funcId, String input) {
        Log.logServices("getbase64 VPassDeviceInfo input : " + input);
        String getbase64 = Tool.getBase64(input);
        Log.logServices("getbase64 ObjectVpassDeviceInfo: " + getbase64);
        ObjectVpassDeviceInfo obj = new Gson().fromJson(getbase64, ObjectVpassDeviceInfo.class);

        if (funcId == 116) {
            VPassDeviceInfo.insertDB(obj);
        }
        if (funcId == 11601) {
            VPassDeviceInfo.update(obj);
        }
        if (funcId == 11602) {
            VPassDeviceInfo.detele(obj);
        }

        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, VimassData.ContentResult, "");
        return res;
    }

    public static ResponseMessage1 listVPassDeviceInfo(int funcId) {
        List<ObjectVpassDeviceInfo> list = VPassDeviceInfo.getListVpassDeviceInfo();
        String getbase64 = Tool.setBase64(list.toString());
        String content = "successfully";
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, getbase64);
        return res;
    }

    //---------------Dichvu them data van tay--------------------

    public static ResponseMessage1 addFingerprint(int funcId, String data) {
        String getbase64 = Tool.getBase64(data.toString());
        ObjectFP obj = new Gson().fromJson(getbase64, ObjectFP.class);
        Fingerprint.insertDB(obj);
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, VimassData.ContentResult, String.valueOf(VimassData.typeResult));
        return res;
    }

    public static ResponseMessage1 ListFPbyIdQR(int funcId) {
        List<ObjectFPbyIdQR> list = Fingerprint.getListFPbyIdQR();
        String getbase64 = Tool.setBase64(list.toString());
        String content = "successfully";
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, content, getbase64);
        return res;
    }

    public static ResponseMessage1 getItemFPbyIdQR(int funcId, String data) {
        ObjectFPbyIdQR obj = Fingerprint.getItemObjectFPbyIdQR(Integer.parseInt(data));

        String setbase64 = Tool.setBase64(obj.toString());
        String content = "successfully";
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, VimassData.ContentResult, setbase64);
        return res;
    }

    public static ResponseMessage1 getItemIpAddress(int funcId, String data) {
        ObjectIpAddress obj = IPaddress.getItemIpAddress(data);
        String setbase64 = Tool.setBase64(obj.toString());
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, VimassData.ContentResult, setbase64);
        return res;
    }

    public static ResponseMessage1 deleteItemFPbyIdQR(int funcId, String data) {
        Fingerprint.deleteItemObjectFPbyIdQR(Integer.parseInt(data));
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, VimassData.ContentResult, String.valueOf(VimassData.typeResult));
        return res;
    }

    public static ResponseMessage1 deleteAllFPbyIdQR(int funcId) {
        Fingerprint.deleteAllObjectFPbyIdQR();
        ResponseMessage1 res = geValueResponseMessage1(funcId, 1, VimassData.ContentResult, String.valueOf(VimassData.typeResult));
        return res;
    }

    public static ResponseMessage1 getInfoVID(String input, int deviceID) {
        ResponseMessage1 response = new ResponseMessage1();
        try {

            response = getInfoVIDFunc(input, deviceID);
            response.result = Tool.setBase64(response.result.toString());
        } catch (Exception ex) {
            Log.logServices("getInfoVID Exception: " + ex.getMessage());
        }
        return response;
    }

    public static ResponseMessage1 getListVpass() {
        ResponseMessage1 response = new ResponseMessage1();
        Log.logServices("getListVpass func: " + 125);
        try {
            ArrayList<ObjVpass> list = BackUpControllerDataBaseVer2.getThietBiVPass();
            response.result = Tool.setBase64(list.toString());
        } catch (Exception ex) {
            Log.logServices("getListVpass Exception: " + ex.getMessage());
        }
        return response;
    }


//    public static ResponseMessage1 updateGroup(int funcId, String data, int diviceID) {
//        Log.logServices("118 Thanh cong" + data);
//        if(May1){
//            goiDenMayHai(funcId, data, 2, "");
//        }
//        ResponseMessage1 response = new ResponseMessage1();
//        response.funcId = 118;
//        try {
//            UpdateGroupRequest uGR = new Gson().fromJson(data, UpdateGroupRequest.class);
//            if (uGR == null || data == null || data.equals("")) {
//
//                response = StatusResponse(3);
//            } else if (uGR.mcID != null && !uGR.mcID.equals("")) {
//                response = TachNguoiVeDiem2(uGR.mcID.trim());
//                //response.msgCode =1;
//            }
//
//        } catch (Exception ex) {
//            Log.logServices("updateGroup Exception: " + ex.getMessage());
//            response = StatusResponse(3);
//        }
//        return response;
//    }


}