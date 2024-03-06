package vn.vimass.service.entity.group;

import com.google.gson.Gson;

public class UpdateGroupRequest {
    public String mcID;

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
