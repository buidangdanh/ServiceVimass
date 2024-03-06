package vn.vimass.service.table;

import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.ObjectControllerVpass;
import vn.vimass.service.utils.DbUtil;
import vn.vimass.service.utils.VimassData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ControllerVpass {

    public static final String TABLE_NAME = "controllervpass";

    public final static String idDiemRaVao = "idDiemRaVao";
    public final static String typeVpass = "typeVpass";
    public final static String IPLockBarrie = "IPLockBarrie";
    public final static String portip = "portip";
    public final static String typeDeviceInteractive = "typeDeviceInteractive";

    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static void insertDB(ObjectControllerVpass item) {

        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME
                    + " ("
                    + idDiemRaVao + ", "
                    + typeVpass +","
                    + IPLockBarrie +","
                    + portip +","
                    + typeDeviceInteractive
                    + " ) VALUES ("
                    + "N'" + item.idDiemRaVao + "',"
                    + item.typeVpass + ","
                    + "N'" + item.idDiemRaVao + "',"
                    + "N'" + item.portip + "',"
                    + "N'" + item.typeDeviceInteractive + "'"
                    + ");";

//			connect = DriverManager.getConnection(url, user, password);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
       //     Log.logServices("executeUpdate1 ObjectControllerVpass " + pstmt);
            Log.logServices("executeUpdate ObjectControllerVpass : " + strSqlInsert);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("add ObjectControllerVpass successfully");
                VimassData.ContentResult="add ObjectControllerVpass successfully";
            } else {
                Log.logServices("add ObjectControllerVpass fail");
                VimassData.ContentResult="add ObjectControllerVpass fail";
            }

        } catch (Exception e) {
            Log.logServices("InsertData ObjectControllerVpass  Exception: " + e.getMessage());
        }
    }

    private static ObjectControllerVpass getValue(ResultSet resultSet, boolean full) {

        ObjectControllerVpass item = new ObjectControllerVpass();
        try
        {
            item.idDiemRaVao = resultSet.getString(idDiemRaVao);
            item.typeVpass = Integer.parseInt(resultSet.getString(typeVpass));
            item.IPlọckBarrie = String.valueOf(resultSet.getInt(IPLockBarrie));
            item.portip = resultSet.getString(portip);
            item.typeDeviceInteractive = resultSet.getString(typeDeviceInteractive);

        }
        catch(Exception e)
        {
            Log.logServices("ObjectLichSuRaVaoQuetQr getValue Exception: " + e.getMessage());
        }

        return item;
    }



    public static ArrayList<ObjectControllerVpass> getListVpassControllerVpass(){

        ArrayList<ObjectControllerVpass> list = new ArrayList<ObjectControllerVpass>();
        ObjectControllerVpass item = new ObjectControllerVpass();
        try {
            String strQuery = "SELECT *FROM " + TABLE_NAME;

            connect = DbUtil.getConnect();
            Log.logServices("strQuery getListVpassControllerVpass: " + strQuery);

            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            // Log.logServices("strQuery getListLichSuRaVao: " + pstmt);

            while ( resultSet.next()) {

                item = getValue(resultSet,true);
                list.add(item);

            }

        } catch (Exception e) {
            Log.logServices("getListVpassControllerVpass Exception: " + e.getMessage());
        }

        return list;

    }
    public static ObjectControllerVpass getVpassDeviceInfo(String key, int pos) {

        ObjectControllerVpass item = new ObjectControllerVpass();
        try {
            String strQuery = "SELECT *FROM " + TABLE_NAME + " WHERE " +idDiemRaVao+"='"+key+"';";

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
    public static ArrayList<ObjectControllerVpass> getListVpassDeviceInfo(){

        ArrayList<ObjectControllerVpass> list = new ArrayList<ObjectControllerVpass>();
        ObjectControllerVpass item = new ObjectControllerVpass();
        try {
            String strQuery = "SELECT *FROM " + TABLE_NAME;

            connect = DbUtil.getConnect();
            Log.logServices("strQuery load data: " + strQuery);

            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            // Log.logServices("strQuery getListLichSuRaVao: " + pstmt);

            while ( resultSet.next()) {

                item = getValue(resultSet,true);
                list.add(item);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static void update(ObjectControllerVpass item) {
        try {
            String strSqlInsert = "UPDATE " + TABLE_NAME + " SET "
                    + typeVpass + " = "+item.typeVpass+ ", "
                    + IPLockBarrie  + " = N'"+item.IPlọckBarrie+ "', "
                    + portip  + " = N'"+item.portip+ "', "
                    + typeDeviceInteractive  + " = "+item.typeDeviceInteractive+ ", "
                    + " WHERE "
                    + idDiemRaVao+ " = N'" + item.idDiemRaVao + "'";

            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjectVpassDeviceInfo: " + pstmt);


            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("Update ObjectControllerVpass successfully");
                VimassData.ContentResult="Update ObjectControllerVpass successfully";

            } else {
                Log.logServices("Update ObjectControllerVpass fail");
                VimassData.ContentResult="Update ObjectControllerVpass fail";
            }

        } catch (Exception e) {
            Log.logServices("Exception UPDATE ObjectControllerVpass: " + e.getMessage());
        }
    }

    public static void detele(ObjectControllerVpass item) {
        try {
            String strSqlInsert = "DELETE FROM " + TABLE_NAME + " WHERE "
                    + "idDiemRaVao = N'" + item.idDiemRaVao + "'";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjectControllerVpass delete: " + pstmt);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("DELETE ObjectVpassDeviceInfo successfully");
                VimassData.ContentResult="DELETE ObjectVpassDeviceInfo successfully";


            }else{
                Log.logServices("DELETE ObjectControllerVpass fail");
                VimassData.ContentResult="DELETE ObjectControllerVpass fail";

            }

        } catch (Exception e) {
            Log.logServices("Exception detele ObjectControllerVpass: " + e.getMessage());
        }
    }

}
