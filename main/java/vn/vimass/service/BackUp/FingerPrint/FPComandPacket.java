package vn.vimass.service.BackUp.FingerPrint;

import vn.vimass.service.log.Log;

import static vn.vimass.service.BackUp.FingerPrint.FPFunC.tinhCks;

public class FPComandPacket {
    public static String testConnection() {
        String hexString = "";
        try {
            hexString = "55 AA 50 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 50 01";

        } catch (Exception e) {
            Log.logServices("testConnection Exception!" + e.getMessage());

        }
        return hexString;
    }

    public static String getDeiceID(String tempNo) {
        String hexSFinal = "";
        try {
            String hexString = "55 AA 11 01 00 00 " + tempNo + " 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
            hexSFinal = hexString + tinhCks(hexString);
        } catch (Exception e) {
            Log.logServices("testConnection Exception!" + e.getMessage());

        }
        return hexSFinal;
    }

    public static String getEmptyID() {
        String hexSFinal = "";
        try {
            hexSFinal = "55 AA 07 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 07 01";
        } catch (Exception e) {
            Log.logServices("getEmptyID Exception!" + e.getMessage());

        }
        return hexSFinal;
    }

    public static String identifyFree(String RCM) {
        String hexSFinal = "";
        try {
            String hexS = "55 AA " + RCM + "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
            hexSFinal = hexS + tinhCks(hexS);
        } catch (Exception e) {
            Log.logServices("testConnection Exception!" + e.getMessage());

        }
        return hexSFinal;
    }

    public static String setDeiceID(String DeiceID) {
        String hexSFinal = "";
        try {
            String hexS = "55 AA 10 01 02 00" + DeiceID + "00 00 00 00 00 00 00 00 00 00 00 00 00 00";
            hexSFinal = hexS + tinhCks(hexS);
        } catch (Exception e) {
            Log.logServices("setDeiceID Exception!" + e.getMessage());

        }
        return hexSFinal;
    }
    public static String enrollOneTime(String emptyID) {
        String hexSFinal = "";
        try {
            String hexS = "55 AA 04 01 02 00 " + emptyID + "00 00 00 00 00 00 00 00 00 00 00 00 00 00 ";
            hexSFinal = hexS + tinhCks(hexS);
        } catch (Exception e) {
            Log.logServices("enrollOneTime Exception!" + e.getMessage());

        }
        return hexSFinal;
    }
    public static String readTemp(String tempNo) {
        String hexSFinal = "";
        try {
            String hexS = "55 AA 0A 01 02 00 " + tempNo + "00 00 00 00 00 00 00 00 00 00 00 00 00 00";
            hexSFinal = hexS + tinhCks(hexS);
        } catch (Exception e) {
            Log.logServices("enrollOneTime Exception!" + e.getMessage());

        }
        return hexSFinal;
    }
    public static String clearTemp(String tempNo) {
        String hexSFinal = "";
        try {
            String hexS = "55 AA 05 01 02 00 " + tempNo + " 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
            hexSFinal = hexS + tinhCks(hexS);
        } catch (Exception e) {
            Log.logServices("clearTemp Exception!" + e.getMessage());

        }
        return hexSFinal;
    }
}
