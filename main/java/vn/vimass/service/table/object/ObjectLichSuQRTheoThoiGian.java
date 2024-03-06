package vn.vimass.service.table.object;

public class ObjectLichSuQRTheoThoiGian {
    public long from;
    public long to;
    public int limit;
    public int offset;

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

    @Override
    public String toString() {
        return "ObjectLichSuQRTheoThoiGian{" +
                "from=" + from +
                ", to=" + to +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}
