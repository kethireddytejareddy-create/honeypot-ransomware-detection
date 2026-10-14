package com.security;

public class alert {

    private String message;
    private String type;
    private String attackerIp;

    public alert() {
    }

    public alert(String message, String type, String attackerIp) {
        this.message = message;
        this.type = type;
        this.attackerIp = attackerIp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttackerIp() {
        return attackerIp;
    }

    public void setAttackerIp(String attackerIp) {
        this.attackerIp = attackerIp;
    }
}