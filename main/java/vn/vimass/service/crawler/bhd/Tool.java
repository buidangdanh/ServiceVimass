package vn.vimass.service.crawler.bhd;

import org.apache.commons.codec.binary.Base64;
import vn.vimass.service.log.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.Random;
import java.util.regex.Pattern;

public class Tool {
    public static String getIpAddress() {
        String ipAddress = "";
        try {
            // Get the local host address
            InetAddress localhost = InetAddress.getLocalHost();
            // Get the IP address as a string
            ipAddress = localhost.getHostAddress();
            Log.logServices("IP Address: " + ipAddress);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    public static String aesEncrypt(String str, String key) {

        if (str == null || key == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
            byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(bytes));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String base64(String str) {

        if (str == null ) {
            return null;
        }
        try {
            byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
//            String encoded = DatatypeConverter.printBase64Binary(str.getBytes());
            return new String(bytesEncoded);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String setBase64(String input)
    {
        String kq = "";
        try
        {
            if(input != null)
            {
                byte[] encodedBytes = Base64.encodeBase64(input.getBytes(StandardCharsets.UTF_8));
                kq = new String(encodedBytes);
            }
        }
        catch(Exception e)
        {

        }
        return kq;
    }

    public static String getBase64(String input)
    {
        String kq = "";
        try
        {
            if(input != null)
            {
                byte[] decodedBytes = Base64.decodeBase64(input.getBytes(StandardCharsets.UTF_8));
                kq = new String(decodedBytes);
            }
        }
        catch(Exception e)
        {

        }
        return kq;
    }
    public static String bamMD5(String input)
    {
        StringBuffer sb = new StringBuffer();
        try {
            input = input.trim();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());

            byte[] byteData = md.digest();

            // convert the byte to hex format method 1

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }

    public static String removeVietnameseAccents(String str) {
        if (str != null) {
            str = deAccent(str);
            str = str.replaceAll("á|à|ả|ã|ạ|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ","a");
            str = str.replaceAll("Á|À|Ả|Ã|Ạ|Ă|Ắ|Ằ|Ẳ|Ẵ|Ặ|Â|Ấ|Ầ|Ẩ|Ẫ|Ậ","A");
            str = str.replaceAll("đ","d");
            str = str.replaceAll("Đ","D");
            str = str.replaceAll("é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ", "e");
            str = str.replaceAll("É|È|Ẻ|Ẽ|Ẹ|Ê|Ế|Ề|Ể|Ễ|Ệ", "E");
            str = str.replaceAll("í|ì|ỉ|ĩ|ị","i");
            str = str.replaceAll("Í|Ì|Ỉ|Ĩ|Ị","I");
            str = str.replaceAll("ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ","o");
            str = str.replaceAll("Ó|Ò|Ỏ|Õ|Ọ|Ô|Ố|Ồ|Ổ|Ỗ|Ộ|Ơ|Ớ|Ờ|Ở|Ỡ|Ợ","O");
            str = str.replaceAll("ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự", "u");
            str = str.replaceAll("Ú|Ù|Ủ|Ũ|Ụ|Ư|Ứ|Ừ|Ử|Ữ|Ự", "U");
            str = str.replaceAll("ý|ỳ|ỷ|ỹ|ỵ","y");
            str = str.replaceAll("Ý|Ỳ|Ỷ|Ỹ|Ỵ","Y");
        } else {
            str = "";
        }

        return str;
    }


    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String generateSessionKeyUpCase(int length) {
        String alphabet = new String(
                "0123456789ABCDEF"); // 9
        int n = alphabet.length(); // 10

        String result = new String();
        Random r = new Random(); // 11

        for (int i = 0; i < length; i++)
            // 12
            result = result + alphabet.charAt(r.nextInt(n)); // 13

        return result;
    }

}
