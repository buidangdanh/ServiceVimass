package vn.vimass.service.BackUp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjFP;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjGetVanTay;
import vn.vimass.service.entity.ObjectThietBiVPassRes;
import vn.vimass.service.entity.ObjectgetThietBiVPass;
import vn.vimass.service.entity.ResponseIP;
import vn.vimass.service.entity.ResponseMessage;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;
import vn.vimass.service.utils.ServivceCommon;

import java.util.ArrayList;
import java.util.Date;

import static vn.vimass.service.BackUp.BackUpControllerDataBaseVer2.themMoiThietBiVPassDB;
import static vn.vimass.service.BackUp.BackUpFunction.urlMay2;
import static vn.vimass.service.CallService.CallService.PostREST;

public class BackUpFunCVer2 {
    public static String mcID = "VM_TEST";
    public static void goiDenMayHai(int FunID, String data, int deviceID, String other) {

        Runnable task = new Runnable() {
            @Override
            public void run() {

                try {
                    ResponseIP objRequest = new ResponseIP();
                    objRequest.funcId = FunID;
                    objRequest.currentime = new Date().getTime();
                    objRequest.device = deviceID;
                    objRequest.data = data;
                    String url = urlMay2;
                    objRequest.listIdDiem = other;
                    Log.logServices("goiDenMayHai 1: " + urlMay2);

                    String kq = PostREST(url, new Gson().toJson(objRequest));
                    Log.logServices("request goiDenMayHai" + new Gson().toJson(objRequest));

                } catch (Exception ex) {
                    Log.logServices("goiDenMayHai Exception!" + ex.getMessage());

                }
            }
        };
        // Khởi tạo và khởi chạy thread
        Thread thread = new Thread(task);
        thread.start();
    }
    public static void goiDenMayHai2(int FunID, String data, int deviceID, String other) {

        Runnable task = new Runnable() {
            @Override
            public void run() {

                try {
                    ResponseIP objRequest = new ResponseIP();
                    objRequest.funcId = FunID;
                    objRequest.currentime = new Date().getTime();
                    objRequest.device = deviceID;
                    objRequest.data = data;
                    String url = urlMay2;
                    objRequest.listIdDiem = other;
                    Log.logServices("goiDenMayHai 1: " + urlMay2);

                    String kq = PostREST(url, new Gson().toJson(objRequest));
                    Log.logServices("request goiDenMayHai" + new Gson().toJson(objRequest));

                } catch (Exception ex) {
                    Log.logServices("goiDenMayHai Exception!" + ex.getMessage());

                }
            }
        };
        // Khởi tạo và khởi chạy thread
        Thread thread = new Thread(task);
        thread.start();
    }
    public static void layThietBiVPass(){
        try{
            ObjectgetThietBiVPass obj = new ObjectgetThietBiVPass();
            obj.user = "0966074236";
            obj.perNum = 0;
            obj.mcID = mcID;
            obj.currentTime = new Date().getTime();
            obj.typeDevice = 0;
            obj.deviceID = "";
            obj.cks = ServivceCommon.bamMD5("Y99JAuGfmYaBYYyycsLy26" + obj.mcID + obj.currentTime);
            String url = "http://210.245.8.7:12318/vimass/services/VUHF/getMayChuDonVi";
            String kq = PostREST(url, new Gson().toJson(obj));
            Log.logServices("request" + new Gson().toJson(obj));

            ResponseMessage responseMessage = new Gson().fromJson(kq, ResponseMessage.class);
            if(responseMessage!=null&&responseMessage.msgCode ==1){
                ObjectThietBiVPassRes objectThietBiVPassRes = new Gson().fromJson(responseMessage.result.toString(),ObjectThietBiVPassRes.class);
                if(objectThietBiVPassRes.devices!=null&&objectThietBiVPassRes.devices.size()>0){
                    for (ObjVpass objVpasSV: objectThietBiVPassRes.devices){
                        themMoiThietBiVPassDB(objVpasSV);
                    }
                }
            }
            Log.logServices("layThietBiVPass" + responseMessage.toString());
        }catch (Exception ex){
            Log.logServices("layThietBiVPass" + ex.getMessage());

        }
    }
    public static ArrayList<ObjFP> layThietBiVanTay(){
        ArrayList<ObjFP> listFPSV = new ArrayList<>();
        try{
            ObjGetVanTay obj = new ObjGetVanTay();
            obj.user = "0966074236";
            obj.perNum = 0;
            obj.deviceID = 3;
            obj.mcID= "DaiDongTTTHCS";
            obj.currentTime = new Date().getTime();
            obj.cks = ServivceCommon.bamMD5(obj.user + obj.deviceID + "ZgVCHxqMd$aNCm54X2YHD" + obj.currentTime + obj.mcID) ;
            String url = "http://210.245.8.7:12318/vimass/services/VUHF/dsVanTay";
            String kq = PostREST(url, new Gson().toJson(obj));
            Log.logServices("request" + new Gson().toJson(obj));

            ResponseMessage responseMessage = new Gson().fromJson(kq, ResponseMessage.class);
            if(responseMessage!=null&&responseMessage.msgCode ==1){
                listFPSV = new Gson().fromJson(responseMessage.result, new TypeToken<ArrayList<ObjFP>>() {
                }.getType());
            }
            Log.logServices("layThietBiVanTay" + responseMessage.toString());
        }catch (Exception ex){
            Log.logServices("layThietBiVanTay" + ex.getMessage());

        }
        return listFPSV;
    }

}
