package vn.vimass.service.table.NhomThietBiDiem;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.crawler.bhd.XuLyLayKhuonMat.ObjecPerOfGr;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjInfoQR;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjPerson;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjQR;
import vn.vimass.service.utils.DbUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PersonOfGroup {

    public static final String TABLE_NAME = "personofgroup";

    public final static String id = "id";
    public final static String chucDanh = "chucDanh";
    public final static String timeTao = "timeTao";
    public final static String idThietBi = "idThietBi";
    public final static String userTao = "userTao";
    public final static String personID = "personID";
    public final static String uID = "uID";
    public final static String vID = "vID";
    public final static String userName = "userName";
    public final static String personName = "personName";
    public final static String sdt = "sdt";
    public final static String groupID = "groupID";
    public final static String perNum = "perNum";
    public final static String avatar = "avatar";
    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;

    public static void taoDuLieu(ObjPerson item) {
        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME
                    + " ("
                    + id + ", "
                    + chucDanh + ", "
                    + timeTao + ", "
                    + idThietBi + ", "
                    + userTao + ", "
                    + personID + ", "
                    + uID +","
                    + vID +","
                    + userName +","
                    + personName +","
                    + sdt +","
                    + groupID +","
                    + perNum +","
                    + avatar
                    + " ) VALUES ("
                    + "N'" + item.id + "',"
                    + "N'" + item.chucDanh + "',"
                    + "N'" + item.timeTao + "',"
                    + "N'" +item.idThietBi + "',"
                    + "N'" +item.userTao + "',"
                    + "N'" + item.personID + "',"
                    + "N'" + item.uID + "',"
                    + "N'" + item.vID + "',"
                    + "N'" + item.name + "',"
                    + "N'" + Tool.removeVietnameseAccents(item.name) + "',"
                    + "N'" + item.sdt + "',"
                    + "N'" + item.groupID + "',"
                    + item.perNum +","
                    + "N'"+item.avatar+"'"
                    + ");";
            System.out.println("executeUpdate1 ObjectPersonOfGroup ==========>  " + pstmt);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            System.out.println("executeUpdate1 ObjectPersonOfGroup: " + pstmt);

            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("ROW INSERTED ObjectPersonOfGroup");

                // JOptionPane.showMessageDialog(null, "Thêm điểm ra vào  " + item.id + " thành công!");
            } else {
                Log.logServices("ROW NOT INSERTED ObjectPersonOfGroup");
            }

        } catch (Exception e) {
            Log.logServices("ROW NOT Exception ObjectPersonOfGroup");
        }
    }

    public static void update(ObjPerson item) {
        try {
            String strSqlInsert = "UPDATE " + TABLE_NAME + ""
                    + " SET "
                    + chucDanh + " = '"+item.chucDanh+ "', "
                    + timeTao + " = '"+item.timeTao +"', "
                    + idThietBi  + " = '"+item.idThietBi +"', "
                    + userTao + " = '"+item.userTao +"', "
                    + personID + " = '"+item.personID +"', "
                    + uID + " = '"+item.uID +"', "
                    + vID + " = '"+item.vID +"', "
                    + userName + " = '"+item.name +"', "
                    + personName + " = '"+item.personName +"', "
                    + sdt + " = '"+item.sdt +"', "
                    + groupID + " = '"+item.groupID +"', "
                    + perNum + " = "+item.perNum+","
                    + avatar + " = '"+item.avatar+"'"
                    + " WHERE "
                    +id + " = N'" + item.id + item.groupID + "'";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            Log.logServices("executeUpdate1 ObjectPersonOfGroup: " + pstmt);


            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("ROW INSERTED ObjectPersonOfGroup");

            } else {
                Log.logServices("ROW NOT UPDATE ObjectPersonOfGroup");
            }

        } catch (Exception e) {
            Log.logServices("Exception e: " + e.getMessage());
        }
    }


    public static void deteleAll() {
        try {
            String strSqlInsert = "DELETE FROM " + TABLE_NAME + " WHERE "
                    + groupID + " LIKE 'G%';";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            System.out.println("executeUpdate1 deteleAll person of group: " + pstmt);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("ROW DELETE deteleAll person of group");
                //  JOptionPane.showMessageDialog(null, "Xóa điểm ra vào  " + item.id + " thành công!");

            }else{

                Log.logServices("ROW NOT deteleAll person of group");

            }

        } catch (Exception e) {
            Log.logServices("ROW NOT deteleAll person of group  Exception");

        }
    }


    public static void deteleItem(String key, String name, String grID) {
        try {
            String strSqlInsert = "DELETE FROM " + TABLE_NAME + " WHERE "
                    + vID + " = '"+key+"' AND "+userName +" = N'"+name+"' OR "+groupID +" = N'"+grID+"';";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            System.out.println("executeUpdate1 deteleItem person of group: " + pstmt);
            int kq = pstmt.executeUpdate();

            if (kq > 0) {
                Log.logServices("ROW DELETE deteleItem person of group");
                System.out.println("ROW DELETE deteleItem person of group");
                //  JOptionPane.showMessageDialog(null, "Xóa điểm ra vào  " + item.id + " thành công!");

            }else{

               // Log.logServices("ROW NOT deteleItem person of group");
                System.out.println("ROW NOT deteleItem person of group");

            }

        } catch (Exception e) {
            Log.logServices("ROW NOT deteleAll person of group  Exception");

        }
    }


    public static ArrayList<ObjPerson> getListPerOfGroup(String str){

        ArrayList<ObjPerson> list = new ArrayList<ObjPerson>();
        ObjPerson objPerson = null;
        String item ="";
        try {
            String strQuery = "SELECT *FROM " + TABLE_NAME + " WHERE"+str+" GROUP BY " + id;

            connect = DbUtil.getConnect();
            Log.logServices("strQuery load data: " + strQuery);

            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            // System.out.println("strQuery getListLichSuRaVao: " + pstmt);

            while (resultSet.next()) {
                objPerson = getValue(resultSet);
                list.add(objPerson);
            }
        } catch (Exception e) {
            Log.logServices("Exception e: " +e.getMessage());
        }
        return list;
    }

    public static ObjPerson getItemPerOfGroup(String idQR, String key){

        ObjPerson objPerson = null;
        try {
            String strQuery = " SELECT tb1.id,tb1.avatar, tb1.chucDanh,tb1.timeTao,tb1.idThietBi,tb1.userTao,tb1.personID,tb1.uID,tb1.vID,tb1.userName,tb1.sdt,tb1.groupID,tb1.perNum, tb2.idQR FROM "+TABLE_NAME+" as tb1, "+GroupOfQR.TABLE_NAME+" as tb2\n" +
                    " WHERE (tb1.groupID= tb2.groupID AND vID = '"+key+"') AND tb2.idQR = '"+idQR+ "' "+" OR (tb1.groupID= tb2.groupID AND sdt = '"+key+"') AND tb2.idQR = '"+idQR+"'; ";

            connect = DbUtil.getConnect();
            System.out.println("strQuery load data: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                objPerson = getValue(resultSet);
            }

        } catch (Exception e) {

            Log.logServices("Exception e: " +e.getMessage());
        }
        return objPerson;
    }

    public static ObjPerson getItemPerOfGroupOfAllQR(String key){

        ObjPerson objPerson = null;
        try {
            String strQuery = " SELECT tb1.id, tb1.chucDanh,tb1.timeTao,tb1.idThietBi,tb1.userTao,tb1.personID,tb1.uID,tb1.vID,tb1.userName,tb1.sdt,tb1.groupID,tb1.perNum, tb1.avatar, tb2.idQR FROM "+PersonOfGroup.TABLE_NAME+" as tb1, "+GroupOfQR.TABLE_NAME+" as tb2\n" +
                    " WHERE (tb1.groupID= tb2.groupID AND vID = '"+key+"') "+" OR (tb1.groupID= tb2.groupID AND sdt = '"+key+"') ORDER BY id LIMIT 1 OFFSET 0;";

            connect = DbUtil.getConnect();
            System.out.println("strQuery load data: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                objPerson = getValue(resultSet);
            }

        } catch (Exception e) {

            Log.logServices("Exception e: " +e.getMessage());
        }
        return objPerson;
    }
    private static ObjPerson getValue(ResultSet resultSet) {

        ObjPerson item = new ObjPerson();
        try
        {

            item.id = resultSet.getString(id);
            item.chucDanh = resultSet.getString(chucDanh);
            item.timeTao = resultSet.getLong(timeTao);
            item.idThietBi = resultSet.getString(idThietBi);
            item.userTao = resultSet.getString(userTao);
            item.personID = resultSet.getString(personID);
            item.uID = resultSet.getString(uID);
            item.vID = resultSet.getString(vID);
            item.name = resultSet.getString(userName);
            item.sdt = resultSet.getString(sdt);
            item.groupID = resultSet.getString(groupID);
            item.perNum = resultSet.getInt(perNum);
            item.idQR = resultSet.getString("idQR");
            item.avatar = resultSet.getString("avatar");
        }
        catch(Exception e)
        {
            Log.logServices("Exception e: " + e.getMessage());
        }
        return item;
    }

    public static ArrayList<ObjecPerOfGr> getListPerOfGr(String key){

        ArrayList<ObjecPerOfGr> listPerOfGr = new ArrayList<>();
        ObjecPerOfGr objPerOfGr = null;

        try{
            String strQuery = "SELECT tb1.id,tb1.userName, tb1.chucDanh, tb1.uID, tb1.vID, tb1.perNum, tb1.groupID, tb2.idQR\n" +
                    "FROM "+TABLE_NAME+" AS tb1\n" +
                    "INNER JOIN "+GroupOfQR.TABLE_NAME+" AS tb2 ON tb1.groupID = tb2.groupID\n" +
                    "WHERE tb2.idQR = '"+key+"'\n" +
                    "GROUP BY tb1.id;";
            System.out.println("strQuery getListPerOfGr: " + strQuery);
            connect = DbUtil.getConnect();
            Log.logServices("strQuery getListPerOfGr: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                objPerOfGr = getValuePerOfGr(resultSet);
                listPerOfGr.add(objPerOfGr);

            }

        }catch (Exception e){

            Log.logServices("Exception getListPerOfGr: " + e.getMessage());
        }

        return listPerOfGr;
    }

    private static ObjecPerOfGr getValuePerOfGr(ResultSet resultSet) {

        ObjecPerOfGr objPerOfGr = new ObjecPerOfGr();
        try
        {
            objPerOfGr.id = resultSet.getString(id);
            objPerOfGr.userName = resultSet.getString(userName);
            objPerOfGr.chucDanh = resultSet.getString(chucDanh);
            objPerOfGr.uID = resultSet.getString(uID);
            objPerOfGr.vID = resultSet.getString(vID);
            objPerOfGr.perNum = resultSet.getInt(perNum);
            objPerOfGr.groupID = resultSet.getString(groupID);
            objPerOfGr.idQR = resultSet.getString("idQR");

        }
        catch(Exception e)
        {
            Log.logServices("Exception e: " + e.getMessage());
        }

        return objPerOfGr;
    }

}
