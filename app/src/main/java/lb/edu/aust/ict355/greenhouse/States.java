package lb.edu.aust.ict355.greenhouse;

public class States {
    private String ipAdd;
    private int port;
    private String state;

    public States(String ipAdd, int port, String state) {
        this.ipAdd = ipAdd;
        this.port = port;
        this.state = state;
    }

    public String getIpAdd() {
        return ipAdd;
    }

    public void setIpAdd(String ipAdd) {
        this.ipAdd = ipAdd;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
