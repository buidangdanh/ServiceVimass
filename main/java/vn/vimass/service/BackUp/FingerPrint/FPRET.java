package vn.vimass.service.BackUp.FingerPrint;

public class FPRET {
    public static String ERR_SUCCESS ="0000";

    public static String ERR_FAIL ="0001";

    public static String GD_NEED_RELEASE_FINGER ="F4FF000000000000000000000000";//Move fingerprint

    public static String ERR_DUPLICATION_ID ="190000000000000000000000000";

    public static String ERR_TIME_OUT ="2300000000000000000000000000";// Timeout expired, Fingerprint unfound


    public static String ERR_TMPL_NOT_EMPTY = "1400000000000000000000000000";//Template existed in the appointed numbe

    public static String ERR_ALL_TMPL_EMPTY = "1500000000000000000000000000";// No registered Template exist

    public static String ERR_EMPTY_ID_NOEXIST = "1600000000000000000000000000";// No Registrable Template ID available
}
