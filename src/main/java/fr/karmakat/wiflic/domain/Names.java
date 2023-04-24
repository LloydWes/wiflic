package fr.karmakat.wiflic.domain;

public class Names {
    public String name;

    public String source;

    public Names() {
    }

    public Names(String name, String source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
