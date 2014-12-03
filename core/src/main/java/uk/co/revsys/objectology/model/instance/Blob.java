package uk.co.revsys.objectology.model.instance;

import java.io.InputStream;

public class Blob {

    private String id;
    private String contentType;
    private InputStream inputStream;

    public Blob() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
