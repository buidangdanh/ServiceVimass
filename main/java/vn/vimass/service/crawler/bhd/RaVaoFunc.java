package vn.vimass.service.crawler.bhd;

import java.util.*;

import com.google.gson.Gson;

import vn.vimass.service.entity.*;
import vn.vimass.service.log.Log;
import vn.vimass.service.log.ServicesData;
import vn.vimass.service.table.IPaddress;
import vn.vimass.service.table.object.*;
import vn.vimass.service.utils.ServivceCommon;

import static vn.vimass.service.crawler.bhd.Data.arrayListStrBackUp;

public class RaVaoFunc {
    public static boolean status = false;

    // get du lieu back up
    public static ResponseMessage getDataBackUp(){
        Log.logServices("input arrayListStrBackUp: " + arrayListStrBackUp);
        ResponseMessage obj = new ResponseMessage();
        if(arrayListStrBackUp != null){
            obj.msgContent ="Thành công";
            obj.msgCode =1;
            obj.result = arrayListStrBackUp.toString();
            Log.logServices("arrayListStrBackUp: " + obj);
        }else{
            obj.msgContent ="Thành công";
            obj.msgCode =1;
            obj.result = "null";

        }

        return obj;
    }

    // post du lieu back up
    private static final ArrayList<String> listCheck =  new ArrayList<>();

    public static ResponseMessage taoMoiDuLieuBackUp(String input){

        ResponseMessage res = new ResponseMessage();
        try{
            Log.logServices("taoMoiDuLieuBackUp input: " + input);

            ObjectBackUp itemBackUp = new Gson().fromJson(input, ObjectBackUp.class);

            if( arrayListStrBackUp == null){
                    arrayListStrBackUp = new ArrayList<>();
                }

            if (listCheck.size() == 0) {
                listCheck.add(itemBackUp.maGD);
                arrayListStrBackUp.add(itemBackUp);
            } else {
                //Log.logServices("them tiep");
                for (int j = 0; j < listCheck.size(); j++) {
                    if (!listCheck.contains(itemBackUp.maGD)) {
                        listCheck.add(itemBackUp.maGD);
                        arrayListStrBackUp.add(itemBackUp);
                    }
                }
            }

                res.msgContent ="Thành công";
                res.msgCode =1;
                res.result =  itemBackUp.toString();
//              Log.logServices(" Data.res: " + res.toString()) ;

        }catch (Exception e){
            Log.logServices("Exception taoMoiDuLieuBackUp item: " + e.getMessage());
        }

        return res;
    }

    // xoa phan tu khi da duoc back up sang
    public static ResponseMessage xoaDuLieuBackUp(String key){

        ResponseMessage res = new ResponseMessage();
        try{
            Log.logServices("input xoaDuLieuBackUp key: " +key );
            Log.logServices("Befor ArrayList xoaDuLieuBackUp item: " +arrayListStrBackUp );

            Iterator<ObjectBackUp> iterator = arrayListStrBackUp.iterator();
            while (iterator.hasNext()) {
                ObjectBackUp item = iterator.next();
                if (item.maGD.equals(key)) {
//                    int position = arrayListStrBackUp.indexOf(item);
                    iterator.remove();  // Safely remove the current item
                    res.msgContent ="Thành công";
                    res.msgCode =1;
                    res.result =  item.toString();

                }
            }

            Log.logServices("After ArrayList xoaDuLieuBackUp item: " +arrayListStrBackUp );

        }catch (Exception e){
            Log.logServices("Exception xoaDuLieuBackUp item: " + e.getMessage());
        }

        return res;
    }

    public static void test() {

    }
}
