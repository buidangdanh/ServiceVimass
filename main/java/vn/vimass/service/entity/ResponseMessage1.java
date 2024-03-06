package vn.vimass.service.entity;

import com.google.gson.Gson;

import java.io.Serializable;

public class ResponseMessage1 <T> implements Serializable {

    public int msgCode;
    public int funcId;
    public T result;
    public String msgContent;
    public int totalAll;

    public int getMsgCode() {
        return msgCode;
    }


    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public int getFuncId() {
        return funcId;
    }

    public void setFuncId(int funcId) {
        this.funcId = funcId;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(int totalAll) {
        this.totalAll = totalAll;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
