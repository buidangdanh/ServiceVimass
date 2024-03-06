package vn.vimass.service.entity;

import java.io.Serializable;

import com.google.gson.Gson;

public class ResponseMessage {
	
	public int msgCode;
	public String msgContent;
	public String result;


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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
