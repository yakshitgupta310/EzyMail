package com.EzyMail.writer.dto;


public class Request {

    private String content;
    private String tone;

    public Request(){

    }

    public Request(String content, String tone) {
        this.content = content;
        this.tone = tone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }
}
