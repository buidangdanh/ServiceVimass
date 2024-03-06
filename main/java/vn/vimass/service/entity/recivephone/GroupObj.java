package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;

import java.util.ArrayList;

public class GroupObj {
    public String id;// id qr
    public String groupName;// nhóm
    public String mcID;
    public long timeTao;
    public String userTao;
    public long timeSua;
    public String userSua;
    public String mess;
    public int groupLevel; // 1: nhóm = list per, 2: nhóm list nhóm 1
    public int type;
    public ArrayList<GroupObj> listGr;
    public ArrayList<ObjListPer> listPer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<GroupObj> getListGr() {
        return listGr;
    }

    public void setListGr(ArrayList<GroupObj> listGr) {
        this.listGr = listGr;
    }

    public ArrayList<ObjListPer> getListPer() {
        return listPer;
    }

    public void setListPer(ArrayList<ObjListPer> listPer) {
        this.listPer = listPer;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }



}
