package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectTime {

    public long timeTo;
    public long timeFrom;

    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(long timeTo) {
        this.timeTo = timeTo;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }

}
