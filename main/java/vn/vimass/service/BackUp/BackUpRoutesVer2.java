package vn.vimass.service.BackUp;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ObjectCheckMat;
import vn.vimass.service.entity.ObjectRecordsAndTotalObjectInfoVid;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.ObjectInfoVid;

import static vn.vimass.service.BackUp.BackUpControllerDataBase.checkLuuIPMayDB;
import static vn.vimass.service.BackUp.BackUpControllerDataBase.getInfoFromDbInfoVID;
import static vn.vimass.service.BackUp.BackUpControllerDataBaseVer2.checkMatDB;
import static vn.vimass.service.BackUp.FingerPrint.FPFunC.LuuDeviceIDSerial;

public class BackUpRoutesVer2 {
    public static ResponseMessage1 checkMat(String request, int deviceID) {
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 10603;
        ObjectInfoVid objRes = new ObjectInfoVid();
        try {
            Log.logServices("FunId 10603 thành công!" + request);
            ObjectCheckMat objS = new Gson().fromJson(request, ObjectCheckMat.class);


            if (objS == null || objS.getTextSearch() == null) {
                throw new Exception("Invalid data fields");
            }

            if (objS.getTextSearch() != null&&!objS.getTextSearch().equals("")) {
                objRes = checkMatDB(objS);
                obj.msgCode = 1;
                obj.msgContent = "Success!";
                obj.result = Tool.setBase64(objRes.toString());

            } else {
                obj.msgCode = 2;
                obj.msgContent = "UnSuccess!";
            }
        } catch (Exception E) {
            obj.msgCode = 2;
            obj.msgContent = "Check the data fields again";
            Log.logServices("FunId 10603 exception!" + E.getMessage());
            E.printStackTrace();
        }
        return obj;
    }
    public static ResponseMessage1 dangKyVanTay2(String request, int deviceID) {
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 126;
        try {
            Log.logServices("FunId 126 thành công!" + request);
            LuuDeviceIDSerial();
        } catch (Exception E) {
            obj.msgCode = 2;
            obj.msgContent = "Check the data fields again";
            Log.logServices("FunId 10603 exception!" + E.getMessage());
            E.printStackTrace();
        }
        return obj;
    }
}
