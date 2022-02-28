package com.zooplus.zooplusassignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Currency {
    private String symbol;
    private String name;
    @JsonProperty("name_full")
    private String nameFull;
    @JsonProperty("icon_url")
    private String iconUrl;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
