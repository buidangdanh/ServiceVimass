package vn.vimass.service.CallService;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class VMString extends StringEntity{


	public VMString(String data) throws UnsupportedEncodingException
	{
		super(data, "utf-8");
		this.setContentType("application/json");
	}
}

