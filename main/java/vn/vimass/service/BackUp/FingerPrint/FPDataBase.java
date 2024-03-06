package vn.vimass.service.BackUp.FingerPrint;

import com.google.gson.Gson;
import vn.vimass.service.BackUp.FingerPrint.Obj.FingerData;
import vn.vimass.service.BackUp.FingerPrint.Obj.FingerInfo;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;

public class FPDataBase {
    public static boolean checkIDupdateDBFingerInfoDB(FingerInfo obj) {
        boolean kq = false;
        try {
            String strQuery = "SELECT * FROM fingerinfo WHERE id =?";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setString(1, obj.id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) { // Check if at least one record exists
                kq = true; // Set kq to true if a record is found
            }

            Log.logServices("checkIDupdateDBFingerInfoDB!" + pstmt);

        } catch (Exception ex) {
            Log.logServices("checkIDupdateDBFingerInfoDB Exception!" + ex.getMessage());

        }
        return kq;
    }

    public static boolean updateDBFingerInfoDB(FingerInfo obj) {
        String strSqlUpdate = "UPDATE fingerinfo SET port = ?, name = ? WHERE id = ? ";
        boolean isSuccess = false;
        try {
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
            pstmt.setString(1, obj.port);
            pstmt.setString(2, "Vân tay " + obj.id.substring(2, 3));
            pstmt.setString(3, obj.id); // Thêm tham số id

            int result = pstmt.executeUpdate();
            Log.logServices("updateDBFingerInfoDB strSqlUpdate: " + pstmt);

            if (result > 0) {
                Log.logServices("ROW UPDATED");
                isSuccess = true;
                Log.logServices("updateDBFingerInfoDB thành công: ");
            } else {
                Log.logServices("ROW NOT UPDATED");
                Log.logServices("updateDBFingerInfoDB không thành công: ");
            }
        } catch (SQLException e) {
            Log.logServices("updateDBFingerInfoDB catch: " + e.getMessage());
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static Boolean insertDBFingerInfoDB(FingerInfo Obj) {
        String strSqlInsert = "INSERT INTO fingerinfo" + " (id, port, name) VALUES (?, ?, ?)";
        boolean kqI = false;
        try {
            Log.logServices("insertDBFingerInfoDB nhay vao day");
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlInsert);
            pstmt.setString(1, Obj.id);
            pstmt.setString(2, Obj.port);
            pstmt.setString(3, "Vân tay " + Obj.id.substring(2, 3));
            int kq = pstmt.executeUpdate();
            Log.logServices("insertDBFingerInfoDB strSqlInsert: " + pstmt);
            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                kqI = true;
                Log.logServices("insertDBFingerInfoDB thành công: " + Obj.id);
            } else {
                Log.logServices("ROW NOT INSERTED");
                Log.logServices("insertDBFingerInfoDB không thành công: " + Obj.id);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
            Log.logServices("insertDBFingerInfoDB cath: " + e.getMessage());
        } catch (SQLException e) {
            Log.logServices("insertDBFingerInfoDB cath: " + e.getMessage());
            e.printStackTrace();
        }
        return kqI;
    }

    public static boolean deleteAllFingerInfo() {
        boolean result = false;
        try {
            String strQuery = "DELETE FROM fingerinfo"; // SQL query to delete all records
            Connection connect = DbUtil.getConnect(); // Get database connection
            PreparedStatement pstmt = connect.prepareStatement(strQuery); // Prepare the query
            int rowsAffected = pstmt.executeUpdate(); // Execute the query

            if (rowsAffected > 0) { // Check if any rows were affected
                result = true; // Set result to true if any rows were deleted

            }
            Log.logServices("deleteAllFingerInfo executed: " + pstmt);

        } catch (Exception ex) {
            Log.logServices("deleteAllFingerInfo Exception: " + ex.getMessage());
        }
        return result;
    }
    public static ArrayList<FingerInfo> getThietBiVanTay() {
        ArrayList<FingerInfo> arr = new ArrayList<>();
        try {
            String strQuery = "SELECT * FROM fingerinfo";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                FingerInfo item = new FingerInfo();
                item.id = resultSet.getString("id");
                item.name = resultSet.getString("name");
                item.port = resultSet.getString("port");
                arr.add(item);
            }

            Log.logServices("getThietBiVanTay!" + pstmt);

        } catch (Exception Ex) {
            Log.logServices("getThietBiVanTay Exception!" + Ex.getMessage());
        }
        return arr;
    }
    public static FingerData getDataFingerFromVid(ObjectInfoVid oTKR) {
        FingerData obj = new FingerData();
        try{
            String strQuery = "SELECT * FROM ObjectInfoVid WHERE idVid = ? AND personName = ? ";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setString(1, oTKR.idVid);
            pstmt.setString(2, oTKR.personName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) { // Check if at least one record exists
                if(resultSet.getString("fingerData")!=null&&!resultSet.getString("fingerData").equals("")){
                    obj = new Gson().fromJson(resultSet.getString("fingerData"),FingerData.class);
                }
            }
            Log.logServices("getDataFingerFromVid!" + pstmt);
        }catch (Exception ex){
            Log.logServices("getDataFingerFromVid Exception" +ex.getMessage());
        }
        return obj;
    }
}
