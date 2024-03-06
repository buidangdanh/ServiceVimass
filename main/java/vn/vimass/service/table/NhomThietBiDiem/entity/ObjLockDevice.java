package vn.vimass.service.table.NhomThietBiDiem.entity;

import com.google.gson.Gson;

public class ObjLockDevice {
        public String idLookDevice;
        public String mcID;
        public String nameDevice;
        public String ip;
        public int portD;
        public String mess;
        public int typeD;

        //public String interacts;
        public int type;

        public String getIdLookDevice() {
            return idLookDevice;
        }
        public void setIdLookDevice(String idLookDevice) {
            this.idLookDevice = idLookDevice;
        }
        public String getMcID() {
            return mcID;
        }
        public void setMcID(String mcID) {
            this.mcID = mcID;
        }
        public String getNameDevice() {
            return nameDevice;
        }
        public void setNameDevice(String nameDevice) {
            this.nameDevice = nameDevice;
        }
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        public int getPortD() {
            return portD;
        }
        public void setPortD(int portD) {
            this.portD = portD;
        }
        public String getMess() {
            return mess;
        }
        public void setMess(String mess) {
            this.mess = mess;
        }
        public int getTypeD() {
            return typeD;
        }
        public void setTypeD(int typeD) {
            this.typeD = typeD;
        }

        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
}
