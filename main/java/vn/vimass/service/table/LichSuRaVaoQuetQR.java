package vn.vimass.service.table;

import vn.vimass.service.entity.ObjectLichSuRaVaoQuetQr;
import vn.vimass.service.entity.ObjectTime;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.ObjectTgDenVe;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LichSuRaVaoQuetQR {

    public static final String TABLE_NAME = "dblichsuravaoqr";

    public final static String maRaVao = "maRaVao";

    public final static String vID = "vID";
    public final static String phone = "phone";

    public final static String accName = "accName";

    public final static String  loiRa= "loiRa";

    public final static String idThietBi = "idThietBi";
    public final static String thongTinDiem = "thongTinDiem";
    public final static String typeXacThuc = "typeXacThuc";

    public final static String diaChi = "diaChi";

    public final static String thoiGianGhiNhan = "thoiGianGhiNhan";
    public final static String timeAuthen = "timeAuthen";

    public final static String phiRaVao = "phiRaVao";

    public final static String result = "result";
    public final static String personNumber = "personNumber";
    public final static String typeDataAuThen = "typeDataAuThen";
    public final static String chucDanh = "chucDanh";
    public final static String vpassID = "vpassID";
    public final static String avatar = "avatar";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static void taoDuLieu(ObjectLichSuRaVaoQuetQr item) {

        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + " ("
                    + maRaVao + ", "
                    + vID + ", "
                    + phone + ", "
                    + typeXacThuc +","
                    + accName + ", "
                    + loiRa + ", "
                    + idThietBi + ", "
                    + diaChi + ", "
                    + thoiGianGhiNhan +", "
                    + timeAuthen +", "
                    + phiRaVao +","
                    + result +","
                    + personNumber+","
                    +typeDataAuThen+","
                    + vpassID+","
                    +chucDanh+","
                    +avatar+","
                    +thongTinDiem
                    + " ) VALUES ("
                    + "N'" + item.maRaVao + "',"
                    + "N'" + item.vID + "',"
                    + "N'" + item.phone + "',"
                    + "N'" + item.typeXacThuc + "',"
                    + "N'"+ item.accName + "',"
                    + "N'"+ item.loiRa + "',"
                    + "N'"+ item.idThietBi + "',"
                    + "N'"+ item.diaChi+ "',"
                    + "'"+ item.thoiGianGhiNhan + "',"
                    + "'"+ item.timeAuthen + "',"
                    + item.phiRaVao + ","
                    + "N'"+ item.result + "',"
                    + item.personNumber+","
                    + item.typeDataAuThen+","
                    + "N'"+ item.vpassID + "',"
                    + "N'"+ item.chucDanh + "',"
                    + "N'"+ item.avatar + "',"
                    + "N'"+ item.thongTinDiem + "'"
                    + ");";
            Log.logServices("executeUpdate item LichSuRaVaoQuetQr: " + strSqlInsert);
//			connect = DriverManager.getConnection(url, user, password);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                System.out.println("ROW INSERTED ObjectLichSuRaVaoQuetQr");
            } else {
                Log.logServices("ROW NOT INSERTED");

                System.out.println("ROW NOT INSERTED ObjectLichSuRaVaoQuetQr");
            }

        } catch (Exception e) {
            Log.logServices("InsertData LichSuRaVaoQuetQr  Exception: " + e.getMessage());
            System.out.println("InsertData LichSuRaVaoQuetQr  Exception: " + e.getMessage());
        }
    }

    public static ArrayList<ObjectLichSuRaVaoQuetQr> getListLichSuRaVaoBackUp(ObjectTime obj){

        ArrayList<ObjectLichSuRaVaoQuetQr> list = new ArrayList<ObjectLichSuRaVaoQuetQr>();
        ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();
        try {

            String strQuery = "SELECT * FROM "+ TABLE_NAME +" WHERE " + thoiGianGhiNhan
                    +" BETWEEN " + obj.timeTo + " AND " +  obj.timeFrom ;
            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            Log.logServices("strQuery getListLichSuRaVao: " + strQuery);

            while ( resultSet .next()) {

                item = getValue(resultSet,full);

                list.add(item);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getListLichSuRaVaoBackUp Exception: " + e.getMessage());
        }
        return list;
    }


    public static ArrayList<ObjectTgDenVe> getListThoiGianRaVaoTrongNgayBackUp(ObjectTime obj ){

        ArrayList<ObjectTgDenVe> list = new ArrayList<ObjectTgDenVe>();
        ObjectTgDenVe item = new ObjectTgDenVe();
        try {

            String strQuery ="SELECT maRaVao, vID, accName, MIN(thoiGianGhiNhan) AS thoiGianDen, MAX(thoiGianGhiNhan) AS thoiGianVe "
                    + " FROM "+TABLE_NAME
                    + " WHERE thoiGianGhiNhan BETWEEN "+obj.timeTo+" AND "+obj.timeFrom
                    + " GROUP BY vID";
            connect = DbUtil.getConnect();
            Log.logServices("strQuery load data: " + strQuery);

            Log.logServices("ThoiGianRaVaoTrongNgay: "+strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            // Log.logServices("strQuery getListLichSuRaVao: " + pstmt);

            while ( resultSet .next()) {

                item = getValueObjTgDenVe(resultSet,full);
                list.add(item);

            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getListThoiGianRaVaoTrongNgay Exception: " + e.getMessage());
        }
        return list;
    }

    private static ObjectTgDenVe getValueObjTgDenVe(ResultSet resultSet, boolean full) {

        ObjectTgDenVe item = new ObjectTgDenVe();

        try
        {
            item.maGD = resultSet.getString(maRaVao);
            item.vID = resultSet.getString(vID);
            item.accName = resultSet.getString(accName);
            item.thoiGianDen = resultSet.getLong("thoiGianDen");
            item.thoiGianVe = resultSet.getLong("thoiGianVe");

        }
        catch(Exception e)
        {
            Log.logServices("ObjectTgDenVe getValueObjTgDenVe Exception: " + e.getMessage());
        }

        return item;
    }

    private static ObjectLichSuRaVaoQuetQr getValue(ResultSet resultSet, boolean full) {

        ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();

        try
        {
            item.maRaVao = resultSet.getString(maRaVao);
            item.vID = resultSet.getString(vID);
            item.phone = resultSet.getString(phone);
            item.accName = resultSet.getString(accName);
            item.loiRa = resultSet.getString(loiRa);
            item.idThietBi = resultSet.getString(idThietBi);
            item.diaChi = resultSet.getString(diaChi);
            item.thoiGianGhiNhan = resultSet.getLong(thoiGianGhiNhan);
            item.result = resultSet.getString(result);
            item.typeXacThuc = resultSet.getInt(typeXacThuc);
            item.personNumber = resultSet.getInt(personNumber);

        }
        catch(Exception e)
        {
            Log.logServices("ObjectLichSuRaVaoQuetQr getValue Exception: " + e.getMessage());
        }

        return item;
    }
}
