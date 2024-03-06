package vn.vimass.service.entity;

import vn.vimass.service.table.object.ObjectIpAddress;

import java.util.ArrayList;

public class ResponseListDsDiaDiem {
    public int msgCode;
    public String msgContent;
    public ArrayList<ObjectIpAddress> result;

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

    public ArrayList<ObjectIpAddress> getResult() {
        return result;
    }

    public void setResult(ArrayList<ObjectIpAddress> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseListDsDiaDiem{" +
                "msgCode=" + msgCode +
                ", msgContent='" + msgContent + '\'' +
                ", result=" + result +
                '}';
    }
}
