package vn.vimass.service.table.object;

import com.google.gson.Gson;

public class ObjectFPRequest {
    public String idVid ;
    public String personName;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
