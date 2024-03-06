package vn.vimass.service.BackUp.FingerPrint;

import com.google.gson.Gson;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjThemSuaXoaRQ;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;

import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;
import static vn.vimass.service.BackUp.FingerPrint.FPFunC.*;

public class FPRoutes {
    public static ResponseMessage1 trangThaiFP() {
        ResponseMessage1 res = new ResponseMessage1();
        res.funcId = 126;
        try {
            if (statusFP) {
                res.msgCode = 1;
                res.msgContent = "Success!";
            } else {
                res.msgCode = 2;
                res.msgContent = "Unsuccess!";
            }
        } catch (Exception ex) {
            Log.logServices("vanTay Exception!" + ex.getMessage());
        }
        return res;

    }

    public static ResponseMessage1 themSuaXoaFP(int funcId, long time, String data) {
        Log.logServices("127 Thanh cong" + data);
        ResponseMessage1 res = new ResponseMessage1();
        res.funcId = 127;
        try {
            statusFP = false;
            ObjThemSuaXoaRQ oTKR = new Gson().fromJson(data, ObjThemSuaXoaRQ.class);
            if (oTKR == null || data == null || data.equals("")) {
                res = StatusResponse(3);
            } else{
                if(oTKR.type==0){
                    res.result = Tool.setBase64(layFPDataTheoNguoi(oTKR.thongTinNguoi).toString());
                }else if(oTKR.type==1){
                    res = dangKyVanTay(oTKR);
                }
            }

        } catch (Exception ex) {
            Log.logServices("layDanhSachGroup Exception: " + ex.getMessage());
            res = StatusResponse(3);
        }
        return res;

    }




}
