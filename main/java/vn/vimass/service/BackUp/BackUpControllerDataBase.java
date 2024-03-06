package vn.vimass.service.BackUp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.crawler.bhd.SendData.SendDataController;
import vn.vimass.service.entity.*;
import vn.vimass.service.entity.group.layDanhSachGroupRequest;
import vn.vimass.service.entity.group.totallayDanhSachGroup;
import vn.vimass.service.entity.mayTinh.ObjectLayIPRequest;
import vn.vimass.service.entity.recivephone.GroupObj;
import vn.vimass.service.entity.recivephone.ObjListPer;
import vn.vimass.service.entity.recivephone.ObjectNguoiNhom;
import vn.vimass.service.entity.thongkeoffline.ObjectThongKeRequest;
import vn.vimass.service.entity.thongkeoffline.ObjectThongKeResponse;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.InforVid;
import vn.vimass.service.table.NhomThietBiDiem.GroupOfQR;
import vn.vimass.service.table.NhomThietBiDiem.PersonOfGroup;
import vn.vimass.service.table.object.*;
import vn.vimass.service.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static vn.vimass.service.BackUp.BackUpFunction.getThietBiVpass;
import static vn.vimass.service.BackUp.BackUpFunction.tongThoiGian;

public class BackUpControllerDataBase {



    public static Boolean cuPhapChenLichSuXacThucVaoDBQR(ObjectLichSuRaVaoQuetQrMayMot item) {
        String strSqlInsert = "INSERT INTO dblichsuravaoqr" + " (maRaVao, vID, accName, loiRa, idThietBi, diaChi, thoiGianGhiNhan, result,phiRaVao,personNumber) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?,?)";


        boolean kqI = false;
        try {
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlInsert);
            pstmt.setString(1, item.maRaVao);
            pstmt.setString(2, item.vID);
            pstmt.setString(3, item.accName);
            pstmt.setString(4, item.loiRa);
            pstmt.setString(5, item.idThietBi);
            pstmt.setString(6, item.diaChi);
            pstmt.setLong(7, item.thoiGianGhiNhan);
            pstmt.setString(8, item.result);
            pstmt.setString(9, item.phiRaVao);
            pstmt.setInt(10, item.personNumber);

            int kq = pstmt.executeUpdate();
            Log.logServices("cuPhapChenLichSuXacThucVaoDBQR strSqlInsert: " + pstmt);
            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                kqI = true;
                Log.logServices("cuPhapChenLichSuXacThucVaoDBQR thành công: " + item.maRaVao);
            } else {
                Log.logServices("ROW NOT INSERTED");
                Log.logServices("cuPhapChenLichSuXacThucVaoDBQR không thành công: " + item.maRaVao);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
            Log.logServices("cuPhapChenLichSuXacThucVaoDBQR cath: " + e.getMessage());
        } catch (SQLException e) {
            Log.logServices("cuPhapChenLichSuXacThucVaoDBQR cath: " + e.getMessage());
            e.printStackTrace();
        }
        return kqI;
    }

    public static Boolean capNhatVaoDBHienThiMayMot(String newValue, long newTimeCapNhat) {
        String strSqlUpdate = "UPDATE dbtrangthai SET value = ?, timeCapNhat = ? WHERE id = 1";

        boolean isSuccess = false;
        try {
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
            pstmt.setString(1, newValue);
            pstmt.setLong(2, newTimeCapNhat);

            int result = pstmt.executeUpdate();
            Log.logServices("capNhatVaoDBHienThiMayMot strSqlUpdate: " + pstmt);

            if (result > 0) {
                Log.logServices("ROW UPDATED");
                isSuccess = true;
                Log.logServices("capNhatVaoDBHienThiMayMot thành công: ");
            } else {
                Log.logServices("ROW NOT UPDATED");
                Log.logServices("capNhatVaoDBHienThiMayMot không thành công: ");
            }
        } catch (SQLException e) {
            Log.logServices("capNhatVaoDBHienThiMayMot catch: " + e.getMessage());
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static ObjectHienThiMayMot layDuLieuDBHienThiMayMot() {
        String strSqlSelect = "SELECT * FROM dbtrangthai WHERE id = 1";
        ObjectHienThiMayMot objectHienThiMayMot = new ObjectHienThiMayMot();
        Gson gson = new Gson();

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strSqlSelect);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Log.logServices("ROW FOUND");

                String jsonValue = rs.getString("value");
                long timeCapNhat = rs.getLong("timeCapNhat");

                ArrayList<ObjectHienThiXacThucThanhCong> listValue = gson.fromJson(jsonValue, new TypeToken<ArrayList<ObjectHienThiXacThucThanhCong>>() {
                }.getType());

                objectHienThiMayMot.setValue(listValue);
                objectHienThiMayMot.setTimeCapNhat(timeCapNhat);

                Log.logServices("layDuLieuDBHienThiMayMot thành công: ");
            } else {
                Log.logServices("ROW NOT FOUND");
                Log.logServices("layDuLieuDBHienThiMayMot không tìm thấy bản ghi: ");
            }
        } catch (SQLException e) {
            Log.logServices("layDuLieuDBHienThiMayMot catch: " + e.getMessage());
            e.printStackTrace();
        }
        return objectHienThiMayMot;
    }

    public static boolean thayDoiTrangThaiHoatDongMayHai(ObjectTrangThaiHoatDong item) {
        boolean kq = false;
        try {
            String strQuery = "UPDATE dbtrangthai" + " SET mayCapNhat = ?,thoiGianGanNhat=? WHERE id = 1";
            Connection connect = DbUtil.getConnect();

            // Create the prepared statement
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            // Set the values. Assuming newMode is the new value for "cheDo".
            pstmt.setInt(1, item.mayCapNhat);
            pstmt.setLong(2, item.thoiGianGanNHat);
            Log.logServices("thayDoiTrangThaiHoatDong strQuery: " + pstmt);
            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                Log.logServices("thayDoiTrangThaiHoatDong thành công!");
                kq = true;
            }
        } catch (Exception e) {
            Log.logServices("Catch thayDoiTrangThaiHoatDong: " + e.getMessage());
        }
        Log.logServices("thayDoiTrangThaiHoatDong kq: " + kq);
        return kq;
    }

    public static ObjectTrangThaiHoatDong layTrangThaiMayHai() {
        String strSqlSelect = "SELECT * FROM dbtrangthai WHERE id = 1";
        ObjectTrangThaiHoatDong objectTrangThaiHoatDong = new ObjectTrangThaiHoatDong();
        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strSqlSelect);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Log.logServices("ROW FOUND");
                objectTrangThaiHoatDong.setMayCapNhat(rs.getInt("mayCapNhat"));
                objectTrangThaiHoatDong.setThoiGianGanNHat(rs.getLong("thoiGianGanNhat"));

                Log.logServices("layTrangThaiMayHai thành công: ");
            } else {
                Log.logServices("ROW NOT FOUND");
                Log.logServices("layTrangThaiMayHai không tìm thấy bản ghi: ");
            }
        } catch (SQLException e) {
            Log.logServices("layTrangThaiMayHai catch: " + e.getMessage());
            e.printStackTrace();
        }
        return objectTrangThaiHoatDong;
    }

    public static boolean doiCheDoidLoiRaVaoALL() {
        boolean kq = false;
        try {
            String strQuery = "UPDATE dbipaddress" + " SET cheDo = ? ";

            Log.logServices("doiCheDoidLoiRaVao strQuery: " + strQuery);

            Connection connect = DbUtil.getConnect();

            // Create the prepared statement
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            // Set the values. Assuming newMode is the new value for "cheDo".
            pstmt.setInt(1, 0);

            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                Log.logServices("doiCheDoidLoiRaVaoALL thành công!");
                kq = true;
            }
        } catch (Exception e) {
            Log.logServices("Catch doiCheDoidLoiRaVaoALL: " + e.getMessage());
        }
        Log.logServices("doiCheDoidLoiRaVaoALL kq: " + kq);
        return kq;
    }

    public static ArrayList<ObjectLichSuRaVaoQuetQr> getLatestRecords(int SoBanGhi) {
        ArrayList<ObjectLichSuRaVaoQuetQr> records = new ArrayList<>();
        Log.logServices("Nhảy vào getLatestRecords thành công!");
        String strQuery = "SELECT * FROM dblichsuravaoqr ORDER BY thoiGianGhiNhan DESC LIMIT ?";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setInt(1, SoBanGhi);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();
                item.maRaVao = resultSet.getString("maRaVao");
                item.phone = resultSet.getString("phone");
                item.vID = resultSet.getString("vID");
                item.accName = resultSet.getString("accName");
                item.loiRa = resultSet.getString("loiRa");
                item.idThietBi = resultSet.getString("idThietBi");
                item.diaChi = resultSet.getString("diaChi");
                item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                item.personNumber = resultSet.getInt("personNumber");
                item.typeXacThuc = resultSet.getInt("typeXacThuc");
                item.timeAuthen = resultSet.getLong("timeAuthen");
                records.add(item);
            }
            Log.logServices("Nhảy vào getLatestRecords thành công!" + pstmt);
        } catch (SQLException e) {
            Log.logServices("getLatestRecords catch" + e.getMessage());
            e.printStackTrace();
        }

        return records;
    }

    public static ObjectLSQRTrangThai cuPhapChenLichSuXacThucVaoDBQRFull(ArrayList<ObjectLichSuRaVaoQuetQrMay1> item) {
        ObjectLSQRTrangThai objectLSQRTrangThai = new ObjectLSQRTrangThai();
        objectLSQRTrangThai.thanhCong = new ArrayList<>();
        objectLSQRTrangThai.khongThanhCong = new ArrayList<>();
        Log.logServices("cuPhapChenLichSuXacThucVaoDBQRFull nhảy vào hàm");
        Connection connect = null;
        connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối
        for (ObjectLichSuRaVaoQuetQrMay1 obj : item) {
            String strSqlInsert = "INSERT INTO dblichsuravaoqr" + " (maRaVao, vID, accName, loiRa, idThietBi, diaChi, thoiGianGhiNhan, result) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
            try {
                PreparedStatement pstmt = connect.prepareStatement(strSqlInsert);
                pstmt.setString(1, obj.id);
                pstmt.setString(2, obj.vID);
                pstmt.setString(3, obj.accName);
                pstmt.setString(4, obj.loiRa);
                pstmt.setString(5, obj.idThietBi);
                pstmt.setString(6, obj.diaChi);
                pstmt.setLong(7, obj.thoiGianGhiNhan);
                pstmt.setString(8, obj.result);

                int kq = pstmt.executeUpdate();
                Log.logServices("cuPhapChenLichSuXacThucVaoDBQRFull strSqlInsert: " + pstmt);
                if (kq > 0) {
                    Log.logServices("ROW INSERTED");
                    Log.logServices("cuPhapChenLichSuXacThucVaoDBQRFull thành công: " + obj.id);
                    objectLSQRTrangThai.thanhCong.add(obj.id);
                } else {
                    Log.logServices("ROW NOT INSERTED");
                    Log.logServices("cuPhapChenLichSuXacThucVaoDBQRFull không thành công: " + obj.id);
                    objectLSQRTrangThai.khongThanhCong.add(obj.id);
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
                objectLSQRTrangThai.khongThanhCong.add(obj.id);
                Log.logServices("cuPhapChenLichSuXacThucVaoDBQRFull cathSQLIntegrityConstraintViolationException: " + e.getMessage());

            } catch (SQLException e) {
                objectLSQRTrangThai.khongThanhCong.add(obj.id);
                Log.logServices("cuPhapChenLichSuXacThucVaoDBQRFull cathSQLException: " + e.getMessage());
                e.printStackTrace();
            }

        }
        return objectLSQRTrangThai;

    }

    public static ArrayList<ObjectIpAddress> getBangDbipaddress() {
        ArrayList<ObjectIpAddress> records = new ArrayList<>();
        Log.logServices("Nhảy vào getLatestRecords thành công!");
        String strQuery = "SELECT * FROM dbipaddress";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectIpAddress item = new ObjectIpAddress();
                item.idLoiRaVao = resultSet.getString("idLoiRaVao");
                item.loiRaVao = resultSet.getString("loiRaVao");
                item.address = resultSet.getString("address");
                item.ip = resultSet.getString("ip");
                item.portIp = String.valueOf(resultSet.getInt("portIp"));
                item.loaiCua = resultSet.getInt("loaiCua");
                item.chieuRaVaoBarrie = resultSet.getInt("chieuRaVaoBarrie");
                Log.logServices("getBangDbipaddress thành công!");
                records.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    public static RecordsAndTotal getRecordsAndTotalWithinTimeRange(long from, long to, int limit, int offset) {
        ArrayList<ObjectLichSuRaVaoQuetQr> records = new ArrayList<>();
        int total = 0; // Số bản ghi

        // Cập nhật truy vấn SQL
        String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            // Thiết lập các giá trị cho tham số trong truy vấn
            pstmt.setLong(1, from);
            pstmt.setLong(2, to);
            pstmt.setInt(3, limit);
            pstmt.setInt(4, offset);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();
                item.maRaVao = resultSet.getString("maRaVao");
                item.vID = resultSet.getString("vID");
                item.accName = resultSet.getString("accName");
                item.loiRa = resultSet.getString("loiRa");
                item.idThietBi = resultSet.getString("idThietBi");
                item.diaChi = resultSet.getString("diaChi");
                item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                item.personNumber = resultSet.getInt("personNumber");
                records.add(item);
            }

            // Lấy tổng số bản ghi trong khoảng thời gian (không sử dụng LIMIT và OFFSET)
            String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ?";
            PreparedStatement countStatement = connect.prepareStatement(countQuery);
            countStatement.setLong(1, from);
            countStatement.setLong(2, to);
            ResultSet countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                total = countResultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new RecordsAndTotal(records, total);
    }

    public static boolean selectTimSoTheVaSoDienThoai(String vID, String sdt) {
        boolean kq = false;

        // Cập nhật truy vấn SQL
        String strQuery = "SELECT * FROM dbdanhsachthevatem WHERE vID = ? OR sdt = ?";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            // Thiết lập các giá trị cho tham số trong truy vấn
            pstmt.setString(1, vID);
            pstmt.setString(2, sdt);

            ResultSet resultSet = pstmt.executeQuery();

            // Kiểm tra xem ResultSet có bản ghi nào không
            if (resultSet.next()) {
                // Có bản ghi thỏa mãn điều kiện, gán kq = true
                kq = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kq;
    }

    public static RecordsAndTotalVerMobile getRecordsAndTotalWithinTimeRangeVID(long from, long to, int limit, int offset, String vID, int personNumber, String phone) {
        ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records = new ArrayList<>();
        int total = 0;
        if (vID != null && !vID.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ? AND personNumber = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, vID);
                pstmt.setInt(4, personNumber);  // set personNumber parameter
                pstmt.setInt(5, limit);
                pstmt.setInt(6, offset);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");
                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeVID thành công!" + pstmt);
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ? AND personNumber = ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, vID);
                countStatement.setInt(4, personNumber);  // Set personNumber parameter

                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (phone != null && !phone.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ? AND personNumber = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, phone);
                pstmt.setInt(4, personNumber);  // set personNumber parameter
                pstmt.setInt(5, limit);
                pstmt.setInt(6, offset);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");
                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeVID thành công!" + pstmt);
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ? AND personNumber = ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, phone);
                countStatement.setInt(4, personNumber);  // Set personNumber parameter

                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return new RecordsAndTotalVerMobile(records, total);
    }

    public static RecordsAndTotalVerMobile getRecordsAndTotalWithinTimeRangeNoPersonNumber(long from, long to, int limit, int offset, String vID, String phone) {  // added vID parameter
        ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records = new ArrayList<>();
        int total = 0; // Số bản ghi
        if (vID != null && !vID.equals("")) { // Updated SQL Query
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                // Set the parameters
                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, vID);  // Set vID parameter
                pstmt.setInt(4, limit);
                pstmt.setInt(5, offset);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");

                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeNoPersonNumber!" + pstmt);
                // Updated countQuery
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, vID);  // Set vID parameter

                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                Log.logServices("getRecordsAndTotalWithinTimeRangeNoPersonNumber Exception!");
                e.printStackTrace();
            }
        } else if (phone != null && !phone.equals("")) {
            // Updated SQL Query
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                // Set the parameters
                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, phone);  // Set vID parameter
                pstmt.setInt(4, limit);
                pstmt.setInt(5, offset);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");
                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeNoPersonNumber!" + pstmt);
                // Updated countQuery
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, phone);  // Set vID parameter

                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                Log.logServices("getRecordsAndTotalWithinTimeRangeNoPersonNumber Exception!");
                e.printStackTrace();
            }
        }
        return new RecordsAndTotalVerMobile(records, total);
    }

    public static RecordsAndTotalVerMobile getRecordsAndTotalWithinTimeRangeVIDVaidThietBi(long from, long to, int limit, int offset, String vID, String idThietBi, String phone) {
        ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records = new ArrayList<>();
        int total = 0;
        if (vID != null && !vID.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ? AND idThietBi = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, vID);
                pstmt.setString(4, idThietBi);  // set personNumber parameter
                pstmt.setInt(5, limit);
                pstmt.setInt(6, offset);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");
                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBi!" + pstmt);
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ? AND idThietBi = ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, vID);
                countStatement.setString(4, idThietBi);  // Set personNumber parameter

                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBi Exception!");
                e.printStackTrace();
            }
        } else if (phone != null && !phone.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ? AND idThietBi = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, phone);
                pstmt.setString(4, idThietBi);  // set personNumber parameter
                pstmt.setInt(5, limit);
                pstmt.setInt(6, offset);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");
                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBi!" + pstmt);
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ? AND idThietBi = ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, phone);
                countStatement.setString(4, idThietBi);  // Set personNumber parameter

                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBi Exception!");
                e.printStackTrace();
            }
        }


        return new RecordsAndTotalVerMobile(records, total);
    }

    public static RecordsAndTotalVerMobile getRecordsAndTotalWithinTimeRangeVIDVaidThietBiVaPer(long from, long to, int limit, int offset, String vID, String idThietBi, int personNumber, String phone) {
        ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records = new ArrayList<>();
        int total = 0;
        if (vID != null && !vID.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ? AND idThietBi = ? AND personNumber = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, vID);
                pstmt.setString(4, idThietBi);
                pstmt.setInt(5, personNumber);// set personNumber parameter
                pstmt.setInt(6, limit);
                pstmt.setInt(7, offset);


                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");
                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBiVaPer!" + pstmt);
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND vID = ? AND idThietBi = ? AND personNumber= ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, vID);
                countStatement.setString(4, idThietBi);  // Set personNumber parameter
                countStatement.setInt(5, personNumber);
                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBiVaPer Exception!");
                e.printStackTrace();
            }
        } else if (phone != null && !phone.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ? AND idThietBi = ? AND personNumber = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                pstmt.setLong(1, from);
                pstmt.setLong(2, to);
                pstmt.setString(3, phone);
                pstmt.setString(4, idThietBi);
                pstmt.setInt(5, personNumber);// set personNumber parameter
                pstmt.setInt(6, limit);
                pstmt.setInt(7, offset);


                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                    item.id = resultSet.getString("maRaVao");
                    item.sdt = resultSet.getString("phone");
                    if (item.sdt != null && !item.sdt.equals("")) {
                    } else {
                        item.vID = resultSet.getString("vID");
                    }
                    item.personName = resultSet.getString("accName");
                    item.chucDanh = resultSet.getString("chucDanh");
                    item.tenThietBi = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.thongTinDiem = resultSet.getString("thongTinDiem");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                    if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                        item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                    }
                    item.avatar = resultSet.getString("avatar");
                    records.add(item);
                }
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBiVaPer!" + pstmt);
                String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND phone = ? AND idThietBi = ? AND personNumber= ?";
                PreparedStatement countStatement = connect.prepareStatement(countQuery);
                countStatement.setLong(1, from);
                countStatement.setLong(2, to);
                countStatement.setString(3, phone);
                countStatement.setString(4, idThietBi);  // Set personNumber parameter
                countStatement.setInt(5, personNumber);
                ResultSet countResultSet = countStatement.executeQuery();
                if (countResultSet.next()) {
                    total = countResultSet.getInt(1);
                }
            } catch (SQLException e) {
                Log.logServices("getRecordsAndTotalWithinTimeRangeVIDVaidThietBiVaPer Exception!");
                e.printStackTrace();
            }
        }


        return new RecordsAndTotalVerMobile(records, total);
    }

    public static RecordsAndTotalVerMobile getRecordsAndTotalWithinTimeRangeIdThietBi(long from, long to, int limit, int offset, String idThietBi) {
        ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records = new ArrayList<>();
        int total = 0;

        String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND idThietBi = ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            pstmt.setLong(1, from);
            pstmt.setLong(2, to);
            pstmt.setString(3, idThietBi);
            pstmt.setInt(4, limit);
            pstmt.setInt(5, offset);// set personNumber parameter


            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                item.id = resultSet.getString("maRaVao");
                item.sdt = resultSet.getString("phone");
                if (item.sdt != null && !item.sdt.equals("")) {
                } else {
                    item.vID = resultSet.getString("vID");
                }
                item.personName = resultSet.getString("accName");
                item.chucDanh = resultSet.getString("chucDanh");
                item.tenThietBi = resultSet.getString("loiRa");
                item.idThietBi = resultSet.getString("idThietBi");
                item.thongTinDiem = resultSet.getString("thongTinDiem");
                item.diaChi = resultSet.getString("diaChi");
                item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                item.personNumber = resultSet.getInt("personNumber");
                item.typeXacThuc = resultSet.getInt("typeXacThuc");
                item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                    item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                }
                item.avatar = resultSet.getString("avatar");
                records.add(item);
            }
            Log.logServices("getRecordsAndTotalWithinTimeRangeIdThietBi!" + pstmt);
            String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? AND idThietBi = ?";
            PreparedStatement countStatement = connect.prepareStatement(countQuery);
            countStatement.setLong(1, from);
            countStatement.setLong(2, to);
            countStatement.setString(3, idThietBi);
            ResultSet countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                total = countResultSet.getInt(1);
            }
        } catch (SQLException e) {
            Log.logServices("getRecordsAndTotalWithinTimeRangeIdThietBi Exception!");
            e.printStackTrace();
        }

        return new RecordsAndTotalVerMobile(records, total);
    }
    public static RecordsAndTotalVerMobile getRecordsAndTotalWithinTimeRangeMcID(long from, long to, int limit, int offset, String mcID) {
        ArrayList<ObjectLichSuRaVaoQuetQrVerMobile> records = new ArrayList<>();
        int total = 0;

        String strQuery = "SELECT * FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ? ORDER BY thoiGianGhiNhan DESC LIMIT ? OFFSET ?";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            pstmt.setLong(1, from);
            pstmt.setLong(2, to);
            pstmt.setInt(3, limit);
            pstmt.setInt(4, offset);


            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectLichSuRaVaoQuetQrVerMobile item = new ObjectLichSuRaVaoQuetQrVerMobile();
                item.id = resultSet.getString("maRaVao");
                item.sdt = resultSet.getString("phone");
                if (item.sdt != null && !item.sdt.equals("")) {
                } else {
                    item.vID = resultSet.getString("vID");
                }
                item.personName = resultSet.getString("accName");
                item.chucDanh = resultSet.getString("chucDanh");
                item.tenThietBi = resultSet.getString("loiRa");
                item.idThietBi = resultSet.getString("idThietBi");
                item.thongTinDiem = resultSet.getString("thongTinDiem");
                item.diaChi = resultSet.getString("diaChi");
                item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                item.personNumber = resultSet.getInt("personNumber");
                item.typeXacThuc = resultSet.getInt("typeXacThuc");
                item.typeDataAuThen = resultSet.getInt("typeDataAuThen");
                if(resultSet.getString("vpassID")!=null&&!resultSet.getString("vpassID").equals("")){
                    item.vpassID = getThietBiVpass(resultSet.getString("vpassID"));
                }
                item.avatar = resultSet.getString("avatar");
                records.add(item);
            }
            Log.logServices("getRecordsAndTotalWithinTimeRangeMcID!" + pstmt);
            String countQuery = "SELECT COUNT(*) FROM dblichsuravaoqr WHERE thoiGianGhiNhan BETWEEN ? AND ?";
            PreparedStatement countStatement = connect.prepareStatement(countQuery);
            countStatement.setLong(1, from);
            countStatement.setLong(2, to);
            ResultSet countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                total = countResultSet.getInt(1);
            }
            Log.logServices("getRecordsAndTotalWithinTimeRangeMcID total!" + total);
        } catch (SQLException e) {
            Log.logServices("getRecordsAndTotalWithinTimeRangeMcID Exception!"+e.getMessage());
            e.printStackTrace();
        }

        return new RecordsAndTotalVerMobile(records, total);
    }

    public static ArrayList<ObjectLock> getInfoLock(String id) {
        ArrayList<ObjectLock> records = new ArrayList<>();
        String strQuery = "";
        try {
            if (!id.equals("")) {
                strQuery = "SELECT * FROM dblock WHERE id = ? ";
            } else {
                strQuery = "SELECT * FROM dblock";
            }
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            if (!id.equals("")) {
                pstmt.setString(1, id);
            }
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectLock item = new ObjectLock();
                item.id = resultSet.getString("id");
                item.name = resultSet.getString("name");
                item.ip = resultSet.getString("ip");
                item.portIp = resultSet.getString("portIp");
                item.loaiCua = resultSet.getInt("loaiCua");
                item.chieuRaVaoBarrie = resultSet.getInt("chieuRaVaoBarrie");
                item.typePhi = resultSet.getInt("typePhi");
                item.lenhMoCua = resultSet.getString("lenhMoCua");
                records.add(item);
            }
            Log.logServices("getInfoLock!" + pstmt);


        } catch (SQLException e) {
            Log.logServices("getInfoLock Exception!" + e.getMessage());
            e.printStackTrace();
        }

        return records;
    }

    public static ObjectLock getInfoLockObject(String id) {
        ObjectLock item = new ObjectLock();
        try {
            String strQuery = "SELECT * FROM dblock WHERE id = ? ";

            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            if (!id.equals("")) {
                pstmt.setString(1, id);
            }
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                item.id = resultSet.getString("id");
                item.name = resultSet.getString("name");
                item.ip = resultSet.getString("ip");
                item.portIp = resultSet.getString("portIp");
                item.loaiCua = resultSet.getInt("loaiCua");
                item.chieuRaVaoBarrie = resultSet.getInt("chieuRaVaoBarrie");
                item.typePhi = resultSet.getInt("typePhi");
                item.lenhMoCua = resultSet.getString("lenhMoCua");
            }
            Log.logServices("getInfoLockObject!" + pstmt);


        } catch (SQLException e) {
            Log.logServices("getInfoLockObject Exception!" + e.getMessage());
            e.printStackTrace();
        }

        return item;
    }

    public static final String TABLE_NAME = "dbipaddress";
    public static final String TABLE_NAME_2 = "dblock";

    public static ArrayList<ObjectIpAddress> getInfoAddRest() {
        ArrayList<ObjectIpAddress> records = new ArrayList<>();
        try {
            String strQuery = "SELECT tb1.*, tb2.ip, tb2.portIp, tb2.loaiCua, tb2.name, tb2.typePhi , tb2.chieuRaVaoBarrie " +
                    "FROM " + TABLE_NAME + " as tb1  LEFT JOIN " + TABLE_NAME_2 + " as tb2 ON tb1.id = tb2.id WHERE tb1.id is not null or tb1.id is null;";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {

                ObjectIpAddress item = getValue(resultSet, true);
                records.add(item);
            }
            Log.logServices("getInfoAddRest!" + pstmt);

        } catch (Exception e) {
            Log.logServices("getInfoAddRest!" + e.getMessage());
        }
        return records;

    }

    private static ObjectIpAddress getValue(ResultSet resultSet, boolean full) {

        ObjectIpAddress item = new ObjectIpAddress();
        try {
            item.idLoiRaVao = resultSet.getString("idLoiRaVao");
            item.textQRBack = resultSet.getString("textQRBack");
            item.tk = resultSet.getString("tk");
            item.loiRaVao = resultSet.getString("loiRaVao");
            item.address = resultSet.getString("address");
            item.id = resultSet.getString("id");
            item.idVpass = resultSet.getString("idVpass");
            item.ip = resultSet.getString("ip");
            item.portIp = resultSet.getString("portIp");
            item.name = resultSet.getString("name");
            item.typePhi = resultSet.getInt("typePhi");
            item.chieuRaVaoBarrie = resultSet.getInt("chieuRaVaoBarrie");
        } catch (Exception e) {
            Log.logServices("Exception e: " + e.getMessage());
        }

        return item;
    }

    public static ArrayList<ObjectDevice> getInfoDevice(String id) {
        ArrayList<ObjectDevice> records = new ArrayList<>();
        String strQuery = "";
        try {
            if (!id.equals("")) {
                strQuery = "SELECT * FROM dbdevice WHERE id = ? ";
            } else {
                strQuery = "SELECT * FROM dbdevice";
            }
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            if (!id.equals("")) {
                pstmt.setString(1, id);
            }
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectDevice item = new ObjectDevice();
                item.id = resultSet.getString("id");
                item.name = resultSet.getString("name");
                item.status = resultSet.getInt("status");
                records.add(item);
            }
            Log.logServices("getInfoDevice!" + pstmt);


        } catch (SQLException e) {
            Log.logServices("getInfoDevice Exception!" + e.getMessage());
            e.printStackTrace();
        }

        return records;
    }

    public static ObjectDevice getInfoDeviceTheoID(String id) {
        ObjectDevice item = new ObjectDevice();
        try {
            String strQuery = "SELECT * FROM dbdevice WHERE id = ? ";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setString(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                item.id = resultSet.getString("id");
                item.name = resultSet.getString("name");
                item.status = resultSet.getInt("status");
                item.idLoiRaVao = resultSet.getString("idLoiRaVao");
            }
            Log.logServices("getInfoDeviceTheoID!" + pstmt);
        } catch (SQLException e) {
            Log.logServices("getInfoDeviceTheoID Exception!" + e.getMessage());
            e.printStackTrace();
        }

        return item;
    }

    public static Boolean chenidVpass(String id, String json) {
        boolean kqI = false;
        try {
            String strSqlUpdate = "UPDATE dbipaddress SET idVpass = ? WHERE idLoiRaVao = ?";
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
            pstmt.setString(1, json);
            pstmt.setString(2, id);

            int kq = pstmt.executeUpdate();
            Log.logServices("chenidVpass strSqlInsert: " + pstmt);
            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                kqI = true;
                Log.logServices("chenidVpass thành công: " + id);
            } else {
                Log.logServices("ROW NOT INSERTED");
                Log.logServices("chenidVpass không thành công: " + id);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
            Log.logServices("chenidVpass cath: " + e.getMessage());
        } catch (SQLException e) {
            Log.logServices("chenidVpass cath: " + e.getMessage());
            e.printStackTrace();
        }
        return kqI;
    }

    public static void themThietBiDevice(String id, String name, String idLoiRaVao) {
        Log.logServices("themThietBiDevice nhảy vào hàm");
        Connection connect = null;
        connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

        String strSqlInsert = "INSERT INTO dbdevice (id, name, status,idLoiRaVao) VALUES (?, ?, ?,?)";

        try {
            PreparedStatement pstmt = connect.prepareStatement(strSqlInsert);
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, 1);
            pstmt.setString(4, idLoiRaVao);
            int kq = pstmt.executeUpdate();
            Log.logServices("themThietBiDevice strSqlInsert: " + pstmt);
            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                Log.logServices("themThietBiDevice thành công: " + id);

            } else {
                Log.logServices("ROW NOT INSERTED");
                Log.logServices("themThietBiDevice không thành công: " + id);

            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
            Log.logServices("themThietBiDevice cathSQLIntegrityConstraintViolationException: " + e.getMessage());

        } catch (SQLException e) {
            Log.logServices("themThietBiDevice cathSQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Boolean updateidLoiRaVaowhereIdVpass(String idVpass, String idLoiRaVao) {
        boolean kqI = false;
        try {
            String strSqlUpdate = "UPDATE dbdevice SET idLoiRaVao = ? WHERE id = ?";
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
            pstmt.setString(1, idLoiRaVao);
            pstmt.setString(2, idVpass);

            int kq = pstmt.executeUpdate();
            Log.logServices("updateidLoiRaVaowhereIdVpass strSqlInsert: " + pstmt);
            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                kqI = true;
                Log.logServices("updateidLoiRaVaowhereIdVpass thành công: " + idLoiRaVao);
            } else {
                Log.logServices("ROW NOT INSERTED");
                Log.logServices("updateidLoiRaVaowhereIdVpass không thành công: " + idLoiRaVao);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
            Log.logServices("updateidLoiRaVaowhereIdVpass cath: " + e.getMessage());
        } catch (SQLException e) {
            Log.logServices("updateidLoiRaVaowhereIdVpass cath: " + e.getMessage());
            e.printStackTrace();
        }
        return kqI;
    }

    public static ObjectIpAddress getIpAddressByIdLoiRaVao(String idLoiRaVaoValue) {
        ObjectIpAddress item = null;
        Connection connect = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            String strQuery = "SELECT * FROM dbipaddress WHERE idLoiRaVao = ?";

            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strQuery);
            pstmt.setString(1, idLoiRaVaoValue); // Set value for the WHERE condition

            resultSet = pstmt.executeQuery();
            Log.logServices("getIpAddressByIdLoiRaVao strSqlInsert: " + pstmt);
            if (resultSet.next()) {
                item = new ObjectIpAddress();
                item.idLoiRaVao = resultSet.getString("idLoiRaVao");
                item.loiRaVao = resultSet.getString("loiRaVao");
                item.address = resultSet.getString("address");
                item.id = resultSet.getString("id");
                item.idVpass = resultSet.getString("idVpass");
                item.lat = resultSet.getDouble("lat");
                item.lng = resultSet.getDouble("lng");
                item.tk = resultSet.getString("tk");
                item.textQRBack = resultSet.getString("textQRBack");
                item.textQRRaVao = resultSet.getString("textQRRaVao");
            }

        } catch (Exception e) {
            e.printStackTrace(); // It's a good practice to print or log the exception for debugging purposes
        } finally {
            // It's a good practice to close the resultSet, pstmt, and connect (if they're open)
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return item;
    }

    public static ObjectInfoVid getInfoFromInfoVID(String phone, String vID) {
        ObjectInfoVid item = null;
        Connection connect = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String strQuery = "";

        try {
            if (!phone.equals("")) {
                strQuery = "SELECT * FROM info_vid WHERE dienThoai = ? ";
            } else if (!vID.equals("")) {
                strQuery = "SELECT * FROM info_vid WHERE idVid = ? ";
            }

            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strQuery);
            if (!phone.equals("")) {
                pstmt.setString(1, phone); // Set value for the WHERE condition
            } else if (!vID.equals("")) {
                pstmt.setString(1, vID); // Set value for the WHERE condition
            }

            resultSet = pstmt.executeQuery();
            Log.logServices("getInfoFromInfoVID strSqlInsert: " + pstmt);
            if (resultSet.next()) {
                item = getValue(resultSet);

            }

        } catch (Exception e) {
            e.printStackTrace(); // It's a good practice to print or log the exception for debugging purposes
        } finally {
            // It's a good practice to close the resultSet, pstmt, and connect (if they're open)
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return item;
    }

    public static ObjectRecordsAndTotalObjectInfoVid getInfoFromDbInfoVID(int limit, int offset, int typeFace, String mcID, String textSearch) {
        ArrayList<ObjectInfoVid> items = new ArrayList<>();
        Connection connect = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String strQuery;
        int total = 0; // Số bản ghi

        try {
            connect = DbUtil.getConnect();

            // Check if limit and offset are 0, if so, fetch all records
            if (limit == 0 && offset == 0) {
                strQuery = "SELECT * FROM info_vid WHERE mcID = ? AND (idVid LIKE ? OR dienThoai LIKE ? OR hoTen LIKE ? OR diaChi LIKE ?) ORDER BY personPosition DESC";
                pstmt = connect.prepareStatement(strQuery);
                pstmt.setString(1, mcID);
                pstmt.setString(2, "%" + textSearch + "%"); // Giả sử idVid là một chuỗi mà bạn muốn tìm kiếm
                pstmt.setString(3, "%" + textSearch + "%"); // Tương tự cho dienThoai
                pstmt.setString(4, "%" + textSearch + "%"); // Tương tự cho hoTen
                pstmt.setString(5, "%" + textSearch + "%"); // Tương tự cho diaChi
            } else {
                strQuery = "SELECT * FROM info_vid WHERE mcID = ? AND (idVid LIKE ? OR dienThoai LIKE ? OR hoTen LIKE ? OR diaChi LIKE ?) ORDER BY personPosition DESC LIMIT ? OFFSET ?";
                pstmt = connect.prepareStatement(strQuery);
                pstmt.setString(1, mcID);
                pstmt.setString(2, "%" + textSearch + "%");
                pstmt.setString(3, "%" + textSearch + "%");
                pstmt.setString(4, "%" + textSearch + "%");
                pstmt.setString(5, "%" + textSearch + "%");
                pstmt.setInt(6, limit);
                pstmt.setInt(7, offset);
            }

            resultSet = pstmt.executeQuery();
            Log.logServices("getInfoFromDbInfoVID strSql: " + pstmt);

            while (resultSet.next()) {
                ObjectInfoVid item = new ObjectInfoVid();
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
                if (resultSet.getString("faceData") != null && !resultSet.getString("faceData").equals("")) {
                    if (typeFace == 0) {
                        item.faceData = resultSet.getString("faceData");
                    } else if (typeFace == 1) {
                        item.faceData = resultSet.getString("faceData").substring(0, 5);
                    }
                }
                item.personName = resultSet.getString("personName");
                item.chucDanh = resultSet.getString("chucDanh");
                item.personPosition = resultSet.getInt("personPosition");
                items.add(item);
            }
            String countQuery = "SELECT COUNT(*) FROM info_vid WHERE mcID = ? AND (idVid LIKE ? OR dienThoai LIKE ? OR hoTen LIKE ? OR diaChi LIKE ?) ";
            PreparedStatement countStatement = connect.prepareStatement(countQuery);
            countStatement.setString(1, mcID);
            countStatement.setString(2, "%" + textSearch + "%");  // n là giới hạn số lượng bản ghi
            countStatement.setString(3, "%" + textSearch + "%");  //
            countStatement.setString(4, "%" + textSearch + "%");  //
            countStatement.setString(5, "%" + textSearch + "%");  //
            Log.logServices("getRecordsAndTotalWithinTimeRangeNoPersonNumber Cout!" + countStatement);
            ResultSet countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                total = countResultSet.getInt(1);
            }
        } catch (Exception e) {
            Log.logServices("getInfoFromDbInfoVID Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (connect != null) connect.close();
            } catch (Exception ex) {
                Log.logServices("getInfoFromDbInfoVID Exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return new ObjectRecordsAndTotalObjectInfoVid(items, total);
    }

    public static boolean capNhatVaoInfoVid(ObjectInfoVid objectInfoVid) {


        String strSqlUpdate = "UPDATE info_vid SET uID = ?, maSoThue = ?, diaChi = ?, dienThoai = ?, email = ?, gioiTinh = ?, hoTen = ?, ngayCapCCCD = ?, ngaySinh = ?, quocTich = ?, soCanCuoc = ?, soTheBHYT = ?, tk = ?, anhDaiDien = ?, anhCMNDMatTruoc = ?, anhCMNDMatSau = ?, faceData = ?, personName = ?, chucDanh = ?, personPosition = ? " +
                "WHERE idVid = ? AND personName = ?";

        boolean isSuccess = false;
        try {
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
            pstmt.setString(1, objectInfoVid.uID);
            pstmt.setString(2, objectInfoVid.maSoThue);
            pstmt.setString(3, objectInfoVid.diaChi);
            pstmt.setString(4, objectInfoVid.dienThoai);
            pstmt.setString(5, objectInfoVid.email);
            pstmt.setString(6, objectInfoVid.gioiTinh);
            pstmt.setString(7, objectInfoVid.hoTen);
            pstmt.setString(8, objectInfoVid.ngayCapCCCD);
            pstmt.setString(9, objectInfoVid.ngaySinh);
            pstmt.setString(10, objectInfoVid.quocTich);
            pstmt.setString(11, objectInfoVid.soCanCuoc);
            pstmt.setString(12, objectInfoVid.soTheBHYT);
            pstmt.setString(13, objectInfoVid.tk);
            pstmt.setString(14, objectInfoVid.anhDaiDien);
            pstmt.setString(15, objectInfoVid.anhCMNDMatTruoc);
            pstmt.setString(16, objectInfoVid.anhCMNDMatSau);
            pstmt.setString(17, objectInfoVid.faceData);
            pstmt.setString(18, objectInfoVid.personName);
            pstmt.setString(19, objectInfoVid.chucDanh);
            pstmt.setInt(20, objectInfoVid.personPosition);
            pstmt.setString(21, objectInfoVid.idVid);
            pstmt.setString(22, objectInfoVid.personName);

            int result = pstmt.executeUpdate();
            Log.logServices("capNhatVaoInfoVid strSqlUpdate: " + pstmt);

            if (result > 0) {
                Log.logServices("ROW UPDATED");
                isSuccess = true;
                Log.logServices("capNhatVaoInfoVid thành công: ");
                SendDataController.SendData(101,objectInfoVid.toString(),objectInfoVid.personName );
            } else {
                Log.logServices("ROW NOT UPDATED");
                Log.logServices("capNhatVaoInfoVid không thành công: ");
            }
        } catch (SQLException e) {
            Log.logServices("capNhatVaoInfoVid catch: " + e.getMessage());
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static ObjectRecordsAndTotalObjectInfoVid getRecordsAndTotalinfo_vid(String idQR, int limit, int offset, String key) {  // added vID parameter
        ArrayList<ObjectInfoVid> records = new ArrayList<>();
        int total = 0; // Số bản ghi

        String strQuery = "SELECT tb1.id, tb1.idVid, tb1.uID, tb1.maSoThue, tb1.diaChi, tb1.diaChi, tb1.dienThoai, tb1.email, tb1.gioiTinh, tb1.hoTen, tb1.ngayCapCCCD, tb1.ngaySinh, tb1.quocTich,\n" +
                "tb1.soCanCuoc, tb1.soTheBHYT, tb1.tk, tb1.anhDaiDien, tb1.anhCMNDMatTruoc, tb1.anhCMNDMatSau, tb1.faceData, tb1.personName, tb1.chucDanh, tb1.personPosition, tb3.idQR, tb2.groupID\n" +
                "FROM "+InforVid.TABLE_NAME+" AS tb1 \n" +
                "INNER JOIN "+PersonOfGroup.TABLE_NAME+" AS tb2 ON tb1.idVid = tb2.vID OR tb1.dienThoai = tb2.sdt \n" +
                "INNER JOIN "+GroupOfQR.TABLE_NAME+" AS tb3 On tb2.groupID = tb3.groupID \n" +
                "WHERE tb3.idQR = '"+idQR+"' AND tb1.dienThoai IS NOT NULL AND LENGTH(tb1.dienThoai) > 0 AND tb1.faceData IS NOT NULL group by tb1.id limit "+limit+" offset "+offset+";";

        Log.logServices("query ds nguoi ra vao tai 1 diem ==========> " + strQuery);
        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectInfoVid item = getValue(resultSet);
                records.add(item);
            }
            // Updated countQuery
            String countQuery = "SELECT COUNT(*) FROM (\n" +
                    "select tb1.id, tb1.chucDanh, tb1.uID, tb1.vID, tb1.groupID, tb3.faceData, tb2.idQR FROM "+PersonOfGroup.TABLE_NAME+" as tb1 , "+GroupOfQR.TABLE_NAME+" as tb2 , "+InforVid.TABLE_NAME+" as tb3\n" +
                    "Where tb2.idQR = '"+idQR+"' AND tb1.groupID = tb2.groupID AND tb1.vID = tb3.idVid AND tb3.faceData <> '' group by tb1.id\n" +
                    ") as tatol;";
            PreparedStatement countStatement = connect.prepareStatement(countQuery);

            Log.logServices("getRecordsAndTotalWithinTimeRangeNoPersonNumber Count!" + countStatement);
            ResultSet countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                total = countResultSet.getInt(1);
            }
        } catch (SQLException e) {
            Log.logServices("getRecordsAndTotalWithinTimeRangeNoPersonNumber Exception!" + e.getMessage());
            e.printStackTrace();
        }

        return new ObjectRecordsAndTotalObjectInfoVid(records, total);
    }


    private static ObjectInfoVid getValue(ResultSet resultSet){

        ObjectInfoVid item = new ObjectInfoVid();
        try {
            item.id = resultSet.getString("id");
            item.idVid = resultSet.getString("idVid");
            item.uID = resultSet.getString("uID");
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
            item.idQR = resultSet.getString("idQR");
            item.groupID = resultSet.getString("groupID");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return item;
    }




//    public static Boolean cuPhapChenVid(ObjectInfoVid item, String mcID) {
//        String strSqlInsert = "INSERT INTO info_vid" + " (id, chucDanh, uID, idVid, dienThoai, hoTen, anhDaiDien,personPosition, listIdDiem,mcID) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)";
//
//
//        boolean kqI = false;
//        try {
//            Connection connect = null;
//            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối
//
//            PreparedStatement pstmt = connect.prepareStatement(strSqlInsert);
//            pstmt.setString(1, item.id);
//            if (item.chucDanh != null && !item.chucDanh.isEmpty()) {
//                pstmt.setString(2, item.chucDanh);
//
//            } else {
//                pstmt.setString(2, "");
//
//            }
//            if (item.uID != null && !item.uID.isEmpty()) {
//                pstmt.setString(3, item.uID);
//
//            } else {
//                pstmt.setString(3, "");
//
//            }
//            if (item.idVid != null && !item.idVid.isEmpty()) {
//                pstmt.setString(4, item.idVid);
//
//            } else {
//                pstmt.setString(4, "");
//
//            }
//            if (item.dienThoai != null && !item.dienThoai.isEmpty()) {
//                pstmt.setString(5, item.dienThoai);
//
//            } else {
//                pstmt.setString(5, "");
//
//            }
//            if (item.hoTen != null && !item.hoTen.isEmpty()) {
//                pstmt.setString(6, item.hoTen);
//
//            } else {
//                pstmt.setString(6, "");
//
//            }
//
//            if (item.anhDaiDien != null && !item.anhDaiDien.isEmpty()) {
//                pstmt.setString(7, item.anhDaiDien);
//
//            } else {
//                pstmt.setString(7, "");
//
//            }
//            pstmt.setInt(8, item.personPosition);
//            if (item.listIdDiem != null && !item.listIdDiem.isEmpty()) {
//                pstmt.setString(9, item.listIdDiem);
//
//            } else {
//                pstmt.setString(9, "");
//
//            }
//            pstmt.setString(10, mcID);
//            int kq = pstmt.executeUpdate();
//            Log.logServices("cuPhapChenVid strSqlInsert: " + pstmt);
//            if (kq > 0) {
//                Log.logServices("ROW INSERTED");
//                kqI = true;
//                Log.logServices("cuPhapChenVid thành công: " + item.id);
//            } else {
//                Log.logServices("ROW NOT INSERTED");
//                Log.logServices("cuPhapChenVid không thành công: " + item.id);
//            }
//        } catch (SQLIntegrityConstraintViolationException e) {
//            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
//            Log.logServices("cuPhapChenVid cath: " + e.getMessage());
//        } catch (SQLException e) {
//            Log.logServices("cuPhapChenVid cath: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return kqI;
//    }

    public static ArrayList<ObjectInfoVid> getIDinfo_vid(String mcID) {  // added vID parameter
        ArrayList<ObjectInfoVid> records = new ArrayList<>();
        String strQuery = "SELECT * FROM info_vid";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            // Set the parameters


            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectInfoVid item = new ObjectInfoVid();
                item.id = resultSet.getString("id");
                records.add(item);
            }
            Log.logServices("getIDinfo_vid!" + pstmt);
        } catch (SQLException e) {
            Log.logServices("getIDinfo_vid Exception!");
            e.printStackTrace();
        }

        return records;
    }

//    public static boolean capNhatDbVid(ObjectInfoVid item, String mcID) {
//        boolean isSuccess = false;
//        try {
//            if (item.idVid != null && !item.idVid.equals("")) {
//                String strSqlUpdate = "UPDATE info_vid SET chucDanh = ?, uID = ?, idVid = ?, dienThoai = ?, hoTen = ?, anhDaiDien = ?, listIdDiem = ? , personPosition = ?,mcID =? WHERE idVid = ? AND personPosition = ?";
//
//                Connection connect = null;
//                connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối
//
//                PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
//                if (item.chucDanh != null && !item.chucDanh.isEmpty()) {
//                    pstmt.setString(1, item.chucDanh);
//
//                } else {
//                    pstmt.setString(1, "");
//
//                }
//                if (item.uID != null && !item.uID.isEmpty()) {
//                    pstmt.setString(2, item.uID);
//
//                } else {
//                    pstmt.setString(2, "");
//
//                }
//                if (item.idVid != null && !item.idVid.isEmpty()) {
//                    pstmt.setString(3, item.idVid);
//
//                } else {
//                    pstmt.setString(3, "");
//
//                }
//                if (item.dienThoai != null && !item.dienThoai.isEmpty()) {
//                    pstmt.setString(4, item.dienThoai);
//
//                } else {
//                    pstmt.setString(4, "");
//
//                }
//                if (item.hoTen != null && !item.hoTen.isEmpty()) {
//                    pstmt.setString(5, item.hoTen);
//
//                } else {
//                    pstmt.setString(5, "");
//
//                }
//
//                if (item.anhDaiDien != null && !item.anhDaiDien.isEmpty()) {
//                    pstmt.setString(6, item.anhDaiDien);
//
//                } else {
//                    pstmt.setString(6, "");
//
//                }
//                if (item.listIdDiem != null && !item.listIdDiem.isEmpty()) {
//                    pstmt.setString(7, item.listIdDiem);
//
//                } else {
//                    pstmt.setString(7, "");
//
//                }
//                pstmt.setInt(8, item.personPosition);
//
//                pstmt.setString(9, mcID);
//                pstmt.setString(10, item.idVid);
//                pstmt.setInt(11, item.personPosition);
//                int result = pstmt.executeUpdate();
//                Log.logServices("capNhatDbVid strSqlUpdate: " + pstmt);
//
//                if (result > 0) {
//                    Log.logServices("ROW UPDATED");
//                    isSuccess = true;
//                    Log.logServices("capNhatDbVid thành công: ");
//                } else {
//                    Log.logServices("ROW NOT UPDATED");
//                    Log.logServices("capNhatDbVid không thành công: ");
//                }
//            } else {
//                String strSqlUpdate = "UPDATE info_vid SET chucDanh = ?, uID = ?, idVid = ?, dienThoai = ?, hoTen = ?, anhDaiDien = ?, listIdDiem = ? , personPosition = ?,mcID =? WHERE  dienThoai = ? AND personPosition = ?";
//
//
//                Connection connect = null;
//                connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối
//
//                PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
//                if (item.chucDanh != null && !item.chucDanh.isEmpty()) {
//                    pstmt.setString(1, item.chucDanh);
//
//                } else {
//                    pstmt.setString(1, "");
//
//                }
//                if (item.uID != null && !item.uID.isEmpty()) {
//                    pstmt.setString(2, item.uID);
//
//                } else {
//                    pstmt.setString(2, "");
//
//                }
//                if (item.idVid != null && !item.idVid.isEmpty()) {
//                    pstmt.setString(3, item.idVid);
//
//                } else {
//                    pstmt.setString(3, "");
//
//                }
//                if (item.dienThoai != null && !item.dienThoai.isEmpty()) {
//                    pstmt.setString(4, item.dienThoai);
//
//                } else {
//                    pstmt.setString(4, "");
//
//                }
//                if (item.hoTen != null && !item.hoTen.isEmpty()) {
//                    pstmt.setString(5, item.hoTen);
//
//                } else {
//                    pstmt.setString(5, "");
//
//                }
//
//                if (item.anhDaiDien != null && !item.anhDaiDien.isEmpty()) {
//                    pstmt.setString(6, item.anhDaiDien);
//
//                } else {
//                    pstmt.setString(6, "");
//
//                }
//                if (item.listIdDiem != null && !item.listIdDiem.isEmpty()) {
//                    pstmt.setString(7, item.listIdDiem);
//
//                } else {
//                    pstmt.setString(7, "");
//
//                }
//                pstmt.setInt(8, item.personPosition);
//
//                pstmt.setString(9, mcID);
//                pstmt.setString(10, item.dienThoai);
//                pstmt.setInt(11, item.personPosition);
//                int result = pstmt.executeUpdate();
//                Log.logServices("capNhatDbVid strSqlUpdate: " + pstmt);
//
//                if (result > 0) {
//                    Log.logServices("ROW UPDATED");
//                    isSuccess = true;
//                    Log.logServices("capNhatDbVid thành công: ");
//                } else {
//                    Log.logServices("ROW NOT UPDATED");
//                    Log.logServices("capNhatDbVid không thành công: ");
//                }
//            }
//        } catch (SQLException e) {
//            Log.logServices("capNhatDbVid catch: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return isSuccess;
//    }

    static ArrayList<ObjectThongKeResponse> records = new ArrayList<>();
    static ArrayList<ObjectThongKeResponse> recordsTraVe = new ArrayList<>();

    public static ArrayList<ObjectThongKeResponse> getBanGhiThongKeTheoNhay(ObjectThongKeRequest oTKR, long thoiGianGhiNhanLong) {

        Log.logServices("Nhảy vào getBanGhiThongKeTheoNhay thành công!" + oTKR.toString());

        // Cập nhật truy vấn SQL
        String strQuery = "SELECT *, MIN(thoiGianGhiNhan) AS ThoiGianBatDau, MAX(thoiGianGhiNhan) AS ThoiGianKetThuc "
                + "FROM dblichsuravaoqr "
                + "WHERE DATE(FROM_UNIXTIME(thoiGianGhiNhan/1000)) = DATE(FROM_UNIXTIME(?/1000)) "
                + "AND (vID LIKE ? OR phone LIKE ? OR accName LIKE ? OR diaChi LIKE ? OR loiRa LIKE ?) "
                + "GROUP BY vID, phone";

        try {
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setLong(1, thoiGianGhiNhanLong);
            pstmt.setString(2, "%" + oTKR.textSearch + "%");
            pstmt.setString(3, "%" + oTKR.textSearch + "%");
            pstmt.setString(4, "%" + oTKR.textSearch + "%");
            pstmt.setString(5, "%" + oTKR.textSearch + "%");
            pstmt.setString(6, "%" + oTKR.textSearch + "%");

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectThongKeResponse item = new ObjectThongKeResponse();

                String vID = resultSet.getString("vID");
                String phone = resultSet.getString("phone");
                boolean vIDEmpty = vID == null || vID.equals("");
                boolean phoneEmpty = phone == null || phone.equals("");

                if (vIDEmpty ^ phoneEmpty) {
                    item.maGD = resultSet.getString("maRaVao");
                    item.phone = phone; // already fetched
                    item.vID = vID; // already fetched
                    item.accName = resultSet.getString("accName");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.loiRa = resultSet.getString("loiRa");
                    item.idQR = resultSet.getString("idThietBi");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianDen = resultSet.getLong("ThoiGianBatDau");
                    item.thoiGianVe = resultSet.getLong("ThoiGianKetThuc");

                    if (item.thoiGianVe - item.thoiGianDen > 0) {
                        item.tongThoiGian = tongThoiGian(item.thoiGianDen, item.thoiGianVe);
                    } else {
                        item.tongThoiGian = "";
                    }

                    if (item.thoiGianDen != item.thoiGianVe) {
                        item.hisOnDay = getBanGhiThongKeTheoNhayChiTiet(item);
                    }

                    records.add(item);
                    Log.logServices("getBanGhiThongKeTheoNhay!" + item.maGD);
                }
            }
            Log.logServices("getBanGhiThongKeTheoNhay!" + pstmt);
        } catch (SQLException e) {
            Log.logServices("getBanGhiThongKeTheoNhay catch" + e.getMessage());
            e.printStackTrace();
        }

        return records;
    }

    public static ArrayList<ObjectLichSuRaVaoQuetQr> getBanGhiThongKeTheoNhayChiTiet(ObjectThongKeResponse obj) {
        ArrayList<ObjectLichSuRaVaoQuetQr> recordsChiTiet = new ArrayList<>();
        Log.logServices("Nhảy vào getBanGhiThongKeTheoNhayChiTiet thành công!");

        // Cập nhật truy vấn SQL
        if (obj.vID != null && !obj.vID.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE (thoiGianGhiNhan BETWEEN ? AND ?) AND (vID = ?)";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                // Thiết lập tham số cho truy vấn
                pstmt.setLong(1, obj.thoiGianDen + 1);
                pstmt.setLong(2, obj.thoiGianVe - 1);
                pstmt.setString(3, obj.vID);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();
                    item.maRaVao = resultSet.getString("maRaVao");
                    item.phone = resultSet.getString("phone");
                    item.vID = resultSet.getString("vID");
                    item.accName = resultSet.getString("accName");
                    item.loiRa = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.typeXacThuc = resultSet.getInt("typeXacThuc");
                    item.personNumber = resultSet.getInt("personNumber");
                    recordsChiTiet.add(item);
                }
                Log.logServices("Nhảy vào getBanGhiThongKeTheoNhayChiTiet thành công!" + pstmt);
            } catch (SQLException e) {
                Log.logServices("getBanGhiThongKeTheoNhayChiTiet catch" + e.getMessage());
                e.printStackTrace();
            }

        } else if (obj.phone != null && !obj.phone.equals("")) {
            String strQuery = "SELECT * FROM dblichsuravaoqr WHERE (thoiGianGhiNhan BETWEEN ? AND ?) AND (phone = ?)";

            try {
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                // Thiết lập tham số cho truy vấn
                pstmt.setLong(1, obj.thoiGianDen);
                pstmt.setLong(2, obj.thoiGianVe);
                pstmt.setString(3, obj.phone);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();
                    item.maRaVao = resultSet.getString("maRaVao");
                    item.phone = resultSet.getString("phone");
                    item.vID = resultSet.getString("vID");
                    item.accName = resultSet.getString("accName");
                    item.loiRa = resultSet.getString("loiRa");
                    item.idThietBi = resultSet.getString("idThietBi");
                    item.diaChi = resultSet.getString("diaChi");
                    item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
                    item.personNumber = resultSet.getInt("personNumber");
                    recordsChiTiet.add(item);
                }
                Log.logServices("Nhảy vào getBanGhiThongKeTheoNhayChiTiet thành công!" + pstmt);
            } catch (SQLException e) {
                Log.logServices("getBanGhiThongKeTheoNhayChiTiet catch" + e.getMessage());
                e.printStackTrace();
            }
        }
        return recordsChiTiet;
    }

    public static boolean checkInfovid(ObjectInfoVid obj) {  // added vID parameter
        boolean kq = false;
        if (obj.idVid != null && !obj.idVid.equals("")) {
            try {
                String strQuery = "SELECT * FROM info_vid WHERE idVid = ? AND personPosition = ?";
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);

                // Set the parameters from the ObjectInfoVid object
                pstmt.setString(1, obj.idVid);
                pstmt.setInt(2, obj.personPosition); // Assuming personPosition is a String

                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) { // Check if at least one record exists
                    kq = true; // Set kq to true if a record is found
                }

                Log.logServices("checkInfovid the!" + pstmt);
            } catch (SQLException e) {
                Log.logServices("checkInfovid the Exception!");
                e.printStackTrace();
            }
        } else {
            try {
                String strQuery = "SELECT * FROM info_vid WHERE dienThoai = ? AND personPosition = ?";
                Connection connect = DbUtil.getConnect();
                PreparedStatement pstmt = connect.prepareStatement(strQuery);
                // Set the parameters from the ObjectInfoVid object
                pstmt.setString(1, obj.dienThoai);
                pstmt.setInt(2, obj.personPosition); // Assuming personPosition is a String

                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) { // Check if at least one record exists
                    kq = true; // Set kq to true if a record is found
                }

                Log.logServices("checkInfovid! sdt" + pstmt);
            } catch (SQLException e) {
                Log.logServices("checkInfovid sdt Exception!");
                e.printStackTrace();
            }
        }

        return kq;
    }

    public static ArrayList<GroupObj> LuuVaoDBQrGroup(GroupObj obj) {
        ArrayList<GroupObj> arr = new ArrayList<>();
        try {
            if (checkIDTachNguoiVeDiem(obj.id)) {
                updateTachNguoiVeDiem(obj);
            } else {
                insertTachNguoiVeDiem(obj);
            }
            arr = selectTachNguoiVeDiem(obj.mcID);
        } catch (Exception e) {
            Log.logServices("LuuVaoDBQrGroup Exception!" + e.getMessage());

        }
        return arr;
    }

    public static boolean checkIDTachNguoiVeDiem(String id) {
        boolean kq = false;
        try {
            String strQuery = "SELECT * FROM qrgroup WHERE id = ?";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setString(1, id);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                kq = true;
            }

            Log.logServices("checkIDTachNguoiVeDiem!" + pstmt);

        } catch (Exception e) {
            Log.logServices("checkIDTachNguoiVeDiem Exception!" + e.getMessage());

        }
        return kq;
    }

    public static Boolean insertTachNguoiVeDiem(GroupObj Obj) {
        String strSqlInsert = "INSERT INTO qrgroup" + " (id, groupName, mcID, timeTao, userTao, timeSua, userSua, mess,groupLevel,listGr,listPer) VALUES (?, ?, ?, ?, ?, ?, ?,?, ?,?, ?)";
        boolean kqI = false;
        try {
            Log.logServices("insertTachNguoiVeDiem nhay vao day");
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlInsert);
            pstmt.setString(1, Obj.id);
            pstmt.setString(2, Obj.groupName);
            pstmt.setString(3, Obj.mcID);
            pstmt.setLong(4, Obj.timeTao);
            pstmt.setString(5, Obj.userTao);
            pstmt.setLong(6, Obj.timeSua);
            pstmt.setString(7, Obj.userSua);
            pstmt.setString(8, Obj.mess);
            pstmt.setInt(9, Obj.groupLevel);
            if (Obj.listGr != null) {
                pstmt.setString(10, Obj.listGr.toString());
            } else {
                pstmt.setString(10, "");

            }
            if (Obj.listPer != null) {
                pstmt.setString(11, Obj.listPer.toString());
            } else {
                pstmt.setString(11, "");

            }
            int kq = pstmt.executeUpdate();
            Log.logServices("insertTachNguoiVeDiem strSqlInsert: " + pstmt);
            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                kqI = true;
                Log.logServices("insertTachNguoiVeDiem thành công: " + Obj.id);
            } else {
                Log.logServices("ROW NOT INSERTED");
                Log.logServices("insertTachNguoiVeDiem không thành công: " + Obj.id);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
            Log.logServices("insertTachNguoiVeDiem cath: " + e.getMessage());
        } catch (SQLException e) {
            Log.logServices("insertTachNguoiVeDiem cath: " + e.getMessage());
            e.printStackTrace();
        }
        return kqI;
    }

    public static boolean updateTachNguoiVeDiem(GroupObj obj) {
        String strSqlUpdate = "UPDATE info_vid SET groupName = ?, mcID = ?,  timeTao= ?, userTao = ?, timeSua = ?, userSua = ?, mess = ?, groupLevel = ?, ,listGr = ?, listPer = ? WHERE id = ? ";

        boolean isSuccess = false;
        try {
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
            pstmt.setString(1, obj.groupName);
            pstmt.setString(2, obj.mcID);
            pstmt.setLong(3, obj.timeTao);
            pstmt.setString(4, obj.userTao);
            pstmt.setLong(5, obj.timeSua);
            pstmt.setString(6, obj.userSua);
            pstmt.setString(7, obj.mess);
            pstmt.setInt(8, obj.groupLevel);
            if (obj.listGr != null) {
                pstmt.setString(9, obj.listGr.toString());
            } else {
                pstmt.setString(9, "");
            }
            if (obj.listPer != null) {
                pstmt.setString(10, obj.listPer.toString());
            } else {
                pstmt.setString(10, "");
            }
            pstmt.setString(11, obj.id); // Thêm tham số id


            int result = pstmt.executeUpdate();
            Log.logServices("updateTachNguoiVeDiem strSqlUpdate: " + pstmt);

            if (result > 0) {
                Log.logServices("ROW UPDATED");
                isSuccess = true;
                Log.logServices("updateTachNguoiVeDiem thành công: ");
            } else {
                Log.logServices("ROW NOT UPDATED");
                Log.logServices("updateTachNguoiVeDiem không thành công: ");
            }
        } catch (SQLException e) {
            Log.logServices("updateTachNguoiVeDiem catch: " + e.getMessage());
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static totallayDanhSachGroup layDanhSachGroupDataBase(layDanhSachGroupRequest obj) {
        int total = 0;
        ArrayList<GroupObj> arr = new ArrayList<>();
        try {
            Connection connect = DbUtil.getConnect();

            // Query to get the limited set of groups
            String strQuery = "SELECT * FROM qrgroup WHERE mcID = ? AND (groupName LIKE ? OR listGr LIKE ? OR listPer LIKE ?)  LIMIT ? OFFSET ?";
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            pstmt.setString(1, obj.mcID);
            pstmt.setString(2, "%" + obj.textSearch + "%");
            pstmt.setString(3, "%" + obj.textSearch + "%");
            pstmt.setString(4, "%" + obj.textSearch + "%");
            pstmt.setInt(5, obj.limit);
            pstmt.setInt(6, obj.offset);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                GroupObj item = new GroupObj();
                item.id = resultSet.getString("id");
                item.groupName = resultSet.getString("groupName");
                item.mcID = resultSet.getString("mcID");
                item.timeTao = resultSet.getLong("timeTao");
                item.userTao = resultSet.getString("userTao");
                item.timeSua = resultSet.getLong("timeSua");
                item.userSua = resultSet.getString("userSua");
                item.mess = resultSet.getString("mess");
                item.groupLevel = resultSet.getInt("groupLevel");
                if (resultSet.getString("listGr") != null) {
                    item.listGr = new Gson().fromJson(resultSet.getString("listGr"), new TypeToken<ArrayList<GroupObj>>() {
                    }.getType());
                }
                if (resultSet.getString("listPer") != null) {
                    item.listPer = new Gson().fromJson(resultSet.getString("listPer"), new TypeToken<ArrayList<ObjListPer>>() {
                    }.getType());
                }
                arr.add(item);
            }


            // Query to count the total number of groups
            String countQuery = "SELECT COUNT(*) FROM qrgroup WHERE mcID = ? AND (groupName LIKE ? OR listGr LIKE ? OR listPer LIKE ?)";
            PreparedStatement countStmt = connect.prepareStatement(countQuery);
            countStmt.setString(1, obj.mcID);
            countStmt.setString(2, "%" + obj.textSearch + "%");
            countStmt.setString(3, "%" + obj.textSearch + "%");
            countStmt.setString(4, "%" + obj.textSearch + "%");

            ResultSet countResultSet = countStmt.executeQuery();
            if (countResultSet.next()) {
                total = countResultSet.getInt(1);
            }

            Log.logServices("layDanhSachGroupDataBase!" + pstmt);

        } catch (Exception e) {
            Log.logServices("layDanhSachGroupDataBase Exception!" + e.getMessage());
        }
        return new totallayDanhSachGroup(arr, total);
    }

    public static ArrayList<GroupObj> selectTachNguoiVeDiem(String mcID) {
        ArrayList<GroupObj> arr = new ArrayList<>();
        try {
            // Updated query with WHERE clause
            String strQuery = "SELECT * FROM qrgroup WHERE mcID = ?";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            // Set the mcID parameter
            pstmt.setString(1, mcID);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                GroupObj item = new GroupObj();
                item.id = resultSet.getString("id");
                item.groupName = resultSet.getString("groupName");
                item.mcID = resultSet.getString("mcID");
                item.timeTao = resultSet.getLong("timeTao");
                item.userTao = resultSet.getString("userTao");
                item.timeSua = resultSet.getLong("timeSua");
                item.userSua = resultSet.getString("userSua");
                item.mess = resultSet.getString("mess");
                item.groupLevel = resultSet.getInt("groupLevel");
                if (resultSet.getString("listGr") != null) {
                    item.listGr = new Gson().fromJson(resultSet.getString("listGr"), new TypeToken<ArrayList<GroupObj>>() {
                    }.getType());
                }
                if (resultSet.getString("listPer") != null) {
                    item.listPer = new Gson().fromJson(resultSet.getString("listPer"), new TypeToken<ArrayList<ObjListPer>>() {
                    }.getType());
                }
                arr.add(item);
            }

            Log.logServices("selectTachNguoiVeDiem!" + pstmt);

        } catch (Exception e) {
            Log.logServices("selectTachNguoiVeDiem Exception!" + e.getMessage());
        }
        return arr;
    }

    public static boolean checkLuuIPMayDB(String id) {
        boolean kq = false;
        try {
            String strQuery = "SELECT * FROM dbippc WHERE id = ?";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);
            pstmt.setString(1, id);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                kq = true;
            }

            Log.logServices("checkLuuIPMayDB!" + pstmt);

        } catch (Exception e) {
            Log.logServices("checkLuuIPMayDB Exception!" + e.getMessage());

        }
        return kq;
    }

    public static Boolean luuIPMayDBInsert(ObjectLayIPRequest Obj) {
        String strSqlInsert = "INSERT INTO dbippc" + " (id, ip) VALUES (?, ?)";
        boolean kqI = false;
        try {
            Log.logServices("luuIPMayDBInsert nhay vao day");
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlInsert);
            pstmt.setString(1, Obj.id);
            pstmt.setString(2, Obj.ip);

            int kq = pstmt.executeUpdate();
            Log.logServices("luuIPMayDBInsert strSqlInsert: " + pstmt);
            if (kq > 0) {
                Log.logServices("ROW INSERTED");
                kqI = true;
                Log.logServices("luuIPMayDBInsert thành công: " + Obj.id);
            } else {
                Log.logServices("ROW NOT INSERTED");
                Log.logServices("luuIPMayDBInsert không thành công: " + Obj.id);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Hiển thị hộp thoại thông báo khi bắt được ngoại lệ
            Log.logServices("luuIPMayDBInsert cath: " + e.getMessage());
        } catch (SQLException e) {
            Log.logServices("luuIPMayDBInsert cath: " + e.getMessage());
            e.printStackTrace();
        }
        return kqI;
    }

    public static boolean luuIPMayDBUpdate(ObjectLayIPRequest obj) {
        String strSqlUpdate = "UPDATE dbippc SET ip = ? WHERE id = ? ";

        boolean isSuccess = false;
        try {
            Connection connect = null;
            connect = DbUtil.getConnect(); // Giả sử bạn có phương thức này để lấy kết nối

            PreparedStatement pstmt = connect.prepareStatement(strSqlUpdate);
            pstmt.setString(1, obj.ip);
            pstmt.setString(2, obj.id);

            int result = pstmt.executeUpdate();
            Log.logServices("luuIPMayDBUpdate strSqlUpdate: " + pstmt);

            if (result > 0) {
                Log.logServices("ROW UPDATED");
                isSuccess = true;
                Log.logServices("luuIPMayDBUpdate thành công: ");
            } else {
                Log.logServices("ROW NOT UPDATED");
                Log.logServices("luuIPMayDBUpdate không thành công: ");
            }
        } catch (SQLException e) {
            Log.logServices("luuIPMayDBUpdate catch: " + e.getMessage());
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static ArrayList<ObjectLayIPRequest> luuIPMayDBUpdateSelect() {
        ArrayList<ObjectLayIPRequest> arr = new ArrayList<>();
        try {
            String strQuery = "SELECT * FROM dbippc";
            Connection connect = DbUtil.getConnect();
            PreparedStatement pstmt = connect.prepareStatement(strQuery);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ObjectLayIPRequest item = new ObjectLayIPRequest();
                item.id = resultSet.getString("id");
                item.ip = resultSet.getString("ip");
                item.moTa = resultSet.getString("moTa");
                arr.add(item);
            }

            Log.logServices("checkLuuIPMayDB!" + pstmt);

        } catch (Exception e) {
            Log.logServices("checkLuuIPMayDB Exception!" + e.getMessage());

        }
        return arr;
    }
    private static final int NUMBER_OF_THREADS = 5; // Số lượng thread trong pool
    public static ArrayList<GroupObj> layDSGroupCoFace(String mcIDR) {
        Log.logServices("layDSGroupCoFace Thanh cong" + mcIDR);
        ArrayList<GroupObj> arr = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        String strQueryGroup = "SELECT id, groupName, mcID, timeTao, userTao, timeSua, userSua, mess,listGr, groupLevel FROM qrgroup WHERE mcID = ?;";
        // Câu truy vấn mới chỉ lấy dữ liệu từ bảng personofgroup dựa vào groupID
        String strQueryMembers = "SELECT * FROM personofgroup WHERE groupID = ?;";


        try (Connection connect = DbUtil.getConnect();
             PreparedStatement pstmtGroup = connect.prepareStatement(strQueryGroup)) {

            pstmtGroup.setString(1, mcIDR);
            try (ResultSet rsGroup = pstmtGroup.executeQuery()) {
                List<Future<GroupObj>> futures = new ArrayList<>();

                while (rsGroup.next()) {
                    final String groupId = rsGroup.getString("id");
                    final String groupName = rsGroup.getString("groupName");
                    final String mcID = rsGroup.getString("mcID");
                    final long timeTao = rsGroup.getLong("timeTao");
                    final String userTao = rsGroup.getString("userTao");
                    final long timeSua = rsGroup.getLong("timeSua");
                    final String userSua = rsGroup.getString("userSua");
                    final String mess = rsGroup.getString("mess");
                    final String listGr = rsGroup.getString("listGr");
                    final int groupLevel = rsGroup.getInt("groupLevel");


                    Future<GroupObj> future = executorService.submit(() -> {
                        GroupObj group = new GroupObj();
                        group.id = groupId;
                        group.groupName = groupName;
                        group.mcID = mcID;
                        group.timeTao = timeTao;
                        group.userTao = userTao;
                        group.timeSua = timeSua;
                        group.userSua = userSua;
                        group.mess = mess;
                        if(listGr!=null&&!listGr.equals("")){
                            group.listGr =  new Gson().fromJson(listGr, new TypeToken<ArrayList<GroupObj>>() {
                            }.getType());
                        }
                        group.groupLevel = groupLevel;
                        // Populate group object with data from rsGroup (similar to your existing code)
                        // ...

                        // Process members
                        try (PreparedStatement pstmtMember = connect.prepareStatement(strQueryMembers)) {
                            pstmtMember.setString(1, groupId);
                            try (ResultSet rsMember = pstmtMember.executeQuery()) {
                                ArrayList<ObjListPer> listPer = new ArrayList<>();
                                while (rsMember.next()) {
                                    ObjListPer person = new ObjListPer();
                                    person.id = rsMember.getString("id");
                                    person.chucDanh = rsMember.getString("chucDanh");
                                    person.timeTao = rsMember.getLong("timeTao");
                                    person.idThietBi = rsMember.getString("idThietBi");
                                    person.userTao = rsMember.getString("userTao");
                                    person.personID = rsMember.getString("personID");
                                    person.uID = rsMember.getString("uID");
                                    person.vID = rsMember.getString("vID");
                                    person.name = rsMember.getString("userName");
                                    person.sdt = rsMember.getString("sdt");
                                    person.groupID = rsMember.getString("groupID");
                                    person.perNum = rsMember.getInt("perNum");
                                    listPer.add(person);
                                }
                                group.listPer = listPer;
                            }
                        }
                        return group;
                    });
                    futures.add(future);
                }

                // Collect results
                for (Future<GroupObj> future : futures) {
                    try {
                        arr.add(future.get());
                    } catch (InterruptedException | ExecutionException e) {
                        Log.logServices("layDSGroupCoFace Exception!" + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            Log.logServices("layDSGroupCoFace Exception!" + e.getMessage());
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException ex) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        return arr;
    }



}
