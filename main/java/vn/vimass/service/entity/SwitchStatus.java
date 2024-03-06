package vn.vimass.service.entity;

public class SwitchStatus {
    public String idLoiRaVao;
    public String loiRaVao;
    public String address;
    public String ip;

    public String getIdLoiRaVao() {
        return idLoiRaVao;
    }

    public void setIdLoiRaVao(String idLoiRaVao) {
        this.idLoiRaVao = idLoiRaVao;
    }

    public String getLoiRaVao() {
        return loiRaVao;
    }

    public void setLoiRaVao(String loiRaVao) {
        this.loiRaVao = loiRaVao;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPortIp() {
        return portIp;
    }

    public void setPortIp(String portIp) {
        this.portIp = portIp;
    }

    public int getCheDo() {
        return cheDo;
    }

    public void setCheDo(int cheDo) {
        this.cheDo = cheDo;
    }

    public String portIp;
    public int cheDo;

    public SwitchStatus(String idLoiRaVao, String loiRaVao, String address, String ip, String portIp, int cheDo) {
        this.idLoiRaVao = idLoiRaVao;
        this.loiRaVao = loiRaVao;
        this.address = address;
        this.ip = ip;
        this.portIp = portIp;
        this.cheDo = cheDo;
    }
}
