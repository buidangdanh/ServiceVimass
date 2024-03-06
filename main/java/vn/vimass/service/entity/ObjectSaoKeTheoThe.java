package vn.vimass.service.entity;

import javax.ws.rs.QueryParam;

public class ObjectSaoKeTheoThe {
    public long from;
    public long to;
    public int limit;
    public int offset;
    public String vID;

    public String phone;
    public String idThietBi;
    public int personNumber;

    public String mcID;

    public long getFrom() {
        return from;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(String idThietBi) {
        this.idThietBi = idThietBi;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    @Override
    public String toString() {
        return "ObjectSaoKeTheoThe{" +
                "from=" + from +
                ", to=" + to +
                ", limit=" + limit +
                ", offset=" + offset +
                ", vID='" + vID + '\'' +
                ", phone='" + phone + '\'' +
                ", idThietBi='" + idThietBi + '\'' +
                ", personNumber=" + personNumber +
                '}';
    }
}
