package vn.vimass.service.table;

import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.ObjectTgDenVe;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ThoiGianLamViec {

    public static final String TABLE_NAME = "dbthoigianlamviec";

    public final static String maRaVao = "maGD";

    public final static String vID = "vID";

    public final static String accName = "accName";

    public final static String thoiGianDen = "thoiGianDen";

    public final static String thoiGianVe = "thoiGianVe";

    public final static String soGioLam = "soGioLam";

    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static void taoDuLieu(ObjectTgDenVe item) {

        Log.logServices("item: "  +item.toString());

        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + " ("
                    + maRaVao + ", "
                    + vID + ", "
                    + accName + ", "
                    + thoiGianDen + ", "
                    + thoiGianVe + ", "
                    + soGioLam

                    + " ) VALUES ("
                    + "N'" + item.maGD + "',"
                    + "N'" + item.vID + "',"
                    + "N'"+ item.accName + "',"
                    + "'"+ item.thoiGianDen + "',"
                    + "'"+ item.thoiGianVe + "',"
                    + "'"+ item.soGioLam+ "'"
                    + ");";

            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strSqlInsert);
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate1 gio lam: " + pstmt);


            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("ROW INSERTED gio lam");
            } else {
                Log.logServices("ROW NOT INSERTED gio lam");
            }

        } catch (Exception e) {

        }
    }


    public static ArrayList<ObjectTgDenVe> getTypeSearchListNgayLam(ObjectTgDenVe item, String textSearch, long timeTo, long timeFrom){

        ArrayList<ObjectTgDenVe> list = new ArrayList<ObjectTgDenVe>();

        try {

            String strQuery = "SELECT * FROM "+ TABLE_NAME +
                    " WHERE "+ thoiGianDen + " >="+timeTo+" AND "+thoiGianVe +" <=" + timeFrom;
            connect = DbUtil.getConnect();
            Log.logServices("strQuery load data: " + strQuery);
            Log.logServices("getTypeSearchListNgayLam: "+strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            // Log.logServices("strQuery getListLichSuRaVao: " + pstmt);

            while ( resultSet .next()) {

                item = getValueObjTgDenVe(resultSet,full);
                list.add(item);

            }
        } catch (Exception e) {
            // TODO: handle exception
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
            item.thoiGianDen = resultSet.getLong(thoiGianDen);
            item.thoiGianVe = resultSet.getLong(thoiGianVe);
            item.soGioLam = resultSet.getString(soGioLam);

        }
        catch(Exception e)
        {
            Log.logServices("ObjectTgDenVe getValueObjTgDenVe Exception: " + e.getMessage());
        }

        return item;
    }
}
