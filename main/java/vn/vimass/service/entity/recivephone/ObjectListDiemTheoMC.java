package vn.vimass.service.entity.recivephone;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ObjectListDiemTheoMC {
    public String id;
    public String mcID;
    public String catID;
    public long timeTao;
    public ArrayList<GroupObj> listGroup;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public long getTimeTao() {
        return timeTao;
    }

    public void setTimeTao(long timeTao) {
        this.timeTao = timeTao;
    }

    public ArrayList<GroupObj> getListGroup() {
        return listGroup;
    }

    public void setListGroup(ArrayList<GroupObj> listGroup) {
        this.listGroup = listGroup;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
