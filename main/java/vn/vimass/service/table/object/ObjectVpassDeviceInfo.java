package vn.vimass.service.table.object;

import com.google.gson.Gson;

public class ObjectVpassDeviceInfo {
    public String id;
    public String IP;////113.190.248.142
    public String serverName;//dpTuVer2
    public int deviceType;// loai thiet bi ( mini-0, desktop-1, iphone-2, aPhone-3)
    public int deviceCapacity;//đơn vị G - gigabit
    public String listController;//[ControllerVpass] = []

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceCapacity() {
        return deviceCapacity;
    }

    public void setDeviceCapacity(int deviceCapacity) {
        this.deviceCapacity = deviceCapacity;
    }

    public String getListController() {
        return listController;
    }

    public void setListController(String listController) {
        this.listController = listController;
    }

    public String toString(){
        return new Gson().toJson(this);
    }
}
