package vn.vimass.service.entity;

public class ObjectQuyenTruyCap {
    public String soThe;
    public String soVi;

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }

    public String getSoVi() {
        return soVi;
    }

    public void setSoVi(String soVi) {
        this.soVi = soVi;
    }

    @Override
    public String toString() {
        return "ObjectQuyenTruyCap{" +
                "soThe='" + soThe + '\'' +
                ", soVi='" + soVi + '\'' +
                '}';
    }
}
