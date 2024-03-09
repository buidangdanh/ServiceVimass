package vn.vimass.service.crawler.bhd.XuLyXacThuc;

import com.google.gson.Gson;

import vn.vimass.service.crawler.bhd.SendData.SendDataController;
import vn.vimass.service.crawler.bhd.updateServer.UploadXacThucClient;
import vn.vimass.service.entity.ObjectLichSuRaVaoQuetQr;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.InforVid;
import vn.vimass.service.table.LichSuRaVaoQuetQR;
import vn.vimass.service.table.NhomThietBiDiem.*;
import vn.vimass.service.table.NhomThietBiDiem.entity.*;
import vn.vimass.service.table.object.ObjecDiaDiemXacThucThanhCong;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.table.object.ObjectQr;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class DinhDanhXacThuc {
    public static ObjectInfoVid objChuThe;
    public static String name = "";
    public static String vIDorPhone = "";
    private static boolean checkPush = false;

    public static String generateSessionKeyLowestCase(int length) {
        String alphabet = new String(
                "0123456789abcdefghijklmnopqrstuvwxyz"); // 9
        int n = alphabet.length(); // 10

        String result = new String();
        Random r = new Random(); // 11

        for (int i = 0; i < length; i++)
            // 12
            result = result + alphabet.charAt(r.nextInt(n)); // 13

        return result;
    }

    public static ObjectLichSuRaVaoQuetQr getValueLichSuRaVaoQr(String maGD, String vID, String phone, String accName, String loiRaVao,
                                                                String idQr, String address, long thoiGianNghiNhan, int fee, int personNumber,
                                                                long timeAuthen, int typeDataAuThen, String vpassID, String chucDanh, int typeXacThuc,
                                                                String thongTinDiem, String avatar) {
        ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();
        item.maRaVao = maGD;
        item.vID = vID;
        item.phone = phone;
        item.typeXacThuc = typeXacThuc;
        item.accName = accName;
        item.loiRa = loiRaVao;
        item.idThietBi = idQr;
        item.thongTinDiem = thongTinDiem;
        item.diaChi = address;
        item.thoiGianGhiNhan = thoiGianNghiNhan;
        item.phiRaVao = fee;
        item.result = item.toString();
        item.personNumber = personNumber;
        item.vpassID = vpassID;
        item.timeAuthen = timeAuthen;
        item.chucDanh = chucDanh;
        item.typeDataAuThen = typeDataAuThen;
        item.avatar = avatar;
        return item;
    }

    public static void goiLenhMoCua(String ipAddress, String loiRaVao, int portIP) {

        try {
            Log.logServices(" ====================> goiLenhMoCua ");
            if (true) {
                Log.logServices("gọi mở cửa 1");
                DatagramSocket socket = new DatagramSocket();
//			// Chuỗi dữ liệu cần gửi
                //String data = FunctionGeneral.tinhChuoiMoCua();
                String data = "ON";
                Log.logServices("gọi mở cửa 2");
                // Chuyển đổi chuỗi dữ liệu thành mảng byte
                byte[] buffer = data.getBytes();
                Log.logServices("gọi mở cửa 4");
                // Khởi tạo địa chỉ IP và cổng của máy chủ UDP
//			    InetAddress address = InetAddress.getByName(GiaoDienHienThi2Cong.textUDPDich);
                InetAddress address = InetAddress.getByName(ipAddress);
//				int port = 8888;
                int port = portIP;
                Log.logServices("gọi mở cửa 4");
                // Khởi tạo DatagramPacket để gửi gói tin UDP
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                Log.logServices("gọi mở cửa 5 =======> gui tin hieu mo cua");
                // Gửi gói tin UDP
                socket.send(packet);
                Log.logServices("gọi mở cửa 6 =======> ket thuc gui tin hieu mo cua");
                socket.close();
                Log.logServices("gọi mở cửa thành công ip: " + ipAddress + " / " + portIP + " room: " + loiRaVao);
                Log.logServices("gọi mở cửa thành công ip: " + ipAddress + " room: " + loiRaVao);

                Log.logServices(" ====================> goiLenhMoCua end");
            }

        } catch (Exception e) {

            Log.logServices("goiLenhMoCua: " + e.getMessage());
            Log.logServices("goiLenhMoCua ex: " + e.getMessage());
        }
    }

    //------------------- check int out new --------------------------------------------------------------------------
    public static boolean checkQR(ObjecDiaDiemXacThucThanhCong objAuthen) {
        ObjPerson objPerson = null;
        boolean checkInOunt = false;
        String avatar = "";
        try {
            if (objAuthen.vID.length() > 0) {
                vIDorPhone = objAuthen.vID;
                objPerson = PersonOfGroup.getItemPerOfGroup(objAuthen.idQR, objAuthen.vID);
                System.out.println("objPerson ===========> " + objPerson);
                Log.logServices("objPerson ===========> " + objPerson);
                if (objPerson == null) {
                    objPerson = PersonOfGroup.getItemPerOfGroupOfAllQR(objAuthen.vID);
                }
            } else {
                vIDorPhone = objAuthen.phone;
                objPerson = PersonOfGroup.getItemPerOfGroup(objAuthen.idQR, objAuthen.phone);
                if (objPerson == null) {
                    objPerson = PersonOfGroup.getItemPerOfGroupOfAllQR(objAuthen.phone);

                }
                avatar = objPerson.avatar;
            }
            if (objPerson.getName() != null) {
                name = objPerson.getName().trim();
            } else {
                name = "Chưa cập nhật";
            }

            String maGD = objAuthen.idQR + objAuthen.thoiGianGhiNhan + generateSessionKeyLowestCase(5);
            System.out.println("maGD ===========> " + maGD + "/n  objAuthen ===========> " + objAuthen);
            Log.logServices("maGD ===========> " + maGD + "/n  objAuthen ===========> " + objAuthen);
            boolean checkInOut = false;

            ObjQR objQR = getObjInfoQR(objAuthen.idQR);
            System.out.println("objQR ===========> " + objQR);
            Log.logServices("objQR ===========> " + objQR);
            ObjInfoQR objInfoQR = objQR.infor;

            ObjLockDevice objLockDevice = ThietBiKhoa.getItem(objQR.LockDeviceID);
            System.out.println("objLockDevice ===========> " + objLockDevice);
            Log.logServices("objLockDevice ===========> " + objLockDevice);
            checkInOut = fucnCheckInOut(objAuthen.idQR, objAuthen.vID, objAuthen.phone);
            System.out.println("checkInOut ===========> " + checkInOut);
            Log.logServices("checkInOut ===========> " + checkInOut);
            ObjectLichSuRaVaoQuetQr itemLichSu = getValueLichSuRaVaoQr(maGD, objAuthen.vID, objAuthen.phone, name,
                    objInfoQR.tenCuaHang, objAuthen.idQR, objInfoQR.diaChi, objAuthen.thoiGianGhiNhan, 0, objAuthen.personNumber,
                    objAuthen.timeAuthen, objAuthen.typeDataAuThen, objAuthen.vpassID, objPerson.chucDanh, objAuthen.typeXacThuc,
                    objInfoQR.toString(), avatar);
            System.out.println("itemLichSu ===========> " + itemLichSu);
            Log.logServices("itemLichSu ===========> " + itemLichSu);
            if (checkInOut) {
                funcXuLyHienThiDongMoCuaV1(objLockDevice, itemLichSu);
                UploadXacThucClient.upLoad(objAuthen.id, objAuthen.vID, "", objAuthen.personNumber,
                        objAuthen.device, objAuthen.typeDataAuThen, objAuthen.thoiGianGhiNhan, objAuthen.idQR, "VCARD");

                checkInOunt = true;
            } else {

                checkInOunt = false;
                System.out.println("Thẻ " + objAuthen.accName + " không có trong danh sách quản lý đơn vị!");
                Log.logServices("Thẻ " + objAuthen.accName + " không có trong danh sách quản lý đơn vị!");
            }
        } catch (Exception ex) {
            Log.logServices("checkQR Exception!" + ex.getMessage());

        }

        return checkInOunt;
    }

    private static void funcXuLyHienThiDongMoCuaV1(ObjLockDevice objLockDevice, ObjectLichSuRaVaoQuetQr itemLichSu) {

        try {
            if (objLockDevice.typeD == 1) {
                goiLenhMoCua(objLockDevice.ip, objLockDevice.nameDevice, objLockDevice.portD);
            }
            if (objLockDevice.typeD == 3) {
//                barrieFunc.actionOpen(objLockDevice.ip, objLockDevice.portD);
//                VimassData.idDiemRaVaoBarrie = objLockDevice.idLookDevice;
//                VimassData.checkOpenBarrie = true;
            }

            LichSuRaVaoQuetQR.taoDuLieu(itemLichSu);
            SendDataController.SendData(100, itemLichSu.toString(), "");

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception mo cua, hien thi: " + e.getMessage());
        }
    }

    // lay thong tin cua 1 qr
    private static ObjQR getObjInfoQR(String key) {
        ObjQR obj = QRvsCard.getQRInfo(key);
//        ObjInfoQR obj = new Gson().fromJson(infoQR, ObjInfoQR.class);
        return obj;
    }

    // kiem tra danh sach ra vao tai diem co duoc phep khong
    private static boolean fucnCheckInOut(String idQR, String vid, String sdt) {
        System.out.println("fucnCheckInOut ===========> " + 1);
        boolean checkInOut = false;
        String key = "";
        if (vid.length() > 0) {
            key = vid;
        }
        if (sdt.length() > 0) {
            key = sdt;
        }
        ArrayList<ObjectGroupOfQR> arrayList = GroupOfQR.getListGrOfQR(idQR);
        if (arrayList.size() > 0) {
            checkInOut = checkItemPerOfGroup(arrayList, key, sdt, vid);
        }
        return checkInOut;

    }

    private static boolean checkItemPerOfGroup(ArrayList<ObjectGroupOfQR> arrayList, String key, String sdt, String vid) {
        ObjPerson objPerson = null;
        for (ObjectGroupOfQR item : arrayList) {
            objPerson = PersonOfGroup.getItemPerOfGroup(item.idQR, key);
            if (objPerson != null) {
                System.out.println("objPerson ===========> " + objPerson);
                if (objPerson.idQR.equals(item.idQR) && objPerson.sdt.equals(sdt) || objPerson.idQR.equals(item.idQR) && objPerson.vID.equals(vid)) {
                    return true;
                }
            }
        }
        return false;
    }

}
