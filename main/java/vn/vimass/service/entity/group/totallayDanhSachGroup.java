package vn.vimass.service.entity.group;

import com.google.gson.Gson;
import vn.vimass.service.entity.recivephone.GroupObj;
import vn.vimass.service.table.object.ObjectInfoVid;

import java.util.ArrayList;

public class totallayDanhSachGroup {
    public ArrayList<GroupObj> records;
    public int total;

    public ArrayList<GroupObj> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<GroupObj> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public totallayDanhSachGroup(ArrayList<GroupObj> records, int total) {
        this.records = records;
        this.total = total;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
