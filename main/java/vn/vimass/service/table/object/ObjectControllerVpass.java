package vn.vimass.service.table.object;

import com.google.gson.Gson;

public class ObjectControllerVpass {

    public String idDiemRaVao; // diêm ra vao(idDiemRaVao)
    public int typeVpass; // loại thiết điều khiển mở của
    public String IPlọckBarrie; // ip cửa/ barrie
    public String portip; // port mo cua
    public String typeDeviceInteractive; // mảng thiết bị xác thực []

    public String getIdDiemRaVao() {
        return idDiemRaVao;
    }

    public void setIdDiemRaVao(String idDiemRaVao) {
        this.idDiemRaVao = idDiemRaVao;
    }

    public int getTypeVpas() {
        return typeVpass;
    }

    public void setTypeVpas(int typeVpas) {
        this.typeVpass = typeVpas;
    }

    public String getIPlọckBarrie() {
        return IPlọckBarrie;
    }

    public void setIPlọckBarrie(String IPlọckBarrie) {
        this.IPlọckBarrie = IPlọckBarrie;
    }

    public String getPortip() {
        return portip;
    }

    public void setPortip(String portip) {
        this.portip = portip;
    }

    public String getTypeDeviceInteractive() {
        return typeDeviceInteractive;
    }

    public void setTypeDeviceInteractive(String typeDeviceInteractive) {
        this.typeDeviceInteractive = typeDeviceInteractive;
    }

    public String toString(){
        return new Gson().toJson(this);
    }
}
