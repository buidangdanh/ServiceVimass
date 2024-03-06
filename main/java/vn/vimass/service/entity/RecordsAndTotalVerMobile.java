package vn.vimass.service.entity;

import java.util.ArrayList;

public class RecordsAndTotalVerMobile {
    public ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records;
    public int total;

    public ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "RecordsAndTotalVerMobile{" +
                "records=" + records +
                ", total=" + total +
                '}';
    }

    public RecordsAndTotalVerMobile(ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records, int total) {
        this.records = records;
        this.total = total;
    }
}
