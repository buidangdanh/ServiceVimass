package vn.vimass.service.table;

import com.google.gson.Gson;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.ObjectControllerVpass;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.table.object.ObjectIpAddress;
import vn.vimass.service.table.object.ObjectVpassDeviceInfo;
import vn.vimass.service.utils.DbUtil;
import vn.vimass.service.utils.VimassData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class VPassDeviceInfo {
    public static final String TABLE_NAME = "vpassdeviceinfo";
    public final static String id = "id";
    public final static String IP = "IP";
    public final static String serverName = "serverName";
    public final static String deviceType = "deviceType";
    public final static String deviceCapacity = "deviceCapacity";
    public final static String listController = "listController";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;
    public static boolean full = true;

    public static void insertDB(ObjectVpassDeviceInfo item) {

        try {

            String strSqlInsert = "INSERT INTO " + TABLE_NAME
                    + " ("
                    + id + ", "
                    + IP + ", "
                    + serverName +","
                    + deviceType +","
                    + deviceCapacity
                    + " ) VALUES ("
                    + item.id + ","
                    + "N'" + item.IP + "',"
                    + item.serverName + ","
                    + "N'" + item.deviceType + "',"
                    + "N'" + item.deviceCapacity + "',"
                    + "N'" + item.listController + "'"
                    + ");";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);

            Log.logServices("executeUpdate1 ObjectControllerVpass  Exception: " + strSqlInsert);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {

                Log.logServices("ROW INSERTED ObjectVpassDeviceInfo");
                VimassData.ContentResult="add ObjectVpassDeviceInfo successfully";

            } else {
                Log.logServices("ROW INSERTED ObjectVpassDeviceInfo fail");
                VimassData.ContentResult="add ObjectVpassDeviceInfo  faile";
            }
        } catch (Exception e) {
            Log.logServices("InsertData ObjectVpassDeviceInfo  Exception: " + e.getMessage());
        }
    }

    private static ObjectVpassDeviceInfo getValue(ResultSet resultSet, boolean full) {

        ObjectVpassDeviceInfo item = new ObjectVpassDeviceInfo();
        try
        {
            item.id = resultSet.getString(id);
            item.IP = resultSet.getString(listController);
            item.serverName = resultSet.getString(listController);
            item.deviceType =  resultSet.getInt(deviceType);
            item.deviceCapacity = resultSet.getInt(deviceCapacity);
            item.listController = resultSet.getString(listController);
        }
        catch(Exception e)
        {
            Log.logServices("ObjectVpassDeviceInfo getValue Exception: " + e.getMessage());
        }
        return item;
    }

    public static ObjectVpassDeviceInfo getVpassDeviceInfo(String key, int pos) {


        ObjectVpassDeviceInfo item = new ObjectVpassDeviceInfo();
        try {
            String strQuery = "SELECT *FROM " + TABLE_NAME + " WHERE " +id+"='"+key+"';";

            connect = DbUtil.getConnect();

            Log.logServices("query ObjectVpassDeviceInfo: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                item = getValue(resultSet,true);
            }

            Log.logServices("ObjectVpassDeviceInfo item: " + item);
        } catch (Exception e) {
            Log.logServices("ObjectVpassDeviceInfo Exception: " + e.getMessage());
        }
        return item;
    }
    public static ArrayList<ObjectVpassDeviceInfo> getListVpassDeviceInfo(){

        ArrayList<ObjectVpassDeviceInfo> list = new ArrayList<ObjectVpassDeviceInfo>();
        ObjectVpassDeviceInfo item = new ObjectVpassDeviceInfo();
        try {
            String strQuery = "SELECT *FROM " + TABLE_NAME;

            connect = DbUtil.getConnect();
            Log.logServices("strQuery getListVpassDeviceInfo: " + strQuery);

            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            // Log.logServices("strQuery getListLichSuRaVao: " + pstmt);

            while ( resultSet.next()) {

                item = getValue(resultSet,true);
                list.add(item);
            }
        } catch (Exception e) {

            Log.logServices("Exception getListVpassDeviceInfo: " + e.getMessage());
        }
        return list;
    }

    public static void update(ObjectVpassDeviceInfo item) {
        try {
            String strSqlInsert = "UPDATE " + TABLE_NAME + " SET "
                    + IP + " = N'"+item.IP+ "', "
                    + serverName  + " = N'"+item.serverName+ "', "
                    + deviceType  + " = N'"+item.deviceType+ "', "
                    + deviceCapacity  + " = "+item.deviceCapacity+ ", "
                    + listController  + " = N'"+item.listController+ "' "
                    + " WHERE "
                    +id + " = N'" + item.id + "'";

            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjectVpassDeviceInfo: " + pstmt);


            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("Update ObjectVpassDeviceInfo successfully");
                VimassData.ContentResult="Update ObjectVpassDeviceInfo successfully";

            } else {
                Log.logServices("Update ObjectVpassDeviceInfo fail");
                VimassData.ContentResult="Update ObjectVpassDeviceInfo fail";
            }

        } catch (Exception e) {
            Log.logServices("Exception UPDATE ObjectVpassDeviceInfo: " + e.getMessage());
        }
    }

    public static void detele(ObjectVpassDeviceInfo item) {
        try {
            String strSqlInsert = "DELETE FROM " + TABLE_NAME + " WHERE "
                    + "id = N'" + item.id + "'";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate1 delete: " + pstmt);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("DELETE ObjectVpassDeviceInfo successfully");
                VimassData.ContentResult="DELETE ObjectVpassDeviceInfo successfully";


            }else{
                Log.logServices("DELETE ObjectVpassDeviceInfo fail");
                VimassData.ContentResult="DELETE ObjectVpassDeviceInfo fail";

            }

        } catch (Exception e) {
            Log.logServices("Exception detele ObjectVpassDeviceInfo : " + e.getMessage());
        }
    }
}
