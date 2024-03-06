package vn.vimass.service.entity;

import vn.vimass.service.table.object.ObjectLSQRTrangThai;

public class ResponseMessageLSQRTrangThai {

    public int msgCode;
    public String msgContent;
    public ObjectLSQRTrangThai result;

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

    public ObjectLSQRTrangThai getResult() {
        return result;
    }

    public void setResult(ObjectLSQRTrangThai result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseMessageLSQRTrangThai{" +
                "msgCode=" + msgCode +
                ", msgContent='" + msgContent + '\'' +
                ", result=" + result +
                '}';
    }
}
