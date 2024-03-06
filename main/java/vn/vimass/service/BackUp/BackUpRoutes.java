package vn.vimass.service.BackUp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.crawler.bhd.RaVaoFunc;
import vn.vimass.service.crawler.bhd.RaVaoWebservice;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.crawler.bhd.error;
import vn.vimass.service.entity.*;
import vn.vimass.service.entity.group.GroupMcID;
import vn.vimass.service.entity.group.UpdateGroupRequest;
import vn.vimass.service.entity.group.layDanhSachGroupRequest;
import vn.vimass.service.entity.group.totallayDanhSachGroup;
import vn.vimass.service.entity.mayTinh.ObjectLayIPRequest;
import vn.vimass.service.entity.thongkeoffline.ObjectThongKeRequest;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.*;

import javax.ws.rs.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static vn.vimass.service.BackUp.BackUpControllerDataBase.*;
import static vn.vimass.service.BackUp.BackUpControllerDataBase.doiCheDoidLoiRaVaoALL;
import static vn.vimass.service.BackUp.BackUpFunCVer2.goiDenMayHai;
import static vn.vimass.service.BackUp.BackUpFunction.*;

@Path("/backup")
@Produces("application/json;charset=utf-8")
public class BackUpRoutes {
    @POST
    @Path("/dblichsuravaoqr")
    public static ResponseMessage traVeDuLieuDbDsTheVaTem(String request) {
        Log.logServices("dblichsuravaoqr request: " + request);

        ResponseMessage responseDbDanhSachTheVaTem = new ResponseMessage();
        try {
            ObjectLichSuRaVaoQuetQrMayMot obj = new Gson().fromJson(request, ObjectLichSuRaVaoQuetQrMayMot.class);

            if (cuPhapChenLichSuXacThucVaoDBQR(obj)) {
                responseDbDanhSachTheVaTem.result = "Được rồi";
                responseDbDanhSachTheVaTem.msgCode = 1;
                responseDbDanhSachTheVaTem.msgContent = "Thành công";
            } else {
                responseDbDanhSachTheVaTem.msgCode = 2;
                responseDbDanhSachTheVaTem.msgContent = "Không thành công";
                responseDbDanhSachTheVaTem.result = "Chưa được";
            }
        } catch (Exception e) {
            responseDbDanhSachTheVaTem.msgCode = 2;
            responseDbDanhSachTheVaTem.msgContent = "Kiểm tra lại các trường dữ liệu";
            responseDbDanhSachTheVaTem.result = "";
            Log.logServices("dblichsuravaoqr Exception e: " + e.getMessage());
        }
        return responseDbDanhSachTheVaTem;
    }

    @POST
    @Path("/dbhienthimaymot")
    public static ResponseMessage ghiDuLieuTheoMay1(String request) {
        Log.logServices("ghiDuLieuTheoMay1 request: " + request);

        ResponseMessage reponseMessage = new ResponseMessage();
        try {
            ObjectHienThiMayMot obj = new Gson().fromJson(request, ObjectHienThiMayMot.class);

            if (capNhatVaoDBHienThiMayMot(obj.value.toString(), obj.timeCapNhat)) {
                reponseMessage.msgCode = 1;
                reponseMessage.msgContent = "Thành công";
                reponseMessage.result = "Được rồi";
            } else {
                reponseMessage.msgCode = 2;
                reponseMessage.msgContent = " Không Thành công";
                reponseMessage.result = "Chưa được";
            }
        } catch (Exception e) {
            Log.logServices("ghiDuLieuTheoMay1 Exception e: " + e.getMessage());
        }
        return reponseMessage;
    }

    @POST
    @Path("/getdbhienthimaymot")
    public static ObjectHienThiMayMot layDuLieuHienThiTheoMay1() {
        ObjectHienThiMayMot objectHienThiMayMot = new ObjectHienThiMayMot();
        try {
            Log.logServices("layDuLieuHienThiTheoMay1 request: ");
            objectHienThiMayMot = layDuLieuDBHienThiMayMot();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectHienThiMayMot;
    }

    @SuppressWarnings("unused")
    @POST
    @Path("/capnhattrangthaihoatdong")
    public ResponseMessage thayDoiTrangThaiHoatDong(String input) {
        ObjectTrangThaiHoatDong item = new Gson().fromJson(input, ObjectTrangThaiHoatDong.class);

        ResponseMessage obj = new ResponseMessage();
        Log.logServices(" input thayDoiTrangThaiHoatDong: " + input);

        if (thayDoiTrangThaiHoatDongMayHai(item)) {
            obj.msgCode = 1;
            obj.msgContent = "Thành công!";
            Log.logServices(" thayDoiTrangThaiHoatDong thành công ");
        } else {
            obj.msgCode = 2;
            obj.msgContent = "Không thành công";
            Log.logServices(" thayDoiTrangThaiHoatDong không thành công ");

        }
        return obj;
    }

    @POST
    @Path("/laytrangthaihoatdong")
    public ObjectTrangThaiHoatDong layTrangThaiHoatDong() {
        Log.logServices("nhảy vào layTrangThaiHoatDong ");
        return layTrangThaiMayHai();
    }

    @POST
    @Path("/doichedocuathanhtatonline")
    public ResponseMessage doiCheDoCuaFullOnline() {
        ResponseMessage obj = new ResponseMessage();
        if (doiCheDoidLoiRaVaoALL()) {
            obj.msgCode = 1;
            obj.msgContent = "Thành công!";
        } else {
            obj.msgCode = 2;
            obj.msgContent = "Không Thành công!";
        }
        return obj;
    }

    @POST
    @Path("/layDuLieuQRMoiNhat")
    public static ResponseMessage1 layDuLieuQRMoiNhat(String Request) {
        ResponseMessage1 res = new ResponseMessage1();
        Log.logServices("layDuLieuQRMoiNhat thành công!" + Request);
        try {
            ObjectLichSuQRBanGhiMoiNhat objectLichSuQRBanGhiMoiNhat = new Gson().fromJson(Request, ObjectLichSuQRBanGhiMoiNhat.class);
            ArrayList<ObjectLichSuRaVaoQuetQr> kq = getLatestRecords(objectLichSuQRBanGhiMoiNhat.soBanGhi);
            if (kq.size() > 0) {
                res.msgCode = 1;
                res.msgContent = "Thành công!";
                res.result = Tool.setBase64(kq.toString());
            } else {
                res.msgCode = 2;
                res.msgContent = "Không Thành công!";
                res.result = kq;
            }

        } catch (Exception E) {
            E.printStackTrace();

        }
        return res;
    }

    @POST
    @Path("/dblichsuravaoqrfull")
    public static ResponseMessageLSQRTrangThai GhiDBLichSuRaVaoFull(String request) {
        Log.logServices("GhiDBLichSuRaVaoFull request: " + request);
        ResponseMessageLSQRTrangThai responseMessageLSQRTrangThai = new ResponseMessageLSQRTrangThai();
        try {
            ArrayList<ObjectLichSuRaVaoQuetQrMay1> arrayListLichSuRaVaoFull = new Gson().fromJson(request, new TypeToken<ArrayList<ObjectLichSuRaVaoQuetQrMay1>>() {
            }.getType());
            Log.logServices("GhiDBLichSuRaVaoFull list: " + arrayListLichSuRaVaoFull.toString());
            ObjectLSQRTrangThai kq = cuPhapChenLichSuXacThucVaoDBQRFull(arrayListLichSuRaVaoFull);
            if (kq.thanhCong.size() > 0 || kq.khongThanhCong.size() > 0) {
                responseMessageLSQRTrangThai.msgCode = 1;
                responseMessageLSQRTrangThai.msgContent = "Thành công!";
                responseMessageLSQRTrangThai.result = kq;
            } else {
                responseMessageLSQRTrangThai.msgCode = 2;
                responseMessageLSQRTrangThai.msgContent = "Không thành công!";
                responseMessageLSQRTrangThai.result = kq;
            }

        } catch (Exception e) {
            responseMessageLSQRTrangThai.msgCode = 0;
            responseMessageLSQRTrangThai.msgContent = "Kiểm tra lại các trường dữ liệu";
            Log.logServices("GhiDBLichSuRaVaoFull Exception e: " + e.getMessage());
        }
        return responseMessageLSQRTrangThai;
    }

    @GET
    @Path("/layDuLieuBangDbipaddress")
    public ResponseListDsDiaDiem layDuLieuBangDbipaddress() {
        Log.logServices("Nhảy vào layDuLieuBangDbipaddress thành công!");
        ResponseListDsDiaDiem obj = new ResponseListDsDiaDiem();
        ArrayList<ObjectIpAddress> result = new ArrayList<>();
        try {
            result = getBangDbipaddress();
            if (result.size() > 0) {
                obj.msgCode = 1;
                obj.msgContent = "Thành công!";
                obj.result = result;
            } else {
                obj.msgCode = 2;
                obj.msgContent = "Không thành công";
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
        return obj;
    }

    @GET
    @Path("/layDuLieuQrTheoThoiGian")
    public ResponseDuLieuQRTheoThoiGian layDuLieuQrTheoThoiGian(
            @QueryParam("from") long from,
            @QueryParam("to") long to,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset) {

        Log.logServices("Nhảy vào layDuLieuQrTheoThoiGian thành công!");

        ResponseDuLieuQRTheoThoiGian obj = new ResponseDuLieuQRTheoThoiGian();
        ArrayList<ObjectLichSuRaVaoQuetQr> result = new ArrayList<>();

        try {
            if (from != 0 && to != 0) {
                // Lấy dữ liệu và tổng số bản ghi trong khoảng thời gian
                RecordsAndTotal recordsAndTotal = getRecordsAndTotalWithinTimeRange(from, to, limit, offset);
                result = recordsAndTotal.records;
                obj.msgCode = 1;
                obj.msgContent = "Thành công!";
                obj.result = result;
                obj.totalAll = recordsAndTotal.total;
            } else {
                obj.msgCode = 2;
                obj.msgContent = "Kiểm tra lại các trường dữ liệu!";
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
        return obj;
    }

    @POST
    @Path("/tranferjson")
    public static void tranferjson(String request) {
        Log.logServices("GhiDBLichSuRaVaoFull request: " + request);
        ResponseMessageLSQRTrangThai responseMessageLSQRTrangThai = new ResponseMessageLSQRTrangThai();
        try {
            ArrayList<ObjectLichSuRaVaoQuetQrMay1> arrayListLichSuRaVaoFull = new Gson().fromJson(request, new TypeToken<ArrayList<ObjectLichSuRaVaoQuetQrMay1>>() {
            }.getType());
            Log.logServices("GhiDBLichSuRaVaoFull list: " + arrayListLichSuRaVaoFull.toString());
            ObjectLSQRTrangThai kq = cuPhapChenLichSuXacThucVaoDBQRFull(arrayListLichSuRaVaoFull);
            if (kq.thanhCong.size() > 0 || kq.khongThanhCong.size() > 0) {
                responseMessageLSQRTrangThai.msgCode = 1;
                responseMessageLSQRTrangThai.msgContent = "Thành công!";
                responseMessageLSQRTrangThai.result = kq;
            } else {
                responseMessageLSQRTrangThai.msgCode = 2;
                responseMessageLSQRTrangThai.msgContent = "Không thành công!";
                responseMessageLSQRTrangThai.result = kq;
            }

        } catch (Exception e) {
            responseMessageLSQRTrangThai.msgCode = 0;
            responseMessageLSQRTrangThai.msgContent = "Kiểm tra lại các trường dữ liệu";
            Log.logServices("GhiDBLichSuRaVaoFull Exception e: " + e.getMessage());
        }

    }

    @POST
    @Path("/layDuLieuQrTheoSoThe")
    public static ResponseMessage1 layDuLieuQrTheoSoThe(String request) {  // added personNumber parameter

        Log.logServices("FunId 104 thành công!");
        ObjectSaoKeTheoThe objS = new Gson().fromJson(request, ObjectSaoKeTheoThe.class);
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 104;
        ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> result = new ArrayList<>();
        Log.logServices("Nhảy vào layDuLieuQrTheoSoThe thành công!" + objS.toString());
        try {
            if (objS.from != 0 && objS.to != 0 && (objS.vID != null && !objS.vID.trim().isEmpty() || objS.phone != null && !objS.phone.trim().isEmpty())) {
                //Lấy theo số thẻ hoặc sdt của toàn đơn vị
                if (objS.from != 0 && objS.to != 0 && (objS.vID != null && !objS.vID.trim().isEmpty() || objS.phone != null && !objS.phone.trim().isEmpty()) && objS.idThietBi.equals("") && objS.personNumber == 0) {
                    RecordsAndTotalVerMobile recordsAndTotal = getRecordsAndTotalWithinTimeRangeNoPersonNumber(objS.from, objS.to, objS.limit, objS.offset, objS.vID, objS.phone);
                    result = recordsAndTotal.records;
                    obj.msgCode = 1;
                    obj.msgContent = "Success!";
                    obj.result = result;
                    obj.totalAll = recordsAndTotal.total;
                    //Lấy theo số thẻ hoặc sdt của toàn đơn vị theo khuôn mặt
                } else if (objS.from != 0 && objS.to != 0 && (objS.vID != null && !objS.vID.trim().isEmpty() || objS.phone != null && !objS.phone.trim().isEmpty()) && objS.idThietBi.equals("") && objS.personNumber != 0) {
                    RecordsAndTotalVerMobile recordsAndTotal = getRecordsAndTotalWithinTimeRangeVID(objS.from, objS.to, objS.limit, objS.offset, objS.vID, objS.personNumber, objS.phone);
                    result = recordsAndTotal.records;
                    obj.msgCode = 1;
                    obj.msgContent = "Success!";
                    obj.result = result;
                    obj.totalAll = recordsAndTotal.total;
                    //Lấy theo số thẻ hoặc sdt tại một điểm
                } else if (objS.from != 0 && objS.to != 0 && (objS.vID != null && !objS.vID.trim().isEmpty() || objS.phone != null && !objS.phone.trim().isEmpty()) && !objS.idThietBi.equals("") && objS.personNumber == 0) {
                    RecordsAndTotalVerMobile recordsAndTotal = getRecordsAndTotalWithinTimeRangeVIDVaidThietBi(objS.from, objS.to, objS.limit, objS.offset, objS.vID, objS.idThietBi, objS.phone);
                    result = recordsAndTotal.records;
                    obj.msgCode = 1;
                    obj.msgContent = "Success!";
                    obj.result = result;
                    obj.totalAll = recordsAndTotal.total;
                    //Lấy theo số thẻ hoặc sdt tại một điểm theo khuôn mặt
                } else if (objS.from != 0 && objS.to != 0 && (objS.vID != null && !objS.vID.trim().isEmpty() || objS.phone != null && !objS.phone.trim().isEmpty()) && !objS.idThietBi.equals("") && objS.personNumber != 0) {
                    RecordsAndTotalVerMobile recordsAndTotal = getRecordsAndTotalWithinTimeRangeVIDVaidThietBiVaPer(objS.from, objS.to, objS.limit, objS.offset, objS.vID, objS.idThietBi, objS.personNumber, objS.phone);
                    result = recordsAndTotal.records;
                    obj.msgCode = 1;
                    obj.msgContent = "Success!";
                    obj.result = result;
                    obj.totalAll = recordsAndTotal.total;
                }
                //Lấy theo điểm
            } else if (objS.from != 0 && objS.to != 0 && objS.idThietBi != null && !objS.idThietBi.equals("") && ((objS.vID == null || objS.vID.trim().isEmpty() && (objS.phone == null || objS.phone.trim().isEmpty())))) {
                RecordsAndTotalVerMobile recordsAndTotal = getRecordsAndTotalWithinTimeRangeIdThietBi(objS.from, objS.to, objS.limit, objS.offset, objS.idThietBi);
                result = recordsAndTotal.records;
                obj.msgCode = 1;
                obj.msgContent = "Success!";
                obj.result = result;
                obj.totalAll = recordsAndTotal.total;
                //Lấy theo đơn vị
            } else if (objS.from != 0 && objS.to != 0 && objS.mcID != null && !objS.mcID.equals("") && ((objS.vID == null || objS.vID.trim().isEmpty() && (objS.phone == null || objS.phone.trim().isEmpty())))) {
                RecordsAndTotalVerMobile recordsAndTotal = getRecordsAndTotalWithinTimeRangeMcID(objS.from, objS.to, objS.limit, objS.offset, objS.mcID);
                result = recordsAndTotal.records;
                obj.msgCode = 1;
                obj.msgContent = "Success!";
                obj.result = result;
                obj.totalAll = recordsAndTotal.total;
            }
            else {
                obj.msgCode = 2;
                obj.msgContent = "Check the data fields again!!";
                Log.logServices("layDuLieuQrTheoSoThe else thành công!");
            }
        } catch (Exception E) {
            Log.logServices("layDuLieuQrTheoSoThe Exception!");
            E.printStackTrace();
        }
        return obj;
    }

    @POST
    @Path("/layDuLieuKhoa")
    public static ResponseMessage1 layDuLieuKhoa(String request) {  // added personNumber parameter
        Log.logServices("FunId 111 thành công!");
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 111;
        try {
            ObjectLockRequest objS = new Gson().fromJson(request, ObjectLockRequest.class);
            if (objS != null) {
                if (objS.id != null) {
                    obj.msgCode = 1;
                    obj.msgContent = "Success!";
                    obj.result = getInfoLock(objS.id);
                } else {
                    obj.msgCode = 2;
                    obj.msgContent = "Check the id again";
                }
            } else {
                obj.msgCode = 2;
                obj.msgContent = "Check the data fields again";
            }
        } catch (Exception E) {
            Log.logServices("FunId 111 Exception!" + E.getMessage());
            E.printStackTrace();
        }
        return obj;
    }

    @POST
    @Path("/layDuLieuThietBi")
    public static ResponseMessage1 layDuLieuThietBi(String request) {  // added personNumber parameter
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 112;
        try {
            Log.logServices("FunId 112 thành công!" + request);
            ObjectDeviceRequest objS = new Gson().fromJson(request, ObjectDeviceRequest.class);
            if (objS != null) {
                if (objS.id != null) {
                    obj.msgCode = 1;
                    obj.msgContent = "Success!";
                    obj.result = getInfoDevice(objS.id);
                } else {
                    obj.msgCode = 2;
                    obj.msgContent = "Check the id again";
                }
            } else {
                obj.msgCode = 2;
                obj.msgContent = "Check the data fields again";
            }
        } catch (Exception E) {
            Log.logServices("FunId 112 Exception!" + E.getMessage());
            E.printStackTrace();
        }
        return obj;
    }

    public static ResponseMessage1 SuaDuLieuThietBi(String request, int deviceID) {
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 113;
        String idDevice = "";
        String NameDevice = "";
        boolean check = true;
        ArrayList<String> listVpass = new ArrayList<>();
        try {
            Log.logServices("FunId 113 thành công!" + request);
            ObjectChangeDive objS = new Gson().fromJson(request, ObjectChangeDive.class);

            if (objS == null || objS.idLoiRaVao == null || objS.idVpass == null || objS.idLoiRaVao.equals("") || objS.idVpass.equals("")) {
                throw new Exception("Invalid data fields");
            }

            if (objS.idVpass != null && !objS.idVpass.equals("") && objS.idVpass.indexOf("_") > -1) {
                idDevice = objS.idVpass.split("_")[0];
                NameDevice = objS.idVpass.split("_")[1];
            }
            ObjectIpAddress objectIpAddress = getIpAddressByIdLoiRaVao(objS.idLoiRaVao);
            if (objectIpAddress.idVpass != null) {
                listVpass = layListVpassCuaDiaDiem(objectIpAddress.idVpass);
                if (listVpass == null) {
                    listVpass = new ArrayList<>();
                }
            }
            if (deviceID == 2) {
                if (objS.typeDangKy != 0) {
                    for (String s : listVpass) {
                        if (s.equals(idDevice)) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        listVpass.add(idDevice);
                    }
                    themThietBiDevice(idDevice, NameDevice, "");
                }
            } else {
                if (objS.typeDangKy != 0) {
                    if (idDevice != null) {
                        ObjectDevice objectDevice = getInfoDeviceTheoID(idDevice);
                        //Trường hợp tồn tại device
                        if (objectDevice != null) {
                            if (objectDevice.id != null && !objectDevice.id.equals("")) {
                                if (objectDevice.idLoiRaVao != null && !objectDevice.idLoiRaVao.equals("")) {
                                    //Thực hiện xóa
                                    xoaIdVpassKhoiIdQR(objectDevice.idLoiRaVao, idDevice);
                                }
                                updateidLoiRaVaowhereIdVpass(idDevice, objS.idLoiRaVao);
                            } else {
                                //Thực hiện đăng ký device
                                themThietBiDevice(idDevice, NameDevice, objS.idLoiRaVao);
                            }
                        }
                        // Thực hiện đăng ký
                        if (objectDevice.idLoiRaVao == null || (objectDevice.idLoiRaVao != null && !objectDevice.idLoiRaVao.equals(objS.idLoiRaVao))) {
                            for (String s : listVpass) {
                                if (s.equals(idDevice)) {
                                    check = false;
                                    break;
                                }
                            }
                            if (check) {
                                listVpass.add(idDevice);
                            }
                        }
                    }
                }
            }
            if (objS.typeDangKy == 0) {
                listVpass.removeIf(s -> s.equals(objS.idVpass.split("_")[0]));
            }
            String json = new Gson().toJson(listVpass);
            if (chenidVpass(objS.idLoiRaVao, json)) {
                obj.msgCode = 1;
                obj.msgContent = "Success!";
                obj.result = getInfoLockObject(objectIpAddress.id);
            } else {
                obj.msgCode = 2;
                obj.msgContent = "Unsuccess!";
            }
        } catch (Exception E) {
            obj.msgCode = 2;
            obj.msgContent = "Check the data fields again";
            Log.logServices("FunId 113 exception!" + E.getMessage());
            E.printStackTrace();
        }
        return obj;
    }

    public static ResponseMessage1 getInfoFrominfo_vid(String request, int deviceID) {
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 114;
        boolean check = true;

        try {
            Log.logServices("FunId 114 thành công!" + request);
            ObjectLayThongTinTuSDT objS = new Gson().fromJson(request, ObjectLayThongTinTuSDT.class);

            if (objS == null || objS.phone == null || objS.vID == null) {
                throw new Exception("Invalid data fields");
            }

            if (!objS.phone.equals("") || !objS.vID.equals("")) {
                obj.msgCode = 1;
                obj.msgContent = "Success!";
                obj.result = getInfoFromInfoVID(objS.phone, objS.vID);
            } else {
                obj.msgCode = 2;
                obj.msgContent = "UnSuccess!";
                obj.result = getInfoFromInfoVID(objS.phone, objS.vID);
            }
        } catch (Exception E) {
            obj.msgCode = 2;
            obj.msgContent = "Check the data fields again";
            Log.logServices("FunId 114 exception!" + E.getMessage());
            E.printStackTrace();
        }
        return obj;
    }

    public static ResponseMessage1 getInfoVIDFunc(String request, int deviceID) {
        ResponseMessage1 obj = new ResponseMessage1();
        obj.funcId = 10602;

        try {
            Log.logServices("FunId 10602 thành công!" + request);
            ObjectGetInfoVID objS = new Gson().fromJson(request, ObjectGetInfoVID.class);


            if (objS == null || objS.getMcID() == null) {
                throw new Exception("Invalid data fields");
            }

            if (objS.getMcID() != null) {
                ObjectRecordsAndTotalObjectInfoVid objectRecordsAndTotalObjectInfoVid = getInfoFromDbInfoVID(objS.limit, objS.offset, objS.typeFace, objS.mcID, objS.textSearch);
                obj.msgCode = 1;
                obj.msgContent = "Success!";
                obj.result = objectRecordsAndTotalObjectInfoVid.records;
                obj.totalAll = objectRecordsAndTotalObjectInfoVid.total;
            } else {
                obj.msgCode = 2;
                obj.msgContent = "UnSuccess!";
                obj.result = getInfoFromDbInfoVID(objS.limit, objS.offset, objS.typeFace, objS.mcID, objS.textSearch).records;
            }
        } catch (Exception E) {
            obj.msgCode = 2;
            obj.msgContent = "Check the data fields again";
            Log.logServices("FunId 10602 exception!" + E.getMessage());
            E.printStackTrace();
        }
        return obj;
    }

    public static ResponseMessage1 thongKeTheoNgay(String data, int diviceID) {
        Log.logServices("120 Thanh cong" + data);
        ResponseMessage1 response = new ResponseMessage1();
        response.funcId = 120;
        try {
            ObjectThongKeRequest oTKR = new Gson().fromJson(data, ObjectThongKeRequest.class);
            if (oTKR == null || data == null || data.equals("")) {
                response = StatusResponse(3);
            } else if (oTKR.limit != 0) {
                response = thongKeTheoNgayFunC(oTKR);
            }

        } catch (Exception ex) {
            Log.logServices("updateGroup Exception: " + ex.getMessage());
            response = StatusResponse(3);
        }
        return response;
    }

    public static ResponseMessage1 layDanhSachGroup(String data, int diviceID) {
        Log.logServices("11801 Thanh cong" + data);
        ResponseMessage1 response = new ResponseMessage1();
        response.funcId = 11801;
        try {
            GroupMcID oTKR = new Gson().fromJson(data, GroupMcID.class);
            if (oTKR == null || data == null || data.equals("")) {
                response = StatusResponse(3);
            } else if (oTKR.mcID != null) {
                response.result = Tool.setBase64(layDSGroupCoFace(oTKR.mcID).toString());
                response.msgCode = 1;
            }

        } catch (Exception ex) {
            Log.logServices("layDanhSachGroup Exception: " + ex.getMessage());
            response = StatusResponse(3);
        }
        return response;
    }

    public static ResponseMessage1 luuIPMay(int funcId,  String data, int diviceID) {
        Log.logServices("121 Thanh cong" + data);
        ArrayList<ObjectLayIPRequest> arrReq = new ArrayList<>();
        ResponseMessage1 response = new ResponseMessage1();
        response.funcId = 121;
        try {
            arrReq = new Gson().fromJson(data, new TypeToken<ArrayList<ObjectLayIPRequest>>() {
            }.getType());
            if (arrReq == null || data == null || data.equals("")) {
                response = StatusResponse(3);
            } else if (arrReq != null&&arrReq.size()>0) {
                for(ObjectLayIPRequest arr:arrReq){
                    if (checkLuuIPMayDB(arr.id)) {
                        BackUpControllerDataBase.luuIPMayDBUpdate(arr);
                    }else {
                        BackUpControllerDataBase.luuIPMayDBInsert(arr);
                    }
                }
                luuIP();
                thayDoiTrangThaiMay();
                response.msgCode =1 ;
                response.msgContent ="Success" ;

            }

        } catch (Exception ex) {
            Log.logServices("luuIPMay Exception: " + ex.getMessage());
            response = StatusResponse(3);
        }
        return response;
    }


}
