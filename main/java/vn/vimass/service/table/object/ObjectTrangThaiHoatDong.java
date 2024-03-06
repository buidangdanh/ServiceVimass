package vn.vimass.service.table.object;

public class ObjectTrangThaiHoatDong {
    public int mayCapNhat;
    public long thoiGianGanNHat;

    public int getMayCapNhat() {
        return mayCapNhat;
    }

    @Override
    public String toString() {
        return "ObjectTrangThaiHoatDong{" +
                "trangThaiMayHai=" + mayCapNhat +
                ", thoiGianGanNHat=" + thoiGianGanNHat +
                '}';
    }

    public void setMayCapNhat(int mayCapNhat) {
        this.mayCapNhat = mayCapNhat;
    }

    public long getThoiGianGanNHat() {
        return thoiGianGanNHat;
    }

    public void setThoiGianGanNHat(long thoiGianGanNHat) {
        this.thoiGianGanNHat = thoiGianGanNHat;
    }
}
