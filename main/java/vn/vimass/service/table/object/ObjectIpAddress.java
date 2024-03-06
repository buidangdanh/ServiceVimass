package vn.vimass.service.table.object;

import com.google.gson.Gson;

public class ObjectIpAddress {
	public String idLoiRaVao;
	public String textQRBack;
	public String textQRRaVao;
	public String tk;
	public String loiRaVao;
	public String address;
	public double lat;
	public double lng;
	public String id;
	public String idVpass;

	public String ip;
	public String portIp;
	public String name;
	public int loaiCua;
	public int typePhi;

	public int chieuRaVaoBarrie;

	public int getLoaiCua() {
		return loaiCua;
	}

	public void setLoaiCua(int loaiCua) {
		this.loaiCua = loaiCua;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPortIp() {
		return portIp;
	}

	public void setPortIp(String portIp) {
		this.portIp = portIp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypePhi() {
		return typePhi;
	}

	public void setTypePhi(int typePhi) {
		this.typePhi = typePhi;
	}

	public int getChieuRaVaoBarrie() {
		return chieuRaVaoBarrie;
	}

	public void setChieuRaVaoBarrie(int chieuRaVaoBarrie) {
		this.chieuRaVaoBarrie = chieuRaVaoBarrie;
	}

	public String getIdVpass() {
		return idVpass;
	}

	public void setIdVpass(String idVpass) {
		this.idVpass = idVpass;
	}

	public String getIdLoiRaVao() {
		return idLoiRaVao;
	}

	public void setIdLoiRaVao(String idLoiRaVao) {
		this.idLoiRaVao = idLoiRaVao;
	}

	public String getTextQRBack() {
		return textQRBack;
	}


	public void setTextQRBack(String textQRBack) {
		this.textQRBack = textQRBack;
	}


	public String getTextQRRaVao() {
		return textQRRaVao;
	}

	public void setTextQRRaVao(String textQRRaVao) {
		this.textQRRaVao = textQRRaVao;
	}

	public String getTk() {
		return tk;
	}

	public void setTk(String tk) {
		this.tk = tk;
	}

	public String getLoiRaVao() {
		return loiRaVao;
	}

	public void setLoiRaVao(String loiRaVao) {
		this.loiRaVao = loiRaVao;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
