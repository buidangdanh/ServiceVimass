package vn.vimass.service.table.object;

import java.util.ArrayList;

public class ObjectHienThiMayMot {
    public ArrayList<ObjectHienThiXacThucThanhCong> value = new ArrayList<>();
    public long timeCapNhat;

    @Override
    public String toString() {
        return "ObjectHienThiMayMot{" +
                "value=" + value +
                ", timeCapNhat=" + timeCapNhat +
                '}';
    }

    public ArrayList<ObjectHienThiXacThucThanhCong> getValue() {
        return value;
    }

    public void setValue(ArrayList<ObjectHienThiXacThucThanhCong> value) {
        this.value = value;
    }

    public long getTimeCapNhat() {
        return timeCapNhat;
    }

    public void setTimeCapNhat(long timeCapNhat) {
        this.timeCapNhat = timeCapNhat;
    }
}
