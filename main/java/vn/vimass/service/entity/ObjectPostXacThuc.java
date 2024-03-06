package vn.vimass.service.entity;

import com.google.gson.Gson;

public class ObjectPostXacThuc {
	public String idQRgreat;
	public long currentTime;
	public String cks; // cks ="0981455707" + idQRgreat + currentTime
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
