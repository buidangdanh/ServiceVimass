package vn.vimass.service.BackUp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.binary.Base64;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ObjectLichSuRaVaoQuetQr;
import vn.vimass.service.entity.ResponseMessage;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.entity.mayTinh.ObjectLayIPRequest;
import vn.vimass.service.entity.recivephone.*;
import vn.vimass.service.entity.thongkeoffline.ObjectThongKeRequest;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.table.object.ObjectIpAddress;
import vn.vimass.service.table.object.ObjectLichSuRaVaoQuetQrMay1;
import vn.vimass.service.table.object.ObjectQr;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

import static vn.vimass.service.BackUp.BackUpControllerDataBase.*;
import static vn.vimass.service.BackUp.BackUpControllerDataBaseVer2.getThietBiVPass;
import static vn.vimass.service.CallService.CallService.PostREST;
import static vn.vimass.service.table.IPaddress.getThongTinDiaDiem;
import static vn.vimass.service.utils.ServivceCommon.bamMD5;

public class BackUpFunction {
    public static ObjectLichSuRaVaoQuetQr chuyenObjLichSuRaVaoQuetQr(ObjectLichSuRaVaoQuetQrMay1 objectLichSuRaVaoQuetQrMay1) {
        ObjectLichSuRaVaoQuetQr objectLichSuRaVaoQuetQr = new ObjectLichSuRaVaoQuetQr();

        try {
            objectLichSuRaVaoQuetQr.maRaVao = objectLichSuRaVaoQuetQrMay1.id;
            objectLichSuRaVaoQuetQr.accName = objectLichSuRaVaoQuetQrMay1.accName;
            objectLichSuRaVaoQuetQr.vID = objectLichSuRaVaoQuetQrMay1.vID;
            objectLichSuRaVaoQuetQr.diaChi = objectLichSuRaVaoQuetQrMay1.diaChi;
            objectLichSuRaVaoQuetQr.idThietBi = objectLichSuRaVaoQuetQrMay1.idThietBi;
            objectLichSuRaVaoQuetQr.thoiGianGhiNhan = objectLichSuRaVaoQuetQrMay1.thoiGianGhiNhan;
            objectLichSuRaVaoQuetQr.loiRa = objectLichSuRaVaoQuetQrMay1.loiRa;
            objectLichSuRaVaoQuetQr.result = objectLichSuRaVaoQuetQrMay1.result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectLichSuRaVaoQuetQr;
    }

    public static ArrayList<String> layListVpassCuaDiaDiem(String json) {
        Log.logServices("layListVpassCuaDiaDiem e: " + json);
        ArrayList<String> stringsList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            stringsList = gson.fromJson(json, type);
        } catch (Exception e) {
            Log.logServices("layListVpassCuaDiaDiem Exception e: " + e.getMessage());
        }
        return stringsList;
    }

    public static boolean xoaIdVpassKhoiIdQR(String idLoiRaVao, String idVpass) {
        boolean kq = false;
        try {
            ObjectIpAddress objectIpAddress = getThongTinDiaDiem(idLoiRaVao);
            ArrayList<String> listVpass = layListVpassCuaDiaDiem(objectIpAddress.idVpass);
            //Thực hiện xóa
            listVpass.removeIf(s -> s.equals(idVpass));
            String json = new Gson().toJson(listVpass);
            kq = chenidVpass(idLoiRaVao, json);
        } catch (Exception e) {
            Log.logServices("xoaIdVpassKhoiIdQR Exception e: " + e.getMessage());
        }
        return kq;
    }

    public static ResponseMessage1 StatusResponse(int TypeTraVe) {
        ResponseMessage1 reponse = new ResponseMessage1();
        try {
            if (TypeTraVe == 1) {
                reponse.msgCode = 1;
                reponse.msgContent = "Success!";
            } else if (TypeTraVe == 2) {
                reponse.msgCode = 2;
                reponse.msgContent = "UnSuccess!";
            } else if (TypeTraVe == 3) {
                reponse.msgCode = 3;
                reponse.msgContent = "Check the data fields again!";
            } else if (TypeTraVe == 4) {
                reponse.msgCode = 4;
                reponse.msgContent = "FaceData of Vid exsit!";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reponse;
    }

    public static ArrayList<ObjectInfoVid> arrLuuDsGroup = new ArrayList<>();

    public static ArrayList<ObjListPer> arrNguoi = new ArrayList<>();

    public static HashMap<String, ObjListPer> hashNguoi;
    public static HashMap<String, ObjectInfoVid> hashNguoi2;

//    public static ResponseMessage1 TachNguoiVeDiem(String mcID) {
//        ArrayList<ObjectInfoVid> arrFinal = new ArrayList<>();
//        ArrayList<ObjectNguoiNhom> arrNguoiNhom = new ArrayList<>();
//        ArrayList<ObjectNguoiNhom> arrNguoiCua = new ArrayList<>();
//        ArrayList<ObjectNguoiNhom> arrNguoiCuaFinal = new ArrayList<>();
//        ArrayList<ObjectListIDDIem> arrNCFinal = new ArrayList<>();
//        hashNguoi = new HashMap<>();
//        hashNguoi2 = new HashMap<>();
//        ResponseMessage1 res = new ResponseMessage1();
//        Log.logServices("TachNguoiVeDiem thanh cong" + mcID);
//
//        try {
//            //Goi dich vu lay nguoi tu server
//            ObjectGoupRequest objectGoupRequest = new ObjectGoupRequest();
//            objectGoupRequest.user = "0353465132";
//            objectGoupRequest.currentTime = new Date().getTime();
//            objectGoupRequest.deviceID = 2;
//            objectGoupRequest.mcID = mcID;
//            objectGoupRequest.cks = bamMD5(objectGoupRequest.user + objectGoupRequest.deviceID + "ZgVCHxqMd$aNCm54X2YHD" + objectGoupRequest.currentTime + mcID);
//            String url = "http://210.245.8.7:12318/vimass/services/VUHF/dsNhomRV";
//            String kq = PostREST(url, new Gson().toJson(objectGoupRequest));
//            Log.logServices("request" + new Gson().toJson(objectGoupRequest));
//
//            ResponseMessage responseMessage = new Gson().fromJson(kq, ResponseMessage.class);
//            Log.logServices("TachNguoiVeDiem 1" + responseMessage.result);
//            ArrayList<GroupObj> listValue = new Gson().fromJson(responseMessage.result, new TypeToken<ArrayList<GroupObj>>() {
//            }.getType());
//
//            if (arrLuuDsGroup != null) {
//                arrLuuDsGroup.clear();
//            }
//            arrLuuDsGroup = getIDinfo_vid(mcID);
//            ArrayList<ObjListPer> listPer = new ArrayList<>();
//            for (GroupObj arrGr : listValue) {
//                if (arrGr != null) {
//                    res.result = Tool.setBase64(LuuVaoDBQrGroup(arrGr).toString());
//                }
//                if (arrGr.listPer != null && !arrGr.listPer.isEmpty()) {
//                    listPer = new Gson().fromJson(String.valueOf(arrGr.listPer), new TypeToken<ArrayList<ObjListPer>>() {
//                    }.getType());
//                    for (ObjListPer arrPer : listPer) {
//                        hashNguoi.put(arrPer.id, arrPer); //Put vào hashNguoiServer id + obj
//                    }
//                }
//            }
//
//            for (String key : hashNguoi.keySet()) {
//                ObjectNguoiNhom objectNN = new ObjectNguoiNhom();
//                for (GroupObj arrGr : listValue) {
//                    if (arrGr.listPer != null && !arrGr.listPer.isEmpty()) {
//                        listPer = new Gson().fromJson(String.valueOf(arrGr.listPer), new TypeToken<ArrayList<ObjListPer>>() {
//                        }.getType());
//
//                        for (ObjListPer arrPer : listPer) {
//                            if (key.equals(arrPer.id)) {
//                                objectNN.listNhom.add(arrGr.id);
//                                objectNN.idNguoi = key;
//
//                            }
//                        }
//                    }
//
//                }
//                arrNguoiNhom.add(objectNN);
//            }
//
//
//            //dich vu 2
//            ObjectLayDiemCoNhom objLDCN = new ObjectLayDiemCoNhom();
//            objLDCN.user = "0353465132";
//            objLDCN.currentTime = new Date().getTime();
//            objLDCN.theLK = false;
//            objLDCN.mcID = mcID;
//            objLDCN.catID = "";
//            objLDCN.cks = bamMD5("Y99JAuGfmYaBYYyycsLy26" + objLDCN.mcID + objLDCN.currentTime);
//            String url2 = "http://210.245.8.7:12318/vimass/services/VUHF/getThongTinDiem";
//            String kq2 = PostREST(url2, new Gson().toJson(objLDCN));
//            ResponseMessage response = new Gson().fromJson(kq2, ResponseMessage.class);
//            Log.logServices("dichvu 2" + response.result);
//
//            ArrayList<ObjectListDiemTheoMC> listValueDiem = new Gson().fromJson(response.result, new TypeToken<ArrayList<ObjectListDiemTheoMC>>() {
//            }.getType());
//            ArrayList<GroupObj> listGr = new ArrayList<>();
//            for (ObjectListDiemTheoMC arrDiem : listValueDiem) {
//                if (arrDiem.listGroup != null && !arrDiem.listGroup.isEmpty()) {
//                    listGr = new Gson().fromJson(String.valueOf(arrDiem.listGroup), new TypeToken<ArrayList<GroupObj>>() {
//                    }.getType());
//                    for (ObjectNguoiNhom arrNN : arrNguoiNhom) {
//                        ObjectNguoiNhom objND = new ObjectNguoiNhom();
//                        for (String arrNhom : arrNN.listNhom) {
//                            for (GroupObj arrNhomCap2 : listGr) {
//                                if (arrNhom.equals(arrNhomCap2.id)) {
//                                    objND.listNhom.add(arrDiem.id);
//                                    objND.idNguoi = arrNN.idNguoi;
//                                }
//
//                            }
//                        }
//                        if (objND.idNguoi != null && !objND.idNguoi.isEmpty()) {
//                            arrNguoiCua.add(objND);
//                        }
//
//                    }
//                }
//
//            }
//
//            Set<String> processedIds = new HashSet<>();
//
//            for (ObjectNguoiNhom arr : arrNguoiCua) {
//                if (!processedIds.contains(arr.idNguoi)) {
//                    ObjectNguoiNhom objectNguoiNhom = new ObjectNguoiNhom();
//                    objectNguoiNhom.idNguoi = arr.idNguoi;
//
//                    for (ObjectNguoiNhom arr2 : arrNguoiCua) {
//                        if (arr2.idNguoi.equals(arr.idNguoi)) {
//                            objectNguoiNhom.listNhom.addAll(arr2.listNhom); // Thêm tất cả các nhóm vào listNhom
//                        }
//                    }
//                    arrNguoiCuaFinal.add(objectNguoiNhom);
//                    processedIds.add(arr.idNguoi); // Đánh dấu idNguoi này đã được xử lý
//                }
//            }
//            for (ObjectNguoiNhom a : arrNguoiCuaFinal) {
//                ObjectListIDDIem objectListIDDIem = new ObjectListIDDIem();
//                ObjectQr objectQr = new ObjectQr();
//                for (String k : a.listNhom) {
//                    objectQr.idQR = k;
//                    objectListIDDIem.listNhom.add(objectQr);
//                    objectListIDDIem.idNguoi = a.idNguoi;
//                }
//                arrNCFinal.add(objectListIDDIem);
//            }
//
//            for (ObjectListIDDIem a : arrNCFinal) {
//                ObjectInfoVid objectInfoVid = new ObjectInfoVid();
//                objectInfoVid.id = a.idNguoi;
//                objectInfoVid.chucDanh = hashNguoi.get(a.idNguoi).chucDanh;
//                if (hashNguoi.get(a.idNguoi).uID != null && !hashNguoi.get(a.idNguoi).uID.isEmpty()) {
//                    objectInfoVid.uID = hashNguoi.get(a.idNguoi).uID;
//                }
//                if (hashNguoi.get(a.idNguoi).vID != null && !hashNguoi.get(a.idNguoi).vID.isEmpty()) {
//                    objectInfoVid.idVid = hashNguoi.get(a.idNguoi).vID.toLowerCase().replace("v", "");
//                }
//                if (hashNguoi.get(a.idNguoi).sdt != null && !hashNguoi.get(a.idNguoi).sdt.isEmpty()) {
//                    objectInfoVid.dienThoai = hashNguoi.get(a.idNguoi).sdt;
//                }
//                if (hashNguoi.get(a.idNguoi).name != null && !hashNguoi.get(a.idNguoi).name.isEmpty()) {
//                    objectInfoVid.hoTen = hashNguoi.get(a.idNguoi).name;
//                }
//                if (hashNguoi.get(a.idNguoi).avatar != null && !hashNguoi.get(a.idNguoi).avatar.isEmpty()) {
//                    objectInfoVid.anhDaiDien = hashNguoi.get(a.idNguoi).avatar;
//                }
//                objectInfoVid.personPosition = hashNguoi.get(a.idNguoi).perNum;
//                if (a.listNhom != null && !a.listNhom.isEmpty()) {
//                    objectInfoVid.listIdDiem = String.valueOf(a.listNhom);
//                }
//                arrFinal.add(objectInfoVid);
//
//
//            }
//            for (ObjectInfoVid arr1 : arrFinal) {
//                if (arr1.idVid != null) {
//                    hashNguoi2.put(arr1.idVid + arr1.personPosition, arr1);
//
//                } else {
//                    hashNguoi2.put(arr1.dienThoai + arr1.personPosition, arr1);
//
//                }
//            }
//
//            for (String key : hashNguoi2.keySet()) {
//                // Lấy giá trị của khóa hiện tại
//                ObjectInfoVid value = hashNguoi2.get(key);
//                if (checkInfovid(value)) {
//                    capNhatDbVid(value, mcID);
//                } else {
//                    cuPhapChenVid(value, mcID);
//
//                }
//            }
//            res.msgCode = 1;
//            res.msgContent = "Success";
//            res.funcId = 118;
//
//        } catch (Exception ex) {
//            Log.logServices("TachNguoiVeDiem Exception!" + ex.getMessage());
//
//        }
//        return res;
//    }

    public static boolean check(ObjectInfoVid objNServer) {
        boolean kq = false;

        try {


            if (arrLuuDsGroup != null && arrLuuDsGroup.size() > 0) {
                for (ObjectInfoVid obj : arrLuuDsGroup) {
                    boolean TheVaPerNum = (objNServer.idVid == obj.idVid) && (objNServer.personPosition == obj.personPosition);
                    boolean SdtVaPerNum = (objNServer.dienThoai == obj.dienThoai) && (objNServer.personPosition == obj.personPosition);
                    if (TheVaPerNum || SdtVaPerNum) {
                        kq = true;
                        break;
                    }
                }
            }

        } catch (Exception e) {

        }
        return kq;
    }

    public static String getBase64(String input) {
        String kq = "";
        try {
            if (input != null) {
                byte[] decodedBytes = Base64.decodeBase64(input.getBytes("UTF-8"));
                kq = new String(decodedBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    public static ResponseMessage1 thongKeTheoNgayFunC(ObjectThongKeRequest oTKR) {
        Log.logServices("thongKeTheoNgayFunC");
        ResponseMessage1 res = new ResponseMessage1();
        try {
            records = new ArrayList<>();
            recordsTraVe = new ArrayList<>();
            if (oTKR.from < oTKR.to) {
                ArrayList<Long> timestamps = getMiddayTimestampsForDates(oTKR.from, oTKR.to);
                for (Long timestamp : timestamps) {
                    getBanGhiThongKeTheoNhay(oTKR, timestamp);
                }
                res.totalAll = records.size();
                for (int i = oTKR.offset; i < oTKR.limit; i++) {
                    if (i < res.totalAll) {
                        recordsTraVe.add(records.get(i));
                    } else {
                        break;
                    }

                }
                res.msgCode = 1;
                res.msgContent = "Success!";
                res.result = Tool.setBase64(recordsTraVe.toString());
            } else {
                res.msgCode = 2;
                res.msgContent = "Check the data fields again!";
            }


        } catch (Exception e) {
            Log.logServices("thongKeTheoNgayFunC Exception!" + e.getMessage());
        }
        return res;
    }

    public static ArrayList<Long> getMiddayTimestampsForDates(long startTime, long endTime) {
        ArrayList<Long> timestamps = new ArrayList<>();
        // Đặt múi giờ là UTC+7
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));

        calendar.setTimeInMillis(startTime);
        calendar.set(Calendar.HOUR_OF_DAY, 12); // Đặt giờ là 12h trưa
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Điều chỉnh để bao gồm cả ngày cuối cùng
        Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        endCalendar.setTimeInMillis(endTime);
        endCalendar.set(Calendar.HOUR_OF_DAY, 12);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        while (!calendar.after(endCalendar)) {
            timestamps.add(calendar.getTimeInMillis());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return timestamps;
    }

    public static String tongThoiGian(long timeFrom, long timeTo) {
        String kq = "";
        try {
            Instant time1 = Instant.ofEpochMilli(timeFrom);
            Instant time2 = Instant.ofEpochMilli(timeTo);

            Duration duration = Duration.between(time1, time2);

            // Tính số giờ
            long hours = duration.toHours();

            // Tính số phút còn lại sau khi loại bỏ số giờ
            long minutes = duration.toMinutes() - hours * 60;

            kq = String.format("%d:%d ", hours, minutes);
        } catch (Exception e) {
            Log.logServices("tongThoiGian Exception!" + e.getMessage());

        }
        return kq;
    }

//    public static ResponseMessage1 updateGroupTuserverFunC(String mcID) {
//        ArrayList<ObjectInfoVid> arrFinal = new ArrayList<>();
//        ArrayList<ObjectNguoiNhom> arrNguoiNhom = new ArrayList<>();
//        ArrayList<ObjectNguoiNhom> arrNguoiCua = new ArrayList<>();
//        ArrayList<ObjectNguoiNhom> arrNguoiCuaFinal = new ArrayList<>();
//        ArrayList<ObjectListIDDIem> arrNCFinal = new ArrayList<>();
//        hashNguoi = new HashMap<>();
//        hashNguoi2 = new HashMap<>();
//        ResponseMessage1 res = new ResponseMessage1();
//        Log.logServices("updateGroupTuserverFunC thanh cong" + mcID);
//
//        try {
//            //Goi dich vu lay nguoi tu server
//            ObjectGoupRequest objectGoupRequest = new ObjectGoupRequest();
//            objectGoupRequest.user = "0353465132";
//            objectGoupRequest.currentTime = new Date().getTime();
//            objectGoupRequest.deviceID = 2;
//            objectGoupRequest.mcID = mcID;
//            objectGoupRequest.cks = bamMD5(objectGoupRequest.user + objectGoupRequest.deviceID + "ZgVCHxqMd$aNCm54X2YHD" + objectGoupRequest.currentTime + mcID);
//            String url = "http://210.245.8.7:12318/vimass/services/VUHF/dsNhomRV";
//            String kq = PostREST(url, new Gson().toJson(objectGoupRequest));
//            Log.logServices("request" + new Gson().toJson(objectGoupRequest));
//
//            ResponseMessage responseMessage = new Gson().fromJson(kq, ResponseMessage.class);
//            Log.logServices("updateGroupTuserverFunC 1" + responseMessage.result);
//            ArrayList<GroupObj> listValue = new Gson().fromJson(responseMessage.result, new TypeToken<ArrayList<GroupObj>>() {
//            }.getType());
//            if (arrLuuDsGroup != null) {
//                arrLuuDsGroup.clear();
//            }
//            arrLuuDsGroup = getIDinfo_vid(mcID);
//            ArrayList<ObjListPer> listPer = new ArrayList<>();
//            for (GroupObj arrGr : listValue) {
//                if (arrGr.listPer != null && !arrGr.listPer.isEmpty()) {
//                    listPer = new Gson().fromJson(String.valueOf(arrGr.listPer), new TypeToken<ArrayList<ObjListPer>>() {
//                    }.getType());
//                    for (ObjListPer arrPer : listPer) {
//                        hashNguoi.put(arrPer.id, arrPer); //Put vào hashNguoiServer id + obj
//                    }
//                }
//            }
//
//            for (String key : hashNguoi.keySet()) {
//                ObjectNguoiNhom objectNN = new ObjectNguoiNhom();
//                for (GroupObj arrGr : listValue) {
//                    if (arrGr.listPer != null && !arrGr.listPer.isEmpty()) {
//                        listPer = new Gson().fromJson(String.valueOf(arrGr.listPer), new TypeToken<ArrayList<ObjListPer>>() {
//                        }.getType());
//
//                        for (ObjListPer arrPer : listPer) {
//                            if (key.equals(arrPer.id)) {
//                                objectNN.listNhom.add(arrGr.id);
//                                objectNN.idNguoi = key;
//
//                            }
//                        }
//                    }
//
//                }
//                arrNguoiNhom.add(objectNN);
//            }
//
//
//            //dich vu 2
//            ObjectLayDiemCoNhom objLDCN = new ObjectLayDiemCoNhom();
//            objLDCN.user = "0353465132";
//            objLDCN.currentTime = new Date().getTime();
//            objLDCN.theLK = false;
//            objLDCN.mcID = mcID;
//            objLDCN.catID = "";
//            objLDCN.cks = bamMD5("Y99JAuGfmYaBYYyycsLy26" + objLDCN.mcID + objLDCN.currentTime);
//            String url2 = "http://210.245.8.7:12318/vimass/services/VUHF/getThongTinDiem";
//            String kq2 = PostREST(url2, new Gson().toJson(objLDCN));
//            ResponseMessage response = new Gson().fromJson(kq2, ResponseMessage.class);
//            Log.logServices("dichvu 2" + response.result);
//
//            ArrayList<ObjectListDiemTheoMC> listValueDiem = new Gson().fromJson(response.result, new TypeToken<ArrayList<ObjectListDiemTheoMC>>() {
//            }.getType());
//            ArrayList<GroupObj> listGr = new ArrayList<>();
//            for (ObjectListDiemTheoMC arrDiem : listValueDiem) {
//                if (arrDiem.listGroup != null && !arrDiem.listGroup.isEmpty()) {
//                    listGr = new Gson().fromJson(String.valueOf(arrDiem.listGroup), new TypeToken<ArrayList<GroupObj>>() {
//                    }.getType());
//                    for (ObjectNguoiNhom arrNN : arrNguoiNhom) {
//                        ObjectNguoiNhom objND = new ObjectNguoiNhom();
//                        for (String arrNhom : arrNN.listNhom) {
//                            for (GroupObj arrNhomCap2 : listGr) {
//                                if (arrNhom.equals(arrNhomCap2.id)) {
//                                    objND.listNhom.add(arrDiem.id);
//                                    objND.idNguoi = arrNN.idNguoi;
//                                }
//
//                            }
//                        }
//                        if (objND.idNguoi != null && !objND.idNguoi.isEmpty()) {
//                            arrNguoiCua.add(objND);
//                        }
//
//                    }
//                }
//
//            }
//
//            Set<String> processedIds = new HashSet<>();
//
//            for (ObjectNguoiNhom arr : arrNguoiCua) {
//                if (!processedIds.contains(arr.idNguoi)) {
//                    ObjectNguoiNhom objectNguoiNhom = new ObjectNguoiNhom();
//                    objectNguoiNhom.idNguoi = arr.idNguoi;
//
//                    for (ObjectNguoiNhom arr2 : arrNguoiCua) {
//                        if (arr2.idNguoi.equals(arr.idNguoi)) {
//                            objectNguoiNhom.listNhom.addAll(arr2.listNhom); // Thêm tất cả các nhóm vào listNhom
//                        }
//                    }
//                    arrNguoiCuaFinal.add(objectNguoiNhom);
//                    processedIds.add(arr.idNguoi); // Đánh dấu idNguoi này đã được xử lý
//                }
//            }
//            for (ObjectNguoiNhom a : arrNguoiCuaFinal) {
//                ObjectListIDDIem objectListIDDIem = new ObjectListIDDIem();
//                ObjectQr objectQr = new ObjectQr();
//                for (String k : a.listNhom) {
//                    objectQr.idQR = k;
//                    objectListIDDIem.listNhom.add(objectQr);
//                    objectListIDDIem.idNguoi = a.idNguoi;
//                }
//                arrNCFinal.add(objectListIDDIem);
//            }
//
//            for (ObjectListIDDIem a : arrNCFinal) {
//                ObjectInfoVid objectInfoVid = new ObjectInfoVid();
//                objectInfoVid.id = a.idNguoi;
//                objectInfoVid.chucDanh = hashNguoi.get(a.idNguoi).chucDanh;
//                if (hashNguoi.get(a.idNguoi).uID != null && !hashNguoi.get(a.idNguoi).uID.isEmpty()) {
//                    objectInfoVid.uID = hashNguoi.get(a.idNguoi).uID;
//                }
//                if (hashNguoi.get(a.idNguoi).vID != null && !hashNguoi.get(a.idNguoi).vID.isEmpty()) {
//                    objectInfoVid.idVid = hashNguoi.get(a.idNguoi).vID.toLowerCase().replace("v", "");
//                }
//                if (hashNguoi.get(a.idNguoi).sdt != null && !hashNguoi.get(a.idNguoi).sdt.isEmpty()) {
//                    objectInfoVid.dienThoai = hashNguoi.get(a.idNguoi).sdt;
//                }
//                if (hashNguoi.get(a.idNguoi).name != null && !hashNguoi.get(a.idNguoi).name.isEmpty()) {
//                    objectInfoVid.hoTen = hashNguoi.get(a.idNguoi).name;
//                }
//                if (hashNguoi.get(a.idNguoi).avatar != null && !hashNguoi.get(a.idNguoi).avatar.isEmpty()) {
//                    objectInfoVid.anhDaiDien = hashNguoi.get(a.idNguoi).avatar;
//                }
//                objectInfoVid.personPosition = hashNguoi.get(a.idNguoi).perNum;
//                if (a.listNhom != null && !a.listNhom.isEmpty()) {
//                    objectInfoVid.listIdDiem = String.valueOf(a.listNhom);
//                }
//                arrFinal.add(objectInfoVid);
//
//
//            }
//            for (ObjectInfoVid arr1 : arrFinal) {
//                if (arr1.idVid != null) {
//                    hashNguoi2.put(arr1.idVid + arr1.personPosition, arr1);
//
//                } else {
//                    hashNguoi2.put(arr1.dienThoai + arr1.personPosition, arr1);
//
//                }
//            }
//
//            for (String key : hashNguoi2.keySet()) {
//                // Lấy giá trị của khóa hiện tại
//                ObjectInfoVid value = hashNguoi2.get(key);
//                if (checkInfovid(value)) {
//                    capNhatDbVid(value, mcID);
//                } else {
//                    cuPhapChenVid(value, mcID);
//
//                }
//            }
//            res.msgCode = 1;
//            res.msgContent = "Success";
//            res.funcId = 118;
//
//        } catch (Exception ex) {
//            Log.logServices("TachNguoiVeDiem Exception!" + ex.getMessage());
//
//        }
//        return res;
//    }

    public static void layDanhSachGroupFunC(String mcID) {
        try {
            ObjectGoupRequest objectGoupRequest = new ObjectGoupRequest();
            objectGoupRequest.user = "0353465132";
            objectGoupRequest.currentTime = new Date().getTime();
            objectGoupRequest.deviceID = 2;
            objectGoupRequest.mcID = mcID;
            objectGoupRequest.cks = bamMD5(objectGoupRequest.user + objectGoupRequest.deviceID + "ZgVCHxqMd$aNCm54X2YHD" + objectGoupRequest.currentTime + mcID);
            String url = "http://210.245.8.7:12318/vimass/services/VUHF/dsNhomRV";
            String kq = PostREST(url, new Gson().toJson(objectGoupRequest));
            Log.logServices("request" + new Gson().toJson(objectGoupRequest));

            ResponseMessage responseMessage = new Gson().fromJson(kq, ResponseMessage.class);
            Log.logServices("TachNguoiVeDiem 1" + responseMessage.result);
            ArrayList<GroupObj> listValue = new Gson().fromJson(responseMessage.result, new TypeToken<ArrayList<GroupObj>>() {
            }.getType());
            for (GroupObj arrGr : listValue) {
                if (arrGr != null) {
                    LuuVaoDBQrGroup(arrGr).toString();
                }
            }
        } catch (Exception e) {
            Log.logServices("layDanhSachGroupFunC Exception!" + e.getMessage());


        }
    }

    public static String urlMay1 = "";
    public static String urlMay2 = "";

    public static String ipCheckMay1 = "";

    public static Boolean May1 = false;

    public static void checkDichVuTamNgung() {
        try {
            if (ipCheckMay1 == null || ipCheckMay1.equals("")) {
                ArrayList<ObjectLayIPRequest> arr = luuIPMayDBUpdateSelect();
                if (arr != null && arr.size() > 0) {
                    for (ObjectLayIPRequest ar : arr) {
                        if (ar.id != null && ar.id.equals("1")) {
                            ipCheckMay1 = ar.ip;
                            urlMay1 = "http://" + ar.ip + ":58080/autobank/services/vimassTool/"+ar.moTa;
                            thayDoiTrangThaiMay();
                        }
                        if (ar.id != null && ar.id.equals("2")) {
                            urlMay2 = "http://" + ar.ip + ":58080/autobank/services/vimassTool/"+ar.moTa;
                        }
                    }

                }
            }

        } catch (Exception e) {
            Log.logServices("layDanhSachGroupFunC Exception!" + e.getMessage());


        }
    }

    public static void luuIP() {
        try {
            ArrayList<ObjectLayIPRequest> arr = luuIPMayDBUpdateSelect();
            if (arr != null && arr.size() > 0) {
                for (ObjectLayIPRequest ar : arr) {
                    Log.logServices("luuIP !" + ar.id);

                    if (ar.id != null && ar.id.equals("1")) {
                        ipCheckMay1 = ar.ip;
                        urlMay1 = "http://" + ar.ip + ":58080/autobank/services/vimassTool/dieuPhoi";
                        thayDoiTrangThaiMay();
                    }
                    if (ar.id != null && ar.id.equals("2")) {
                        urlMay2 = "http://" + ar.ip + ":58080/autobank/services/vimassTool/dieuPhoi";
                    }
                }

            }


        } catch (Exception e) {
            Log.logServices("layDanhSachGroupFunC Exception!" + e.getMessage());


        }
    }

    public static void thayDoiTrangThaiMay() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();

            if (ipCheckMay1 != null && !ipCheckMay1.equals("") && ipCheckMay1.equals(inetAddress.getHostAddress())) {
                May1 = true;
            } else {
                May1 = false;
            }
            Log.logServices("thayDoiTrangThaiMay: " + ipCheckMay1 + "_" + inetAddress.getHostAddress());
            // Print the IP address
            if (May1) {
                Log.logServices("thayDoiTrangThaiMay ok");
            }
        } catch (Exception e) {
            Log.logServices("thayDoiTrangThaiMay Exception!" + e.getMessage());

        }
    }

    private static final int NUMBER_OF_THREADS = 10; // Số lượng thread trong pool
    static ArrayList<ObjectListDiemTheoMC> listValueDiem;
//    public static ResponseMessage1 TachNguoiVeDiem2(String mcID) {
//        ArrayList<ObjectInfoVid> arrFinal = new ArrayList<>();
//        listValueDiem = new ArrayList<>();
//        hashNguoi = new HashMap<>();
//        hashNguoi2 = new HashMap<>();
//        ResponseMessage1 res = new ResponseMessage1();
//        Log.logServices("TachNguoiVeDiem thanh cong" + mcID);
//
//        try {
//            ObjectLayDiemCoNhom objLDCN = new ObjectLayDiemCoNhom();
//            objLDCN.user = "0353465132";
//            objLDCN.currentTime = new Date().getTime();
//            objLDCN.theLK = false;
//            objLDCN.mcID = mcID;
//            objLDCN.catID = "";
//            objLDCN.cks = bamMD5("Y99JAuGfmYaBYYyycsLy26" + objLDCN.mcID + objLDCN.currentTime);
//            String url2 = "http://210.245.8.7:12318/vimass/services/VUHF/getThongTinDiem";
//            String kq2 = PostREST(url2, new Gson().toJson(objLDCN));
//            ResponseMessage response = new Gson().fromJson(kq2, ResponseMessage.class);
//            Log.logServices("dichvu 2" + response.result);
//
//            listValueDiem = new Gson().fromJson(response.result, new TypeToken<ArrayList<ObjectListDiemTheoMC>>() {
//            }.getType());
//
//
//
//            //Goi dich vu lay nguoi tu server
//            ObjectGoupRequest objectGoupRequest = new ObjectGoupRequest();
//            objectGoupRequest.user = "0353465132";
//            objectGoupRequest.currentTime = new Date().getTime();
//            objectGoupRequest.deviceID = 2;
//            objectGoupRequest.mcID = mcID;
//            objectGoupRequest.cks = bamMD5(objectGoupRequest.user + objectGoupRequest.deviceID + "ZgVCHxqMd$aNCm54X2YHD" + objectGoupRequest.currentTime + mcID);
//            String url = "http://210.245.8.7:12318/vimass/services/VUHF/dsNhomRV";
//            String kq = PostREST(url, new Gson().toJson(objectGoupRequest));
//            Log.logServices("request" + new Gson().toJson(objectGoupRequest));
//
//            ResponseMessage responseMessage = new Gson().fromJson(kq, ResponseMessage.class);
//            Log.logServices("TachNguoiVeDiem 1" + responseMessage.result);
//            ArrayList<GroupObj> listValue = new Gson().fromJson(responseMessage.result, new TypeToken<ArrayList<GroupObj>>() {
//            }.getType());
//
//            if (arrLuuDsGroup != null) {
//                arrLuuDsGroup.clear();
//            }
//            arrLuuDsGroup = getIDinfo_vid(mcID);
//            final ArrayList<ObjListPer>[] listPer = new ArrayList[]{new ArrayList<>()};
//
//            ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//            List<Future<?>> futures = new ArrayList<>();
//            for (GroupObj arrGr : listValue) {
//                Future<?> future = executorService.submit(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (arrGr != null) {
//                            res.result = Tool.setBase64(LuuVaoDBQrGroup(arrGr).toString());
//                        }
//                        if (arrGr.groupLevel == 1) {
//                            if (arrGr.listPer != null && !arrGr.listPer.isEmpty()) {
//                                listPer[0] = new Gson().fromJson(String.valueOf(arrGr.listPer), new TypeToken<ArrayList<ObjListPer>>() {
//                                }.getType());
//                                for (ObjListPer arrPer : listPer[0]) {
//                                    if (arrPer.sdt != null && !arrPer.sdt.equals("")) {
//                                        hashNguoi.put(arrPer.sdt + arrPer.perNum, arrPer); //Put vào hashNguoiServer id + obj
//                                    } else if (arrPer.vID != null && !arrPer.vID.equals("")) {
//                                        hashNguoi.put(arrPer.vID + arrPer.perNum, arrPer); //Put vào hashNguoiServer id + obj
//
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//                });
//                futures.add(future);
//            }
//
//            // Đợi cho tất cả các thread hoàn thành công việc
//            for (Future<?> future : futures) {
//                try {
//                    future.get(); // Đợi thread hoàn thành công việc
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            executorService.shutdown();
//            try {
//                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            ExecutorService executorServiceForLoop = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//            List<Future<?>> loopFutures = new ArrayList<>();
//
//            for (String key : hashNguoi.keySet()) {
//                Future<?> loopFuture = executorServiceForLoop.submit(new Runnable() {
//                    @Override
//                    public void run() {
//                        ObjectInfoVid obj = new ObjectInfoVid();
//                        // Cẩn thận với việc truy cập hashNguoi từ bên trong thread
//                        synchronized (hashNguoi) {
//                            obj.id = hashNguoi.get(key).id;
//                            obj.idVid = hashNguoi.get(key).vID;
//                            obj.dienThoai = hashNguoi.get(key).sdt;
//                            obj.personPosition = hashNguoi.get(key).perNum;
//                            obj.chucDanh = hashNguoi.get(key).chucDanh;
//                            obj.anhDaiDien = hashNguoi.get(key).avatar;
//                            obj.hoTen = hashNguoi.get(key).name;
//                            obj.listIdDiem =  checkDiemGanVoiNhom(obj.id);
//                            obj.uID =   hashNguoi.get(key).uID;
//                        }
//
//                        arrFinal.add(obj);
//                        if (checkInfovid(obj)) {
//                            capNhatDbVid(obj, mcID);
//                        } else {
//                            cuPhapChenVid(obj, mcID);
//                        }
//                    }
//                });
//                loopFutures.add(loopFuture);
//            }
//
//            // Đợi cho tất cả các thread trong vòng lặp hoàn thành công việc
//            for (Future<?> future : loopFutures) {
//                try {
//                    future.get(); // Đợi thread hoàn thành công việc
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            executorServiceForLoop.shutdown();
//            try {
//                executorServiceForLoop.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            res.msgCode = 1;
//            res.msgContent = "Success";
//            res.funcId = 118;
//
//        } catch(
//                Exception ex)
//
//        {
//            Log.logServices("TachNguoiVeDiem Exception!" + ex.getMessage());
//
//        }
//        return res;
//    }

    private static String checkDiemGanVoiNhom(String idNguoi) {
        String kq = "";
        String idNguoiFinal = idNguoi.substring(idNguoi.length()-16,idNguoi.length());
        ArrayList<ObjectQr>  arrFinal = new ArrayList<>();
        try {
            ArrayList<GroupObj> listGr = new ArrayList<>();
            for (ObjectListDiemTheoMC arrDiem : listValueDiem) {
                if (arrDiem.listGroup != null && !arrDiem.listGroup.isEmpty()) {
                    listGr = new Gson().fromJson(String.valueOf(arrDiem.listGroup), new TypeToken<ArrayList<GroupObj>>() {
                    }.getType());
                    for (GroupObj arrGr : listGr) {
                        ObjectQr obj = new ObjectQr();
                        if (arrGr.id.indexOf(idNguoiFinal)>-1) {
                            obj.idQR = arrDiem.id;
                            arrFinal.add(obj);
                            break;
                        }
                    }
                }
            }
            return kq = arrFinal.toString();
        }catch (Exception e){
            Log.logServices("checkDiemGanVoiNhom Exception!" + e.getMessage());

        }

        return kq;
    }
    public static String getThietBiVpass(String id){
        String kq = "";
        ArrayList<ObjVpass> listVpassLocal = new ArrayList<>();
        try{
            listVpassLocal = getThietBiVPass();
            if(listVpassLocal!=null&&listVpassLocal.size()>0){
                for(ObjVpass obj:listVpassLocal){
                    if(obj.deviceID!=null&&obj.deviceID.equals(id)){
                        if (obj.typeDevice == 1 || obj.typeDevice == 2)
                        {
                            kq = "V"+obj.stt;
                        }
                        else if (obj.typeDevice == 3 || obj.typeDevice == 4)
                        {
                            kq = "I"+obj.stt;
                        }
                        else
                        {
                            kq = "A"+obj.stt;
                        }
                    }
                }
            }

        }catch (Exception ex){
            Log.logServices("getThietBiVpass Exception!" + ex.getMessage());

        }
        return kq;
    }

}
