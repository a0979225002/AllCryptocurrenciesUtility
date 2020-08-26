package com.lipin.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CryptocurrencyModel {
    //使用Property目的是為了能隨時更新view的資訊
    private IntegerProperty rank;
    private StringProperty name;
    private StringProperty symbol;
    private StringProperty marketCap;
    private StringProperty price;
    private StringProperty volume_24h;
    private StringProperty circulating_Supply;
    private StringProperty change_1h;
    private StringProperty change_24h;
    private StringProperty change_7d;

    public CryptocurrencyModel(int rank , String name,String symbol,
                               String marketCap, String price,
                               String circulating_Supply, String volume_24h,
                               String change_1h, String change_24h,
                               String change_7d) {
        this.rank = new SimpleIntegerProperty(rank);
        this.name = new SimpleStringProperty(name);
        this.symbol = new SimpleStringProperty(symbol);
        this.marketCap = new SimpleStringProperty(marketCap);
        this.price = new SimpleStringProperty(price);
        this.circulating_Supply = new SimpleStringProperty(circulating_Supply);
        this.volume_24h = new SimpleStringProperty(volume_24h);
        this.change_1h = new SimpleStringProperty(change_1h);
        this.change_24h = new SimpleStringProperty(change_24h);
        this.change_7d = new SimpleStringProperty(change_7d);
    }

    public int getRank() {
        return rank.get();
    }

    public IntegerProperty rankProperty() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank.set(rank);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public StringProperty symbolProperty() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol.set(symbol);
    }

    public String getMarketCap() {
        return marketCap.get();
    }

    public StringProperty marketCapProperty() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap.set(marketCap);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getVolume_24h() {
        return volume_24h.get();
    }

    public StringProperty volume_24hProperty() {
        return volume_24h;
    }

    public void setVolume_24h(String volume_24h) {
        this.volume_24h.set(volume_24h);
    }

    public String getCirculating_Supply() {
        return circulating_Supply.get();
    }

    public StringProperty circulating_SupplyProperty() {
        return circulating_Supply;
    }

    public void setCirculating_Supply(String circulating_Supply) {
        this.circulating_Supply.set(circulating_Supply);
    }

    public String getChange_1h() {
        return change_1h.get();
    }

    public StringProperty change_1hProperty() {
        return change_1h;
    }

    public void setChange_1h(String change_1h) {
        this.change_1h.set(change_1h);
    }

    public String getChange_24h() {
        return change_24h.get();
    }

    public StringProperty change_24hProperty() {
        return change_24h;
    }

    public void setChange_24h(String change_24h) {
        this.change_24h.set(change_24h);
    }

    public String getChange_7d() {
        return change_7d.get();
    }

    public StringProperty change_7dProperty() {
        return change_7d;
    }

    public void setChange_7d(String change_7d) {
        this.change_7d.set(change_7d);
    }
}
