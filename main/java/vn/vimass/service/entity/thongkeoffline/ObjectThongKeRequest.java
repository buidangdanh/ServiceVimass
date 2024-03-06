package vn.vimass.service.entity.thongkeoffline;

import com.google.gson.Gson;

public class ObjectThongKeRequest {
    public long from;
    public long to;
    public int limit;
    public int offset;
    public int typeXacThuc;   // 0 là QR(Q), 1 là chạm thẻ(C), 2 là tất cả
    public String textSearch;

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
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

    public int getTypeXacThuc() {
        return typeXacThuc;
    }

    public void setTypeXacThuc(int typeXacThuc) {
        this.typeXacThuc = typeXacThuc;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
