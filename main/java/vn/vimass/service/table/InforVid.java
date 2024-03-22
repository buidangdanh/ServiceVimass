package vn.vimass.service.table;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.vimass.service.BackUp.FingerPrint.Obj.FingerData;
import vn.vimass.service.BackUp.FingerPrint.Obj.ObjFP;
import vn.vimass.service.crawler.bhd.XuLyLayKhuonMat.ObjecFace;
import vn.vimass.service.crawler.bhd.XuLyLayKhuonMat.ObjectFaceOfVid;
import vn.vimass.service.entity.ObjectRecordsAndTotalObjectInfoVid;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.GroupOfQR;
import vn.vimass.service.table.NhomThietBiDiem.Nhom;
import vn.vimass.service.table.NhomThietBiDiem.PersonOfGroup;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjGroup;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjectCheckTypeGr;
import vn.vimass.service.table.object.ObjecDiaDiemXacThucThanhCong;
import vn.vimass.service.table.object.ObjectInfoVid;
import vn.vimass.service.table.object.ObjectIpAddress;
import vn.vimass.service.utils.DbUtil;
import vn.vimass.service.utils.VimassData;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InforVid {

    public static final String TABLE_NAME="info_vid";

    public static final String id ="id";
    public static final String idVid = "idVid";
    public static final String uID = "uID";
    public static final String maSoThue ="maSoThue";
    public static final String diaChi = "diaChi";
    public static final String dienThoai ="dienThoai";
    public static final String email = "email";
    public static final String gioiTinh="gioiTinh";
    public static final String hoTen="hoTen";
    public static final String ngayCapCCCD="ngayCapCCCD";
    public static final String ngaySinh ="ngaySinh";
    public static final String quocTich ="quocTich";
    public static final String soCanCuoc="soCanCuoc";
    public static final String soTheBHYT ="soTheBHYT";
    public static final String tk="TK";
    public static final String anhDaiDien="anhDaiDien";
    public static final String anhCMNDMatTruoc="anhCMNDMatTruoc";
    public static final String anhCMNDMatSau="anhCMNDMatSau";
    public static final String faceData="faceData";
    public static final String personName="personName";
    public static final String chucDanh="chucDanh";
    public static final String personPosition="personPosition";
    public static final String fingerData="fingerData";
    public static final String mcID="mcID";

    public static PreparedStatement pstmt = null;
    public static Connection connect = null;
    public static ResultSet resultSet = null;

    public static boolean full = true;
    public static void InsertData(ObjectInfoVid item) {
        try {
            String strSqlInsert = "INSERT INTO " + TABLE_NAME + " ("
                    + id+ ","
                    + idVid + ","
                    + uID + ","
                    + maSoThue+ ","
                    + diaChi+ ","
                    + dienThoai+ ","
                    + email+ ","
                    + gioiTinh+ ","
                    + hoTen+ ","
                    + ngayCapCCCD+ ","
                    + ngaySinh+ ","
                    + quocTich+ ","
                    + soCanCuoc+ ","
                    + soTheBHYT+ ","
                    + tk+ ","
                    + anhDaiDien+ ","
                    + anhCMNDMatTruoc+ ","
                    + anhCMNDMatSau+ ","
                    + faceData+ ","
                    + personName+ ","
                    + chucDanh+ ","
                    + personPosition +","
                    + mcID
                    + " ) VALUES ("
                    + "N'" + item.id +"',"
                    + "N'" + item.idVid + "',"
                    + "N'" + item.uID.trim() + "',"
                    + "N'" + item.maSoThue.trim() + "',"
                    + "N'" + item.diaChi.trim() + "',"
                    + "N'" + item.dienThoai.trim()+ "',"
                    + "N'" + item.email.trim() + "',"
                    + "N'" + item.gioiTinh.trim()+ "',"
                    + "N'" + item.hoTen.trim() + "',"
                    + "N'" + item.ngayCapCCCD.trim() + "',"
                    + "N'" + item.ngaySinh.trim()+ "',"
                    + "N'" + item.quocTich.trim() + "',"
                    + "N'" + item.soCanCuoc.trim() + "',"
                    + "N'" + item.soTheBHYT.trim() + "',"
                    + "N'" + item.tk + "',"
                    + "N'" + item.anhDaiDien.trim() + "',"
                    + "N'" + item.anhCMNDMatTruoc.trim() + "',"
                    + "N'" + item.anhCMNDMatSau.trim() + "',"
                    + "N'" + item.faceData+ "',"
                    + "N'" + item.personName + "',"
                    + "N'" + item.chucDanh + "',"
                    +  item.personPosition+","
                    + "N'" + item.mcID + "'"
                    + ");";

            System.out.println("query InsertData ObjectInfoVid: " + strSqlInsert);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);
            System.out.println("executeUpdate1 InsertData ObjectInfoVid: " + pstmt);

            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                VimassData.ContentResult ="inserted successfully";
                VimassData.typeResult = 1;
                System.out.println(" ObjectInfoVid inserted successfully");

            } else {
                VimassData.ContentResult ="inserted fail";
                VimassData.typeResult = 0;
                System.out.println("ObjectInfoVid inserted fail");
            }
        } catch (Exception e) {
            System.out.println(" InsertData ObjectInfoVid Exception: " + e.getMessage());
        }
    }

    public static void updateData(String idvID, int pos){

        try {
            String strSqlInsert = "UPDATE " + TABLE_NAME + " SET "
                    + faceData + " = '"+faceData+ "', "
                    + pos + " = '"+pos+ "' "
                    + " WHERE idVid = N'" + idvID + "' AND personPosition = '" + pos +"';";

            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strSqlInsert);

            Log.logServices(" strSql updateData info_Vid:" + strSqlInsert);
            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                Log.logServices(" ROW updateData info_Vid successfully");

                VimassData.ContentResult = "updateData info_Vid successfully";
            } else {
                Log.logServices("ROW updateData info_Vid fail");
                VimassData.ContentResult = "updateData info_Vid fail";
            }

        } catch (Exception e) {
            Log.logServices("Exception info_Vid: " + e.getMessage());
        }

    }

    public static void delete(String key, String perName) {

        try {
            String strQuery = "DELETE FROM " + TABLE_NAME + " WHERE "+idVid+ " = '" +key+"' AND  "+personName+ " ='"+perName+"';";
            Log.logServices("query delete ObjectInfoVid: " + strQuery);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            Log.logServices("executeUpdate1 delete ObjectInfoVid: " + pstmt);

            int kq = pstmt.executeUpdate();
            if (kq > 0) {
                VimassData.ContentResult = "delete successfully";
                Log.logServices("delete successfully");
            } else {
                Log.logServices("delete fail");
                VimassData.ContentResult = "delete fail";
            }
        } catch (Exception e) {
            Log.logServices(" delete ObjectInfoVid Exception: " + e.getMessage());
        }
    }

    public static ObjectInfoVid getInfoVid(String key) {


        ObjectInfoVid item = new ObjectInfoVid();
        try {
            String strQuery = "SELECT *FROM " + TABLE_NAME + " WHERE " +id+" ='"+key+"' OR "+idVid+" = N'"+key+"' OR "+dienThoai+" = N'"+key+"';";

            connect = DbUtil.getConnect();

            Log.logServices("query getInfoVid: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                item = getValue(resultSet,true);
            }

            Log.logServices("getInfoVid item: " + item);
        } catch (Exception e) {
            Log.logServices("getInfoVid Exception: " + e.getMessage());
        }
        return item;
    }

    public static boolean checkExist(String vID, String perName) {
        boolean exist = false;

        try {

            String strQuery = "SELECT * FROM "+TABLE_NAME+" WHERE " +idVid+ " ='"+vID+"' AND " +personName+ "=N'"+perName+"';";

            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();

            System.out.println("strQuery checkExist: " + strQuery);
            while (resultSet.next()) {
                return true;
            }

        }catch (Exception e) {
            System.out.println("Exception checkNameExist: " + e.getMessage());
            // TODO: handle exception
        }

        return false;

    }


    public static ArrayList<ObjectInfoVid> getlistDataFaceofVid(String key){
        ObjectInfoVid objeItem = null;
        ArrayList<ObjectInfoVid> list = new ArrayList<ObjectInfoVid>();
        try {

            String strQuery = "SELECT * FROM "+TABLE_NAME+" where idVid ='"+key+"' And faceData <> '' order by "+personPosition+" asc";

            connect = DbUtil.getConnect();
            pstmt = (PreparedStatement) connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                objeItem  = getValue(resultSet, true);
                list.add(objeItem);
            }

        }catch (Exception e) {
            System.out.println("Exception checkNameExist: " + e.getMessage());
            // TODO: handle exception
        }

        return list;
    }


    public static ObjectInfoVid getThongTinChuThe1(String keySearch) {

        ObjectInfoVid objeItem = new ObjectInfoVid();
        try {

            // check data thong tin chủ thẻ
            String strQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + idVid + " = '" + keySearch + "' OR " + uID + " ='" +keySearch+ "' OR " + dienThoai + " ='" +keySearch
                    +"' OR " + dienThoai +" = '" + keySearch+"'; " ;
            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strQuery);

            resultSet = pstmt.executeQuery();

            Log.logServices("strQuery getThongTinChuThe: " + pstmt);

            while (resultSet.next()) {

                objeItem = getValue(resultSet, full);

            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getThongTinChuThe exception: " + e.getMessage());
        }

        return objeItem;
    }

    public static ObjectInfoVid getThongTinChuThe(String keySearch, int position) {

        ObjectInfoVid objeItem = new ObjectInfoVid();
        try {

            // check data thong tin chủ thẻ
            String strQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + idVid + " = '" + keySearch + "' AND " +personPosition +" = "+position+";";
            connect = DbUtil.getConnect();

            pstmt = connect.prepareStatement(strQuery);

            resultSet = pstmt.executeQuery();

            Log.logServices("strQuery getThongTinChuThe: " + pstmt);

            while (resultSet.next()) {

                objeItem = getValue(resultSet, full);

            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.logServices("getThongTinChuThe exception: " + e.getMessage());
        }

        return objeItem;
    }

    public static ObjectInfoVid getValue(ResultSet resultSet, boolean full) {
        ObjectInfoVid item = new ObjectInfoVid();
        try
        {
            item.id = resultSet.getString(id);
            item.idVid = resultSet.getString(idVid);
            item.uID = resultSet.getString(uID);
            item.maSoThue = resultSet.getString(maSoThue);
            item.diaChi = resultSet.getString(diaChi);
            item.dienThoai = resultSet.getString(dienThoai);
            item.email = resultSet.getString(email);
            item.gioiTinh = resultSet.getString(gioiTinh);
            item.hoTen = resultSet.getString(hoTen);
            item.ngayCapCCCD = resultSet.getString(ngayCapCCCD);
            item.ngaySinh = resultSet.getString(ngaySinh);
            item.quocTich = resultSet.getString(quocTich);
            item.soCanCuoc = resultSet.getString(soCanCuoc);
            item.soTheBHYT = resultSet.getString(soTheBHYT);
            item.tk = resultSet.getString(tk);
            item.anhDaiDien = resultSet.getString(anhDaiDien);
            item.anhCMNDMatTruoc = resultSet.getString(anhCMNDMatTruoc);
            item.anhCMNDMatSau = resultSet.getString(anhCMNDMatSau);
            item.faceData = resultSet.getString(faceData);
            item.personName = resultSet.getString(personName);
            item.chucDanh = resultSet.getString(chucDanh);
            item.personPosition = resultSet.getInt(personPosition);
            item.fingerData = new Gson().fromJson(resultSet.getString(fingerData), new TypeToken<ArrayList<FingerData>>() {
            }.getType()); ;
//            item.idQR = resultSet.getString("idQR");
//            item.groupID = resultSet.getString("groupID");
        }
        catch(Exception e)
        {
            Log.logServices("getValue InfoVid Exception: " + e.getMessage());
        }
        return item;
    }


    public static ArrayList<ObjecFace> getListObjecFace() {
        System.out.println("strQuery getListObjecFace: " + 1);
        ArrayList<ObjecFace> arrayList = new ArrayList<>();
        System.out.println("strQuery getListObjecFace: " + 2);
        ObjecFace item = null;
        try {
            String strQuery = "SELECT tb1.id, tb1.chucDanh, tb1.dienThoai, tb1.uID, tb1.idVid, tb1.faceData,tb1.personName, tb1.personPosition " +
                    "FROM "+TABLE_NAME+" AS tb1 WHERE  tb1.faceData <> '' group by tb1.id";
            System.out.println("strQuery getListObjecFace: 3 ==>" + strQuery);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                item = getValueObjectFace(resultSet);
                System.out.println("getListObjecFace item: " + item);
                arrayList.add(item);
            }
            System.out.println("getListObjecFace ArrayList: " + arrayList);

        } catch (Exception e) {
            Log.logServices("getListObjecFace Exception: " + e.getMessage());
        }
        return arrayList;
    }

    public static ArrayList<ObjectFaceOfVid> getListObjecFaceV1(int limit, int offset, String idQR) {
        String strQuery = "";

        ArrayList<ObjectFaceOfVid> arrayList = new ArrayList<>();
        ObjectFaceOfVid item = null;
        try {
            String condition = getCondition(idQR);
            if(limit > 0) {
                strQuery += "SELECT tb3.id,tb3.hoTen, tb1.chucDanh, tb3.uID, tb3.vID, tb3.personPosition,tb1.userName,tb3.faceData, tb1.groupID\n" +
                        "FROM " + PersonOfGroup.TABLE_NAME + " AS tb1\n" +
                        "INNER JOIN " + TABLE_NAME + " AS tb3 ON tb1.personName = tb3.personName\n" +
                        "WHERE (" + condition + ")" +
                        " GROUP BY tb1.id limit " + limit + " offset " + offset + ";";
            }else if(limit == 0 && offset == 0){
                strQuery += "SELECT tb3.id,tb3.hoTen, tb1.chucDanh, tb3.uID, tb3.vID, tb3.personPosition,tb1.userName,tb3.faceData, tb1.groupID\n" +
                        "FROM "+PersonOfGroup.TABLE_NAME+" AS tb1\n" +
                        "INNER JOIN "+TABLE_NAME+" AS tb3 ON tb1.personName = tb3.personName\n" +
                        "WHERE ("+condition+")"+
                        " GROUP BY tb1.id;";
            }

          //  System.out.println("strQuery getListObjecFace v1:  ==>" + strQuery);
            Log.logServices("strQuery getListObjecFace v1:  ==>" + strQuery);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                item = getValueObjectFaceV1(resultSet, idQR);
             //   System.out.println("getListObjecFace itemv1: " + item);
              //  Log.logServices("getListObjecFace itemv1: " + item);
                arrayList.add(item);
            }
           // System.out.println("getListObjecFace ArrayList v1: " + arrayList);
            Log.logServices("strQuery getListObjecFace v1:  ==>" + strQuery);

        } catch (Exception e) {
            Log.logServices("getListObjecFace Exception v1: " + e.getMessage());
        }
        return arrayList;
    }


    public static ArrayList<ObjectFaceOfVid> getListObjecFaceV2(String idQR) {
        String strQuery = "";

        ArrayList<ObjectFaceOfVid> arrayList = new ArrayList<>();
        ObjectFaceOfVid item = null;
        try {
            String condition = getCondition(idQR);
            strQuery += "SELECT tb3.id,tb3.hoTen, tb1.chucDanh, tb3.uID, tb3.idVid, tb3.personPosition,tb1.userName,tb3.faceData, tb1.groupID\n" +
                    "FROM "+PersonOfGroup.TABLE_NAME+" AS tb1\n" +
                    "INNER JOIN "+TABLE_NAME+" AS tb3 ON tb1.personName = tb3.personName\n" +
                    "WHERE "+condition+
                    " GROUP BY tb1.id DESC;";

            System.out.println("strQuery getListObjecFace v2:  ==>" + strQuery);
            Log.logServices("strQuery getListObjecFace v12:  ==>" + strQuery);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                item = getValueObjectFaceV1(resultSet, idQR);
                //System.out.println("getListObjecFace itemv2: " + item);
                Log.logServices("getListObjecFace itemv2: " + item);
                arrayList.add(item);
            }
         //   System.out.println("getListObjecFace ArrayList v2: " + arrayList);
            Log.logServices("strQuery getListObjecFace v2:  ==>" + strQuery);

        } catch (Exception e) {
            Log.logServices("getListObjecFace Exception v2: " + e.getMessage());
        }
        return arrayList;
    }

    private static String getCondition(String idQR){
        String condition = "";
        ArrayList<String> arrStr = new ArrayList<String>();
        ArrayList<ObjectCheckTypeGr> arrObjectCheckTypeGr = Nhom.getListObjectCheckTypeGr(idQR);
        System.out.println("getObjectCheckTypeGr ==>" + arrObjectCheckTypeGr);
        Log.logServices("getObjectCheckTypeGr ==>" + arrObjectCheckTypeGr);
        if(arrObjectCheckTypeGr != null){
            for(ObjectCheckTypeGr itemGrOfQr: arrObjectCheckTypeGr){

                if(itemGrOfQr.groupLevel == 2){
                    Type collectionTypeGr = new TypeToken<List<ObjGroup>>() {
                    }.getType();
                    ArrayList<ObjGroup> arrListGr = new Gson().fromJson(itemGrOfQr.listGr, collectionTypeGr);

                    for(ObjGroup itemGr: arrListGr){
                        if(arrStr.isEmpty()) {
                            condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND  tb3.faceData <> '') OR ";
                            arrStr.add(itemGrOfQr.groupID);
                        }else{
                            if(!arrStr.contains(itemGrOfQr.groupID)){
                                condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND  tb3.faceData <> '') OR ";
                                arrStr.add(itemGrOfQr.groupID);
                            }
                        }
                    }
                }
                if(itemGrOfQr.groupLevel == 1){
                    if(arrStr.isEmpty()) {
                        condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND  tb3.faceData <> '') OR ";
                        arrStr.add(itemGrOfQr.groupID);
                    }else {
                        if(!arrStr.contains(itemGrOfQr.groupID)){
                            condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND  tb3.faceData <> '') OR ";
                            arrStr.add(itemGrOfQr.groupID);
                        }
                    }
                }

            }
        }
        return condition.substring(0, condition.length() - 3);

    }

    public static int tatol(String idQR){
        int count = 0;
        try {
            String strQuery = "";
            String condition = getConditionTatol(idQR);
            strQuery += "SELECT count(*) as tatol\n" +
                    "FROM (SELECT tb3.id,tb3.hoTen, tb1.chucDanh, tb3.uID, tb3.idVid, tb3.personPosition,tb1.userName,tb3.faceData, tb1.groupID\n" +
                    "FROM "+ PersonOfGroup.TABLE_NAME+" AS tb1\n" +
                    "INNER JOIN "+TABLE_NAME+" AS tb3 ON tb1.personName = tb3.personName\n" +
                    "WHERE "+condition+ " GROUP BY tb1.id) AS subquery;";
            Log.logServices("tatol strQuery: " + strQuery);
            connect = DbUtil.getConnect();
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("tatol");
            }

        } catch (Exception e) {
            Log.logServices("tatol Exception v1: " + e.getMessage());
        }
        return count;

    }

    private static String getConditionTatol(String idQR){
        String condition = "";
        ArrayList<String> arrStr = new ArrayList<String>();
        ArrayList<ObjectCheckTypeGr> arrObjectCheckTypeGr = Nhom.getListObjectCheckTypeGr(idQR);
        System.out.println("getObjectCheckTypeGr ==>" + arrObjectCheckTypeGr);
        Log.logServices("getObjectCheckTypeGr ==>" + arrObjectCheckTypeGr);
        if(arrObjectCheckTypeGr != null){
            for(ObjectCheckTypeGr itemGrOfQr: arrObjectCheckTypeGr){

                if(itemGrOfQr.groupLevel == 2){
                    Type collectionTypeGr = new TypeToken<List<ObjGroup>>() {
                    }.getType();
                    ArrayList<ObjGroup> arrListGr = new Gson().fromJson(itemGrOfQr.listGr, collectionTypeGr);

                    for(ObjGroup itemGr: arrListGr){
                        if(arrStr.isEmpty()) {
                            condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND tb3.faceData <> '') OR ";
                            arrStr.add(itemGrOfQr.groupID);
                        }else{
                            if(!arrStr.contains(itemGrOfQr.groupID)){
                                condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND tb3.faceData <> '') OR ";
                                arrStr.add(itemGrOfQr.groupID);
                            }
                        }
                    }
                }
                if(itemGrOfQr.groupLevel == 1){
                    if(arrStr.isEmpty()) {
                        condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND tb3.faceData <> '') OR ";
                        arrStr.add(itemGrOfQr.groupID);
                    }else {
                        if(!arrStr.contains(itemGrOfQr.groupID)){
                            condition += "(tb1.groupID = '" + itemGrOfQr.groupID + "' AND tb3.faceData <> '') OR ";
                            arrStr.add(itemGrOfQr.groupID);
                        }
                    }
                }

            }
        }
        return condition.substring(0, condition.length() - 3);

    }

    public static ArrayList<ObjectInfoVid> getListObjecFaceLimitOffset(int limit, int offset) {

        ArrayList<ObjectInfoVid> arrayList = new ArrayList<ObjectInfoVid>();
        ObjectInfoVid item = null;
        try {
            String strQuery = "SELECT *FROM "+TABLE_NAME+" AS tb1 WHERE tb1.faceData <> '' group by tb1.id LIMIT "+limit+" OFFSET "+offset+";";

            connect = DbUtil.getConnect();
            System.out.println("strQuery getListObjecFaceLimitOffset: " + strQuery);
            Log.logServices("strQuery getListObjecFaceLimitOffset: " + strQuery);

            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                item = getValue(resultSet, true);
                item.setFaceData("");
                arrayList.add(item);
            }
            System.out.println("getListObjecFaceLimitOffset ArrayList: " + arrayList.size());
            Log.logServices("getListObjecFaceLimitOffset ArrayList: " + arrayList.size());

        } catch (Exception e) {
            System.out.println("getListObjecFaceLimitOffset Exception: " + e.getMessage());

            Log.logServices("getListObjecFaceLimitOffset Exception: " + e.getMessage());
        }
        return arrayList;
    }

    public static int TatolListFace(){
        int count = 0;
        try {
            String strQuery = "SELECT COUNT(*) as tatol FROM "+TABLE_NAME+" WHERE faceData <> '' group by id";
            connect = DbUtil.getConnect();
            Log.logServices("strQuery TatolListFace: " + strQuery);
            pstmt = connect.prepareStatement(strQuery);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("tatol");
            }

        } catch (Exception e) {
            Log.logServices("getListObjecFace Exception: " + e.getMessage());
        }
        return count;
    }

    private static ObjectFaceOfVid getValueObjectFaceV1(ResultSet resultSet, String idQR){
        ObjectFaceOfVid obj = null;
        try{
            obj = new ObjectFaceOfVid();
            obj.id = resultSet.getString("id");
            obj.hoTen = resultSet.getString("hoTen");
            obj.chucDanh = resultSet.getString("chucDanh");
            obj.idVid = resultSet.getString("idVid");
            obj.uID = resultSet.getString("uID");
            obj.personPosition = resultSet.getInt("personPosition");
            obj.personName = resultSet.getString("userName");
            obj.faceData = resultSet.getString("faceData");
            obj.groupID = idQR;
        }catch (Exception e){
            Log.logServices("Exception getValueObjectFace: " + e.getMessage());

        }
        return obj;
    }
    private static ObjecFace getValueObjectFace(ResultSet resultSet){
        ObjecFace obj = null;
        try{
            obj = new ObjecFace();
            obj.id = resultSet.getString("id");
            obj.chucDanh = resultSet.getString("chucDanh");
            obj.dienThoai = resultSet.getString("dienThoai");
            obj.vID = resultSet.getString("idVid");
            obj.uID = resultSet.getString("uID");
            obj.faceData = resultSet.getString("faceData");
            obj.personPosition = resultSet.getInt("personPosition");
            obj.personName = resultSet.getString("personName");

        }catch (Exception e){
            Log.logServices("Exception getValueObjectFace: " + e.getMessage());

        }
        return obj;
    }

}
