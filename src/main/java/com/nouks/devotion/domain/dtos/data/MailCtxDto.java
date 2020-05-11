package com.nouks.devotion.domain.dtos.data;

public class MailCtxDto {
    private String key;
    private Object value;

    public MailCtxDto() {
    }

    public MailCtxDto(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
