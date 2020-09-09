package com.lipin.model;

import com.lipin.Utils.SimpleBigDecimalProperty;
import com.lipin.Utils.SimpleBigIntegerProperty;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CryptocurrencyModel {
    //使用Property目的是為了能隨時更新view的資訊
    private IntegerProperty rank;
    private StringProperty name;
    private StringProperty symbol;
    private SimpleBigIntegerProperty marketCap;
    private SimpleBigDecimalProperty price;
    private SimpleBigIntegerProperty circulating_Supply;
    private SimpleBigIntegerProperty volume_24h;
    private SimpleBigDecimalProperty change_1h;
    private SimpleBigDecimalProperty change_24h;
    private SimpleBigDecimalProperty change_7d;
    private BooleanProperty booleanProperty;


    public CryptocurrencyModel(int rank , String name, String symbol,
                               BigInteger marketCap, BigDecimal price,
                               BigInteger circulating_Supply, BigInteger volume_24h,
                               BigDecimal change_1h, BigDecimal change_24h,
                               BigDecimal change_7d) {



        this.rank = new SimpleIntegerProperty(rank);
        this.name = new SimpleStringProperty(name);
        this.symbol = new SimpleStringProperty(symbol);
        this.marketCap = new SimpleBigIntegerProperty(marketCap);
        this.price = new SimpleBigDecimalProperty(price);
        this.circulating_Supply = new SimpleBigIntegerProperty(circulating_Supply);
        this.volume_24h = new SimpleBigIntegerProperty(volume_24h);
        this.change_1h = new SimpleBigDecimalProperty(change_1h);
        this.change_24h = new SimpleBigDecimalProperty(change_24h);
        this.change_7d = new SimpleBigDecimalProperty(change_7d);
    }

    public boolean isBooleanProperty() {
        return booleanProperty.get();
    }

    public BooleanProperty booleanPropertyProperty() {
        return booleanProperty;
    }

    public void setBooleanProperty(boolean booleanProperty) {
        this.booleanProperty.set(booleanProperty);
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

    public BigInteger getMarketCap() {
        return (BigInteger) marketCap.get();
    }

    public SimpleBigIntegerProperty marketCapProperty() {
        return marketCap;
    }

    public void setMarketCap(BigInteger marketCap) {
        this.marketCap.set(marketCap);
    }

    public BigDecimal getPrice() {
        return (BigDecimal) price.get();
    }

    public SimpleBigDecimalProperty priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public BigInteger getVolume_24h() {
        return (BigInteger) volume_24h.get();
    }

    public SimpleBigIntegerProperty volume_24hProperty() {
        return volume_24h;
    }

    public void setVolume_24h(BigInteger volume_24h) {
        this.volume_24h.set(volume_24h);
    }

    public BigInteger getCirculating_Supply() {
        return (BigInteger) circulating_Supply.get();
    }

    public SimpleBigIntegerProperty circulating_SupplyProperty() {
        return circulating_Supply;
    }

    public void setCirculating_Supply(BigInteger circulating_Supply) {
        this.circulating_Supply.set(circulating_Supply);
    }

    public BigDecimal getChange_1h() {
        return (BigDecimal) change_1h.get();
    }

    public SimpleBigDecimalProperty change_1hProperty() {
        return change_1h;
    }

    public void setChange_1h(BigDecimal change_1h) {
        this.change_1h.set(change_1h);
    }

    public BigDecimal getChange_24h() {
        return (BigDecimal) change_24h.get();
    }

    public SimpleBigDecimalProperty change_24hProperty() {
        return change_24h;
    }

    public void setChange_24h(BigDecimal change_24h) {
        this.change_24h.set(change_24h);
    }

    public BigDecimal getChange_7d() {
        return (BigDecimal) change_7d.get();
    }

    public SimpleBigDecimalProperty change_7dProperty() {
        return change_7d;
    }

    public void setChange_7d(BigDecimal change_7d) {
        this.change_7d.set(change_7d);
    }
}
