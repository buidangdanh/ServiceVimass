package vn.vimass.service.entity;

import java.util.ArrayList;

public class ResponseMessageDuLieuMoiNhatQR {
    public int msgCode;
    public String msgContent;

    @Override
    public String toString() {
        return "ResponseMessageDuLieuMoiNhatQR{" +
                "msgCode=" + msgCode +
                ", msgContent='" + msgContent + '\'' +
                ", result=" + result +
                '}';
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

    public ArrayList<ObjectLichSuRaVaoQuetQr> getResult() {
        return result;
    }

    public void setResult(ArrayList<ObjectLichSuRaVaoQuetQr> result) {
        this.result = result;
    }

    public ArrayList<ObjectLichSuRaVaoQuetQr> result;




}
