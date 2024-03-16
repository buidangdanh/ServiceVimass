package vn.vimass.service.table;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ListDiem;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DeviceVPass {

    public static final String TABLE_NAME="thietbivpass";
    public static final String id = "id";
    public static final String mcID = "mcID";
    public static final String desDevice  = "desDevice";
    public static final String storage  = "storage";
    public static final String typeDevice = "typeDevice";
    public static final String portD  = "portD";
    public static final String function  = "function";
    public static final String ip  = "ip";
    public static final String listDiem  = "listDiem";
    public static final String deviceID  = "deviceID";
    public static final String stt  = "stt";
    public static final String timeTao  = "timeTao";
    public static final String timeSua  = "timeSua";
    public static final String timeSyncDataFace  = "timeSyncDataFace";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static ArrayList<ObjVpass> getListVpass(){
        ArrayList<ObjVpass> arr = new ArrayList<>();
        try {
            String strQuery = "SELECT * FROM thietbivpass";
            Log.logServices("strQuery getThietBiVPass ===> " + strQuery);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjVpass item = getValue(resultSet);
                arr.add(item);
            }

        } catch (Exception Ex) {
            Log.logServices("getThietBiVPass Exception!" + Ex.getMessage());
        }
        return arr;
    }


    public static ObjVpass getVpassInfo(String key){
        ObjVpass obj = null;
        try {
            String strQuery = "SELECT * FROM thietbivpass WHERE " +deviceID+ " ='"+key+"';" ;
            Log.logServices("strQuery getObjVpass ===> " + strQuery);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                 obj = getValue(resultSet);
            }

        } catch (Exception Ex) {
            Log.logServices("getObjVpass Exception!" + Ex.getMessage());
        }
        return obj;
    }

    public static void update(String ip, String port, String deviceId){

        try {
            String strQuery = "UPDATE " + TABLE_NAME + ""
                    + " SET "
                    + ip  + " = N'"+ip+ "', "
                    + portD  + " = N'"+port+ "' "
                    + " WHERE "
                    +deviceID + " = N'" + deviceId + "'";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            Log.logServices("executeUpdate ObjQR: " + pstmt);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("ROW update deviceVpass");
            } else {
                Log.logServices("ROW NOT update deviceVpass");
            }

        } catch (Exception e) {
            Log.logServices("Exception update deviceVpass: " + e.getMessage());
        }
    }

    private static ObjVpass getValue(ResultSet resultSet){

        ObjVpass obj = new ObjVpass();
        try{
            obj.id = resultSet.getString(id);
            obj.mcID = resultSet.getString(mcID);
            obj.desDevice = resultSet.getString(desDevice);
            obj.storage = resultSet.getDouble(storage);
            obj.typeDevice = resultSet.getInt(typeDevice);
            obj.portD = resultSet.getString(portD);
            obj.function = resultSet.getInt(function);
            obj.ip = resultSet.getString(ip);
            obj.listDiem = new Gson().fromJson(resultSet.getString(listDiem), new TypeToken<ArrayList<ListDiem>>() {
            }.getType());
            obj.deviceID = resultSet.getString(deviceID);
            obj.stt = resultSet.getInt(stt);
        }catch (Exception e){
            Log.logServices("getValue ObjVpass Exception!" + e.getMessage());
        }
        return obj;
    }
}
