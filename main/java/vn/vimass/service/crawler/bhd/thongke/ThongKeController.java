package vn.vimass.service.crawler.bhd.thongke;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ObjectLichSuRaVaoQuetQr;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.entity.recivephone.ObjectGoupRequest;
import vn.vimass.service.entity.thongKe.ObjectTgDenVe;
import vn.vimass.service.log.Log;
import vn.vimass.service.utils.DbUtil;
import vn.vimass.service.utils.ServivceCommon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static vn.vimass.service.BackUp.BackUpFunCVer2.goiDenMayHai;
import static vn.vimass.service.BackUp.BackUpFunction.May1;
import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;

public class ThongKeController {

    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;
    public static ResponseMessage1 thongKe(int funcId,long currentime, String data, int deviceID) {
        Log.logServices("thongKe data: " + data);
        ResponseMessage1 response = new ResponseMessage1();
        try {
            objThongKe objTk = new Gson().fromJson(data, objThongKe.class);
            String cks = String.valueOf(funcId+ currentime + deviceID);
            Log.logServices("thongKe cks: " + cks);

            String cksService =ServivceCommon.bamMD5(cks);
            Log.logServices("thongKe cksService: " + cksService);
//
//            if(cksService.equals(objTk.cks)){
//                return resArrList(objTk);
//            }else{
//                response = StatusResponse(2);
//                return response;
//            }
            ArrayList<ObjectTgDenVe> arr = getListThoiGianRaVaoTrongNgay(objTk);
            for(ObjectTgDenVe item: arr){
                List<ObjectLichSuRaVaoQuetQr> objHisOnDay = objHisOnDay(item.vID, item.phone, item.thoiGianDen);
                String strOnHisDay = objHisOnDay.toString();
                item.setHisOnDay(strOnHisDay);
            }
            response = StatusResponse(1);
            response.funcId = funcId;
            response.result = Tool.setBase64(arr.toString());
            Log.logServices("result setBase64 ======================>" + Tool.setBase64(arr.toString()));
            response.totalAll = tatol(objTk);
            Log.logServices("result response ======================>" + response.toString());
           return response;

        } catch (Exception ex) {
            Log.logServices("thongKe Exception: " + ex.getMessage());
            response = StatusResponse(3);
        }
        return response;
    }

    private static int tatol(objThongKe objTK){

        int sl = 0;
        String TABLE_NAME = "dblichsuravaoqr";
        String idThietBi = "idThietBi";
        String accName = "accName";
        try {
            String strQuery = "";
            if(!objTK.key.equals("All")) {

                strQuery ="SELECT COUNT(maRaVao) AS Tatol "
                        + " FROM ("
                            + " SELECT maRaVao,vID, loiRa, accName, phone, idThietBi, MIN(thoiGianGhiNhan) AS thoiGianDen, MAX(thoiGianGhiNhan) AS thoiGianVe "
                            + " FROM "+TABLE_NAME
                            + " WHERE "+accName+"= N'"+objTK.key+ "' OR " +idThietBi+"= N'"+objTK.key+ "' OR phone = N'"+objTK.key+"' AND FROM_UNIXTIME(thoiGianGhiNhan/1000) BETWEEN DATE(FROM_UNIXTIME("+(objTK.timeTo + 60*1000)+"/1000)) AND DATE(FROM_UNIXTIME("+objTK.timeTo+"/1000))"
                            + " group by vID,accName, phone, DATE(FROM_UNIXTIME(thoiGianGhiNhan / 1000))"
                            + " ORDER BY DATE(FROM_UNIXTIME(thoiGianGhiNhan/1000))"
                        + ") AS subquery";
            }else {
                strQuery ="SELECT COUNT(maRaVao) AS Tatol "
                        + " FROM ("
                            + " SELECT maRaVao, vID, loiRa, phone,idThietBi, accName, MIN(thoiGianGhiNhan) AS thoiGianDen, MAX(thoiGianGhiNhan) AS thoiGianVe "
                            + " FROM "+TABLE_NAME
                            + " WHERE FROM_UNIXTIME(thoiGianGhiNhan/1000) BETWEEN DATE(FROM_UNIXTIME("+objTK.timeFrom+"/1000)) AND DATE(FROM_UNIXTIME("+(objTK.timeTo + 60*1000)+"/1000))"
                            + " group by vID,accName, phone, DATE(FROM_UNIXTIME(thoiGianGhiNhan / 1000))"
                            + " ORDER BY DATE(FROM_UNIXTIME(thoiGianGhiNhan/1000))"
                        + ") AS subquery";
            }

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            Log.logServices("strQuery getListThoiGianRaVaoTrongNgay tatol: " + pstmt);

            while (resultSet.next()) {

                sl = Integer.valueOf(resultSet.getString("Tatol"));
                return sl;
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getListThoiGianRaVaoTrongNgay tatol exception: "  +e.getMessage());
        }

        return sl;
    }
    private static ArrayList<ObjectTgDenVe> getListThoiGianRaVaoTrongNgay(objThongKe objTK){

        ArrayList<ObjectTgDenVe> arrList = new ArrayList<ObjectTgDenVe>();
        String TABLE_NAME = "dblichsuravaoqr";
        String idThietBi = "idThietBi";
        String accName = "accName";

        ObjectTgDenVe item = new ObjectTgDenVe();

        try {
            String strQuery = "";
            if(!objTK.key.equals("All")) {

                strQuery ="SELECT maRaVao,vID, loiRa, accName, phone, typeXacThuc, idThietBi, MIN(thoiGianGhiNhan) AS thoiGianDen, MAX(thoiGianGhiNhan) AS thoiGianVe "
                        + " FROM "+TABLE_NAME
                        + " WHERE "+accName+"= N'"+objTK.key+ "' OR " +idThietBi+"= N'"+objTK.key+ "' OR phone = N'"+objTK.key+"' AND FROM_UNIXTIME(thoiGianGhiNhan/1000) BETWEEN DATE(FROM_UNIXTIME("+objTK.timeFrom+"/1000)) AND DATE(FROM_UNIXTIME("+(objTK.timeTo + 60*1000)+"/1000))"
                        + " group by vID,accName, phone, DATE(FROM_UNIXTIME(thoiGianGhiNhan / 1000))"
                        + " ORDER BY DATE(FROM_UNIXTIME(thoiGianGhiNhan/1000)) DESC limit "+objTK.limit+" offset "+objTK.offset+";";
            }else {
                strQuery ="SELECT maRaVao, vID, loiRa, phone,typeXacThuc,idThietBi, accName, MIN(thoiGianGhiNhan) AS thoiGianDen, MAX(thoiGianGhiNhan) AS thoiGianVe "
                        + " FROM "+TABLE_NAME
                        + " WHERE FROM_UNIXTIME(thoiGianGhiNhan/1000) BETWEEN DATE(FROM_UNIXTIME("+objTK.timeFrom+"/1000)) AND DATE(FROM_UNIXTIME("+(objTK.timeTo + 60*1000)+"/1000))"
                        + " group by vID,accName, phone, DATE(FROM_UNIXTIME(thoiGianGhiNhan / 1000))"
                        + " ORDER BY DATE(FROM_UNIXTIME(thoiGianGhiNhan/1000)) DESC limit "+objTK.limit+" offset "+objTK.offset+";";
            }

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            Log.logServices("strQuery getListThoiGianRaVaoTrongNgay: " + pstmt);

            while (resultSet.next()) {
                item = getValueObjTgDenVe(resultSet);
                Log.logServices("arrHisOnDay item: " +item.toString());
                Log.logServices("arrHisOnDay item: " +item.toString());
                arrList.add(item);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getListThoiGianRaVaoTrongNgay exception: "  +e.getMessage());
        }

        return arrList;
    }

    private static List<ObjectLichSuRaVaoQuetQr> objHisOnDay(String viD, String phone, long timeDay) {

        String TABLE_NAME = "dblichsuravaoqr";
        String idThietBi = "idThietBi";
        String accName = "accName";

        List<ObjectLichSuRaVaoQuetQr> arrHisOnDay = new ArrayList<>();
        ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();
        try {

            String strQuery = "";
            if(viD.length() > 0){
                strQuery ="SELECT *FROM "+TABLE_NAME
                        + " WHERE vID = N'"+ viD+ "'AND DATE(FROM_UNIXTIME(thoiGianGhiNhan/1000)) = DATE(FROM_UNIXTIME("+timeDay+"/1000));";
            }else{
                strQuery ="SELECT *FROM "+TABLE_NAME
                        + " WHERE phone = N'"+ phone+ "' AND DATE(FROM_UNIXTIME(thoiGianGhiNhan/1000)) = DATE(FROM_UNIXTIME("+timeDay+"/1000));";
            }

            connect = DbUtil.getConnect();
            // Log.logServices("strQuery thoi gian lam viec trong ngay: " + strQuery);

            Log.logServices("objHisOnDay strQuery: "+strQuery);
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                item = getValue(resultSet);
                arrHisOnDay.add(item);

            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("Exception objHisOnDay: "  +e.getMessage());
        }
        return arrHisOnDay;
    }


    private static ObjectLichSuRaVaoQuetQr getValue(ResultSet resultSet) {

        ObjectLichSuRaVaoQuetQr item = new ObjectLichSuRaVaoQuetQr();

        try
        {
            item.maRaVao = resultSet.getString("maRaVao");
            item.vID = resultSet.getString("vID");
            item.phone = resultSet.getString("phone");
            item.typeXacThuc = Integer.valueOf(resultSet.getString("typeXacThuc"));
            item.accName = resultSet.getString("accName");
            item.loiRa = resultSet.getString("loiRa");
            item.idThietBi = resultSet.getString("idThietBi");
            item.diaChi = resultSet.getString("diaChi");
            item.phiRaVao = resultSet.getInt("phiRaVao");
            item.thoiGianGhiNhan = resultSet.getLong("thoiGianGhiNhan");
            //	item.result = resultSet.getString("result");
            item.personNumber = resultSet.getInt("personNumber");

        }
        catch(Exception e)
        {
            Log.logServices("Exception ObjectLichSuRaVaoQuetQr getValue: " + e.getMessage());
        }

        return item;
    }

    private static ObjectTgDenVe getValueObjTgDenVe(ResultSet resultSet) {

        ObjectTgDenVe item = new ObjectTgDenVe();

        try
        {
            item.maGD = resultSet.getString("maRaVao");
            item.vID = resultSet.getString("vID");
            item.loiRa = resultSet.getString("loiRa");
            item.typeXacThuc = Integer.valueOf(resultSet.getString("typeXacThuc"));
            item.phone = resultSet.getString("phone");

            item.accName = resultSet.getString("accName");

            long timeStart = resultSet.getLong("thoiGianDen");
            item.thoiGianDen = timeStart;
            long timeEnd = resultSet.getLong("thoiGianVe");

            if(timeStart == timeEnd) {
                item.thoiGianVe = 0;
            }else {
                item.thoiGianVe = timeEnd;
            }

        //   List<ObjectLichSuRaVaoQuetQr> arrHisOnDay = objHisOnDay(item.vID, item.phone, timeStart);

         //  item.hisOnDay = arrHisOnDay.toString();
           item.hisOnDay = "null";
        }
        catch(Exception e)
        {
            Log.logServices("Exception getValueObjTgDenVe : " + e.getMessage());
        }

        return item;
    }

}





