package vn.vimass.service.BackUp.FingerPrint;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.Gson;
import vn.vimass.service.BackUp.FingerPrint.Obj.FingerData;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjFP;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjThemSuaXoaRQ;
import vn.vimass.service.BackUp.FingerPrint.Obj.RequestTTFP;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;
import static vn.vimass.service.BackUp.FingerPrint.FPComandPacket.clearTemp;
import static vn.vimass.service.BackUp.FingerPrint.FPComandPacket.identifyFree;
import static vn.vimass.service.BackUp.FingerPrint.FPDataBase.*;
import static vn.vimass.service.BackUp.FingerPrint.FPFunC.*;
import static vn.vimass.service.BackUp.FingerPrint.Obj.CommandList.FP_Cancel;

public class FPRoutes {
    public static HashMap<String,Boolean> HashIP = new HashMap<>();
    public static ResponseMessage1 trangThaiFP(String data) {
        ResponseMessage1 res = new ResponseMessage1();
        res.funcId = 126;

        try {
            RequestTTFP requestTTFP = new Gson().fromJson(data,RequestTTFP.class);
            for(ObjFP arr: getThietBiFP()){
                if(!HashIP.containsKey(arr.id)){
                    HashIP.put(arr.id,true);
                }
            }
            if(HashIP!=null&&HashIP.size()>0){
                if(HashIP.containsKey(requestTTFP.idFP)){
                    if(HashIP.get(requestTTFP.idFP)){
                        res.msgCode = 1;
                        res.msgContent = "Success!";
                    }else {
                        res.msgCode = 2;
                        res.msgContent = "UnSuccess!";
                    }
                }
            }
        } catch (Exception ex) {
            Log.logServices("vanTay Exception!" + ex.getMessage());
        }
        return res;

    }

    public static ResponseMessage1 themSuaXoaFP(int funcId, long time, String data) {
        Log.logServices("127 Thanh cong" + data);
        ResponseMessage1 res = new ResponseMessage1();
        ArrayList<ObjFP> listFPLocal = new ArrayList<>();
        res.funcId = 127;
        try {
            listFPLocal = getThietBiFP();
            ObjThemSuaXoaRQ o = new ObjThemSuaXoaRQ();
            ArrayList<FingerData> dataFPTrongMini = new ArrayList<>();
            ObjThemSuaXoaRQ requestClient = new Gson().fromJson(data, ObjThemSuaXoaRQ.class);
            if (requestClient == null || data == null || data.equals("")) {
                res = StatusResponse(3);
            } else {
                chuyenTrangThai(requestClient.idFP,false);
                Thread.sleep(1000);
                capNhatPort(listFPLocal);
                Thread.sleep(300);
                String COM = "";
                dataFPTrongMini = layFPDataTheoNguoi(requestClient.thongTinNguoi);
                COM = truyenIDRaCOM(requestClient.idFP);
                    if (COM != null && !COM.equals("")) {
                        if (requestClient.type == 0) {
                            res.msgCode = 1;
                            res.result = Tool.setBase64(dataFPTrongMini.toString());
                        } else if (requestClient.type == 1) {
                            if (dataFPTrongMini.size() >= 10) {
                                res.msgCode = 2;
                                res.msgContent = Tool.setBase64("Vượt số lượng vân tay cho phép");
                            } else {
                                res = dangKyVanTay(requestClient, COM, dataFPTrongMini);
                            }
                        } else if (requestClient.type == 2) {
                            for (FingerData fingerData : dataFPTrongMini) {
                                if (fingerData.emptyID.equals(requestClient.emptyID)) {
                                    fingerData.name = requestClient.nameFP;
                                }
                            }
                            if (capNhatCoSoDuLieuFPCuThe(requestClient.thongTinNguoi.idVid, requestClient.thongTinNguoi.personName, dataFPTrongMini)) {
                                res.msgCode = 1;
                                res.msgContent = Tool.setBase64("Thành công");
                            } else {
                                res.msgCode = 2;
                                res.msgContent = Tool.setBase64("Không thành công");
                            }

                        } else if (requestClient.type == 3) {
                            xoaKhoiDB(dataFPTrongMini, requestClient);
                            if(xoaKhoiThietBi(requestClient, COM)){
                                res.msgCode = 1;
                                res.msgContent = Tool.setBase64("Thành công");
                            }else {
                                res.msgCode = 2;
                                res.msgContent = Tool.setBase64("Không thành công");
                            }


                        }
                    } else {
                        res.msgCode = 3;
                        res.msgContent = Tool.setBase64("Thiết bị vân tay chưa được active");
                    }


            }
            chuyenTrangThai(requestClient.idFP,true);

        } catch (Exception ex) {
            Log.logServices("themSuaXoaFP Exception: " + ex.getMessage());
            res = StatusResponse(3);
        }

        return res;

    }

    private static void chuyenTrangThai(String idFP, boolean b) {
        try {
            if(HashIP.containsKey(idFP)){
                HashIP.put(idFP,b);
            }
        }catch (Exception ex){
            Log.logServices("chuyenTrangThai Exception: " + ex.getMessage());

        }
    }

    public static boolean xoaKhoiThietBi(ObjThemSuaXoaRQ requestClient, String COM) {
        boolean kq = false;
        try {
            if (sendData(SerialPort.getCommPort(COM), clearTemp(requestClient.emptyID), 100).substring(16, 20).equals(requestClient.emptyID)) {
                kq = true;
            }
        } catch (Exception ex) {
            Log.logServices("xoaKhoiThietBi Exception: " + ex.getMessage());

        }
        return kq;
    }


}
