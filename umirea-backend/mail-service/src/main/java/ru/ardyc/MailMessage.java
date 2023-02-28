package ru.ardyc;

public class MailMessage {

    private String address;
    private String theme;
    private String description;

    public MailMessage(String address, String theme, String description) {
        this.address = address;
        this.theme = theme;
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public String getTheme() {
        return theme;
    }

    public String getDescription() {
        return description;
    }
}
