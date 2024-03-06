package vn.vimass.service.BackUp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjFP;
import vn.vimass.service.entity.ObjectCheckMat;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ListDiem;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BackUpControllerDataBaseVer2 {
    public static ObjectInfoVid checkMatDB(ObjectCheckMat objectCheckMat) {  // added vID parameter
        boolean kq = false;
        ObjectInfoVid item = new ObjectInfoVid();
        try {

            String strQuery = "SELECT * FROM info_vid WHERE idVid = ? AND personName = ?";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            // Set the parameters from the ObjectInfoVid object
            pstmt.setString(1, objectCheckMat.vID);
            pstmt.setString(2, objectCheckMat.textSearch); // Assuming personPosition is a String

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) { // Check if at least one record exists
                kq = true; // Set kq to true if a record is found
                item.id = resultSet.getString("id");
                item.idVid = resultSet.getString("idVid");
                item.uID = resultSet.getString("uID");
                item.id = resultSet.getString("id");
                item.maSoThue = resultSet.getString("maSoThue");
                item.diaChi = resultSet.getString("diaChi");
                item.dienThoai = resultSet.getString("dienThoai");
                item.email = resultSet.getString("email");
                item.gioiTinh = resultSet.getString("gioiTinh");
                item.hoTen = resultSet.getString("hoTen");
                item.ngayCapCCCD = resultSet.getString("ngayCapCCCD");
                item.ngaySinh = resultSet.getString("ngaySinh");
                item.quocTich = resultSet.getString("quocTich");
                item.soCanCuoc = resultSet.getString("soCanCuoc");
                item.soTheBHYT = resultSet.getString("soTheBHYT");
                item.tk = resultSet.getString("tk");
                item.anhDaiDien = resultSet.getString("anhDaiDien");
                item.anhCMNDMatTruoc = resultSet.getString("anhCMNDMatTruoc");
                item.anhCMNDMatSau = resultSet.getString("anhCMNDMatSau");
                item.faceData = resultSet.getString("faceData");
                item.personName = resultSet.getString("personName");
                item.chucDanh = resultSet.getString("chucDanh");
                item.personPosition = resultSet.getInt("personPosition");
            }

            Log.logServices("checkMatDB the!" + pstmt);
        } catch (SQLException e) {
            Log.logServices("checkMatDB the Exception!");
            e.printStackTrace();
        }


        return item;
    }

    public static ArrayList<ObjVpass> getThietBiVPass() {
        ArrayList<ObjVpass> arr = new ArrayList<>();
        try {
            String strQuery = "SELECT * FROM thietbivpass";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjVpass item = new ObjVpass();
                item.id = resultSet.getString("id");
                item.mcID = resultSet.getString("mcID");
                item.desDevice = resultSet.getString("desDevice");
                item.storage = resultSet.getDouble("storage");
                item.typeDevice = resultSet.getInt("typeDevice");
                item.portD = resultSet.getString("portD");
                item.function = resultSet.getInt("function");
                item.ip = resultSet.getString("ip");
                item.listDiem = new Gson().fromJson(resultSet.getString("listDiem"), new TypeToken<ArrayList<ListDiem>>() {
                }.getType());
                item.deviceID = resultSet.getString("deviceID");
                item.stt = resultSet.getInt("stt");
                arr.add(item);
            }

            Log.logServices("getThietBiVPass!" + pstmt);

        } catch (Exception Ex) {
            Log.logServices("getThietBiVPass Exception!" + Ex.getMessage());
        }
        return arr;
    }

    public static boolean xoaThietBiVPass(String id) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "DELETE FROM thietbivpass WHERE id = ?";
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

            Log.logServices("xoaThietBiVPass: Đã xóa thiết bị có ID = " + id);
        } catch (Exception Ex) {
            Log.logServices("xoaThietBiVPass Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("xoaThietBiVPass SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }

    public static boolean themMoiThietBiVPassDB(ObjVpass objVpass) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "INSERT INTO thietbivpass (id, mcID, desDevice, storage, typeDevice, portD, function, ip, listDiem, deviceID, stt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            pstmt.setString(1, objVpass.id);
            pstmt.setString(2, objVpass.mcID);
            pstmt.setString(3, objVpass.desDevice);
            pstmt.setDouble(4, objVpass.storage);
            pstmt.setInt(5, objVpass.typeDevice);
            pstmt.setString(6, objVpass.portD);
            pstmt.setInt(7, objVpass.function);
            pstmt.setString(8, objVpass.ip);
            pstmt.setString(9, objVpass.listDiem.toString()); // Chuyển ArrayList<ListDiem> thành JSON string
            pstmt.setString(10, objVpass.deviceID);
            pstmt.setInt(11, objVpass.stt);

            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được thêm không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("themMoiThietBiVPass: Đã thêm mới thiết bị có ID = " + objVpass.id);
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

    public static boolean capNhatThietBiVPass(ObjVpass objVpass) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;
        try {
            String strQuery = "UPDATE thietbivpass SET mcID = ?, desDevice = ?, storage = ?, typeDevice = ?, portD = ?, function = ?, ip = ?, listDiem = ?, deviceID = ?, stt = ? WHERE id = ?";
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);

            // Thiết lập giá trị cho các tham số từ đối tượng ObjVpass
            pstmt.setString(1, objVpass.mcID);
            pstmt.setString(2, objVpass.desDevice);
            pstmt.setDouble(3, objVpass.storage);
            pstmt.setInt(4, objVpass.typeDevice);
            pstmt.setString(5, objVpass.portD);
            pstmt.setInt(6, objVpass.function);
            pstmt.setString(7, objVpass.ip);
            pstmt.setString(8, new Gson().toJson(objVpass.listDiem)); // Chuyển ArrayList<ListDiem> thành JSON string
            pstmt.setString(9, objVpass.deviceID);
            pstmt.setInt(10, objVpass.stt);
            pstmt.setString(11, objVpass.id);

            // Thực thi câu lệnh
            int affectedRows = pstmt.executeUpdate();

            // Kiểm tra xem có dòng nào được cập nhật không
            if (affectedRows > 0) {
                isSuccess = true;
            }

            Log.logServices("capNhatThietBiVPass: Đã cập nhật thiết bị có ID = " + objVpass.id);
        } catch (Exception Ex) {
            Log.logServices("capNhatThietBiVPass Exception: " + Ex.getMessage());
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                Log.logServices("capNhatThietBiVPass SQLException: " + ex.getMessage());
            }
        }
        return isSuccess;
    }

    public static ArrayList<ObjVpass> getThietBiVPassTheoDieuKien(int type) {
        ArrayList<ObjVpass> arr = new ArrayList<>();
        String strQuery = "";
        try {
            if (type == 1) {
                strQuery = "SELECT * FROM thietbivpass WHERE typeDevice = 1 OR typeDevice = 2";
            } else if (type == 2) {
                strQuery = "SELECT * FROM thietbivpass WHERE typeDevice = 3 OR typeDevice = 4";
            } else {
                strQuery = "SELECT * FROM thietbivpass WHERE typeDevice >4";
            }
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjVpass item = new ObjVpass();
                item.id = resultSet.getString("id");
                item.mcID = resultSet.getString("mcID");
                item.desDevice = resultSet.getString("desDevice");
                item.storage = resultSet.getDouble("storage");
                item.typeDevice = resultSet.getInt("typeDevice");
                item.portD = resultSet.getString("portD");
                item.function = resultSet.getInt("function");
                item.ip = resultSet.getString("ip");
                item.listDiem = new Gson().fromJson(resultSet.getString("listDiem"), new TypeToken<ArrayList<ListDiem>>() {
                }.getType());
                item.deviceID = resultSet.getString("deviceID");
                item.stt = resultSet.getInt("stt");
                arr.add(item);
            }

            Log.logServices("getThietBiVPass!" + pstmt);

        } catch (Exception Ex) {
            Log.logServices("getThietBiVPass Exception!" + Ex.getMessage());
        }
        return arr;
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

            Log.logServices("getThietBiFP!" + pstmt);

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


}
