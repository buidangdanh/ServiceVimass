package vn.vimass.service.crawler.bhd;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import vn.vimass.service.entity.*;

import vn.vimass.service.log.FileManager;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.IPaddress;
import vn.vimass.service.table.LichSuRaVaoQuetQR;
import vn.vimass.service.table.object.ObjectIpAddress;
import vn.vimass.service.table.object.ObjectTgDenVe;

import static vn.vimass.service.BackUp.BackUpFunCVer2.goiDenMayHai;
import static vn.vimass.service.BackUp.BackUpFunction.*;


@Path("/vimassTool")
@Produces("application/json;charset=utf-8")
public class RaVaoWebservice {
    @GET
    @Path("/test")
    public String getDanhSach() {
        return "ok";
   }

    @SuppressWarnings("unused")
    @POST
    @Path("/dieuPhoi")
        public ResponseMessage1 dieuPhoi(String input) {
        ResponseMessage1 res1 = new ResponseMessage1();
        try {
            checkDichVuTamNgung();
            Log.logServices("Navigate input: " + input);
            ResponseIP res = new Gson().fromJson(input, ResponseIP.class);
            if(May1){
                goiDenMayHai(res.funcId, res.data, res.device, res.listIdDiem);
            }
            res1 = DieuPhoiController.responseMessage(res.funcId,res.currentime, res.data, res.device);

            Log.logServices(" Navigate result: " + res1);


        }catch (Exception ex){
            Log.logServices(" dieuPhoi Exception: " + ex.getMessage());

        }

        return res1;
    }


    @GET
    @Path("/listDiaDiem")
    public ArrayList<ObjectIpAddress> listDiaDiem() {
        ArrayList<ObjectIpAddress> list = new ArrayList<ObjectIpAddress>();
        list = IPaddress.getListIpAddress();
        return list;
    }


    @POST
    @Path("/backUp/getDataLichSuRaVaoQR")
    public ResponseMessage getListLichSuRaVaoQuetQr(String input) {
        Log.logServices("getListLichSuRaVaoQuetQr request input: " + input);
        ResponseMessage res = new ResponseMessage();
        ObjectTime item = new Gson().fromJson(input, ObjectTime.class);
        ArrayList<ObjectLichSuRaVaoQuetQr> arr = new ArrayList<ObjectLichSuRaVaoQuetQr>();

        try {

            if(item.timeTo != 0 && item.timeFrom != 0){

                arr = LichSuRaVaoQuetQR.getListLichSuRaVaoBackUp(item);
                res.msgCode = 1;
                res.msgContent = "Thành công";
                res.result = arr.toString();

            }else{
                res.msgCode = error.YEU_CAU_KHONG_HOP_LE;
                res.msgContent = "Yêu cầu không hợp lệ!";
                res.result = arr.toString();
            }

        } catch (Exception e) {
            Log.logServices("getListLichSuRaVaoQuetQr Exception: " + e.getMessage());
        }
        return res;
    }

    @POST
    @Path("/backUp/getDataThoiGianLamViec")
    public ResponseMessage getListThoiGianLamViec(String input) {
        Log.logServices("getListThoiGianLamViec request input: " + input);
        ResponseMessage res = new ResponseMessage();
        ObjectTime item = new Gson().fromJson(input, ObjectTime.class);
        ArrayList<ObjectTgDenVe> arr = new ArrayList<ObjectTgDenVe>();

        try {

            if(item.timeTo != 0 && item.timeFrom != 0){

                arr = LichSuRaVaoQuetQR.getListThoiGianRaVaoTrongNgayBackUp(item);
                res.msgCode = 1;
                res.msgContent = "Thành công";
                res.result = arr.toString();

            }else{
                res.msgCode = error.YEU_CAU_KHONG_HOP_LE;
                res.msgContent = "Yêu cầu không hợp lệ!";
                res.result = arr.toString();
            }

        } catch (Exception e) {
            Log.logServices("getListThoiGianLamViec Exception: " + e.getMessage());
        }
        return res;
    }

    @SuppressWarnings("unused")
    @POST
    @Path("/backUpToPC")
    public ResponseMessage postBackUp(String input) {
        return RaVaoFunc.taoMoiDuLieuBackUp(input);
    }

    @GET
    @Path("/getBackUp")
    public ResponseMessage getBackUp() {
        return RaVaoFunc.getDataBackUp();
    }

    @POST
    @Path("/deleteItemBackUpSuccess")
    public ResponseMessage deleteItemBackUp(String input){
        return RaVaoFunc.xoaDuLieuBackUp(input);
    }
}
