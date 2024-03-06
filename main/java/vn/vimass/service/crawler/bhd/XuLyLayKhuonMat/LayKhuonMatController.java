package vn.vimass.service.crawler.bhd.XuLyLayKhuonMat;

import com.google.gson.Gson;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ObjectGetInfoVID;
import vn.vimass.service.entity.ObjectOffsetLimit;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.InforVid;
import vn.vimass.service.table.NhomThietBiDiem.PersonOfGroup;
import vn.vimass.service.table.object.ObjectInfoVid;

import java.util.ArrayList;

import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;
import static vn.vimass.service.BackUp.BackUpRoutes.getInfoVIDFunc;

public class LayKhuonMatController {
       public static ResponseMessage1 resFaceDataOfQR(int funcId,String data){
           Log.logServices("resFaceDataOfQR ===================== > 1");
           ResponseMessage1 response = null;
           ArrayList<ObjectFaceOfVid> listFaceOfVid = new ArrayList<>();
           System.out.println("Test ===================== > OK");
           Log.logServices("Test ===================== > OK");
           try{
               ObjectOffsetLimit objInput = new Gson().fromJson(data, ObjectOffsetLimit.class);

               listFaceOfVid = InforVid.getListObjecFaceV1(objInput.limit, objInput.offset, objInput.idQRgreat);
               System.out.println("listFaceOfVid.size() 2=======> " +listFaceOfVid.size());
               Log.logServices("listFaceOfVid.size() 2=======> " +listFaceOfVid.size());
               response = new ResponseMessage1();
               response = StatusResponse(1);
               response.funcId = funcId;
               response.result = Tool.setBase64(listFaceOfVid.toString());
               response.totalAll = InforVid.tatol(objInput.idQRgreat);

               return response;

           }catch (Exception e){
               System.out.println("Exception resFaceDataOfQR: " +e.getMessage());
               Log.logServices("Exception resFaceDataOfQR: " +e.getMessage());
           }

           return response;
       }

       private static ObjectFaceOfVid getValue(ObjecPerOfGr objecPerOfGr, ObjecFace objecFace){
           ObjectFaceOfVid obj = new ObjectFaceOfVid();
           obj.id= objecFace.id;
           obj.hoTen= objecPerOfGr.userName;
           obj.chucDanh= objecPerOfGr.chucDanh;
           obj.idVid= objecFace.vID;
           obj.uID= objecFace.uID;
           obj.personPosition= objecFace.personPosition;
           obj.faceData= objecFace.faceData;
           obj.groupID= objecPerOfGr.groupID;
           obj.idQR= objecPerOfGr.idQR;
           return obj;
       }

    //--------------------------- lay tat ca user co du lieu FaceData
    public static ResponseMessage1 getListUserFaceData(int func, String input) {

        ObjectGetInfoVID obj = new Gson().fromJson(input, ObjectGetInfoVID.class);
        System.out.println("ObjectGetInfoVID: " + obj);
        ResponseMessage1 response = new ResponseMessage1();
        try {
            ArrayList<ObjectInfoVid> listFaceData = InforVid.getListObjecFaceLimitOffset(obj.limit, obj.offset);
            response.result = Tool.setBase64(listFaceData.toString());
            response.funcId = func;
            if(obj.getMcID().length() > 0){
                response.msgCode = 1;
                response.msgContent = "Success!";
            }else{
                response.msgCode = 2;
                response.msgContent = "Success!";
            }

            response.totalAll = InforVid.TatolListFace();

        } catch (Exception ex) {
            Log.logServices("getListUserFaceData Exception: " + ex.getMessage());
        }
        return response;
    }
}
