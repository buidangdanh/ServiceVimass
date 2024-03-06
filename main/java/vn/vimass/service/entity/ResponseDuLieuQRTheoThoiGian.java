package vn.vimass.service.entity;

import vn.vimass.service.table.object.ObjectIpAddress;
import vn.vimass.service.table.object.ObjectLichSuQRTheoThoiGian;

import java.util.ArrayList;

public class ResponseDuLieuQRTheoThoiGian {
    public int msgCode;
    public String msgContent;
    public ArrayList<ObjectLichSuRaVaoQuetQr> result;
    public int totalAll;

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

    public int getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(int totalAll) {
        this.totalAll = totalAll;
    }

    @Override
    public String toString() {
        return "ResponseDuLieuQRTheoThoiGian{" +
                "msgCode=" + msgCode +
                ", msgContent='" + msgContent + '\'' +
                ", result=" + result +
                ", totalAll=" + totalAll +
                '}';
    }
}
