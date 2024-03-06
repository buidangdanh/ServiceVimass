package vn.vimass.service.entity;

import vn.vimass.service.table.object.ObjectIpAddress;

//Danh Dịch vụ trả về địa điểm theo chế độ
public class ResponseThongTinDiaDiem {
    public int msgCode;
    public String msgContent;

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
    public String toString() {
        return "ResponseThongTinDiaDiem{" +
                "msgCode=" + msgCode +
                ", msgContent='" + msgContent + '\'' +
                ", result=" + result +
                '}';
    }
    public ObjectIpAddress result;

    public ObjectIpAddress getResult() {
        return result;
    }

    public void setResult(ObjectIpAddress result) {
        this.result = result;
    }
}
