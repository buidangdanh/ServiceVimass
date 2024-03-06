package vn.vimass.service.entity;

import com.google.gson.Gson;
import vn.vimass.service.table.object.ObjectInfoVid;

import java.util.ArrayList;

public class ObjectRecordsAndTotalObjectInfoVid {
    public ArrayList<ObjectInfoVid> records;
    public int total;

    public ArrayList<ObjectInfoVid> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<ObjectInfoVid> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ObjectRecordsAndTotalObjectInfoVid(ArrayList<ObjectInfoVid> records, int total) {
        this.records = records;
        this.total = total;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


}
