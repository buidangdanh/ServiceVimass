package vn.vimass.service.table.object;

import java.security.GuardedObject;

import com.google.gson.Gson;

public class ObjectXacThuc {

	public String idQR;// id qrtao
	public String nameQR;
	public String addressQR;
	public int device;//0-android, 1-ios,
	public String phone;// so vi
	public String vID;// so the
	public int personNumber;//1||2||3||4
	public String accName;// ten chu the
	public long thoiGianGhiNhan; // thoi gian ghi nhan hien tai
	public String cks; // device + idQR + phone + thoiGianGhiNhan
	public int typeXacThuc;// loai xac thuc 0-quet, 1-cham the, 3- dung khuon mat
	public String vpassID;// thiet bi dinh danh
	public int typeDataAuThen; // 100: ko xac dinh, 0: khuon mat, 1 van tay
	public long timeAuthen;


	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
