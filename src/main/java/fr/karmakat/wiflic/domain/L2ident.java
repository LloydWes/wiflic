package fr.karmakat.wiflic.domain;

public class L2ident {
    public String id;
    public String type;

    public L2ident() {
    }

    public L2ident(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
