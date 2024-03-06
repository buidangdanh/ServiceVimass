package vn.vimass.service.table.NhomThietBiDiem.entity;

import com.google.gson.Gson;

public class ObjectGroupOfQR {
    public String id;
    public String groupID;
    public String groupName;
    public String mcID;
    public long timeTao;
    public String userTao;
    public long timeSua;
    public String userSua;
    public String mess;
    public int groupLevel;
    public String idQR;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGroupID() {
        return groupID;
    }
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getMcID() {
        return mcID;
    }
    public void setMcID(String mcID) {
        this.mcID = mcID;
    }
    public long getTimeTao() {
        return timeTao;
    }
    public void setTimeTao(long timeTao) {
        this.timeTao = timeTao;
    }
    public String getUserTao() {
        return userTao;
    }
    public void setUserTao(String userTao) {
        this.userTao = userTao;
    }
    public long getTimeSua() {
        return timeSua;
    }
    public void setTimeSua(long timeSua) {
        this.timeSua = timeSua;
    }
    public String getUserSua() {
        return userSua;
    }
    public void setUserSua(String userSua) {
        this.userSua = userSua;
    }
    public String getMess() {
        return mess;
    }
    public void setMess(String mess) {
        this.mess = mess;
    }
    public int getGroupLevel() {
        return groupLevel;
    }
    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
    }
    public String getIdQR() {
        return idQR;
    }
    public void setIdQR(String idQR) {
        this.idQR = idQR;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
