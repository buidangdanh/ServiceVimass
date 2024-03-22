package vn.vimass.service.BackUp.FingerPrint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.BackUp.FingerPrint.Obj.*;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ListDiem;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;
import vn.vimass.service.table.object.ObjectFPRequest;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static vn.vimass.service.BackUp.FingerPrint.FPFunC.removeAccent;

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
    public static ArrayList<FingerData> getDataFingerFromVid(ObjectFPRequest oTKR) {
        ArrayList<FingerData> kqF = new ArrayList<>();
        FingerData obj = new FingerData();
        try{
            String strQuery = "SELECT * FROM info_vid WHERE idVid = ? AND personName = ? ";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setString(1, oTKR.idVid);
            pstmt.setString(2, removeAccent(oTKR.personName));
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) { // Check if at least one record exists
                if(resultSet.getString("fingerData")!=null&&!resultSet.getString("fingerData").equals("")){
                    kqF = new Gson().fromJson(resultSet.getString("fingerData"), new TypeToken<ArrayList<FingerData>>() {
                    }.getType());
                }
            }
            Log.logServices("getDataFingerFromVid!" + pstmt);
        }catch (Exception ex){
            Log.logServices("getDataFingerFromVid Exception" +ex.getMessage());
        }
        return kqF;
    }
    public static ArrayList<ObjFP> getThietBiFP() {
        ArrayList<ObjFP> arr = new ArrayList<>();
        try {
            String strQuery = "SELECT * FROM thietbivantay";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjFP item = new ObjFP();
                item.id = resultSet.getString("id");
                item.mcID = resultSet.getString("mcID");
                item.idDonVi = resultSet.getString("idDonVi");
                item.nameF = resultSet.getString("nameF");
                item.totalF = resultSet.getInt("totalF");
                item.currentF = resultSet.getInt("currentF");
                item.timeTao = resultSet.getLong("timeTao");
                item.timeSua = resultSet.getLong("timeSua");
                item.type = resultSet.getInt("type");
                item.listDiem = new Gson().fromJson(resultSet.getString("listDiem"), new TypeToken<ArrayList<ListDiem>>() {
                }.getType());
                item.deviceV = new Gson().fromJson(resultSet.getString("deviceV"), ObjVpass.class);
                item.port = resultSet.getString("port");
                arr.add(item);
            }

            //Log.logServices("getThietBiFP!" + pstmt);

        } catch (Exception Ex) {
            Log.logServices("getThietBiFP Exception!" + Ex.getMessage());
        }
        return arr;
    }

    public static boolean themMoiThietBiVanTayDB(ObjFP objFP) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "INSERT INTO thietbivantay (id, mcID, idDonVi, nameF, totalF, currentF, timeTao, timeSua, type, listDiem, deviceV) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            pstmt.setString(1, objFP.id);
            pstmt.setString(2, objFP.mcID);
            pstmt.setString(3, objFP.idDonVi);
            pstmt.setString(4, objFP.nameF);
            pstmt.setInt(5, objFP.totalF);
            pstmt.setInt(6, objFP.currentF);
            pstmt.setLong(7, objFP.timeTao);
            pstmt.setLong(8, objFP.timeSua);
            pstmt.setInt(9, objFP.type); // Chuyển ArrayList<ListDiem> thành JSON string
            pstmt.setString(10, objFP.listDiem.toString());
            pstmt.setString(11, objFP.deviceV.toString());

            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được thêm không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("themMoiThietBiVPass: Đã thêm mới thiết bị có ID = " + objFP.id);
        } catch (Exception Ex) {
            Log.logServices("themMoiThietBiVPass Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("themMoiThietBiVPass SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }
    public static boolean capNhatThietBiVanTayDB(ObjFP objFP) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "UPDATE thietbivantay SET mcID = ?, idDonVi = ?, nameF = ?, totalF = ?, currentF = ?, timeTao = ?, timeSua = ?, type = ?, listDiem = ?, deviceV = ? WHERE id = ?";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            // Thiết lập giá trị cho các tham số từ đối tượng ObjVpass
            pstmt.setString(1, objFP.mcID);
            pstmt.setString(2, objFP.idDonVi);
            pstmt.setString(3, objFP.nameF);
            pstmt.setInt(4, objFP.totalF);
            pstmt.setInt(5, objFP.currentF);
            pstmt.setLong(6, objFP.timeTao);
            pstmt.setLong(7, objFP.timeSua);
            pstmt.setInt(8, objFP.type); // Chuyển ArrayList<ListDiem> thành JSON string
            pstmt.setString(9, objFP.listDiem.toString());
            pstmt.setString(10, objFP.deviceV.toString());
            pstmt.setString(11, objFP.id);

            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được cập nhật không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("capNhatThietBiVanTayDB: Đã cập nhật thiết bị có ID = " + objFP.id);
        } catch (Exception Ex) {
            Log.logServices("capNhatThietBiVanTayDB Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("capNhatThietBiVanTayDB SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }
    public static boolean xoaThietBiFP(String id) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "DELETE FROM thietbivantay WHERE id = ?";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            // Thiết lập giá trị cho tham số (id)
            pstmt.setString(1, id);

            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được xóa không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("xoaThietBiFP: Đã xóa thiết bị có ID = " + id);
        } catch (Exception Ex) {
            Log.logServices("xoaThietBiFP Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("xoaThietBiFP SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }
    public static boolean capNhatPortVanTayDB(String port,String idDonVi) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "UPDATE thietbivantay SET port = ? WHERE idDonVi = ?";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            // Thiết lập giá trị cho các tham số từ đối tượng ObjVpass
            pstmt.setString(1, port);
            pstmt.setString(2, idDonVi);


            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được cập nhật không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("capNhatPortVanTayDB: Đã cập nhật thiết bị có idDonVi = " + idDonVi);
        } catch (Exception Ex) {
            Log.logServices("capNhatPortVanTayDB Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("capNhatPortVanTayDB SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }
    public static String truyenIDRaCOM(String s) {
        String kq = "";
        try {
            for(ObjFP arr: getThietBiFP()){
                Log.logServices("truyenIDRaCOM 1" +arr.id +"x"+s);

                if(s.equals(arr.id)){
                    kq=arr.port;
                }
            }


        }catch (Exception ex){
            Log.logServices("truyenIDRaCOM Exception" +ex.getMessage());

        }
        return kq;

    }
    public static boolean capNhatCoSoDuLieuFP(ObjThemSuaXoaRQ orK, ArrayList<FingerData> objF) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "UPDATE info_vid SET fingerData = ? WHERE idVid = ? AND personName = ?";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            // Thiết lập giá trị cho các tham số từ đối tượng ObjVpass
            pstmt.setString(1, objF.toString());
            pstmt.setString(2, orK.thongTinNguoi.idVid);
            pstmt.setString(3, removeAccent(orK.thongTinNguoi.personName));


            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được cập nhật không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("capNhatCoSoDuLieuFP: Đã cập nhật thiết bị có ID = " + pstmt);
        } catch (Exception Ex) {
            Log.logServices("capNhatCoSoDuLieuFP Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("capNhatCoSoDuLieuFP SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }
    public static boolean capNhatCoSoDuLieuFPTheoThe(ObjTSXFPTheoThe orK, ArrayList<FingerData> objF) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "UPDATE info_vid SET fingerData = ? WHERE idVid = ? AND personName = ?";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            // Thiết lập giá trị cho các tham số từ đối tượng ObjVpass
            pstmt.setString(1, objF.toString());
            pstmt.setString(2, orK.thongTinNguoi.idVid);
            pstmt.setString(3, removeAccent(orK.thongTinNguoi.personName));


            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được cập nhật không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("capNhatCoSoDuLieuFPTheoThe: Đã cập nhật thiết bị có ID = " + pstmt);
        } catch (Exception Ex) {
            Log.logServices("capNhatCoSoDuLieuFPTheoThe Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("capNhatCoSoDuLieuFPTheoThe SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }
    public static boolean capNhatCoSoDuLieuFPCuThe(String idVid,String personName, ArrayList<FingerData> objF) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "UPDATE info_vid SET fingerData = ? WHERE idVid = ? AND personName = ?";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            // Thiết lập giá trị cho các tham số từ đối tượng ObjVpass
            pstmt.setString(1, objF.toString());
            pstmt.setString(2, idVid);
            pstmt.setString(3, removeAccent(personName));


            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được cập nhật không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("capNhatCoSoDuLieuFPCuThe: Đã cập nhật thiết bị có ID = " + idVid);
        } catch (Exception Ex) {
            Log.logServices("capNhatCoSoDuLieuFPCuThe Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("capNhatCoSoDuLieuFPCuThe SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }
    public static HashMap<String,String> getGroupInfo(String vID, String personName) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String groupID = null;
        HashMap<String,String> hashDiem = new HashMap<>();

        try {
            connect = DbUtil.getConnect(); // Sử dụng cách kết nối đã được cung cấp trong ví dụ của bạn

            // Lấy groupID từ bảng personofgroup
            String personOfGroupQuery = "SELECT groupID FROM personofgroup WHERE vID = ? AND personName = ?";
            pstmt = connect.prepareStatement(personOfGroupQuery);
            pstmt.setString(1, vID);
            pstmt.setString(2, personName);

            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                groupID = resultSet.getString("groupID");

                // Đóng pstmt và resultSet để tái sử dụng
                resultSet.close();
                pstmt.close();

                // Lấy thông tin từ bảng groupofqr sử dụng groupID
                String groupOfQrQuery = "SELECT * FROM groupofqr WHERE groupID = ?";
                pstmt = connect.prepareStatement(groupOfQrQuery);
                pstmt.setString(1, groupID);

                resultSet = pstmt.executeQuery();

                while (resultSet.next()) {
                    // Xử lý và hiển thị thông tin từ bảng groupofqr
                    hashDiem.put(resultSet.getString("idQR"),resultSet.getString("idQR"));
                }
            } else {
                Log.logServices("Không tìm thấy groupID phù hợp với vID và personName.");
            }

        } catch (SQLException e) {
            Log.logServices("capNhatCoSoDuLieuFPCuThe SQLException1: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("capNhatCoSoDuLieuFPCuThe SQLException2: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return hashDiem;
    }
    public static ObjectInfoVid getidVidFromEmptyID(String emptyID, String idThietBiFP) {
        ObjectInfoVid item = new ObjectInfoVid();
        try {
            String strQuery = "SELECT * FROM info_vid WHERE fingerData LIKE ? AND fingerData LIKE ?";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            // Thiết lập các tham số cho truy vấn dựa trên giá trị emptyID và idThie
            String emptyIDPattern = "%\"emptyID\":\"" + emptyID + "\"%";
            String idThiePattern = "%\"idThietBiFP\":\"" + idThietBiFP + "\"%";
            pstmt.setString(1, emptyIDPattern);
            pstmt.setString(2, idThiePattern);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) { // Kiểm tra nếu có ít nhất một bản ghi tồn tại
                item.id = resultSet.getString("id");
                item.idVid = resultSet.getString("idVid");
                item.hoTen = resultSet.getString("hoTen");
                item.dienThoai = resultSet.getString("dienThoai");
            }
            Log.logServices("getidVidFromEmptyID!" + pstmt);
        } catch (Exception ex) {
            Log.logServices("getidVidFromEmptyID Exception" + ex.getMessage());
        }
        return item;
    }
}
