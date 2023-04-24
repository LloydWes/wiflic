package fr.karmakat.wiflic.domain;

public class DeviceEvent {
    public String action;
    public String event;
    public String source;
    public Boolean success;
    public Result result;

    public DeviceEvent() {
    }

    public DeviceEvent(String action, String event, String source, Boolean success, Result result) {
        this.action = action;
        this.event = event;
        this.source = source;
        this.success = success;
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
