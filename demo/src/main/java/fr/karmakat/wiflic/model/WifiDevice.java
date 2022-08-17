package fr.karmakat.wiflic.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.List;

@Document(collection = "test")
public class WifiDevice {
    private boolean active;
    private boolean reachable;
    private long RXBytes;
    private long TXBytes;
    private long RXRate;
    private long TXRate;
    private long lastActivity;
    private String bssid;
    private String name;
    private String hostName;
    private String defaultName;
    private String vendorName;
    private String host_type;
    private String interfaceStr;
    private String id;
    private String hostId;
    private String deviceMac;

    private String APMac;
    private String access_point;
    private long conn_duration;
    private int signal;
    private int inactive;
    private List<Connectivity> connectivities;
    private Timestamp saveTime;

    public String getAccess_point() {
        return access_point;
    }

    public void setAccess_point(String access_point) {
        this.access_point = access_point;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public void setRXBytes(long RXBytes) {
        this.RXBytes = RXBytes;
    }

    public void setTXBytes(long TXBytes) {
        this.TXBytes = TXBytes;
    }
    public void setAPMac(String APMac) {
        this.APMac = APMac;
    }
    public void setRXRate(long RXRate) {
        this.RXRate = RXRate;
    }

    public void setTXRate(long TXRate) {
        this.TXRate = TXRate;
    }

    public void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setHost_type(String host_type) {
        this.host_type = host_type;
    }

    public void setInterfaceStr(String interfaceStr) {
        this.interfaceStr = interfaceStr;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setDeviceMac(String mac) {
        this.deviceMac = mac;
    }

    public void setConn_duration(long conn_duration) {
        this.conn_duration = conn_duration;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public void setInactive(int inactive) {
        this.inactive = inactive;
    }

    public void setConnectivities(List<Connectivity> connectivities) {
        this.connectivities = connectivities;
    }

    public void setSaveTime(Timestamp saveTime) {
        this.saveTime = saveTime;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isReachable() {
        return reachable;
    }

    public long getRXBytes() {
        return RXBytes;
    }

    public long getTXBytes() {
        return TXBytes;
    }

    public long getRXRate() {
        return RXRate;
    }

    public long getTXRate() {
        return TXRate;
    }

    public long getLastActivity() {
        return lastActivity;
    }

    public String getBssid() {
        return bssid;
    }

    public String getName() {
        return name;
    }

    public String getHostName() {
        return hostName;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getHost_type() {
        return host_type;
    }

    public String getInterfaceStr() {
        return interfaceStr;
    }

    public String getId() {
        return id;
    }

    public String getHostId() {
        return hostId;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public String getAPMac() {
        return APMac;
    }

    public long getConn_duration() {
        return conn_duration;
    }

    public int getSignal() {
        return signal;
    }

    public int isInactive() {
        return inactive;
    }

    public List<Connectivity> getConnectivities() {
        return connectivities;
    }

    public Timestamp getSaveTime() {
        return saveTime;
    }
}
