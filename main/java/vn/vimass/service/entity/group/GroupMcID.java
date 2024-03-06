package vn.vimass.service.entity.group;

import com.google.gson.Gson;

public class GroupMcID {
    public String mcID;
    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
