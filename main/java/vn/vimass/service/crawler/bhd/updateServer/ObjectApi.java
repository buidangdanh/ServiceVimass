package vn.vimass.service.crawler.bhd.updateServer;

import com.google.gson.Gson;

public class ObjectApi {
    public String serverPC;
    public String url;

    public String getServerPC() {
        return serverPC;
    }
    public void setServerPC(String serverPC) {
        this.serverPC = serverPC;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
