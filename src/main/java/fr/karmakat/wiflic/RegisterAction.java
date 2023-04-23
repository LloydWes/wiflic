package fr.karmakat.wiflic;

public class RegisterAction {
    public String action;
    public String[] events;

    RegisterAction() {
        action = "register";
        events = new String[]{"lan_host_l3addr_reachable"};
    }
}
