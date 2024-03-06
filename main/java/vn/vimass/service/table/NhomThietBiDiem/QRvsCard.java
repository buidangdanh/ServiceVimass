package vn.vimass.service.table.NhomThietBiDiem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjQR;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjInfoQR;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjGroup;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjLockDevice;
import vn.vimass.service.utils.DbUtil;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QRvsCard {
    public static final String TABLE_NAME = "dbqr";

    public final static String id = "id";
    public final static String mcID = "mcID";
    public final static String timeTao = "timeTao";
    public final static String catID = "catID";
    public final static String theDaNangLK = "theDaNangLK";
    public final static String infor = "infor";
    public final static String LockDeviceID = "LockDeviceID";

    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static void insertDB(ObjQR item) {
        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + ""
                    + " ("
                    + id + ", "
                    + mcID + ", "
                    + timeTao + ", "
                    + catID + ", "
                    + theDaNangLK + ", "
                    + infor + ", "
                    + LockDeviceID
                    + " ) VALUES ("
                    + "N'" + item.id + "',"
                    + "N'" + item.mcID + "',"
                    + "N'" + item.timeTao + "',"
                    + item.catID + ","
                    + "N'" + item.theDaNangLK + "',"
                    + "N'" + item.infor + "',"
                    + "N'" + item.LockDeviceID + "'"
                    + ");";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
           Log.logServices("executeUpdate dbqr: " + pstmt);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
               Log.logServices("ROW INSERTED dbqr");
            } else {
               Log.logServices("ROW NOT INSERTED dbqr");
            }

        } catch (Exception e) {
           Log.logServices("Exception dbqr insertDB: " + e.getMessage());
        }
    }

    public static ObjQR getQRInfo(String key) {

        ObjQR obj = new ObjQR();
        try {
            String strQuery = "SELECT * FROM dbqr WHERE id ='" + key +"';";
            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
            Log.logServices("strQuery getQRInfo: " + strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                obj = getValue(resultSet, full);
                return obj;
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("Exception getQRInfo: " + e.getMessage());
        }
        return obj;
    }

    private static ObjQR getValue(ResultSet resultSet, boolean full) {

        ObjQR item = new ObjQR();

        try
        {
            item.id = resultSet.getString(id);
            item.mcID = resultSet.getString(mcID);
            item.timeTao = resultSet.getLong(timeTao);
            item.catID = resultSet.getInt(catID);
            item.theDaNangLK = resultSet.getString(theDaNangLK);

            String objInfo = resultSet.getString(infor);
            ObjInfoQR obj =  new Gson().fromJson(objInfo, ObjInfoQR.class);
            item.infor =  obj;
            item.LockDeviceID = resultSet.getString("lockDeviceID");

        }
        catch(Exception e)
        {
           Log.logServices("getValue ObjQR Exception: " + e.getMessage());
        }
        return item;
    }

    public static void update(ObjQR item) {

        try {
            String strSqlInsert = "UPDATE " + TABLE_NAME + ""
                    + " SET "
                    + mcID  + " = N'"+item.mcID+ "', "
                    + timeTao  + " = N'"+item.timeTao+ "', "
                    + catID  + " = N'"+item.catID+ "', "
                    + theDaNangLK  + " = N'"+item.theDaNangLK+ "', "
                    + LockDeviceID  + " = N'"+item.LockDeviceID+ "', "
                    + infor  + " = N'"+item.infor+ "' "
                    + " WHERE "
                    +id + " = N'" + item.id + "'";

            connect = DbUtil.getConnect();

            pstmt = (PreparedStatement) connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
           Log.logServices("executeUpdate1 ObjQR: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
               Log.logServices(" update ObjQR Successful");
            } else {
               Log.logServices(" update ObjQR fail");
            }

        } catch (Exception e) {
           Log.logServices("Exception update ObjQR: " + e.getMessage());
        }
    }

    public static void delete(String key) {
        try {

            String strSqlInsert = "DELETE FROM " + TABLE_NAME + " WHERE "+id + " = N'" + key + "';";
            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
           Log.logServices("executeUpdate delete ObjQR: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
               Log.logServices("delete ObjQR successful");
            } else {
               Log.logServices("delete ObjQR fail");
            }

        } catch (Exception e) {
           Log.logServices("Exception delete ObjQR: " + e.getMessage());
        }
    }

    public static ArrayList<ObjQR> getListQRInfo() {

        ArrayList<ObjQR> list = new ArrayList<ObjQR>();
        try {
            String strQuery = "SELECT * FROM dbqr";
            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjQR item = getValue(resultSet, full);
                list.add(item);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

}
