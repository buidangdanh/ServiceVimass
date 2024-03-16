package vn.vimass.service.crawler.bhd;

import vn.vimass.service.BackUp.BackUpRoutes;
import vn.vimass.service.BackUp.BackUpRoutesVer2;
import vn.vimass.service.BackUp.FingerPrint.FPFunC;
import vn.vimass.service.BackUp.FingerPrint.FPRoutes;
import vn.vimass.service.crawler.bhd.DongBoVsServer.DongBoController;
import vn.vimass.service.crawler.bhd.DongBoVsServer.DongBoDiemRaVaoController;
import vn.vimass.service.crawler.bhd.DongBoVsServer.SendDataFaceToDevice;
import vn.vimass.service.crawler.bhd.XuLyLayKhuonMat.LayKhuonMatController;
import vn.vimass.service.crawler.bhd.thongke.ThongKeController;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;

public class DieuPhoiController {
    public static ResponseMessage1 responseMessage(int funcId,long time, String data,int diviceID){

        ResponseMessage1 res = new ResponseMessage1();

        switch (funcId) {
            case 100: // dich vụ xac thuc ra vao
                res = DieuPhoiFunc.funcAuthenticInOut(funcId,data);
                break;
            case 102:// dich vu lay tat ca diem ra vao
                res = DieuPhoiFunc.getlistAddressAll(funcId);
                break;
            case 103:// lay thong tin 1 diem ra vao
                res = DieuPhoiFunc.getDetail(funcId,data);
                break;
            case 104:// dich vu lay sao ke
                res = DieuPhoiFunc.getSaoKe(funcId,data);
                break;
            case 105:// dich vu insert thong tin cua the
                res = DieuPhoiFunc.funcInsertInfoVid(funcId, data);
                break;
//            case 10501:// dich vu delete diem ra vao cau 1 the
//                res = DieuPhoiFunc.deleteIdDiemInfoVid(funcId, data, idDiem);
//                break;
            case 10502:// dich vu update thong tin cua the
                res = DieuPhoiFunc.funcInsertInfoVidOnly(funcId, data);
                break;

            case 106:// dich vu lay danh sach thong tin cua the duoc ra vao tai diem
                res = DieuPhoiFunc.getListInfoVid(funcId, data);
                break;
            case 10601:// dich vu lay danh sach thong tin cua the theo offset Limit duoc ra vao tai diem
                //   res = DieuPhoiFunc.getListInfoVidLimit(funcId, data);
                res = LayKhuonMatController.resFaceDataOfQR(funcId, data);
                break;
            case 10602:// dich vu lay danh sach thong tin cua the theo offset Limit duoc ra vao tai diem
                res = DieuPhoiFunc.getInfoVID(data,diviceID);
                break;
            case 10603:// dich vu lay danh sach thong tin cua the theo offset Limit duoc ra vao tai diem
                res = BackUpRoutesVer2.checkMat(data,diviceID);
                break;
            case 10604:// lay tat ca user co du lieu FaceData
                res = LayKhuonMatController.getListUserFaceData(funcId,data);
                break;
            case 107:// dich vu xoa thong tin the
                res = DieuPhoiFunc.deleteInfoVid(funcId, data);
                break;
//            case 108:// dich vu xac thuc khi tat wiffi
//                res = DieuPhoiFunc.funcAuthenticInOutServer(funcId, data);
//                break;
            case 109:// dich vu kiem tra server dang hoat dong
                res = DieuPhoiFunc.geValueResponseMessage1(funcId, 1,"successfully","ok");
                break;
            case 110:// dich vu send Data to pc default
                res = DieuPhoiFunc.sendData(funcId, data);
                break;
            case 111:// lay thong tin khoa
                res = DieuPhoiFunc.getInfoLock(funcId, data);
                break;
            case 112:// lay thong tin device
                res = DieuPhoiFunc.getInfoDevice(funcId, data);
                break;
            case 113:// dang ki thiet bi quan ly
                res = DieuPhoiFunc.changeInfoDevice(funcId, data,diviceID);
                break;
            case 114:// lay thong tin tu so dien thoai hoac so the
                res = DieuPhoiFunc.getInfoFromInfo_vidSDTorVID(funcId, data,diviceID);
                break;
//            case 115:// them ControllerVpass
//                res = DieuPhoiFunc.ControllerVpass(funcId, data);
//                break;
//            case 11501:// update ControllerVpass
//                res = DieuPhoiFunc.ControllerVpass(funcId, data);
//                break;
//            case 11502:// delete ControllerVpass
//                res = DieuPhoiFunc.ControllerVpass(funcId, data);
//                break;
//            case 11503:// delete ControllerVpass
//                res = DieuPhoiFunc.listControllerVpass(funcId);
//                break;
            case 116://
                res = DieuPhoiFunc.VPassDeviceInfo(funcId, data);
                break;
            case 11601://
                res = DieuPhoiFunc.VPassDeviceInfo(funcId, data);
                break;
            case 11602://
                res = DieuPhoiFunc.listVPassDeviceInfo(funcId);
                break;
            case 117:// them van tay
                res = DieuPhoiFunc.addFingerprint(funcId,data);
                break;
            case 11701:// get list
                res = DieuPhoiFunc.ListFPbyIdQR(funcId);
                break;
            case 11702:// get item van tay theo diem ra vao
                res = DieuPhoiFunc.getItemFPbyIdQR(funcId,data);
                break;
            case 11703:// get item thong tin diem ra vao
                res = DieuPhoiFunc.getItemIpAddress(funcId,data);
                break;
            case 11704://
                res = DieuPhoiFunc.deleteItemFPbyIdQR(funcId,data);
                break;
            case 11705://
                res = DieuPhoiFunc.deleteAllFPbyIdQR(funcId);
                break;
//            case 118://
//                res = DieuPhoiFunc.updateGroup(funcId,data,diviceID);
//                break;
            case 11801://lay danh sach group
                res = BackUpRoutes.layDanhSachGroup(data,diviceID);
                break;
            case 119:// thong ke giao dich dinh danh
                res = ThongKeController.thongKe(funcId,time, data, diviceID);
                break;
            case 120:// thong ke giao dich dinh danh 2
                res = BackUpRoutes.thongKeTheoNgay( data, diviceID);
                break;
            case 121:// luu ip cua may
                res = BackUpRoutes.luuIPMay(funcId, data, diviceID);
                break;
            case 122:// lay sao ke gan nhat
                res = BackUpRoutes.layDuLieuQRMoiNhat(data);
                break;
            case 123:// thong bao cap nhat nhom
                res = DongBoController.thongBaoCapNhatNhom_ThietBiKhoa_QRvsThe(funcId,time,data);
                break;
            case 12301:// nhan thong tin ip,post, id(Điểm thiết bị quản lý) cua thiet bi dien thoai
                res = SendDataFaceToDevice.resDeviceVpassActive(funcId,time,data);
                break;
            case 12302:// gui thong bao dong bo du lieu khuon mat tai diem dinh dnah, khi co thay doi
                res = SendDataFaceToDevice.func(funcId,time,data);
                break;
            case 124:// xoa user trong don vi
                res = DongBoController.xoaUserTrongDonVi(funcId,time,data);
                break;
            case 125:// lay danh sach thiet bị vpass
                res = DieuPhoiFunc.getListVpass();
                break;
            case 126:// lay danh sach thiet bị vpass
                res = FPRoutes.trangThaiFP(data);
                break;
            case 127:// lay danh sach thiet bị vpass
                res = FPRoutes.themSuaXoaFP(funcId,time,data);
                break;
            default:
                Log.logServices("post Dieu PHOI input:");
        }
        return res;
    }
}

