package vn.vimass.service.BackUp.FingerPrint;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.google.gson.Gson;
import vn.vimass.service.BackUp.FingerPrint.Obj.*;
import vn.vimass.service.crawler.bhd.Tool;
import vn.vimass.service.entity.ResponseMessage;
import vn.vimass.service.entity.ResponseMessage1;
import vn.vimass.service.log.Log;
import vn.vimass.service.table.NhomThietBiDiem.entity.ListDiem;
import vn.vimass.service.table.object.ObjectFPRequest;
import vn.vimass.service.utils.ServivceCommon;

import static vn.vimass.service.BackUp.FingerPrint.FPComandPacket.*;
import static vn.vimass.service.BackUp.FingerPrint.FPDataBase.*;
import static vn.vimass.service.BackUp.FingerPrint.FPDataBase.capNhatCoSoDuLieuFPCuThe;
import static vn.vimass.service.BackUp.FingerPrint.Obj.CommandList.FP_Cancel;
import static vn.vimass.service.CallService.CallService.PostREST;

public class FPFunC {
    public static HashMap<String, String> diviceIDSerial;
    public static ArrayList<SerialPort> listPort = new ArrayList<>();
    public static ArrayList<FingerInfo> listFB = new ArrayList<>();
    public static boolean statusFP = true;

    public static void LuuDeviceIDSerial() {

        ExecutorService executor = Executors.newFixedThreadPool(10); // Khởi tạo ExecutorService trong phương thức
        try {
            HashMap<String, String> deviceIDSerial = new HashMap<>();
            ArrayList<SerialPort> listPort = new ArrayList<>();
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                if (port.getDescriptivePortName() != null && port.getDescriptivePortName().contains("USB Serial Port")) {
                    listPort.add(port);
                }
            }
            for (SerialPort port : listPort) {
                executor.submit(() -> {
                    String response = sendDataToSerialPort(port, testConnection());
                    Log.logServices(port.getSystemPortName() + ": " + response);
                    // Bạn có thể thêm phần lưu deviceID vào map ở đây nếu cần
                });
            }

            // Đóng ExecutorService và chờ cho tất cả tasks hoàn thành
            executor.shutdown();
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            Log.logServices("LuuDeviceIDSerial Exception!" + ex.getMessage());
        }
    }

    public static ArrayList<FingerInfo> kiemTraTTFP() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ArrayList<FingerInfo> arr = new ArrayList<>();
        try {
            deleteAllFingerInfo();
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                executorService.submit(() -> {
                    if (port.getDescriptivePortName() != null && port.getDescriptivePortName().contains("USB Serial Port")) {
                        FingerInfo objF = new FingerInfo();
                        String t = sendData(SerialPort.getCommPort(port.getSystemPortName()), "55 AA 11 01 00 00 0200 00 00 00 00 00 00 00 00 00 00 00 00 00 001301", 100);
                        Log.logServices("keest qua: " + t.substring(15, 19));
                        objF.id = t.substring(15, 19);
                        objF.port = port.getSystemPortName();
                        updateDBFingerInfo(objF);
                    }
                });
            }
            executorService.shutdown();
            if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                executorService.shutdown();
            }
            arr = getThietBiVanTay();
        } catch (InterruptedException e) {
            Log.logServices("InterruptedException Exception!" + e.getMessage());
            executorService.shutdown();
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            Log.logServices("kiemTraTTFP Exception!" + ex.getMessage());
        }
        return arr;
    }

    private static void updateDBFingerInfo(FingerInfo objF) {
        try {
            insertDBFingerInfoDB(objF);

        } catch (Exception ex) {
            Log.logServices("updateDBFingerInfo Exception!" + ex.getMessage());
        }
    }


    private static String sendDataToSerialPort(SerialPort port, String hexString) {
        AtomicReference<String> res = new AtomicReference<>(""); // Use AtomicReference
        try {
            if (port.openPort()) {
                System.out.println("Successfully opened the port.");
                port.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
                try {
                    Thread.sleep(000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] dataToSend = hexStringToByteArray(hexString);
                port.writeBytes(dataToSend, dataToSend.length);

                port.addDataListener(new SerialPortDataListener() {
                    @Override
                    public int getListeningEvents() {
                        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                    }

                    @Override
                    public void serialEvent(SerialPortEvent event) {
                        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                            byte[] newData = new byte[port.bytesAvailable()];
                            int numRead = port.readBytes(newData, newData.length);
                            Log.logServices("Read " + numRead + " bytes.");
                            res.set(res.get() + bytesToHex(newData)); // Kết quả trả về
                        }
                    }
                });

                try {
                    Thread.sleep(500); // Waiting for 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                port.closePort();
                System.out.println("Port closed.");
            } else {
                System.out.println("Unable to open the port.");
            }
        } catch (Exception ex) {
            Log.logServices("sendDataToSerialPort Exception!" + ex.getMessage());
        }
        return res.get(); // Return the accumulated result
    }


    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static byte[] hexStringToByteArray(String s) {
        if (s.indexOf(" ") > -1) {
            s = s.replaceAll(" ", "");
        }
        // Remove all spaces

        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String bytesToHex2(byte[] bytes, int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(String.format("%02x", bytes[i]));
        }
        return result.toString();
    }

    public static String tinhCks(String hexString) {
        // Loại bỏ dấu cách và chuyển đổi thành mảng byte
        byte[] byteArray = hexStringToByteArray(hexString);

        // Tính tổng các giá trị byte
        int sum = calculateSum(byteArray);

        // Chuyển tổng thành chuỗi hex và đảo ngược thứ tự các cặp ký tự
        String sumHex = intToHexString(sum);

        System.out.println("Tổng của chuỗi hex: " + sumHex);
        return sumHex;
    }

    public static int calculateSum(byte[] byteArray) {
        int sum = 0;
        for (byte b : byteArray) {
            sum += b & 0xFF;  // Chúng ta sử dụng & 0xFF để đảm bảo giá trị byte là dương
        }
        return sum;
    }

    public static String intToHexString(int value) {
        String hex = Integer.toHexString(value);
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }

        // Đảo ngược thứ tự các cặp ký tự trong chuỗi
        StringBuilder reversedHex = new StringBuilder();
        for (int i = hex.length() - 2; i >= 0; i -= 2) {
            reversedHex.append(hex.substring(i, i + 2));
        }

        return reversedHex.toString();
    }

    private static String sendData2(SerialPort port, String hexString, int timeOut, int timeChoKetNoi) {
        AtomicReference<String> res = new AtomicReference<>(""); // Use AtomicReference
        try {
            if (port.openPort()) {
                System.out.println("Successfully opened the port.");
                port.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
                try {
                    Thread.sleep(timeChoKetNoi);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] dataToSend = hexStringToByteArray(hexString);
                port.writeBytes(dataToSend, dataToSend.length);

                port.addDataListener(new SerialPortDataListener() {
                    @Override
                    public int getListeningEvents() {
                        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                    }

                    @Override
                    public void serialEvent(SerialPortEvent event) {
                        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                            byte[] newData = new byte[port.bytesAvailable()];
                            int numRead = port.readBytes(newData, newData.length);
                            Log.logServices("Read " + numRead + " bytes.");
                            res.set(res.get() + bytesToHex(newData)); // Kết quả trả về
                        }
                    }
                });

                try {
                    Thread.sleep(timeOut); // Waiting for 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                port.closePort();
                System.out.println("Port closed.");
            } else {
                System.out.println("Unable to open the port.");
            }
        } catch (Exception ex) {
            Log.logServices("sendDataToSerialPort Exception!" + ex.getMessage());
        }
        return res.get(); // Return the accumulated result
    }

    public static String sendData(SerialPort port, String hexString, int timeOut) {
        try {
            if (port.openPort()) {
                Log.logServices("sendData gui data: " + hexString);

                port.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
                port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

                // Gửi dữ liệu
                byte[] dataToSend = hexStringToByteArray(hexString);
                port.writeBytes(dataToSend, dataToSend.length);

                // Đọc dữ liệu phản hồi
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, timeOut, 0);
                byte[] readBuffer = new byte[1024];
                int numRead = port.readBytes(readBuffer, readBuffer.length);
                System.out.println("Read " + numRead + " bytes.");

                port.closePort();
                return bytesToHex2(readBuffer, numRead); // Chuyển dữ liệu nhận được thành hex string
            } else {
                System.out.println("Unable to open the port.");
                return null;
            }
        } catch (Exception ex) {
            Log.logServices("sendDataToSerialPort Exception!" + ex.getMessage());
            return null;
        }
    }

    public static void sendDataX() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                executorService.submit(() -> {
                    if (port.getDescriptivePortName() != null && port.getDescriptivePortName().contains("USB Serial Port")) {
                        Log.logServices("sendData dữ liệu trả về: " + port.getSystemPortName() + " " + sendData(port, "55 AA 02 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 02 01", 5000));
                    }
                });
            }
            executorService.shutdown();
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdown();
            }
        } catch (Exception ex) {

            System.out.println("Error in sendData: " + ex.getMessage());

        }
    }

    public static ArrayList<FingerData> layFPDataTheoNguoi(ObjectFPRequest oTKR) {
        ArrayList<FingerData> data = new ArrayList<>();
        try {
            data = getDataFingerFromVid(oTKR);
        } catch (Exception ex) {
            Log.logServices("layFPDataTheoNguoi Exception!" + ex.getMessage());

        }
        return data;

    }

    public static ResponseMessage1 dangKyVanTay(ObjThemSuaXoaRQ orK, String COM, ArrayList<FingerData> arrF) {
        ResponseMessage1 res = new ResponseMessage1();
        String kqF = "";
        res.funcId = 127;
        try {
            FingerData fp = new FingerData();
            kqF = layValueVanTay(COM);
            if (kqF != null && !kqF.equals("")) {
                if (kqF.equals("2")) {
                    res.msgCode = 2;
                    res.msgContent = Tool.setBase64("Vân tay không rõ hoặc đã tồn tại");
                } else if (kqF.equals("3")) {
                    res.msgCode = 2;
                    res.msgContent = Tool.setBase64("Vượt thời gian đặt vân tay");
                } else {
                    fp = themVaoDBFP(orK, kqF, arrF);
                    if (fp != null) {
                        res.msgCode = 1;
                        res.msgContent = Tool.setBase64("Thành công!");
                        res.result = Tool.setBase64(fp.toString());
                    }
                }
            }

        } catch (Exception ex) {
            Log.logServices("dangKyVanTay Exception!" + ex.getMessage());

        }
        return res;
    }

    private static FingerData themVaoDBFP(ObjThemSuaXoaRQ orK, String kqFData, ArrayList<FingerData> arrF) {
        FingerData fp = new FingerData();
        try {
            if (arrF == null || (arrF != null && arrF.size() < 4)) {
                fp.emptyID = kqFData.split("-")[0];
                fp.idThietBiFP = orK.idFP;
                fp.name = orK.nameFP;
                fp.data = kqFData.split("-")[1];
            }
            arrF.add(fp);
            capNhatCoSoDuLieuFP(orK, arrF);

        } catch (Exception ex) {
            Log.logServices("themVaoDBFP Exception!" + ex.getMessage());

        }
        return fp;
    }


    private static String layValueVanTay(String COM) {
        String kqFinal = "";
        String emtyID = "";
        String kq = "";
        String value = "";
        try {
            Log.logServices("layValueVanTay COM " + COM);

            sendData(SerialPort.getCommPort(COM), identifyFree(FP_Cancel), 1);
            NhayDen(COM);
            emtyID = sendData(SerialPort.getCommPort(COM), getEmptyID(), 100).substring(16, 20);
            kq = sendData(SerialPort.getCommPort(COM), enrollOneTime(emtyID), 5000);
            if (kq != null && !kq.equals("")) {
                Log.logServices("layValueVanTay enrol " + kq);
                String resCM = kq.substring(16, 20);
                if (kq.indexOf("f4ff") > -1) {
                    kqFinal = emtyID + "-" + sendData(SerialPort.getCommPort(COM), readTemp(emtyID), 1000).substring(68, 1064);
                    Log.logServices("dangKyVanTay readtemp " + kqFinal);
                } else if (resCM.equals("1900")) {
                    kqFinal = "2";

                } else if (resCM.equals("2300")) {
                    kqFinal = "3";
                }
            }
        } catch (Exception ex) {
            Log.logServices("layValueVanTay Exception!" + ex.getMessage());

        }
        return kqFinal;

    }

    public static void NhayDen(String COM) {
        try {
            sendData(SerialPort.getCommPort(COM), identifyFree(FP_Cancel), 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), "55 AA 04 01 02 00 e7 03 00 00 00 00 00 00 00 00 00 00 00 00 00 00 f0 01", 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), identifyFree(FP_Cancel), 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), "55 AA 04 01 02 00 e7 03 00 00 00 00 00 00 00 00 00 00 00 00 00 00 f0 01", 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), identifyFree(FP_Cancel), 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), "55 AA 04 01 02 00 e7 03 00 00 00 00 00 00 00 00 00 00 00 00 00 00 f0 01", 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), identifyFree(FP_Cancel), 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), "55 AA 04 01 02 00 e7 03 00 00 00 00 00 00 00 00 00 00 00 00 00 00 f0 01", 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), identifyFree(FP_Cancel), 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), "55 AA 04 01 02 00 e7 03 00 00 00 00 00 00 00 00 00 00 00 00 00 00 f0 01", 1);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(SerialPort.getCommPort(COM), identifyFree(FP_Cancel), 1);
        } catch (Exception ex) {
            Log.logServices("hamNhayDen Exception!" + ex.getMessage());

        }
    }

    public static int chuyenChuSangSo(String hexString) {
        int kq = 0;
        try {
            // Đảo ngược chuỗi hex để có định dạng big-endian
            String bigEndianHexString = reverseHexString(hexString);
            kq = Integer.parseInt(bigEndianHexString, 16);
        } catch (Exception ex) {
            Log.logServices("littleEndianHexStringToNumber Exception!" + ex.getMessage());
        }
        // Chuyển đổi chuỗi hex big-endian thành số
        return kq;
    }

    public static String reverseHexString(String hexString) {
        StringBuilder reversedHexString = new StringBuilder();
        // Xử lý từng cặp ký tự (mỗi byte) trong chuỗi hex
        for (int i = 0; i < hexString.length(); i += 2) {
            // Thêm từng byte vào đầu chuỗi StringBuilder
            reversedHexString.insert(0, hexString.substring(i, i + 2));
        }
        return reversedHexString.toString();
    }

    public static String chuyenSoThanhChu(int number) {
        // Chuyển số nguyên thành chuỗi hex và đệm nếu cần
        String hexString = Integer.toHexString(number);
        hexString = padHexString(hexString, 4);
        // Đảo ngược thứ tự của các byte để có định dạng little-endian
        return reverseIntToHexString(hexString);
    }

    private static String padHexString(String hexString, int minLength) {
        while (hexString.length() < minLength) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    private static String reverseIntToHexString(String hexString) {
        StringBuilder reversedHexString = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            reversedHexString.insert(0, hexString.substring(i, i + 2));
        }
        return reversedHexString.toString();
    }

    public static void capNhatIdFP(String idMoiNhat, HashMap<String, String> hs) {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                executorService.submit(() -> {
                    if (port.getDescriptivePortName() != null && port.getDescriptivePortName().contains("USB Serial Port")) {
                        if (!hs.containsKey(port.getSystemPortName())) {
                            Log.logServices("capNhatIdFP kq!" + sendData(port, setDeiceID(idMoiNhat), 200));
                            NhayDen(port.getSystemPortName());
                        }

                    }
                });
            }
            executorService.shutdown();
            if (!executorService.awaitTermination(200, TimeUnit.MILLISECONDS)) {
                executorService.shutdown();
            }

        } catch (Exception ex) {
            Log.logServices("capNhatIdFP " + ex.getMessage());

        }

    }

    public static void xoaFpID(String COM) {
        try {
            sendData(SerialPort.getCommPort(COM), setDeiceID("0100"), 200);

        } catch (Exception ex) {
            Log.logServices("xoaFpID " + ex.getMessage());

        }

    }

    public static String taoIdFPMoiNhat(ArrayList<ObjFP> listFPSV) {
        String kq = "";
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            if (listFPSV == null || listFPSV.size() < 2) {
                kq = "0200";
            } else {
                for (ObjFP obj : listFPSV) {
                    if (obj.idDonVi != null && !obj.idDonVi.equals("")) {
                        numbers.add(chuyenChuSangSo(obj.idDonVi));
                    }
                }
                Log.logServices("taoIdFPMoiNhat so lon nhat " + findMaxNumber(numbers));
                kq = chuyenSoThanhChu(findMaxNumber(numbers));

            }

        } catch (Exception ex) {
            Log.logServices("taoIdFPMoiNhat Exception" + ex.getMessage());

        }

        return kq;
    }

    private static int findMaxNumber(ArrayList<Integer> numbers) {
        int max = 2; // Khởi tạo giá trị lớn nhất là giá trị nhỏ nhất có thể của int
        for (int number : numbers) {
            if (number > max) {
                max = number; // Cập nhật giá trị lớn nhất nếu tìm thấy số lớn hơn
            }
        }
        return max + 1;
    }

    public static boolean capNhatLenServer(ObjFP objFP) {
        boolean kqC = false;
        ArrayList<ObjFPSua> arrayList = new ArrayList<>();
        try {

            ObjectSuaVanTay obj = new ObjectSuaVanTay();
            obj.user = "0966074236";
            obj.perNum = 0;
            obj.deviceID = 3;
            obj.mcID = "DaiDongTTTHCS";
            obj.currentTime = new Date().getTime();
            obj.cks = ServivceCommon.bamMD5(obj.user + "ZgVCHxqMd$aNkk54X2YHD" + obj.currentTime);
            arrayList.add(chuyenObjSangObjectSuaVanTay(objFP));
            obj.listItem = arrayList;
            String url = "http://210.245.8.7:12318/vimass/services/VUHF/vanTay";
            String kq = PostREST(url, new Gson().toJson(obj));
            Log.logServices("request" + new Gson().toJson(obj));

            ResponseMessage responseMessage = new Gson().fromJson(kq, ResponseMessage.class);
            if (responseMessage != null && responseMessage.msgCode == 1) {
                kqC = true;
            }
            Log.logServices("capNhatLenServer" + responseMessage.toString());

        } catch (Exception ex) {
            Log.logServices("capNhatLenServer" + ex.getMessage());

        }
        return kqC;
    }

    private static ObjFPSua chuyenObjSangObjectSuaVanTay(ObjFP objFP) {
        ObjFPSua objFPSua = new ObjFPSua();
        ArrayList<String> listIDDiem = new ArrayList<>();
        try {
            objFPSua.id = objFP.id;
            objFPSua.mcID = objFP.mcID;
            objFPSua.idDonVi = objFP.idDonVi;
            objFPSua.nameF = objFP.nameF;
            objFPSua.totalF = objFP.totalF;
            objFPSua.currentF = objFP.currentF;
            Log.logServices("chuyenObjSangObjectSuaVanTay1" + objFP.id);

            objFPSua.IdDeviceVManager = objFP.deviceV.id;

            objFPSua.type = 2;
            for (ListDiem arr : objFP.listDiem) {
                Log.logServices("chuyenObjSangObjectSuaVanTay2" + arr.id);
                listIDDiem.add(arr.id);
            }
            objFPSua.listIDDiem = listIDDiem;

        } catch (Exception ex) {
            Log.logServices("chuyenObjSangObjectSuaVanTay Exception " + ex.getMessage());

        }
        return objFPSua;
    }

    public static void capNhatPort(ArrayList<ObjFP> arrayList) {
        try {
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                if (port.getDescriptivePortName() != null && port.getDescriptivePortName().contains("USB Serial Port")) {
                    String t = sendData(SerialPort.getCommPort(port.getSystemPortName()), "55 AA 11 01 00 00 0200 00 00 00 00 00 00 00 00 00 00 00 00 00 001301", 100);
                    if(t!=null){
                        for (ObjFP obj : arrayList) {
                            if (obj.idDonVi.equals(t.substring(16, 20))) {
                                capNhatPortVanTayDB(port.getSystemPortName(), t.substring(16, 20));
                            }
                        }
                    }
                }

            }
        } catch (Exception ex) {
            Log.logServices("capNhatPort" + ex.getMessage());

        }
    }

    public static void xoaKhoiDB(ArrayList<FingerData> dataFPTrongMini, ObjThemSuaXoaRQ requestClient) {
        try {
            Iterator<FingerData> iterator = dataFPTrongMini.iterator();
            while (iterator.hasNext()) {
                FingerData obj = iterator.next();
                // Kiểm tra điều kiện để xóa
                if (obj.emptyID.equals(requestClient.emptyID)) {
                    iterator.remove();
                }
            }
            capNhatCoSoDuLieuFPCuThe(requestClient.thongTinNguoi.idVid, requestClient.thongTinNguoi.personName, dataFPTrongMini);
        } catch (Exception ex) {
            Log.logServices("xoaKhoiDB" + ex.getMessage());


        }
    }
    public static String removeAccent(String input) {
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }


}
