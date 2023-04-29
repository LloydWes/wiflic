package fr.karmakat.wiflic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    public Boolean active;
    public String default_name;
    public Long first_activity;
    public String host_type;
    public String id;
    @JsonProperty("interface")
    public String interface2;
    public Long last_activity;
    public Long last_time_reachable;
    public Boolean persistent;
    public String primary_name;
    public Boolean primary_name_manual;
    public Boolean reachable;
    public String vendor_name;
    public List<Names> names;
    public L2ident l2ident;
    public String model;
    public List<L3connectivities> l3connectivities;

    public Result() {
    }

    public Result(Boolean active, String default_name, Long first_activity, String host_type, String id, String interface2,
                  Long last_activity, Long last_time_reachable, Boolean persistent, String primary_name, Boolean primary_name_manual,
                  Boolean reachable, String vendor_name, List<Names> names, L2ident l2ident, List<L3connectivities> l3connectivities) {
        this.active = active;
        this.default_name = default_name;
        this.first_activity = first_activity;
        this.host_type = host_type;
        this.id = id;
        this.interface2 = interface2;
        this.last_activity = last_activity;
        this.last_time_reachable = last_time_reachable;
        this.persistent = persistent;
        this.primary_name = primary_name;
        this.primary_name_manual = primary_name_manual;
        this.reachable = reachable;
        this.vendor_name = vendor_name;
        this.names = names;
        this.l2ident = l2ident;
        this.l3connectivities = l3connectivities;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDefault_name() {
        return default_name;
    }

    public void setDefault_name(String default_name) {
        this.default_name = default_name;
    }

    public Long getFirst_activity() {
        return first_activity;
    }

    public void setFirst_activity(Long first_activity) {
        this.first_activity = first_activity;
    }

    public String getHost_type() {
        return host_type;
    }

    public void setHost_type(String host_type) {
        this.host_type = host_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterface2() {
        return interface2;
    }

    public void setInterface2(String interface2) {
        this.interface2 = interface2;
    }

    public Long getLast_activity() {
        return last_activity;
    }

    public void setLast_activity(Long last_activity) {
        this.last_activity = last_activity;
    }

    public Long getLast_time_reachable() {
        return last_time_reachable;
    }

    public void setLast_time_reachable(Long last_time_reachable) {
        this.last_time_reachable = last_time_reachable;
    }

    public Boolean getPersistent() {
        return persistent;
    }

    public void setPersistent(Boolean persistent) {
        this.persistent = persistent;
    }

    public String getPrimary_name() {
        return primary_name;
    }

    public void setPrimary_name(String primary_name) {
        this.primary_name = primary_name;
    }

    public Boolean getPrimary_name_manual() {
        return primary_name_manual;
    }

    public void setPrimary_name_manual(Boolean primary_name_manual) {
        this.primary_name_manual = primary_name_manual;
    }

    public Boolean getReachable() {
        return reachable;
    }

    public void setReachable(Boolean reachable) {
        this.reachable = reachable;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public List<Names> getNames() {
        return names;
    }

    public void setNames(List<Names> names) {
        this.names = names;
    }

    public L2ident getL2ident() {
        return l2ident;
    }

    public void setL2ident(L2ident l2ident) {
        this.l2ident = l2ident;
    }

    public List<L3connectivities> getL3connectivities() {
        return l3connectivities;
    }

    public void setL3connectivities(List<L3connectivities> l3connectivities) {
        this.l3connectivities = l3connectivities;
    }
}
