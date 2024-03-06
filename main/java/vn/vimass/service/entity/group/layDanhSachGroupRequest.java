package vn.vimass.service.entity.group;

import com.google.gson.Gson;

public class layDanhSachGroupRequest {
    public String mcID;
    public int offset;//
    public int limit;

    public String textSearch;

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public String getMcID() {
        return mcID;
    }

    public void setMcID(String mcID) {
        this.mcID = mcID;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
