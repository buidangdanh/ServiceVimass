package vn.vimass.service.BackUp.FingerPrint;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.google.gson.Gson;
import vn.vimass.service.BackUp.FingerPrint.Obj.*;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ResponseIP;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;
import vn.vimass.service.log.ServicesData;
import vn.vimass.service.table.NhomThietBiDiem.entity.ListDiem;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.table.object.ObjectXacThuc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static vn.vimass.service.BackUp.BackUpFunCVer2.layThietBiVanTay;
import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;
import static vn.vimass.service.BackUp.FingerPrint.FPComandPacket.clearTemp;
import static vn.vimass.service.BackUp.FingerPrint.FPComandPacket.setDeiceID;
import static vn.vimass.service.BackUp.FingerPrint.FPDataBase.*;
import static vn.vimass.service.BackUp.FingerPrint.FPFunC.*;
import static vn.vimass.service.BackUp.FingerPrint.FPFunC.sendData;
import static vn.vimass.service.CallService.CallService.PostREST;

public class FPRoutes {
    public static HashMap<String, Boolean> HashIP = new HashMap<>();

    public static ResponseMessage1 trangThaiFP(String data) {
        ResponseMessage1 res = new ResponseMessage1();
        res.funcId = 126;

        try {
            RequestTTFP requestTTFP = new Gson().fromJson(data, RequestTTFP.class);
            for (ObjFP arr : getThietBiFP()) {
                if (!HashIP.containsKey(arr.id)) {
                    HashIP.put(arr.id, true);
                }
            }
            if (HashIP != null && HashIP.size() > 0) {
                if (HashIP.containsKey(requestTTFP.idFP)) {
                    if (HashIP.get(requestTTFP.idFP)) {
                        res.msgCode = 1;
                        res.msgContent = "Success!";
                    } else {
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
                chuyenTrangThai(requestClient.idFP, false);
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
                        if (xoaKhoiThietBi(requestClient, COM)) {
                            res.msgCode = 1;
                            res.msgContent = Tool.setBase64("Thành công");
                        } else {
                            res.msgCode = 2;
                            res.msgContent = Tool.setBase64("Không thành công");
                        }


                    }
                } else {
                    res.msgCode = 3;
                    res.msgContent = Tool.setBase64("Thiết bị vân tay chưa được active");
                }


            }
            chuyenTrangThai(requestClient.idFP, true);

        } catch (Exception ex) {
            Log.logServices("themSuaXoaFP Exception: " + ex.getMessage());
            res = StatusResponse(3);
        }

        return res;

    }

    public static ResponseMessage1 themSuaXoaFPTheoThe(int funcId, long time, String data) {
        Log.logServices("128 Thanh cong" + data);
        ResponseMessage1 res = new ResponseMessage1();
        ArrayList<ObjFP> listFPLocal = new ArrayList<>();
        res.funcId = 128;
        try {
            listFPLocal = getThietBiFP(); //Lay thiet bi van tay hien co
            ArrayList<FingerData> dataFPTrongMini = new ArrayList<>();
            ObjTSXFPTheoThe requestClient = new Gson().fromJson(data, ObjTSXFPTheoThe.class); // Gson tu client Gui len
            if (requestClient == null || data == null || data.equals("")) {
                res = StatusResponse(3);
            } else {
                chuyenTrangThai(requestClient.idFP, false);  // Chuyen trang thai cua van tay sang che cho khong xac thuc
                Thread.sleep(1000);
                capNhatPort(listFPLocal); // Cap nhat port cho cac van tay
                Thread.sleep(300);
                String COM = "";
                dataFPTrongMini = layFPDataTheoNguoi(requestClient.thongTinNguoi); //Lay thong tin van tay hien tai của nguoi do
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
                            res = dangKyVanTayTheoThe(requestClient, COM, dataFPTrongMini);
                        }
                    }
                    /*else if (requestClient.type == 3) {
                        xoaKhoiDB(dataFPTrongMini, requestClient);
                        if(xoaKhoiThietBi(requestClient, COM)){
                            res.msgCode = 1;
                            res.msgContent = Tool.setBase64("Thành công");
                        }else {
                            res.msgCode = 2;
                            res.msgContent = Tool.setBase64("Không thành công");
                        }


                    }*/
                } else {
                    res.msgCode = 3;
                    res.msgContent = Tool.setBase64("Thiết bị vân tay chưa được active");
                }


            }
            chuyenTrangThai(requestClient.idFP, true);

        } catch (Exception ex) {
            Log.logServices("themSuaXoaFPTheoThe Exception: " + ex.getMessage());
            res = StatusResponse(3);
        }

        return res;

    }

    public static void chuyenTrangThai(String idFP, boolean b) {
        try {
            if (HashIP.containsKey(idFP)) {
                HashIP.put(idFP, b);
            }
        } catch (Exception ex) {
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


    public static ResponseMessage1 XacThuc(int funcId, long time, String data) {
        Log.logServices("129 Thanh cong" + data);
        ResponseMessage1 res = new ResponseMessage1();
        ArrayList<ObjFP> listFPLocal = new ArrayList<>();
        ObjFP objFP = new ObjFP();
        res.funcId = 129;
        try {
            listFPLocal = getThietBiFP();
            ObjXacThucTuDienThoai requestClient = new Gson().fromJson(data, ObjXacThucTuDienThoai.class);
            objFP = tuIDRaObjFP(listFPLocal, requestClient.idFP);
            if (requestClient == null || data == null || data.equals("")) {
                res = StatusResponse(3);
            } else {
                chuyenTrangThai(requestClient.idFP, false);
                Thread.sleep(1000);
                capNhatPort(listFPLocal);
                Thread.sleep(300);
                String COM = "";
                COM = truyenIDRaCOM(requestClient.idFP);
                if (COM != null && !COM.equals("")) {
                    if (requestClient.type == 0) {
                        res.msgCode = 1;
                        res.result = Tool.setBase64("Gọi sai type rồi");
                    } else if (requestClient.type == 1) {
                        Log.logServices("XacThuc : " + COM);

                        XTFPTuDienThoaiFunC(COM, objFP);
                        res.msgCode = 1;
                        res.result = Tool.setBase64("Thành Công");

                    }
                } else {
                    res.msgCode = 3;
                    res.msgContent = Tool.setBase64("Thiết bị vân tay chưa được active");
                }


            }
            chuyenTrangThai(requestClient.idFP, true);

        } catch (Exception ex) {
            Log.logServices("themSuaXoaFP Exception: " + ex.getMessage());
            res = StatusResponse(3);
        }

        return res;
    }

    public static ObjFP tuIDRaObjFP(ArrayList<ObjFP> listFPLocal, String idFP) {
        ObjFP objFP = new ObjFP();
        try {

            if (listFPLocal != null) {
                for (ObjFP arr : listFPLocal) {
                    if (arr.id != null && arr.id.equals(idFP)) {
                        objFP = arr;
                    }
                }
            }
        } catch (Exception ex) {
            Log.logServices("tuIDRaObjFP Exception: " + ex.getMessage());

        }
        return objFP;
    }

    private static ResponseMessage1 XTFPTuDienThoaiFunC(String COM, ObjFP objFP) {
        ResponseMessage1 res = new ResponseMessage1();
        try {
            //sendXacThucTuDienThoai(SerialPort.getCommPort(COM), identifyFree(Identify_Free), objFP);
            sendXacThucTuDienThoai(SerialPort.getCommPort(COM), "55 AA 02 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 02 01 ", objFP);

        } catch (Exception ex) {
            Log.logServices("XTFPTuDienThoaiFunC Exception: " + ex.getMessage());

        }
        return res;
    }

    public static boolean sendXacThucTuDienThoai(SerialPort port, String hexString, ObjFP objFP) {
        final boolean[] kq = {false};
        AtomicReference<String> res = new AtomicReference<>(""); // Use AtomicReference
        try {
            if (port.openPort()) {
                System.out.println("Successfully opened the port.");
                port.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
                byte[] dataToSend = hexStringToByteArray(hexString);
                port.writeBytes(dataToSend, dataToSend.length);

                SerialPortDataListener dataListener = new SerialPortDataListener() {
                    @Override
                    public int getListeningEvents() {
                        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                    }

                    @Override
                    public void serialEvent(SerialPortEvent event) {
                        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                            byte[] tempBuffer = new byte[48]; // Tạo một bộ đệm tạm thời
                            int totalBytesRead = 0;

                            while (totalBytesRead < 48) {
                                int availableBytes = port.bytesAvailable();
                                if (availableBytes > 0) {
                                    byte[] newData = new byte[Math.min(availableBytes, 48 - totalBytesRead)];
                                    int numRead = port.readBytes(newData, newData.length);
                                    System.arraycopy(newData, 0, tempBuffer, totalBytesRead, numRead);
                                    totalBytesRead += numRead;
                                }
                                try {
                                    Thread.sleep(100); // Chờ một chút để dữ liệu tiếp tục đến
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }

                            Log.logServices("Read " + totalBytesRead + " bytes.");
                            String currentData = "";
                            currentData = bytesToHex(tempBuffer);

                            if (currentData.indexOf("F4FF") > -1 && currentData.length() == 96) {

                                String finalCurrentData = currentData;
                                Log.logServices("fff" + finalCurrentData);
                                kq[0] = goiDichVuSaoKe(finalCurrentData, objFP);
                                port.removeDataListener(); // Stop listening to data available events
                                port.closePort(); // Close the port
                            }

                            res.set(currentData);
                            System.out.println("cso hasdf: " + currentData);// Continue accumulating the data
                        }
                    }
                };
                port.addDataListener(dataListener);

                try {
                    Thread.sleep(5000); // Waiting for 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                port.closePort();
                System.out.println("Port closed.");
            } else {
                System.out.println("Unable to open the port.");
            }
        } catch (Exception ex) {
            Log.logServices("sendDataToSerialPort Exception!" + ex.getMessage());
        }
        return kq[0]; // Return the accumulated result
    }

    private static boolean goiDichVuSaoKe(String finalCurrentData, ObjFP objFP) {
        Boolean kq = false;
        try {
            ObjectInfoVid objectInfoVid = new ObjectInfoVid();
            objectInfoVid = getidVidFromEmptyID(hexStringRaID(finalCurrentData), objFP.id);
            if (objectInfoVid != null && objectInfoVid.idVid != null && !objectInfoVid.idVid.equals("")) {
                kq = goiDichVu100(objectInfoVid, objFP);
            }

        } catch (Exception ex) {
            Log.logServices("goiDichVuSaoKe Exception: " + ex.getMessage());
        }
        return kq;
    }

    private static boolean goiDichVu100(ObjectInfoVid infoVid, ObjFP objFP) {
        boolean kq = false;
        try {
            ArrayList<ListDiem> listDiemArrayList = new ArrayList<>();
            listDiemArrayList = objFP.listDiem;
            if (listDiemArrayList != null && listDiemArrayList.size() > 0) {

                ObjectXacThuc obj = new ObjectXacThuc();
                obj.idQR = listDiemArrayList.get(0).infor.id;
                obj.nameQR = listDiemArrayList.get(0).infor.tenCuaHang;
                obj.addressQR = listDiemArrayList.get(0).infor.diaChi;
                obj.device = 3;
                obj.phone = "";
                obj.vID = infoVid.idVid;
                obj.personNumber = 0;
                obj.accName = infoVid.hoTen;
                obj.thoiGianGhiNhan = new Date().getTime();
                obj.cks = "";
                obj.typeXacThuc = 4;
                obj.vpassID = "1234";
                obj.typeDataAuThen = 1;
                obj.timeAuthen = 1;
                ResponseIP objPost = new ResponseIP();
                objPost.funcId = 100;
                objPost.device = 3;
                objPost.currentime = new Date().getTime();
                objPost.data = obj.toString();

                String kqGoiMini = PostREST("http://193.169.1.69:58080/autobank/services/vimassTool/dieuPhoi", new Gson().toJson(objPost));
                Log.logServices("request kqGoiMini" + new Gson().toJson(objPost));
                ResponseVanTay responseVanTay = new Gson().fromJson(kqGoiMini, ResponseVanTay.class);
                if (responseVanTay.msgCode == 1) {
                    kq = true;
                }
            }


        } catch (Exception ex) {
            Log.logServices("goiDichVu100 Exception: " + ex.getMessage());

        }
        return kq;
    }

    public static ResponseMessage1 themSuaXoaTBFP(int funcId, long time, String data) {
        Log.logServices("130 Thanh cong" + data);
        ResponseMessage1 res = new ResponseMessage1();
        ArrayList<ObjFP> listFPLocal = new ArrayList<>();
        ArrayList<ObjFP> listFPServer = new ArrayList<>();
        ObjFP objFP = new ObjFP();
        res.funcId = 130;
        try {
            listFPLocal = getThietBiFP();
            ObjectSuaVanTay requestClient = new Gson().fromJson(data, ObjectSuaVanTay.class);
            if (requestClient == null || data == null || data.equals("")) {
                res = StatusResponse(3);
            } else {
                String idMoiNhat = taoIdFPMoiNhat(listFPLocal);
                if (requestClient.listItem != null) {
                    for (ObjFPSua thietBiVanTayRequest : requestClient.listItem) {
                        if (thietBiVanTayRequest.type == 1) {
                            thietBiVanTayRequest.id = "F" + new Date().getTime() + ServicesData.generateSessionKeyLowestCase(4).toUpperCase();
                            themMoiThietBiVanTayDBTuClient(thietBiVanTayRequest);
                            activeThietBiMoi(idMoiNhat);
                            capNhatIdVanTayDB(idMoiNhat,thietBiVanTayRequest.id);
                        } else if (thietBiVanTayRequest.type == 2) {
                            if(capNhatThietBiVanTayDBKhongDongBo(thietBiVanTayRequest)){
                                res.msgCode = 1;
                                res.msgContent = "Success!";
                            }else {
                                res.msgCode = 2;
                                res.msgContent = "Unsuccess!";
                            }
                        } else if (thietBiVanTayRequest.type==3) {
                            for (ObjFP ar : listFPLocal) {
                                if (ar.id.equals(thietBiVanTayRequest.id)) {
                                    xoaFpID(ar.port);
                                    NhayDen(ar.port);
                                }
                            }
                            xoaThietBiFP(thietBiVanTayRequest.id);
                        }
                    }
                }


            }


        } catch (Exception ex) {
            Log.logServices("themSuaXoaTBFP Exception: " + ex.getMessage());
            res = StatusResponse(3);
        }

        return res;
    }
    public static ResponseMessage1 layThietBiVanTay(int funcId, long time, String data) {
        Log.logServices("131 Thanh cong" + data);
        ResponseMessage1 res = new ResponseMessage1();
        ArrayList<ObjFP> listFPLocal = new ArrayList<>();
        res.funcId = 131;
        try {
            listFPLocal = getThietBiFP();
            ObjectSuaVanTay requestClient = new Gson().fromJson(data, ObjectSuaVanTay.class);
            if (requestClient == null || data == null || data.equals("")) {
                res = StatusResponse(3);
            } else {
                res.msgCode = 1;
                res.msgContent = "Success";
                res.result = Tool.setBase64(listFPLocal.toString());
            }


        } catch (Exception ex) {
            Log.logServices("layThietBiVanTay Exception: " + ex.getMessage());
            res = StatusResponse(3);
        }

        return res;
    }

    private static void activeThietBiMoi(String idMoiNhat) {
        try{
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                if (port.getDescriptivePortName() != null && port.getDescriptivePortName().contains("USB Serial Port")) {
                    String t = sendData(SerialPort.getCommPort(port.getSystemPortName()), "55 AA 11 01 00 00 0200 00 00 00 00 00 00 00 00 00 00 00 00 00 001301", 100);
                    if (t != null) {
                        if(t.substring(16,20).equals("0000")||t.substring(16,20).equals("0100")){
                            Log.logServices("activeThietBiMoi kq: "+sendData(SerialPort.getCommPort(port.getSystemPortName()), setDeiceID(idMoiNhat), 100));
                            capNhatPortVanTayDB(port.getSystemPortName(), t.substring(16, 20));
                        }
                    }
                }
            }
        }catch (Exception ex){
            Log.logServices("activeThietBiMoi Exception: " + ex.getMessage());

        }
    }
}
