package ru.ardyc.utils.mail;

public class Mail {
    private String address;
    private String theme;
    private String description;

    public Mail(String address, String theme, String description) {
        this.theme = theme;
        this.address = address;
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}
