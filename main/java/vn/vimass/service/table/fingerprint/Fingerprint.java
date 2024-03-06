package vn.vimass.service.table.fingerprint;

import vn.vimass.service.log.Log;
import vn.vimass.service.table.fingerprint.obj.ObjectFP;
import vn.vimass.service.table.fingerprint.obj.ObjectFPbyIdQR;
import vn.vimass.service.utils.DbUtil;
import vn.vimass.service.utils.VimassData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Fingerprint {

    public static final String TABLE_NAME = "fingerprint";
    public final static String idfp = "idfp";
    public final static String idVid = "idVid";
    public final static String positionFP = "positionFP";

    public final static String idLoiRaVao = "idLoiRaVao";

    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static boolean insertDB(ObjectFP item) {
        boolean check = false;
        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME
                    + " ("
                    + idfp + ", "
                    + idVid +","
                    + positionFP +","
                    + idLoiRaVao
                    + " ) VALUES ("
                    + "N'" + item.idfp + "',"
                    + "N'"+ item.idVid + "',"
                    + item.positionFP +","
                    + "N'"+ item.idLoiRaVao + "'"
                    + ");";

//			connect = DriverManager.getConnection(url, user, password);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjectFP  Exception: " + strSqlInsert);
            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                Log.logServices("ROW INSERTE ObjectFP");
                check = true;
                VimassData.ContentResult="add ObjectFPD successfully";
                VimassData.typeResult = 1;
            } else {
                Log.logServices("ROW NOT INSERTED ObjectFP");
                VimassData.ContentResult="add ObjectFPD fail";
                VimassData.typeResult = 0;
            }

        } catch (Exception e) {
            Log.logServices("InsertData ObjectFP  Exception: " + e.getMessage());
        }
        return check;
    }

    public static ArrayList<ObjectFPbyIdQR> getListFPbyIdQR(){

        ArrayList<ObjectFPbyIdQR> list = new ArrayList<ObjectFPbyIdQR>();
        ObjectFPbyIdQR item = new ObjectFPbyIdQR();
        try {
            String strQuery ="SELECT fingerprint.*, info_vid.hoTen, info_vid.dienThoai" +
                            " FROM fingerprint "+
                            " INNER JOIN info_vid ON fingerprint.idVid = info_vid.idVid;";
            connect = DbUtil.getConnect();

            Log.logServices("strQuery getListFPbyIdQR: "+strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while ( resultSet .next()) {
                item = getValue(resultSet,full);
                list.add(item);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getListFPbyIdQR Exception: " + e.getMessage());
        }
        return list;
    }

    public static ObjectFPbyIdQR getItemObjectFPbyIdQR(int key){
        ObjectFPbyIdQR item = new ObjectFPbyIdQR();
        try {
            String strQuery ="SELECT fingerprint.*, info_vid.hoTen, info_vid.dienThoai" +
                    " FROM fingerprint "+
                    " INNER JOIN info_vid ON fingerprint.idVid = info_vid.idVid" +
                    " WHERE " +positionFP+ " = "+key+
                    ";";
            connect = DbUtil.getConnect();

            Log.logServices("strQuery getItemObjectFPbyIdQR: "+strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while ( resultSet .next()) {
                item = getValue(resultSet,full);

            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getItemObjectFPbyIdQR Exception: " + e.getMessage());
        }
        return item;
    }


    public static void deleteItemObjectFPbyIdQR(int pos) {
        try {
            String strSql= "DELETE FROM " + TABLE_NAME + " WHERE "
                    +positionFP+ " = " + pos + ";";

            connect = DbUtil.getConnect();

            Log.logServices("strQuery deleteItemObjectFPbyIdQR: "+strSql);
            pstmt = connect.prepareStatement(strSql);
            pstmt = connect.prepareStatement(strSql);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("deleteItemObjectFPbyIdQR successfully");
                VimassData.ContentResult="delete successfully";
                VimassData.typeResult = 1;
            }else{
                Log.logServices("deleteItemObjectFPbyIdQR fail");
                VimassData.ContentResult="delete fail";
                VimassData.typeResult = 0;
            }

        } catch (Exception e) {
            Log.logServices("deleteItemObjectFPbyIdQR Exception: " + e.getMessage());
        }
    }

    public static void deleteAllObjectFPbyIdQR() {
        try {
            String strSql= "DELETE FROM " + TABLE_NAME + " WHERE "
                    +positionFP+ " > 0 ;";

            connect = DbUtil.getConnect();

            Log.logServices("strQuery deleteAllObjectFPbyIdQR: "+strSql);
            pstmt = connect.prepareStatement(strSql);
            pstmt = connect.prepareStatement(strSql);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("delete all FPbyIdQR successfully");
                VimassData.ContentResult="delete all FPbyIdQR successfully";
                VimassData.typeResult = 1;
            }else{
                Log.logServices("delete all FPbyIdQR fail");
                VimassData.ContentResult="delete all FPbyIdQR fail";
                VimassData.typeResult = 0;
            }

        } catch (Exception e) {
            Log.logServices("deleteItemObjectFPbyIdQR Exception: " + e.getMessage());
        }
    }

    private static ObjectFPbyIdQR getValue(ResultSet resultSet, boolean full) {

        ObjectFPbyIdQR item = new ObjectFPbyIdQR();
        try
        {
            item.idfp = resultSet.getString(idfp);
            item.idVid = resultSet.getString(idVid);
            item.positionFP = Integer.parseInt(resultSet.getString(positionFP));
            item.idLoiRaVao = resultSet.getString(idLoiRaVao);
            item.hoTen = resultSet.getString("hoTen");
            item.dienThoai = resultSet.getString("dienThoai");

        }
        catch(Exception e)
        {
            Log.logServices("ObjectFPbyIdQR getValue Exception: " + e.getMessage());
        }
        return item;
    }
}
