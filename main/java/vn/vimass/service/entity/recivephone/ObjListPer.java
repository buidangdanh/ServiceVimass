package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;

public class ObjListPer {
    public String id;
    public String chucDanh;
    public long timeTao;
    public String idThietBi;
    public String userTao;
    public String personID;
    public String uID;
    public String vID;
    public String name;
    public String sdt;
    public String groupID;
    public int type;
    public int perNum;
    public String avatar;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    public long getTimeTao() {
        return timeTao;
    }

    public void setTimeTao(long timeTao) {
        this.timeTao = timeTao;
    }

    public String getIdThietBi() {
        return idThietBi;
    }

    public void setIdThietBi(String idThietBi) {
        this.idThietBi = idThietBi;
    }

    public String getUserTao() {
        return userTao;
    }

    public void setUserTao(String userTao) {
        this.userTao = userTao;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getvID() {
        return vID;
    }

    public void setvID(String vID) {
        this.vID = vID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPerNum() {
        return perNum;
    }

    public void setPerNum(int perNum) {
        this.perNum = perNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
