package vn.vimass.service.BackUp.FingerPrint.Obj;

import java.util.ArrayList;

public class ObjFPSua {
    public String id;
    public String mcID;

    public String idDonVi;
    public String nameF;
    public int totalF; // 1k, 3k
    public int currentF; // so luong van tay hien co

    public ArrayList<String> listIDDiem; // id cua QR diem ra vao
    public String IdDeviceVManager; // id thiet bi V|I|A. luu y khong phai id cua deviceID

    public int type; // 1 them, 2 sua, 3 xoa // k luu db. trang thai de cap nhat khi client gọi dv

}
