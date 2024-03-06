package vn.vimass.service.table.object;

public class ObjectLichSuQRBanGhiMoiNhat {
    public int soBanGhi;

    @Override
    public String toString() {
        return "ObjectLichSuQRBanGhiMoiNhat{" +
                "soBanGhi=" + soBanGhi +
                '}';
    }

    public int getSoBanGhi() {
        return soBanGhi;
    }

    public void setSoBanGhi(int soBanGhi) {
        this.soBanGhi = soBanGhi;
    }
}
