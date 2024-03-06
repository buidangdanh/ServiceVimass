package vn.vimass.service.CallService;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.codec.CharEncoding.UTF_8;

public class CallService {
    public static String PostREST(String url, String json) {
        try {
            int timeout = 300000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost post = new HttpPost(url);

            // Sử dụng UTF-8 để mã hóa dữ liệu JSON
            StringEntity entity = new StringEntity(json, UTF_8);
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json; charset=UTF-8");

            HttpResponse response = httpclient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                // Sử dụng UTF-8 để chuyển đổi dữ liệu phản hồi thành chuỗi
                return out.toString(StandardCharsets.UTF_8.name());
            } else {
                response.getEntity().getContent().close();
                post.abort();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Hoặc xử lý lỗi theo cách khác
        }
        return "";
    }
}
