package vn.vimass.service.crawler.bhd.DongBoVsServer;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjFP;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjFPSua;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjGetVanTay;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjectSuaVanTay;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.TypeThongBao;
import vn.vimass.service.crawler.bhd.SendData.SendDataController;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ResponseMessage;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.InforVid;
import vn.vimass.service.table.NhomThietBiDiem.*;
import vn.vimass.service.table.NhomThietBiDiem.entity.*;
import vn.vimass.service.table.SendData.sendData;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.utils.ServivceCommon;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.sql.SQLOutput;
import java.util.*;

import static vn.vimass.service.BackUp.BackUpControllerDataBaseVer2.*;
import static vn.vimass.service.BackUp.BackUpFunCVer2.*;
import static vn.vimass.service.BackUp.BackUpFunction.May1;
import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;
import static vn.vimass.service.BackUp.FingerPrint.FPFunC.*;
import static vn.vimass.service.BackUp.FingerPrint.FPFunC.capNhatIdFP;
import static vn.vimass.service.CallService.CallService.PostREST;

public class DongBoController {
    public static ResponseMessage1 thongBaoCapNhatNhom_ThietBiKhoa_QRvsThe(int funcId, long currentime, String data) {
        Log.logServices("thongBaoCapNhat =============> " + funcId + " /currentime" + currentime + " /" + data);

        System.out.println("int put 123: " + data);


        ResponseMessage1 response = new ResponseMessage1();
        try {
            response.funcId = funcId;

            TypeThongBao obj = new Gson().fromJson(data, TypeThongBao.class);
            Log.logServices("TypeThongBao : " + obj);
            switch (obj.type) {
                case 1:
                    if (!obj.listGr.isEmpty()) {
                        funcNhom(obj.listGr);
                        response = StatusResponse(1);
                        response.result = "update Group =============> true";
                        SendDataController.SendData(102, data, "");
                    } else {
                        response = StatusResponse(2);
                        response.result = "update Group =============> false";
                    }
                    break;
                case 2:
                    if (!obj.listDevice.isEmpty()) {

                        Log.logServices("thongBaoCapNhatThietBi =============> ok");
                        funcThietBiKhoa(obj.listDevice);
                        response = StatusResponse(1);
                        response.result = "update Lock =============> true";
                        SendDataController.SendData(102, data, "");

                    } else {
                        response = StatusResponse(2);
                        response.result = "update Lock =============> false";
                    }
                    break;
                case 3:
                    if (obj.type == 3 && !obj.listDiem.isEmpty()) {

                        Log.logServices("thongBaoCapNhatQRvsCard =============> ok");
                        funcQRvsCard(obj.listDiem);
                        response = StatusResponse(1);
                        response.result = "update QR vs Card =============> true";
                        SendDataController.SendData(102, data, "");

                    } else {
                        response = StatusResponse(2);
                        response.result = "update QR vs Card =============> false";
                    }
                    break;
                case 4:
                    Log.logServices("thongBaoCapNhatQRvsCard =============> ok");
                    funcVPass(obj.listVpass);
                    response = StatusResponse(1);
                    response.result = "update QR vs Card =============> true";
                    SendDataController.SendData(102, data, "");

                    break;
                case 5:
                    Log.logServices("thongBaoCapNhatQRvsCard =============> ok");
                    funcFP(obj.listFP);
                    response = StatusResponse(1);
                    response.result = "update QR vs Card =============> true";
                    SendDataController.SendData(102, data, "");

                    break;
                default:
                    Log.logServices("thongBaoCapNhat =============> ok");
            }
        } catch (Exception ex) {
            Log.logServices("thongBaoCapNhatNhom_ThietBiKhoa_QRvsThe Exception" + ex.getMessage());
        }
        return response;
    }

    private static void funcFP(ArrayList<ObjFP> listFP) {
        HashMap<String, String> hashFPLocal = new HashMap<>();
        HashMap<String, String> hashFPLocal2 = new HashMap<>();
        ArrayList<ObjFP> listFPLocal = new ArrayList<>();
        ArrayList<ObjFP> listFPSV = new ArrayList<>();
        try {
            //Lay ip internet
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            //lay thiet bi van tay hien tai
            listFPLocal = getThietBiFP();
            for (ObjFP ar : listFPLocal) {
                if (ar.port != null && !ar.port.equals("")) {
                    hashFPLocal.put(ar.port, ar.idDonVi);
                }
            }
            Log.logServices("test" + listFPLocal.toString());
            capNhatPort(listFPLocal);
            //lay thiet bi van tay tu sv
            listFPSV = layThietBiVanTay();
            String idMoiNhat = taoIdFPMoiNhat(listFPSV);
            Log.logServices("funcFP" + listFPLocal.toString());
            if (listFP != null && listFP.size() > 0) {
                for (ObjFP objFP : listFP) {
                    if (objFP.type == 1) {
                        themMoiThietBiVanTayDB(objFP);
                        if (true) {
                            if (objFP.idDonVi == null || objFP.idDonVi.equals("")) {
                                objFP.idDonVi = idMoiNhat;
                            }
                            capNhatIdFP(idMoiNhat, hashFPLocal);
                            capNhatLenServer(objFP);
                        }
                    }else if(objFP.type==2){
                        capNhatThietBiVanTayDB(objFP);
                    }else {
                        xoaThietBiFP(objFP.id);
                        for (ObjFP ar : listFPLocal) {
                            if (ar.idDonVi.equals(objFP.idDonVi)) {
                                xoaFpID(ar.port);
                            }
                        }

                    }
                }
            }

        } catch (Exception ex) {
            Log.logServices("funcVPass Exception" + ex.getMessage());
        }
    }




    private static void funcVPass(ArrayList<ObjVpass> listVpassServer) {
        ArrayList<ObjVpass> listVpassLocal = new ArrayList<>();
        ArrayList<ObjVpass> arr = new ArrayList<>();
        try {
            listVpassLocal = getThietBiVPass();
            Log.logServices("funcVPass" + listVpassLocal.toString());

            if (listVpassLocal == null || listVpassLocal.size() < 1) {
                layThietBiVPass();
            } else {
                if (listVpassServer != null && listVpassServer.size() > 0) {
                    for (ObjVpass objVpass : listVpassServer) {
                        if (objVpass.type == 11) {
                            themMoiThietBiVPassDB(objVpass);
                        } else if (objVpass.type == 3) {
                            xoaThietBiVPass(objVpass.id);
                            if (objVpass.typeDevice == 1 || objVpass.typeDevice == 2) {
                                arr = getThietBiVPassTheoDieuKien(1);
                            } else if (objVpass.typeDevice == 3 || objVpass.typeDevice == 4) {
                                arr = getThietBiVPassTheoDieuKien(2);
                            } else {
                                arr = getThietBiVPassTheoDieuKien(3);
                            }
                            if (arr != null) {
                                for (ObjVpass obj : arr) {
                                    if (obj.stt > objVpass.stt) {
                                        obj.stt = obj.stt - 1;
                                        capNhatThietBiVPass(obj);
                                    }
                                }
                            }

                        } else if (objVpass.type == 2) {
                            capNhatThietBiVPass(objVpass);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.logServices("funcVPass Exception" + ex.getMessage());
        }
    }

    private static void themMoiThietBiVPass(ArrayList<ObjVpass> listVpassServer, ArrayList<ObjVpass> listVpassLocal) {
        try {
            Set<String> idInListVpassLocal = new HashSet<>();

            for (ObjVpass objVPLocal : listVpassLocal) {
                idInListVpassLocal.add(objVPLocal.id);
            }
            for (ObjVpass objVPServer : listVpassServer) {
                if (!idInListVpassLocal.contains(objVPServer.id)) {
                    themMoiThietBiVPassDB(objVPServer);
                }
            }
        } catch (Exception ex) {
            Log.logServices("themMoiThietBiVPass Exception" + ex.getMessage());

        }
    }

    private static void CapNhatThietBiVPass(ArrayList<ObjVpass> listVpassServer, ArrayList<ObjVpass> listVpassLocal) {
        try {
            Set<String> idInListVpassLocal = new HashSet<>();

            for (ObjVpass objVPLocal : listVpassLocal) {
                idInListVpassLocal.add(objVPLocal.id);
            }
            for (ObjVpass objVPServer : listVpassServer) {
                if (idInListVpassLocal.contains(objVPServer.id)) {
                    capNhatThietBiVPass(objVPServer);
                }
            }

        } catch (Exception ex) {
            Log.logServices("xoaIDKhoiThietBiVPass Exception" + ex.getMessage());
        }
    }

    private static void xoaIDKhoiThietBiVPass(ArrayList<ObjVpass> listVpassServer, ArrayList<ObjVpass> listVpassLocal) {
        try {
            Set<String> idInListVpassServer = new HashSet<>();

            for (ObjVpass objVPServer : listVpassServer) {
                idInListVpassServer.add(objVPServer.id);
            }
            for (ObjVpass objVPLocal : listVpassLocal) {
                if (!idInListVpassServer.contains(objVPLocal.id)) {
                    xoaThietBiVPass(objVPLocal.id);
                }
            }
        } catch (Exception ex) {
            Log.logServices("xoaIDKhoiThietBiVPass Exception" + ex.getMessage());
        }
    }


    public static void updateSendData(String data) {

        Type collectionType = new TypeToken<List<TypeThongBao>>() {
        }.getType();
        List<TypeThongBao> list = new Gson().fromJson(data, collectionType);

        for (TypeThongBao item : list) {
            TypeThongBao obj = new Gson().fromJson(item.toString(), TypeThongBao.class);
            switch (obj.type) {
                case 1:
                    if (!obj.listGr.isEmpty()) {
                        funcNhom(obj.listGr);
                    }
                    break;
                case 2:
                    if (!obj.listDevice.isEmpty()) {
                        funcThietBiKhoa(obj.listDevice);
                    }
                    break;
                case 3:
                    if (obj.type == 3 && !obj.listDiem.isEmpty()) {
                        funcQRvsCard(obj.listDiem);
                    }
                    break;

                case 4:
                    if (obj.type == 4 && !obj.listVpass.isEmpty()) {
                        funcVPass(obj.listVpass);
                    }
                    break;
                default:
                    Log.logServices("thongBaoCapNhat =============> ok");
            }
        }
    }

    private static void funcNhom(ArrayList<ObjGroup> listGr) {

        for (ObjGroup itemGr : listGr) {

            if (itemGr.type == 1) { // them nhom moi
                Nhom.insertDB(itemGr);

                if (itemGr.groupLevel == 1) {
                    upDatePersonOfGroup(itemGr.listPer, itemGr.mcID);
                }
                System.out.println("nhay vao =============> 1");
                if (itemGr.groupLevel == 2) {
                    System.out.println("itemGr.listGr =============>" + itemGr.listGr);
                    Nhom.update(itemGr);
                }
            }

            if (itemGr.type == 2) { // cap nhat nhom
                if (itemGr.groupLevel == 1) {
                    upDatePersonOfGroup(itemGr.listPer, itemGr.mcID);
                }
                System.out.println("nhay vao =============> 2");
                if (itemGr.groupLevel == 2) {
                    System.out.println("itemGr.listGr =============>" + itemGr.listGr);
                    Nhom.update(itemGr);
                }
            }

            if (itemGr.type == 3) { // xoa nhom
                deleteGr(itemGr);
            }

        }
    }

    private static void upDatePersonOfGroup(ArrayList<ObjPerson> listPer, String mcID) {
        ObjectInfoVid objectInfoVid = null;
        System.out.println("listPer =============>" + listPer);
        for (ObjPerson itemPer : listPer) {
            String strRep = "";
            if (itemPer.vID.contains("V")) {
                strRep = itemPer.vID.replace("V", "");
            } else {
                strRep = itemPer.vID;
            }
            itemPer.setvID(strRep);

            System.out.println("itemPer =============>" + itemPer.type);

            if (itemPer.type == 1) {
                System.out.println("Them user vao nhom, tao du lieu face = '' =============>" + itemPer);

                PersonOfGroup.taoDuLieu(itemPer);

                if (itemPer.vID.length() > 0) {
                    boolean check = checkVidExist(itemPer.vID, itemPer.name);
                    if (check) {
                        objectInfoVid = getValueVid(itemPer, mcID);
                        System.out.println(" tao du lieu face = '' =============>" + objectInfoVid);
                        InforVid.InsertData(objectInfoVid);
                    }
                }
            }

            if (itemPer.type == 2) {
                System.out.println("cap nhat user =============>" + itemPer);
                PersonOfGroup.update(itemPer);
            }

            if (itemPer.type == 3) {
                System.out.println("xóa user trong nhóm, khuon mat =============>" + itemPer);
                // InforVid.delete(itemPer.vID, itemPer.name);
                sendData.deleteFaceDataBackUp(itemPer.name);
                PersonOfGroup.deteleItem(itemPer.vID, itemPer.name, itemPer.id);

            }
        }
    }

    private static void deleteGr(ObjGroup objGr) {

        if (objGr.groupLevel == 1) {
            for (ObjPerson item : objGr.listPer) {
                InforVid.delete(item.vID, item.name); // xoa du lieu khuon mat trong he thong
            }
            PersonOfGroup.deteleItem("", "", objGr.id); // xoa tat ca user cua 1 nhom can xoa
            GroupOfQR.delete(objGr.id); // xoa nhom thuoc Diem ra vao
            Nhom.delete(objGr.id);// xoa nhom trong danh sach;
        }
        if (objGr.groupLevel == 2) {
            Nhom.delete(objGr.id);
        }
    }

    private static boolean checkVidExist(String vID, String personName) {

        String strBoDau = Tool.removeVietnameseAccents(personName);
        System.out.println("input checkVidExist =============>" + strBoDau);

        boolean Exist = InforVid.checkExist(vID, strBoDau);
        if (Exist) {
            System.out.println(" item check =============>" + false);
            return false;
        } else {
            System.out.println(" item check =============>" + true);
            return true;
        }
    }

    private static ObjectInfoVid getValueVid(ObjPerson itemPer, String mcID) {
        ObjectInfoVid object = new ObjectInfoVid();
        object.id = itemPer.vID + itemPer.groupID + Tool.generateSessionKeyUpCase(5); // = idVid +
        object.idVid = itemPer.vID;
        object.uID = itemPer.uID;
        object.maSoThue = " ";
        object.diaChi = " ";
        object.dienThoai = itemPer.sdt;
        object.email = " ";
        object.gioiTinh = " ";
        object.hoTen = itemPer.name;
        object.ngayCapCCCD = " ";
        object.ngaySinh = " ";
        object.quocTich = " ";
        object.soCanCuoc = " ";
        object.soTheBHYT = " ";
        object.tk = " ";
        object.anhDaiDien = " ";
        object.anhCMNDMatTruoc = " ";
        object.anhCMNDMatSau = " ";
        object.faceData = " ";
        object.personName = Tool.removeVietnameseAccents(itemPer.name);
        object.chucDanh = itemPer.chucDanh;
        object.personPosition = itemPer.perNum;
        object.mcID = mcID;
        return object;
    }

    private static void funcThietBiKhoa(ArrayList<ObjLockDevice> listDevice) {
        Log.logServices("funcThietBiKhoa =============> ok");

        for (ObjLockDevice itemThietBiKhoa : listDevice) {

            ObjLockDevice objTb = ThietBiKhoa.getItem(itemThietBiKhoa.idLookDevice);

            if (objTb != null) {
                // update data
                if (itemThietBiKhoa.type == 2) {
                    objTb.setIdLookDevice(itemThietBiKhoa.ip);
                    objTb.setPortD(itemThietBiKhoa.portD);
                    objTb.setNameDevice(itemThietBiKhoa.nameDevice);
                    objTb.setTypeD(itemThietBiKhoa.typeD);
                    objTb.setMess(itemThietBiKhoa.mess);
                    ThietBiKhoa.update(objTb);
                }
                // delete data
                if (itemThietBiKhoa.type == 3) {

                    ArrayList<ObjectGroupOfQR> list = GroupOfQR.getListGrOfQR(objTb.idLookDevice);
                    for (ObjectGroupOfQR item : list) {
                        item.setIdQR("");
                        GroupOfQR.update(item);

                    }
                    ThietBiKhoa.delete(objTb.idLookDevice);

                }

            } else {
                ThietBiKhoa.insertDB(itemThietBiKhoa);
            }

        }
    }

    private static void funcQRvsCard(ArrayList<ObjQR> listDiem) {
        Log.logServices("funcQRvsCard =============> listDiem" + listDiem);
        for (ObjQR itemQRvsCard : listDiem) {

            ObjQR objQR = QRvsCard.getQRInfo(itemQRvsCard.id);
            // update data
            //  if( objQR != null){
            if (itemQRvsCard.type == 2) {

                if (itemQRvsCard.listIDLockDevice != null) {
                    for (String itemLockDevice : itemQRvsCard.listIDLockDevice) {
                        objQR.setLockDeviceID(itemLockDevice);
                        QRvsCard.update(objQR);
                    }
                } else {
                    QRvsCard.insertDB(objQR);
                }

                if (!itemQRvsCard.listGroup.isEmpty()) {

                    GroupOfQR.delete(itemQRvsCard.id);
//                        if(itemQRvsCard.id.equals("1704860940683qzmgu")){
//                            System.out.println("idQR : "+itemQRvsCard.id);
//                        }
                    for (ObjGroup itemGroup : itemQRvsCard.listGroup) {
                        ObjGroup objGr = Nhom.getItemQRGroup(itemGroup.id);
                        ObjectGroupOfQR objectGroupOfQR = getValueGroupOfQR(objGr, itemQRvsCard.id);
//                            if(itemQRvsCard.id.equals("1704860940683qzmgu")){
//                                System.out.println("objGr ====>" + objGr +"\n objectGroupOfQR ====>" + objectGroupOfQR);
//                            }
                        GroupOfQR.insertDB(objectGroupOfQR);
                    }
                } else {
                    ArrayList<ObjectGroupOfQR> arr = GroupOfQR.getListGrOfQR(itemQRvsCard.id);
                    if (!arr.isEmpty()) {
                        GroupOfQR.delete(itemQRvsCard.id);
                    }

                }
            }
        }
        // }
    }

    private static ObjectGroupOfQR getValueGroupOfQR(ObjGroup objGr, String idQR) {

        ObjectGroupOfQR objGrQR = new ObjectGroupOfQR();

        objGrQR.id = idQR + objGr.id;// id qr
        objGrQR.groupID = objGr.id;// nhóm
        objGrQR.groupName = objGr.groupName;
        objGrQR.mcID = objGr.mcID;
        objGrQR.timeTao = objGr.timeTao;
        objGrQR.userTao = objGr.userTao;
        objGrQR.timeSua = objGr.timeSua;
        objGrQR.userSua = objGr.userSua;
        objGrQR.mess = objGr.mess;
        objGrQR.groupLevel = objGr.groupLevel; // 1: nhóm = list per, 2: nhóm list nhóm 1
        objGrQR.idQR = idQR;
        return objGrQR;

    }

    public static ResponseMessage1 xoaUserTrongDonVi(int funcId, long currentime, String data) {
        Log.logServices("xoaUserTrongDonVi =============> " + funcId + " /currentime" + currentime + " /" + data);

        System.out.println("int put xoaUserTrongDonVi: " + data);

        ResponseMessage1 response = new ResponseMessage1();

        response.funcId = funcId;

        TypeThongBao obj = new Gson().fromJson(data, TypeThongBao.class);
        response = StatusResponse(1);
        response.result = "xoaUserTrongDonVi =============> true";


        return response;
    }

}
