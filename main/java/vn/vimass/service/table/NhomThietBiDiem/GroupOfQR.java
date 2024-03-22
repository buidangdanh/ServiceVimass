package vn.vimass.service.table.NhomThietBiDiem;

import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjLockDevice;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjectGroupOfQR;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GroupOfQR {

    public static final String TABLE_NAME = "groupofqr";
    public final static String id = "id";
    public final static String groupID = "groupID";
    public final static String groupName = "groupName";
    public final static String mcID = "mcID";
    public final static String timeTao = "timeTao";
    public final static String userTao = "userTao";
    public final static String timeSua = "timeSua";
    public final static String userSua = "userSua";
    public final static String mess = "mess";
    public final static String groupLevel = "groupLevel";
    public final static String idQR = "idQR";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;
    public static boolean full = true;

    public static void insertDB(ObjectGroupOfQR item) {
        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + ""
                    + " ("
                    + id + ", "
                    + groupID + ", "
                    + groupName + ", "
                    + mcID + ", "
                    + timeTao + ", "
                    + userTao + ", "
                    + timeSua + ", "
                    + userSua + ", "
                    + mess + ", "
                    + groupLevel + ", "
                    + idQR + " "

                    + " ) VALUES ("
                    + "N'" + item.id + "',"
                    + "N'" + item.groupID + "',"
                    + "N'" + item.groupName + "',"
                    + "N'" + item.mcID + "',"
                    + "N'" + item.timeTao + "',"
                    + "N'" + item.userTao + "',"
                    + "N'" + item.timeSua + "',"
                    + "N'" + item.userSua + "',"
                    + "N'" + item.mess + "',"
                    + item.groupLevel + ","
                    + "N'" + item.idQR + "'"
                    + ");";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjectGroupOfQR: " + pstmt);
           // System.out.println(" executeUpdate ObjectGroupOfQR =====> " +pstmt);
            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                Log.logServices(" ObjectGroupOfQR successfully");
            } else {
                Log.logServices(" inserted ObjectGroupOfQR fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception inserted ObjectGroupOfQR: " + e.getMessage());
        }
    }

    public static ObjectGroupOfQR getItem(String key) {

        ObjectGroupOfQR obj = new ObjectGroupOfQR();
        try {

            String strQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + groupID + " = '" + key +  "' OR " +idQR+" = '"+key+"';";

            connect =  DbUtil.getConnect();

            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);

            resultSet = pstmt.executeQuery();

            //  Log.logServices("strQuery getObjGroup: " + pstmt);

            while (resultSet.next()) {

                obj = getValue(resultSet);

            }

        } catch (Exception e) {
            //System.out.println("getObjGroup Exception: " + e.getMessage());
        }

        return obj;

    }

    public static ArrayList<ObjectGroupOfQR> getListGrOfQR(String key) {
        ArrayList<ObjectGroupOfQR> list = new ArrayList<ObjectGroupOfQR>();
        ObjectGroupOfQR obj = null;
        try {

            String strQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + groupID + " = '" + key +  "' OR " +idQR+" = '"+key+"' " +
                    "group by "+id+";";

            connect =  DbUtil.getConnect();

            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);

            resultSet = pstmt.executeQuery();

            //  Log.logServices("strQuery getObjGroup: " + pstmt);

            while (resultSet.next()) {

                obj = getValue(resultSet);
                list.add(obj);

            }

        } catch (Exception e) {
            //System.out.println("getObjGroup Exception: " + e.getMessage());
        }

        return list;

    }
    private static ObjectGroupOfQR getValue(ResultSet resultSet) {

        ObjectGroupOfQR obj = new ObjectGroupOfQR();
        try {

            obj.id = resultSet.getString(id);
            obj.groupID = resultSet.getString(groupID);
            obj.groupName = resultSet.getString(groupName);
            obj.mcID = resultSet.getString(mcID);
            obj.timeTao = resultSet.getLong(timeTao);
            obj.userTao = resultSet.getString(userTao);
            obj.timeSua = resultSet.getLong(timeSua);
            obj.userSua = resultSet.getString(userSua);
            obj.mess = resultSet.getString(mess);
            obj.groupLevel  = resultSet.getInt(groupLevel);
            obj.idQR = resultSet.getString(idQR);


        } catch (Exception e) {

            Log.logServices("Exception ObjectGroupOfQR getValue: " + e.getMessage());
        }
        return obj;
    }

    public static void update(ObjectGroupOfQR item) {
        try {
            String strSqlInsert = "UPDATE " + TABLE_NAME + ""
                    + " SET "
                    + groupID + " = N'"+item.groupID+ "', "
                    + groupName +  " = N'"+item.groupName+ "', "
                    + mcID +  " = N'"+item.mcID+ "', "
                    + timeSua +  " = N'"+item.timeSua+ "', "
                    + userSua +  " = N'"+item.userSua+ "', "
                    + mess +  " = N'"+item.mess+ "', "
                    + groupLevel +  " = "+item.groupLevel+ ", "
                    + idQR +  " = N'"+item.idQR+ "' "
                    + " WHERE "
                    +id + " = N'" + item.id + "'";

            connect =  DbUtil.getConnect();

            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjectGroupOfQR: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("Update ObjectGroupOfQR successfully");
            } else {
                Log.logServices("Update ObjectGroupOfQR fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception update ObjectGroupOfQR: " + e.getMessage());
        }
    }

    public static void delete(String key) {

        try {
            String strSql = "DELETE FROM " + TABLE_NAME + " WHERE "+ idQR + " = N'" + key + "';";
            connect =  DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSql);

           // System.out.println("delete ObjectGroupOfQR ============> " + pstmt);
            Log.logServices("executeUpdate delete ObjectGroupOfQR: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("delete ObjectGroupOfQR successful");
            } else {
                Log.logServices("delete ObjectGroupOfQR fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception delete ObjectGroupOfQR: " + e.getMessage());
        }
    }
}
