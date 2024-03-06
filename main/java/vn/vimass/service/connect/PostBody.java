package vn.vimass.service.connect;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import vn.vimass.service.log.Log;

public class PostBody {
    public static String callService(String url, String json) {

        try {
            int timeout = 10000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost post = new HttpPost(url);

            if(url.toLowerCase().contains("https://")){
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
                httpclient= new DefaultHttpClient(ccm, params);
            }


            VMString entity = null;
            try {
                entity = new VMString(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");
//			post.setHeader("Content-type", "application/x-www-form-urlencoded");


            long time = new Date().getTime();

//			Log.logServices("request:" + url);
            Log.logServices("request:" + json);

            HttpResponse response = httpclient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            Log.logServices("request:" + statusLine.getReasonPhrase());
            Log.logServices("request:" + statusLine.toString());
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String content = new String(out.toByteArray());
                Log.logServices("time:" + (new Date().getTime() - time));
                return content;
            } else {

                response.getEntity().getContent().close();
            }

            post.abort();
        } catch (Exception e) {
//			e.printStackTrace();
            Log.logServices("Loi ket noi:" + e.getMessage());
        }
        return "";
    }

    public static String callService(String url, String json, String contentType) {

        try {
            int timeout = 300000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost post = new HttpPost(url);

            if(url.toLowerCase().contains("https://")){
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
                httpclient= new DefaultHttpClient(ccm, params);
            }

            VMString entity = null;
            try {
                entity = new VMString(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            post.setEntity(entity);
//			post.setHeader("Content-type", "application/json");
            post.setHeader("Content-type", contentType);

            long time = new Date().getTime();
            HttpResponse response = httpclient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            Log.logServices("request:" + url);
            Log.logServices("request:" + json);
            Log.logServices("request:" + statusLine.getReasonPhrase());
            Log.logServices("request:" + statusLine.toString());
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String content = new String(out.toByteArray());
                Log.logServices("time:" + (new Date().getTime() - time));
                return content;
            } else {

                response.getEntity().getContent().close();
            }

            post.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String callServicePostBoy(String url, List<NameValuePair> urlParameters, String contentType) {

        try {
            int timeout = 300000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);




            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost post = new HttpPost(url);



            if(url.toLowerCase().contains("https://")){
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
                httpclient= new DefaultHttpClient(ccm, params);
            }




            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            post.setHeader("Content-type", contentType);



            long time = new Date().getTime();
            HttpResponse response = httpclient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            Log.logServices("request:" + url);
//			Log.logServices("request:" + json);
            Log.logServices("request:" + statusLine.getReasonPhrase());
            Log.logServices("request:" + statusLine.toString());
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String content = new String(out.toByteArray());
                Log.logServices("time:" + (new Date().getTime() - time));
                return content;
            } else {

                response.getEntity().getContent().close();
            }

            post.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String callServicePostBoyKemHeader(String url,
                                                     String accept,
                                                     String contentType,
                                                     String auth,
                                                     String transaction_id,
                                                     String authorization_data,
                                                     String api_timestamp,
                                                     String json) {

        try {
            int timeout = 300000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost post = new HttpPost(url);

            if(url.toLowerCase().contains("https://")){
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
                httpclient= new DefaultHttpClient(ccm, params);
            }


            VMString entity = null;
            try {
                entity = new VMString(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            post.setEntity(entity);
            post.setHeader("Accept", accept);
            post.setHeader("Content-type", contentType);
            post.setHeader("Authorization", "Bearer " + auth);
            post.setHeader("transaction_id", transaction_id);
            post.setHeader("authorization_data", authorization_data);
            post.setHeader("api_timestamp", api_timestamp);


            long time = new Date().getTime();
            HttpResponse response = httpclient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            Log.logServices("request:" + url);
            Log.logServices("request:" + json);
            Log.logServices("request:" + statusLine.getReasonPhrase());
            Log.logServices("request:" + statusLine.toString());
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String content = new String(out.toByteArray());
                Log.logServices("time:" + (new Date().getTime() - time));
                return content;
            } else {

                response.getEntity().getContent().close();
            }

            post.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String callServicePostBoyKemHeaderVPBPush(String url,
                                                            String accept,
                                                            String contentType,
                                                            String auth,
                                                            String Signature,
                                                            String json) {

        try {
            int timeout = 300000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);




            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost post = new HttpPost(url);



            if(url.toLowerCase().contains("https://")){
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
                httpclient= new DefaultHttpClient(ccm, params);
            }




            VMString entity = null;
            try {
                entity = new VMString(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            post.setEntity(entity);
            post.setHeader("Accept", accept);
            post.setHeader("Content-type", contentType);
            post.setHeader("Authorization", auth);
            post.setHeader("Signature", Signature);


            long time = new Date().getTime();
            HttpResponse response = httpclient.execute(post);
            StatusLine statusLine = response.getStatusLine();
            Log.logServices("request:" + url);
            Log.logServices("request:" + json);
            Log.logServices("request:" + statusLine.getReasonPhrase());
            Log.logServices("request:" + statusLine.toString());
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String content = new String(out.toByteArray());
                Log.logServices("time:" + (new Date().getTime() - time));
                return content;
            } else {

                response.getEntity().getContent().close();
            }

            post.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String callServicePUT(String url, String json, String contentType) {

        try {
            String putEndpoint = url;
            HttpPut httpPut = new HttpPut(putEndpoint);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            String inputJson = json;

            VMString entity = null;
            try {
                entity = new VMString(inputJson);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            httpPut.setEntity(entity);

            int timeout = 300000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            HttpResponse response = httpclient.execute(httpPut);
            StatusLine statusLine = response.getStatusLine();


            Log.logServices("request PUT: " + url);
            Log.logServices("request PUT: " + json);
            Log.logServices("request PUT getReasonPhrase() : " + statusLine.getReasonPhrase());
            Log.logServices("request PUT statusLine: " + statusLine.toString());
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String content = new String(out.toByteArray());
                return content;
            } else {

                response.getEntity().getContent().close();
            }

            httpPut.abort();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
