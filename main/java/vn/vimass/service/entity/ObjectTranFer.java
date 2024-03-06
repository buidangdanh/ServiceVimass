package vn.vimass.service.entity;

public class ObjectTranFer {
    public String jsonData;

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public String toString() {
        return "ResponseTranFer{" +
                "jsonData='" + jsonData + '\'' +
                '}';
    }
}
