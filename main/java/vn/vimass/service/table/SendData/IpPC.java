package vn.vimass.service.table.SendData;

import vn.vimass.service.log.Log;
import vn.vimass.service.table.SendData.entity.ObjecIpPC;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class IpPC {
    public static final String TABLE_NAME = "dbippc";
    public final static String id = "id";
    public final static String ip = "ip";
    public final static String nameServer = "nameServer";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static void insertDb(ObjecIpPC item) {
        Log.logServices("item:"  +item.toString());
        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + " ("
                    + id + ", "
                    + ip + ", "
                    + nameServer
                    + " ) VALUES ("
                    + item.id + ","
                    + "N'" + item.ip + "',"
                    + "N'"+ item.nameServer + "'"
                    + ");";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate ObjecIpPC: " + pstmt);
            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                Log.logServices("ROW INSERTED ObjecIpPC");
            } else {
                Log.logServices("ROW NOT INSERTED ObjecIpPC");
            }

        } catch (Exception e) {
            Log.logServices("Exception INSERTED ObjecIpPC: " + e.getMessage());
        }
    }

    public static ArrayList<ObjecIpPC> getListIpPC(int typeData){

        ArrayList<ObjecIpPC> list = new ArrayList<ObjecIpPC>();
        ObjecIpPC item = new ObjecIpPC();
        try {
            String strQuery = "SELECT * FROM "+ TABLE_NAME +";";
            connect = DbUtil.getConnect();
            Log.logServices("strQuery load getListIpPC: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while ( resultSet .next()) {
                item = getValue(resultSet);
                list.add(item);

            }
        } catch (Exception e) {
            Log.logServices("Exception getListIpPC: " + e.getMessage());
        }
        return list;
    }


    private static ObjecIpPC getValue(ResultSet resultSet) {

        ObjecIpPC item = new ObjecIpPC();
        try
        {
            item.id = resultSet.getInt(id);
            item.ip = resultSet.getString(ip);
            item.nameServer = resultSet.getString(nameServer);

        }
        catch(Exception e)
        {
            Log.logServices("ObjectData Exception: " + e.getMessage());
        }

        return item;
    }
}
