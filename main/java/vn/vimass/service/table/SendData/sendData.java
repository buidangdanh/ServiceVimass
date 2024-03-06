package vn.vimass.service.table.SendData;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.TypeThongBao;
import vn.vimass.service.crawler.bhd.SendData.entity.ObjectSendData;
import vn.vimass.service.entity.ObjectLichSuRaVaoQuetQr;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.InforVid;
import vn.vimass.service.table.LichSuRaVaoQuetQR;
import vn.vimass.service.table.SendData.entity.ObjectData;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class sendData {
    public static final String TABLE_NAME = "senddata";
    public final static String id = "id";
    public final static String ipPC = "ipPC";
    public final static String data = "dataSend";
    public final static String personFace = "personFace"; // dung khi xoa 1 face trong pc thi du lieu gui cung xoa theo
    public final static String type = "typeService";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static void insertDb(ObjectData item) {

        Log.logServices("item: "  +item.toString());

        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + " ("
                    + id + ", "
                    + ipPC + ", "
                    + data + ", "
                    + personFace + ", "
                    + type + " "
                    + " ) VALUES ("
                    + "N'" + item.id + "',"
                    + "N'" + item.ipPC + "',"
                    + "N'"+ item.data + "',"
                    + "N'"+ item.personFace + "',"
                    +  item.type
                    + ");";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            System.out.println("executeUpdate sendata: " + pstmt);
            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                System.out.println("ROW INSERTED ObjectData back up");
            } else {
                System.out.println("ROW NOT INSERTED ObjectData back up");
            }

        } catch (Exception e) {

        }
    }

    public static void delete(int typeService, String Ip) {

        try {
            String strQuery = "DELETE FROM " + TABLE_NAME + " WHERE "+type + " = " + typeService + " AND "+ipPC +" = '"+Ip+"';";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            Log.logServices("executeUpdate delete sendData: " + strQuery);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {

                Log.logServices("delete sendData successful");

            } else {
                Log.logServices("delete sendData fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception delete sendData: " + e.getMessage());
        }
    }


    public static void deleteFaceDataBackUp(String key) {

        try {
            String strQuery = "DELETE FROM " + TABLE_NAME + " WHERE "+personFace + " = N'" + key +"';";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            Log.logServices("executeUpdate deleteFaceDataBackUp sendData: " + strQuery);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {

                Log.logServices("deleteFaceDataBackUp sendData successful");

            } else {
                Log.logServices("deleteFaceDataBackUp sendData fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception deleteFaceDataBackUp sendData: " + e.getMessage());
        }
    }

    public static ArrayList<ObjectLichSuRaVaoQuetQr> getListDataBackUpLichSuRaVao(int typeData, String ip){

        ArrayList<ObjectLichSuRaVaoQuetQr> list = new ArrayList<ObjectLichSuRaVaoQuetQr>();
        ObjectLichSuRaVaoQuetQr item = null;
        try {
            String strQuery = "SELECT * FROM "+ TABLE_NAME +
                    " WHERE "+ type + " ="+typeData+" AND "+ipPC+" = N'"+ip+"';";
            connect = DbUtil.getConnect();
            Log.logServices("strQuery load data: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                String dataBackUp =  resultSet.getString(data);
                ObjectLichSuRaVaoQuetQr obj = new Gson().fromJson(dataBackUp, ObjectLichSuRaVaoQuetQr.class);
                item = getValueHisInOut(obj);
                list.add(item);

            }
        } catch (Exception e) {
            Log.logServices("Exception getListDataBackUp: " + e.getMessage());
        }
        return list;
    }


    public static ArrayList<ObjectInfoVid> getListDataBackUpFaceData(int typeData, String ip){

        ArrayList<ObjectInfoVid> list = new ArrayList<ObjectInfoVid>();
        ObjectInfoVid item = null;
        try {
            String strQuery = "SELECT * FROM "+ TABLE_NAME +
                    " WHERE "+ type + " ="+typeData+" AND "+ipPC+" = N'"+ip+"';";
            connect = DbUtil.getConnect();
            Log.logServices("strQuery load data: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                String dataBackUp =  resultSet.getString(data);
                ObjectInfoVid obj = new Gson().fromJson(dataBackUp, ObjectInfoVid.class);
                item = InforVid.getValue(resultSet, true);
                list.add(item);

            }
        } catch (Exception e) {
            Log.logServices("Exception getListDataBackUp: " + e.getMessage());
        }
        return list;
    }

    public static ArrayList<TypeThongBao> getListDataBackUpQRvsGrvsVpass(int typeData, String ip){

        ArrayList<TypeThongBao> list = new ArrayList<TypeThongBao>();
        TypeThongBao item = null;
        try {
            String strQuery = "SELECT * FROM "+ TABLE_NAME +
                    " WHERE "+ type + " ="+typeData+" AND "+ipPC+" = N'"+ip+"';";
            connect = DbUtil.getConnect();
            Log.logServices("strQuery getListDataBackUpQRvsGrvsVpass data: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                ObjectData objSend = getValue(resultSet);
                item  = new Gson().fromJson(objSend.data, TypeThongBao.class);
                list.add(item);

            }
        } catch (Exception e) {
            Log.logServices("Exception getListDataBackUpQRvsGrvsVpass: " + e.getMessage());
        }
        return list;
    }

    public static ObjectLichSuRaVaoQuetQr getValueHisInOut(ObjectLichSuRaVaoQuetQr obj) {

        ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();

        try
        {
            item.maRaVao = obj.maRaVao;
            item.vID = obj.vID;
            item.phone = obj.phone;
            item.accName = obj.accName;
            item.loiRa = obj.loiRa;
            item.idThietBi = obj.idThietBi;
            item.diaChi = obj.diaChi;
            item.thoiGianGhiNhan = obj.thoiGianGhiNhan;
            item.result = obj.result;
            item.typeXacThuc = obj.typeXacThuc;
            item.personNumber = obj.personNumber;

        }
        catch(Exception e)
        {
            Log.logServices("ObjectLichSuRaVaoQuetQr getValue Exception: " + e.getMessage());
        }

        return item;
    }


    private static ObjectData getValue(ResultSet resultSet) {

        ObjectData item = new ObjectData();
        try
        {
            item.id = resultSet.getString(id);
            item.ipPC = resultSet.getString(ipPC);
            item.data = resultSet.getString(data);
            item.type = resultSet.getInt(type);
        }
        catch(Exception e)
        {
            Log.logServices("ObjectData Exception: " + e.getMessage());
        }

        return item;
    }
}
