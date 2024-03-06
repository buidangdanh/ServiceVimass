package vn.vimass.service.crawler.bhd.SendData.entity;
import com.google.gson.Gson;

public class ObjectSendData {
    public int funcID; // 125  dich vu gui du lieu di
    public long currentTime; // thoi gian gui
    public String ipPC; // ip may chu
    public int type; // loai data
    public String data; // du lieu gui di
    public int msgCode;
    public String msgContent;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFuncID() {
        return funcID;
    }

    public void setFuncID(int funcID) {
        this.funcID = funcID;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getIpPC() {
        return ipPC;
    }

    public void setIpPC(String ipPC) {
        this.ipPC = ipPC;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
