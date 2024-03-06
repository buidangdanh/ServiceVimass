package vn.vimass.service.table.object;

public class ObjectLichSuRaVaoQuetQrMay1 {
    public String id;
    public String vID;
    public String accName;
    public String loiRa;
    public String idThietBi;
    public String diaChi;
    public long thoiGianGhiNhan;
    public String result;
    public String phiRaVao;
    public int personNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getLoiRa() {
        return loiRa;
    }

    public void setLoiRa(String loiRa) {
        this.loiRa = loiRa;
    }

    public String getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(String idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public long getThoiGianGhiNhan() {
        return thoiGianGhiNhan;
    }

    public void setThoiGianGhiNhan(long thoiGianGhiNhan) {
        this.thoiGianGhiNhan = thoiGianGhiNhan;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPhiRaVao() {
        return phiRaVao;
    }

    public void setPhiRaVao(String phiRaVao) {
        this.phiRaVao = phiRaVao;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    @Override
    public String toString() {
        return "ObjectLichSuRaVaoQuetQrMay1{" +
                "id='" + id + '\'' +
                ", vID='" + vID + '\'' +
                ", accName='" + accName + '\'' +
                ", loiRa='" + loiRa + '\'' +
                ", idThietBi='" + idThietBi + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", thoiGianGhiNhan=" + thoiGianGhiNhan +
                ", result='" + result + '\'' +
                ", phiRaVao='" + phiRaVao + '\'' +
                ", personNumber=" + personNumber +
                '}';
    }
}
