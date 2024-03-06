package vn.vimass.service.table.object;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectLSQRTrangThai {
    public ArrayList<String> thanhCong;
    public ArrayList<String> khongThanhCong;

    @Override
    public String toString() {
        return "ObjectLSQRTrangThai{" +
                "thanhCong=" + thanhCong +
                ", khongThanhCong=" + khongThanhCong +
                '}';
    }

    public ArrayList<String> getThanhCong() {
        return thanhCong;
    }

    public void setThanhCong(ArrayList<String> thanhCong) {
        this.thanhCong = thanhCong;
    }

    public ArrayList<String> getKhongThanhCong() {
        return khongThanhCong;
    }

    public void setKhongThanhCong(ArrayList<String> khongThanhCong) {
        this.khongThanhCong = khongThanhCong;
    }
}
