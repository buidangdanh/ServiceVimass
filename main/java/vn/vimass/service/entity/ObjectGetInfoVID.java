package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectGetInfoVID {
    public String mcID;
    public int limit;
    public int offset;
    public int typeFace;

    public String textSearch;

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public int getTypeFace() {
        return typeFace;
    }

    public void setTypeFace(int typeFace) {
        this.typeFace = typeFace;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
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

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


}
