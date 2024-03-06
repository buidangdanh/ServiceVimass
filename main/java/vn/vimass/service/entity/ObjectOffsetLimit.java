package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectOffsetLimit {

    public String idQRgreat;
    public int offset;
    public int limit;
    public String textSearch;

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public String getIdQRgreat() {
        return idQRgreat;
    }

    public void setIdQRgreat(String idQRgreat) {
        this.idQRgreat = idQRgreat;
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

    public String toString() {

        return new Gson().toJson(this);
    }
}
