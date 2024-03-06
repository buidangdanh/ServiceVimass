package vn.vimass.service.entity;

import java.util.ArrayList;

public class RecordsAndTotal {
    public ArrayList<ObjectLichSuRaVaoQuetQr> records;
    public int total;

    public ArrayList<ObjectLichSuRaVaoQuetQr> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<ObjectLichSuRaVaoQuetQr> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public RecordsAndTotal(ArrayList<ObjectLichSuRaVaoQuetQr> records, int total) {
        this.records = records;
        this.total = total;
    }

    @Override
    public String toString() {
        return "RecordsAndTotal{" +
                "records=" + records +
                ", total=" + total +
                '}';
    }
}
