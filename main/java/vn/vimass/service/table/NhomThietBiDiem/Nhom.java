package vn.vimass.service.table.NhomThietBiDiem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.entity.recivephone.GroupObj;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjGroup;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjPerson;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjectCheckTypeGr;
import vn.vimass.service.utils.DbUtil;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Nhom {
    public static final String TABLE_NAME = "qrgroup"; //tablegroup
    public final static String id = "id";
    public final static String groupName = "groupName";
    public final static String mcID = "mcID";
    public final static String timeTao = "timeTao";
    public final static String userTao = "userTao";
    public final static String timeSua = "timeSua";
    public final static String userSua = "userSua";
    public final static String mess = "mess";
    public final static String groupLevel = "groupLevel";
    public final static String type = "type";
    public final static String listGr = "listGr";
    public final static String listPer = "listPer";

    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    private static ObjGroup getValue(ResultSet resultSet, boolean b) {
        // TODO Auto-generated method stub

        ObjGroup obj = new ObjGroup();
        try {

            obj.id = resultSet.getString(id);
            obj.groupName = resultSet.getString(groupName);
            obj.mcID = resultSet.getString(mcID);
            obj.timeTao = Long.valueOf(resultSet.getString(timeTao));
            obj.userTao = resultSet.getString(userTao);
            obj.timeSua = Long.valueOf(resultSet.getString(timeSua));
            obj.userSua = resultSet.getString(userSua);
            obj.mess = resultSet.getString(mess);
            obj.groupLevel = Integer.valueOf(resultSet.getString(groupLevel));
            //	obj.type = Integer.valueOf(resultSet.getString(type));

            String strListGr = resultSet.getString(listGr);
            Type collectionTypeGr = new TypeToken<List<ObjGroup>>() {
            }.getType();
            ArrayList<ObjGroup> listParse = new Gson().fromJson(strListGr, collectionTypeGr);
            obj.listGr = listParse;

        }catch (Exception e){
            Log.logServices("Exception getValue: " + e.getMessage());
        }

        return obj;
    }


    public static void insertDB(ObjGroup item) {
        try {
            String strQuery = "INSERT INTO " + TABLE_NAME + ""
                    + " ("
                    + id + ", "
                    + groupName + ", "
                    + mcID + ", "
                    + timeTao + ", "
                    + userTao + ", "
                    + timeSua + ", "
                    + userSua + ", "
                    + mess + ", "
                    + groupLevel + ", "
                    + listGr +""

                    + " ) VALUES ("
                    + "N'" + item.id + "',"
                    + "N'" + item.groupName + "',"
                    + "N'" + item.mcID + "',"
                    + "N'" + item.timeTao + "',"
                    + "N'" + item.userTao + "',"
                    + "N'" + item.timeSua + "',"
                    + "N'" + item.userSua + "',"
                    + "N'" + item.mess + "',"
                    + item.groupLevel + ","
                    + "N'" + item.listGr + "'"

                    + ");";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            System.out.println("executeUpdate dbObjGroup: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                System.out.println("ROW INSERTED dbObjGroup");
            } else {
                System.out.println("ROW NOT INSERTED dbObjGroup");
            }

        } catch (Exception e) {
            System.out.println("Exception dbObjGroup"  +e.getMessage());
        }
    }

    public static void update(ObjGroup item) {
        try {
            String strQuery = "UPDATE " + TABLE_NAME + ""
                    + " SET "
                    + mcID  + " = N'"+item.mcID+ "', "
                    + timeTao  + " = N'"+item.timeTao+ "', "
                    + userTao  + " = N'"+item.userTao+ "', "
                    + timeSua  + " = N'"+item.timeSua+ "', "
                    + groupName  + " = N'"+item.groupName+ "', "
                    + userSua  + " = N'"+item.userSua+ "', "
                    + mess  + " = N'"+item.mess+ "', "
                    + groupLevel  + " = "+item.groupLevel+ ","
                    + listGr  + " = N'"+item.listGr+ "' "

                    + " WHERE "
                    +id + " = N'" + item.id + "'";


            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            System.out.println("executeUpdate ObjQR: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                System.out.println("ROW update ObjGroup");
            } else {
                System.out.println("ROW NOT update ObjGroup");
            }

        } catch (Exception e) {
            Log.logServices("Exception update ObjGroup: " + e.getMessage());
        }
    }

    public static void delete(String key) {
        try {

            String strQuery = "DELETE FROM " + TABLE_NAME + " WHERE "+id + " = N'" + key + "';";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            Log.logServices("executeUpdate delete ObjGroup: " + strQuery);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {

                Log.logServices("delete ObjGroup successful");

            } else {
                Log.logServices("delete ObjGroup fail");
            }

        } catch (Exception e) {
            Log.logServices("Exception delete ObjGroup: " + e.getMessage());
        }
    }

    public static ArrayList<ObjPerson> getListPerson(String key) {

        ArrayList<ObjPerson> list = new ArrayList<ObjPerson>();
        try {

            String strQuery = "SELECT * FROM "+TABLE_NAME+" WHERE " + id + " ='"+key+"' ORDER BY "+ listPer+";";
            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);

            resultSet = pstmt.executeQuery();

            Log.logServices("strQuery ObjPerson: " +strQuery );

            while (resultSet.next()) {

                String strListPer = resultSet.getString("listPer");
                Type collectionType = new TypeToken<List<ObjPerson>>() {
                }.getType();
                List<ObjPerson> listParse = new Gson().fromJson(strListPer, collectionType);
                list = (ArrayList<ObjPerson>) listParse;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static ArrayList<ObjGroup> getListGroup() {
        ObjGroup obj = new ObjGroup();
        ArrayList<ObjGroup> list = new ArrayList<ObjGroup>();
        try {

            String strQuery = "SELECT * FROM "+TABLE_NAME+";";
            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);

            resultSet = pstmt.executeQuery();

            Log.logServices("strQuery  getListGroup: " +strQuery );

            while (resultSet.next()) {
                obj = getValue(resultSet, true);
                list.add(obj);

            }
        } catch (Exception e) {
            Log.logServices("Exception getListGroup: " + e.getMessage());
        }
        return list;
    }
    public static ObjGroup getItemQRGroup(String key){


        ObjGroup item = new ObjGroup();
        try {

            String strQuery =" SELECT * FROM "+TABLE_NAME+" WHERE " + id + " ='"+key+"';";
            connect = DbUtil.getConnect();
            Log.logServices("strQuery getItemQRGroup: " + strQuery);
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            while(resultSet.next()) {

                item = getValue(resultSet, true);
                //return item;
            }

        } catch (Exception e) {

            Log.logServices("Exception getItemQRGroup: " + e.getMessage());
        }

        return item;
    }

    public static  ArrayList<ObjectCheckTypeGr> getListObjectCheckTypeGr(String key){

        ArrayList<ObjectCheckTypeGr> list = new ArrayList<>();
        ObjectCheckTypeGr obj = null;
        try {
            String strQuery ="SELECT tb2.groupID, tb1.groupLevel,tb1.listGr, tb2.idQR \n" +
                    "FROM "+TABLE_NAME +" as tb1, "+GroupOfQR.TABLE_NAME+" as tb2\n" +
                    "WHERE tb1.id = tb2.groupID AND tb2.idQR ='"+key+"' ORDER BY tb2.groupLevel desc;";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet  = pstmt.executeQuery();
            System.out.println("strQuerygetObjectCheckTypeGr ObjPerson: " +strQuery );
            Log.logServices("strQuerygetObjectCheckTypeGr ObjPerson: " +strQuery );
            while (resultSet.next()) {
                obj = getValueObjectCheckTypeGr(resultSet);
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            System.out.println("Exception getObjectCheckTypeGr: " + e.getMessage());
            Log.logServices("Exception getObjectCheckTypeGr: " + e.getMessage());
        }
        return list;
    }

    private static ObjectCheckTypeGr getValueObjectCheckTypeGr(ResultSet resultSet){
        ObjectCheckTypeGr obj = new ObjectCheckTypeGr();
        try {

            obj.groupID = resultSet.getString("groupID");
            obj.idQR = resultSet.getString("idQR");
            obj.listGr = resultSet.getString(listGr);
            obj.groupLevel =resultSet.getInt(groupLevel);
//            String strListGr = resultSet.getString(listGr);
//            Type collectionTypeGr = new TypeToken<List<ObjGroup>>() {
//            }.getType();
//            ArrayList<ObjGroup> listParse = new Gson().fromJson(strListGr, collectionTypeGr);

        }catch (Exception e){
            System.out.println("Exception getValue: " + e.getMessage());
        }
        return obj;

    }


}
