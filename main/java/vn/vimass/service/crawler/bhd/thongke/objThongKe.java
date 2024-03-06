package vn.vimass.service.crawler.bhd.thongke;

import com.google.gson.Gson;

public class objThongKe {
    public long timeTo;// tu ngay
    public long timeFrom;// den ngay
    public int offset;//
    public int limit;
    public String key;// key = so the, ten
    public String cks;// key = bam(funcId+ currentime + device);
    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(long timeTo) {
        this.timeTo = timeTo;
    }

    public long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(long timeFrom) {
        this.timeFrom = timeFrom;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
