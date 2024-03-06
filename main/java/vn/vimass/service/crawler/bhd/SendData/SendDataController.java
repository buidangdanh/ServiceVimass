package vn.vimass.service.crawler.bhd.SendData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.BackUp.BackUpControllerDataBase;
import vn.vimass.service.crawler.bhd.Data;
import vn.vimass.service.crawler.bhd.DongBoVsServer.DongBoController;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.TypeThongBao;
import vn.vimass.service.crawler.bhd.SendData.entity.ObjectSendData;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ObjectLichSuRaVaoQuetQr;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.table.InforVid;
import vn.vimass.service.table.LichSuRaVaoQuetQR;
import vn.vimass.service.table.NhomThietBiDiem.Nhom;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjGroup;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjPerson;
import vn.vimass.service.table.SendData.IpPC;
import vn.vimass.service.table.SendData.entity.ObjecIpPC;
import vn.vimass.service.table.SendData.entity.ObjectData;
import vn.vimass.service.table.SendData.sendData;
import vn.vimass.service.table.object.ObjectInfoVid;

import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static vn.vimass.service.CallService.CallService.PostREST;

public class SendDataController {
    public static void SendData(int type, String strObjData,String personFace) { // type = 1: luu data lich su ra vao, type = 2..., type = 3...

        try{
            ResponseMessage1 response = null;
            String url = "";
            ArrayList<ObjecIpPC> arr = IpPC.getListIpPC(type);
            if (!arr.isEmpty()) {
                for (ObjecIpPC objecIpPC : arr) {
                    String dataSend = getValueSendData(type, objecIpPC.ip, strObjData);
                    ObjectSendData obj = getValue(objecIpPC.ip, type, dataSend);
                    url ="http://"+objecIpPC.ip+":58080/autobank/services/vimassTool/"+objecIpPC.nameServer;
                    String res = PostREST(url, new Gson().toJson(obj));
                    if(res.length() > 0){
                        response = new Gson().fromJson(res, ResponseMessage1.class);
                        if (response.msgCode != 1) {
                            notSendData(objecIpPC.ip ,type, strObjData, personFace);
                            System.out.println("send data fail");
                        } else {
                            System.out.println("send data success");
                            deleteData(type, objecIpPC.ip);
                        }

                    }else{
                        System.out.println("send data fail 1");
                        notSendData(objecIpPC.ip ,type, strObjData, personFace);
                    }

                }
            }
        }catch (Exception e){
            System.out.println("SendData Exception: " + e.getMessage());
        }
    }

    private static String getValueSendData(int type, String ip, String strObjData){
        String result = "";
        switch (type) {
            case 100: //lich su ra vao
                ObjectLichSuRaVaoQuetQr item = new Gson().fromJson(strObjData, ObjectLichSuRaVaoQuetQr.class);
                ArrayList<ObjectLichSuRaVaoQuetQr> list = sendData.getListDataBackUpLichSuRaVao(type, ip);
                list.add(item);
                result = list.toString();
                break;
            case 101: //khuon mat
                ObjectInfoVid objInfoVid = new Gson().fromJson(strObjData, ObjectInfoVid.class);
                ArrayList<ObjectInfoVid> listFaceData = sendData.getListDataBackUpFaceData(type, ip);
                listFaceData.add(objInfoVid);
                result = listFaceData.toString();
                break;
            case 102: // nhom, qr
                ArrayList<TypeThongBao> listQRvsGRvsVpass = sendData.getListDataBackUpQRvsGrvsVpass(type, ip);
                TypeThongBao obj = new Gson().fromJson(strObjData, TypeThongBao.class);
                listQRvsGRvsVpass.add(obj);

                result = listQRvsGRvsVpass.toString();
                break;
            default:
        }

        return result;
    }

    private static void deleteData(int type , String ip ){ // xoa du lieu khi gui thanh cong

        switch (type) {
            case 100: // delete lich su ra vao
                sendData.delete(100, ip);
                break;
            case 2: // delete khuon mat, thiet bi
                sendData.delete(101, ip);
                break;
            case 3:
                sendData.delete(102, ip);
                break;
            default:
        }

    }

    private static void notSendData(String ipPc, int type,String strObjData , String personFace){

                ObjectData item = new ObjectData();
                item.id = Tool.generateSessionKeyUpCase(5) + new Date().getTime();;
                item.ipPC = ipPc;
                item.data = strObjData;
                item.personFace = personFace;
                item.type = type;
                sendData.insertDb(item);
    }

    private static ObjectSendData getValue( String ipPC,int type, String data){

        ObjectSendData obj = new ObjectSendData();
        obj.funcID = 125;
        obj.ipPC = ipPC;
        obj.data = data;
        obj.type = type;
        return obj;
    }

    public static ResponseMessage1 resReceiveData(int funcId, String data){
        ResponseMessage1 response = null;
        if(!data.isEmpty()){
            // gui data thanh cong
            ObjectSendData obj = new Gson().fromJson(data, ObjectSendData.class);
            response.msgCode = 1;
            response.msgContent = "true";
            updateReceiveData(obj);
        }else{
            // gui data khong thanh cong
            response.msgCode = 2;
            response.msgContent = "false";
        }
        return response;
    }

    private static void updateReceiveData(ObjectSendData objectSendData){

        switch (objectSendData.type) {
            case 100: // update lich su ra vao

                Type collectionType = new TypeToken<List<ObjectLichSuRaVaoQuetQr>>() {
                }.getType();
                ArrayList<ObjectLichSuRaVaoQuetQr> arrItemLichSuRaVao = new Gson().fromJson(objectSendData.data, collectionType);
                for(ObjectLichSuRaVaoQuetQr item: arrItemLichSuRaVao){
                    LichSuRaVaoQuetQR.taoDuLieu(item);
                }
                break;
            case 2: // update du lieu khuon mat

                Type collectionType1 = new TypeToken<List<ObjectInfoVid>>() {
                }.getType();
                ArrayList<ObjectInfoVid> arrItemFaceData = new Gson().fromJson(objectSendData.data, collectionType1);
                for(ObjectInfoVid item: arrItemFaceData){
                   // InforVid.InsertData(item);

                    BackUpControllerDataBase.capNhatVaoInfoVid(item);
                }
                break;

            case 3:
                DongBoController.updateSendData(objectSendData.data);
                break;

            default:
        }
    }
}
