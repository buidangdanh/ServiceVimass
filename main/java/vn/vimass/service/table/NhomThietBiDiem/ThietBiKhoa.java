package vn.vimass.service.table.NhomThietBiDiem;

import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjLockDevice;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ThietBiKhoa {
    public static final String TABLE_NAME = "dblockdevice";
    public final static String idLookDevice = "idLookDevice";
    public final static String mcID = "mcID";
    public final static String nameDevice = "nameDevice";
    public final static String ip = "ip";
    public final static String portD = "portD";
    public final static String mess = "mess";
    public final static String typeD = "typeD";
    public final static String type = "type";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;
    public static boolean full = true;

    public static void insertDB(ObjLockDevice item) {
        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + ""
                    + " ("
                    + idLookDevice + ", "
                    + mcID + ", "
                    + nameDevice + ", "
                    + ip + ", "
                    + portD + ", "
                    + mess + ", "
                    + typeD + " "

                    + " ) VALUES ("
                    + "N'" + item.idLookDevice + "',"
                    + "N'" + item.mcID + "',"
                    + "N'" + item.nameDevice + "',"
                    + "N'" + item.ip + "',"
                    + "N'" + item.portD + "',"
                    + "N'" + item.mess + "',"
                    + item.typeD + ""

                    + ");";

            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate dblockdevice: " + pstmt);

            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                Log.logServices(" inserteddblockdevice successfully");
            } else {
                Log.logServices(" inserted dblockdevice fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception inserted dblockdevice: " + e.getMessage());
        }
    }

    public static ObjLockDevice getItem(String key) {

        ObjLockDevice obj = new ObjLockDevice();
        try {
            String strQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + idLookDevice + " = '" + key +  "';";
            connect =  DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                obj = getValue(resultSet);
            }

        } catch (Exception e) {
            //System.out.println("getObjGroup Exception: " + e.getMessage());
        }

        return obj;

    }
    private static ObjLockDevice getValue(ResultSet resultSet) {

        ObjLockDevice obj = new ObjLockDevice();
        try {

            obj.idLookDevice = resultSet.getString(idLookDevice);
            obj.mcID = resultSet.getString(mcID);
            obj.nameDevice = resultSet.getString(nameDevice);
            obj.ip = resultSet.getString(ip);
            obj.portD = Integer.valueOf(resultSet.getString(portD));
            obj.mess = resultSet.getString(mess);
            obj.typeD = Integer.valueOf(resultSet.getString(typeD));


        } catch (Exception e) {

            Log.logServices("Exception ObjLockDevice getValue: " + e.getMessage());
        }
        return obj;
    }

    public static void update(ObjLockDevice item) {
        try {
            String strSqlInsert = "UPDATE " + TABLE_NAME + ""
                    + " SET "
                    + mess  + " = N'"+item.mess+ "', "
                    + nameDevice  + " = N'"+item.nameDevice+ "', "
                    + ip  + " = N'"+item.ip+ "', "
                    + typeD  + " = "+item.typeD+ ", "
                    + portD  + " = "+item.portD+ " "
                    + " WHERE "
                    +idLookDevice + " = N'" + item.idLookDevice + "'";

            connect =  DbUtil.getConnect();

            pstmt = (PreparedStatement) connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjLockDevice: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("Update ObjLockDevice successfully");
            } else {
                Log.logServices("Update ObjLockDevice fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception update ObjLockDevice: " + e.getMessage());
        }
    }

    public static void delete(String key) {
        try {

            String strSql = "DELETE FROM " + TABLE_NAME + " WHERE "+idLookDevice + " = N'" + key + "';";
            connect =  DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSql);
            Log.logServices("executeUpdate delete ObjLockDevice: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("delete ObjLockDevice successful");
            } else {
                Log.logServices("delete ObjLockDevice fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception delete ObjLockDevice: " + e.getMessage());
        }
    }
}
