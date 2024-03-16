package vn.vimass.service.crawler.bhd.DongBoVsServer;
import com.google.gson.Gson;
import vn.vimass.service.BackUp.BackUpControllerDataBaseVer2;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.ObjectIpPhone;
import vn.vimass.service.crawler.bhd.DongBoVsServer.entity.TypeThongBao;
import vn.vimass.service.crawler.bhd.SendData.SendDataController;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.crawler.bhd.XuLyLayKhuonMat.ObjectFaceOfVid;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.DeviceVPass;
import vn.vimass.service.table.InforVid;
import vn.vimass.service.table.NhomThietBiDiem.entity.ListDiem;
import vn.vimass.service.table.NhomThietBiDiem.entity.ObjVpass;
import vn.vimass.service.utils.ServivceCommon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import static vn.vimass.service.BackUp.BackUpFunction.StatusResponse;

public class SendDataFaceToDevice {
    public static ResponseMessage1 resDeviceVpassActive(int funcId, long currentime, String data) {

        Log.logServices("int put resDeviceVpassActive : " + data + " time: "+currentime);
        ResponseMessage1 response = new ResponseMessage1();
        try {
            response.funcId = funcId;
            ObjectIpPhone obj = new Gson().fromJson(data, ObjectIpPhone.class);
            Log.logServices("resDeviceVpassActive : " + obj);
            boolean check = checkInfoVpassActive(obj);
            if (check) {
                response = StatusResponse(1);
                response.result = "update resDeviceVpassActive =============> true";
                sendDataFace(obj.ip, Integer.valueOf(obj.port), obj.idQR);
            } else {
                response = StatusResponse(2);
                response.result = "update resDeviceVpassActive =============> false";
            }
        } catch (Exception ex) {
            Log.logServices("resDeviceVpassActive Exception" + ex.getMessage());
        }
        return response;
    }
    public static ResponseMessage1 func(int funcId, long currentime,String idQR){
        ResponseMessage1 response = new ResponseMessage1();
        try{
            Log.logServices("func input: " + funcId + " time: "+currentime);
            ArrayList<ObjectFaceOfVid> arrayList = InforVid.getListObjecFaceV1(0, 0, idQR);
            ArrayList<ObjVpass> arrVpass = DeviceVPass.getListVpass();
            if(arrVpass.size() > 0){
                for(ObjVpass item: arrVpass){
                    ArrayList<ListDiem> arrDiem = item.listDiem;
                    for(ListDiem itemDiem: arrDiem) {
                        if((idQR.equals(itemDiem.id) && item.portD != null && item.ip != null)
                                || (idQR.equals(itemDiem.id) && !item.portD.isEmpty() && !item.ip.isEmpty())){
                            sendDataFace(item.ip, Integer.valueOf(item.portD) , Tool.setBase64(arrayList.toString()));
                        }
                    }
                }
            }
            response = StatusResponse(1);
            response.result = "send data to phone" ;
            Log.logServices("send data to phone");
        }catch (Exception e){
            Log.logServices("send data to phone Exception: " + e.getMessage());
        }

        return response;
    }

    private static void sendDataFace(String ip, int port, String data){

        try (Socket socket = new Socket()) {
            // Set the timeout to 5 seconds (5000 milliseconds)
            socket.setSoTimeout(3000);
            // Connect to the server
            socket.connect(new InetSocketAddress(ip, port));
            // Perform socket operations (send/receive data)
            // Example:
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Send data
            out.println(data);
            // Receive data
            String response = in.readLine();
            Log.logServices("Server response: " + response);
            if (response.equals("Success")) {
                Log.logServices("Data sent successfully.");
            } else {
                Log.logServices("Failed to send data.");
            }

        } catch (SocketTimeoutException e) {
            Log.logServices("Socket operation timed out: " + e.getMessage());
            // Handle timeout exception
        } catch (UnknownHostException e) {
            Log.logServices("Unknown host: " + ip);
            // Handle unknown host exception
        } catch (IOException e) {
            Log.logServices("I/O error: " + e.getMessage());
            // Handle other I/O exceptions
        }
    }

    public static boolean checkInfoVpassActive(ObjectIpPhone obj){
        try{
            ObjVpass objVpass = DeviceVPass.getVpassInfo(obj.deviceID);
            ArrayList<ObjectFaceOfVid> arrayList = InforVid.getListObjecFaceV1(0, 0, obj.idQR);
            if(objVpass.id != null){
                if(obj.deviceID.equals(objVpass.deviceID) && obj.ip.equals(objVpass.ip) && obj.port.equals(objVpass.portD)){
                    String str = ""; // vid + "_"+ personName +"_"+(5 ky tu dau dac trung khuon mat))
                    for(ObjectFaceOfVid itemFaceOfVid: arrayList){
                        str += itemFaceOfVid.idVid+"_"+itemFaceOfVid.personName+"_"+itemFaceOfVid.faceData.substring(0, Math.min(itemFaceOfVid.faceData.length(), 5));
                    }
                    String checkSum = ServivceCommon.bamMD5(str);
                    if(!obj.cks.equals(checkSum)){

                        return true;
                    }
                }else if(obj.deviceID.equals(objVpass.deviceID) && !obj.ip.equals(objVpass.ip) && obj.port.equals(objVpass.portD)){
                    DeviceVPass.update(obj.ip, obj.port, obj.deviceID);
                    return true;
                }
            }

        }catch (Exception e){
            Log.logServices("checkInfoVpassActive Exception: " + e.getMessage());
        }

     return false;
    }
}
