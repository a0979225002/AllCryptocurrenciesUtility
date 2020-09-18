package com.lipin.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//包裝貨幣列表,保存用戶勾選我的最愛的資訊,轉成xml檔
@XmlRootElement(name = "MyLoveCheckboxes")
public class CryptocurrencyListWrapper {
    private List<CryptocurrencyModel> cryptocurrencyData;

    @XmlElement(name = "MyLoveCheckbox")
    public List<CryptocurrencyModel> getCryptocurrencyData(){
        return cryptocurrencyData;
    }

    public void setCryptocurrencyData(List<CryptocurrencyModel> cryptocurrencyData){
        this.cryptocurrencyData = cryptocurrencyData;
    }

}
