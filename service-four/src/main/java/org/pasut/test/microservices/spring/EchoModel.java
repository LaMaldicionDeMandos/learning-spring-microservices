package org.pasut.test.microservices.spring;

public class EchoModel {
    private String message;

    public EchoModel() {}

    public EchoModel(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
