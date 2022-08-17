package fr.karmakat.wiflic.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "test")
public class Result {
    private boolean success;
    private List<WifiDevice> wifiDevices;

    public Result() {
    }

    public boolean isSuccess() {
        return success;
    }

    public List<WifiDevice> getWifiDevices() {
        return wifiDevices;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setWifiDevices(List<WifiDevice> wifiDevices) {
        this.wifiDevices = wifiDevices;
    }
}
