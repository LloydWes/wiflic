package fr.karmakat.wiflic.model;

import java.sql.Timestamp;

public class Connectivity {
    private String adresse;
    private boolean active;
    private boolean reachable;
    private Timestamp lastActivity;
    private Timestamp lastTimeReachable;
    private String addresseType;
}
