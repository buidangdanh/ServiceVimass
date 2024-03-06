package vn.vimass.service.table.object;

import com.google.gson.Gson;

public class ObjecDiaDiemXacThucThanhCong {
	
	public String id; // id = thoiGianNghiNhan+randomKyTu(3) 
	public int device;//0-android, 1-ios,
	public String idQR;// id qrtao
	public String nameQR;//ten qr tao
	public String addressQR;// dia diem qr tao
	public int typeDataAuThen; // 0: khuon mat, 1: van tay
	public String phone;// so vi
	public String vID;// so the
	public int personNumber;
	public String accName;// ten chu the
	public long thoiGianGhiNhan; // thoi gian ghi nhan hien tai
	public int typeXacThuc;// loai xac thuc 0-quet, 1-chạm
	public int trangThai; // status = 1
	public String vpassID; // Thiet bi dinh danh
	public long timeAuthen;

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
