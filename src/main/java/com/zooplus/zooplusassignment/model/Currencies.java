package com.zooplus.zooplusassignment.model;

import java.util.Map;

public class Currencies {
    private boolean success;
    private Map<String, Currency> crypto;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Currency> getCrypto() {
        return crypto;
    }

    public void setCrypto(Map<String, Currency> crypto) {
        this.crypto = crypto;
    }
}
