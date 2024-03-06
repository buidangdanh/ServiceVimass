package vn.vimass.service.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.ObjectIpAddress;
import vn.vimass.service.utils.DbUtil;
import vn.vimass.service.utils.VimassData;

public class IPaddress {

	public static final String TABLE_NAME = "dbipaddress";
	public static final String TABLE_NAME_2 = "dblock";
	public static final String TABLE_NAME_1 = "dblock";
	public final static String idLoiRaVao = "idLoiRaVao";
	public final static String tk = "tk";
	public final static String loiRaVao = "loiRaVao";

	public final static String address = "address";

	public final static String ip = "ip";
	public final static String portIp = "portIp";
	public final static String loaiCua = "loaiCua";
	public final static String name = "name";
	public final static String chieuRaVaoBarrie = "chieuRaVaoBarrie";

	public final static String id = "id";
	public final static String idVpass = "idVpass";
	public final static String textQRBack = "textQRBack";
	public final static String textQRRaVao = "textQRRaVao";
	public final static String lat = "lat";
	public final static String lng = "lng";

	public static PreparedStatement pstmt = null;
	public static Connection connect = null;
	public static ResultSet resultSet = null;

	public static boolean full = true;

	public static ArrayList<ObjectIpAddress> getListIpAddress(){


		ArrayList<ObjectIpAddress> list = new ArrayList<ObjectIpAddress>();
		ObjectIpAddress item = new ObjectIpAddress();
		try {

			String strQuery =" SELECT tb1.*, tb2.ip, tb2.portIp, tb2.loaiCua, tb2.name, tb2.typePhi , tb2.chieuRaVaoBarrie FROM "+TABLE_NAME+" as tb1"
					+" INNER JOIN "+TABLE_NAME_2+" as tb2"
					+" ON tb1.id = tb2.id;";

			connect = DbUtil.getConnect();
			Log.logServices("getListIpAddress strQuery: " + strQuery);

			pstmt = connect.prepareStatement(strQuery);
			resultSet = pstmt.executeQuery();


			Log.logServices("strQuery getListIpAddress: " + strQuery);
			while(resultSet.next()) {

				item = getValue(resultSet,true, true);
				list.add(item);
			}
//			 if(!resultSet.next()) {
			strQuery =" SELECT tb1.*FROM "+TABLE_NAME+" as tb1";
			connect = DbUtil.getConnect();
			pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
			resultSet = pstmt.executeQuery();
			while(resultSet.next()) {

				item = getValue(resultSet,true, false);

				if(item.id.isEmpty() ) {
					list.add(item);
				}


			}
//			 }

		} catch (Exception e) {


			Log.logServices("Exception getListIpAddress: " + e.getMessage());
		}

		return list;


	}

	public static String getTenRaVao(String idQR,String key) {

		String result ="";
		try {
			String strQuery = "SELECT "+key+" FROM " + TABLE_NAME + " WHERE idLoiRaVao = '" +idQR+"' ORDER BY "+key ;

			connect = DbUtil.getConnect();
			Log.logServices("strQuery load data: " + strQuery);

			pstmt = connect.prepareStatement(strQuery);
			resultSet = pstmt.executeQuery();

			// Log.logServices("strQuery getListLichSuRaVao: " + pstmt);

			while ( resultSet.next()) {

				result = resultSet.getString(key);

			}

		} catch (Exception e) {

		}

		return result;

	}

	public static ObjectIpAddress getThongTinDiaDiem(String key) {

		String result ="";
		ObjectIpAddress item = new ObjectIpAddress();
		try {
			String strQuery = "SELECT *FROM " + TABLE_NAME + " WHERE idLoiRaVao = '" +key+"' OR tk = '" +key+"'";

			connect = DbUtil.getConnect();

			Log.logServices("getThongTinDiaDiem: " + strQuery);
			pstmt = connect.prepareStatement(strQuery);
			resultSet = pstmt.executeQuery();
			while ( resultSet.next()) {

				item = getValue(resultSet,true, true);
				Log.logServices("getThongTinDiaDiem item: " + item);
			}

		} catch (Exception e) {
			Log.logServices("getThongTinDiaDiem Exception: " + e.getMessage());
		}

		return item;

	}


	private static ObjectIpAddress getValue(ResultSet resultSet, boolean full, boolean chek) {


		ObjectIpAddress item = new ObjectIpAddress();
		try
		{
			if(chek) {
				item.idLoiRaVao = resultSet.getString(idLoiRaVao);
				item.textQRBack = resultSet.getString(textQRBack);
				item.tk = resultSet.getString(tk);
				item.loiRaVao = resultSet.getString(loiRaVao);
				item.address = resultSet.getString(address);
				item.id = resultSet.getString(id);
				item.idVpass= resultSet.getString(idVpass);
				item.ip = resultSet.getString("ip");
				item.portIp = resultSet.getString("portIp");
				item.loaiCua= resultSet.getInt("loaiCua");
				item.name = resultSet.getString("name");
				item.typePhi = resultSet.getInt("typePhi");
				item.chieuRaVaoBarrie = resultSet.getInt("chieuRaVaoBarrie");
			}else {
				item.idLoiRaVao = resultSet.getString(idLoiRaVao);
				item.textQRBack = resultSet.getString(textQRBack);
				item.tk = resultSet.getString(tk);
				item.loiRaVao = resultSet.getString(loiRaVao);
				item.address = resultSet.getString(address);
				item.id = resultSet.getString(id);
				item.idVpass= resultSet.getString(idVpass);
			}

		}
		catch(Exception e)
		{
			Log.logServices("Exception e: " + e.getMessage());
		}

		return item;
	}

	public static ArrayList<ObjectIpAddress> getListIpAddressNew() {

		ArrayList<ObjectIpAddress> list = new ArrayList<ObjectIpAddress>();
		ObjectIpAddress item = new ObjectIpAddress();
		try {
			String strQuery = "SELECT *FROM " + TABLE_NAME;

			connect = DbUtil.getConnect();
			Log.logServices("strQuery load data: " + strQuery);

			pstmt = connect.prepareStatement(strQuery);
			resultSet = pstmt.executeQuery();

			// Log.logServices("strQuery getListLichSuRaVao: " + pstmt);

			while ( resultSet.next()) {

				item.idLoiRaVao = resultSet.getString("idLoiRaVao");
				item.loiRaVao = resultSet.getString("loiRaVao");
				item.address = resultSet.getString("address");
				item.ip = resultSet.getString("ip");
				item.portIp = String.valueOf(resultSet.getInt("portIp"));
//				item.cheDo = resultSet.getInt("cheDo");
				item.loaiCua = resultSet.getInt("loaiCua");
				item.chieuRaVaoBarrie = resultSet.getInt("chieuRaVaoBarrie");
				item.id = resultSet.getString("id");
				item.idVpass = resultSet.getString("idVpass");
				list.add(item);
			}

		} catch (Exception e) {
			Log.logServices("getListIpAddressNew!" + e.getMessage());
		}
		return list;
	}
	public static ObjectIpAddress getItemIpAddress(String key){

		ObjectIpAddress item = new ObjectIpAddress();
		try {
			String strQuery ="SELECT tb1.*, tb2.ip, tb2.portIp, tb2.loaiCua, tb2.name, tb2.typePhi , tb2.chieuRaVaoBarrie " +
					"FROM "+TABLE_NAME+" as tb1  LEFT JOIN "+TABLE_NAME_1+" as tb2 ON tb1.id = tb2.id WHERE "+idLoiRaVao+"='"+key+"';";
			Connection connect = DbUtil.getConnect();
			PreparedStatement pstmt = connect.prepareStatement(strQuery);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {

				item = getValue(resultSet, true, true);

			}
			Log.logServices("getItemIpAddress!" + pstmt);
			VimassData.ContentResult = "successfully";

		} catch (Exception e) {
			Log.logServices("getItemIpAddress!" + e.getMessage());
			VimassData.ContentResult = "fail";
		}
		return item;

	}
//	public static ArrayList<ObjectIpAddress> getListIpAddressCache(){
//
//		ArrayList<ObjectIpAddress> listCache = new ArrayList<>();
//
//		Log.logServices("listIpAddress input: " +VimassData.listIpAddress);
//
//		if(VimassData.listIpAddress == null || VimassData.listIpAddress.isEmpty()) {
//			listCache = getListIpAddress();
//			Log.logServices("listIpAddress null: " +listCache);
//
//		}else {
//			Log.logServices("listIpAddress not null: " +VimassData.listIpAddress);
//			return VimassData.listIpAddress ;
//
//		}
//
//		return listCache;
//
//	}

}
