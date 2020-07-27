package com.model;

import java.io.Serializable;

public class Attach implements Serializable {
    private String path;
    private AttachType attachType;

    public Attach(String path, AttachType attachType) {
        this.path = path;
        this.attachType = attachType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AttachType getAttachType() {
        return attachType;
    }

    public void setAttachType(AttachType attachType) {
        this.attachType = attachType;
    }
}
