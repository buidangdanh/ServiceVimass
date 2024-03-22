package vn.vimass.service.crawler.bhd.DongBoVsServer;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.ObjectInforDiem;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.TypeThongBao;
import vn.vimass.service.crawler.bhd.SendData.SendDataController;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.QRvsCard;
import vn.vimass.service.table.NhomThietBiDiem.entity.*;

import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;

public class DongBoDiemRaVaoController {

    public static ResponseMessage1 thongBaoCapNhatDiemRaVao(int funcId, long currentime, String data) {
        Log.logServices("thongBaoCaDiemRaVao =============> " + funcId + " /currentime" + currentime + " /" + data);
        ResponseMessage1 response = new ResponseMessage1();
        try {
            response.funcId = funcId;
            ObjectInforDiem obj = new Gson().fromJson(data, ObjectInforDiem.class);
            boolean kq = funcUpate(obj);
            if (kq) {
                response = StatusResponse(1);
                response.result = "update Cap nhat diem ra vao =============> true";
                SendDataController.SendData(12301,data,"");
            } else {
                response = StatusResponse(2);
                response.result = "update Cap nhat diem ra vao =============> false";
            }

        } catch (Exception ex) {
            Log.logServices("thongBaoCapNhatDiemRaVao Exception" + ex.getMessage());
        }
        return response;
    }

    private static boolean funcUpate(ObjectInforDiem obj){
        Log.logServices("funcUpate objVpass =============> " + obj);
        if(obj.status == 1){
            ObjQR ObjQR = QRvsCard.getQRInfo(obj.id);
            if(ObjQR.id != null){
                if(obj.id.equals(ObjQR.id)){
                    ObjInfoQR objInfoQR = getValueObjInfoQR(obj);
                    ObjQR.setInfor(objInfoQR);
                    QRvsCard.update(ObjQR);
                    return true;
                }
            }else{
                ObjQR objQr = getValueObjQR(obj);
                QRvsCard.insertDB(objQr);
                return true;
            }
        }
        if(obj.status == -1){
            ObjQR ObjQR = QRvsCard.getQRInfo(obj.id);
            if(obj.id.equals(ObjQR.id)){
                QRvsCard.delete(obj.id);
                return true;
            }
        }
        return false;
    }

    private static ObjQR getValueObjQR(ObjectInforDiem item){
        ObjQR obj = new ObjQR();
        obj.id = item.id; // id Diem dinh danh, diem thanh toan
        obj.mcID = item.maMcId;
        obj.timeTao = item.timeTao;
        obj.catID = item.catId;
        obj.theDaNangLK = item.theDaNangLK;
        obj.infor = getValueObjInfoQR(item);
        obj.LockDeviceID = "";
        obj.listIDLockDevice = null;
        obj.listGroup = null;
        return obj;

    }

    private static ObjInfoQR getValueObjInfoQR(ObjectInforDiem item){
        ObjInfoQR objInfoQR = null;
        try{
            objInfoQR = new ObjInfoQR();
            objInfoQR.id = item.id;
            objInfoQR.tenCuaHang = item.tenCuaHang;
            objInfoQR.diaChi = item.diaChi;
            objInfoQR.lat = item.lat;
            objInfoQR.lng = item.lng;
            objInfoQR.theDaNangLK = item.theDaNangLK;
            objInfoQR.dsPathURLMayDieuKhien = item.dsPathURLMayDieuKhien;
            objInfoQR.textQRBack = item.textQRBack;
            objInfoQR.textQRRaVao = item.textQRRaVao;
        }catch (Exception e){
            Log.logServices("funcUpate Exception =============> " + objInfoQR.toString());
        }

        Log.logServices("funcUpate getValueObjInfoQR =============> " + objInfoQR.toString());
        return objInfoQR;
    }
}
