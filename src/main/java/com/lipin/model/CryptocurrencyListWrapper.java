package com.lipin.model;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//包裝貨幣列表,保存用戶勾選我的最愛的資訊,轉成xml檔
@XmlRootElement(name = "MyLoveCheckboxes")
public class CryptocurrencyListWrapper {
    private List<MyLoveCheckboxOutputModel> myLoveCheckboxData = new LinkedList<>();

    @XmlElement(name = "MyLoveCheckbox")
    public List<MyLoveCheckboxOutputModel> getCryptocurrencyData(){
        return myLoveCheckboxData;
    }

    public void setCryptocurrencyData(List<CryptocurrencyModel> cryptocurrencyData){
        for (CryptocurrencyModel data:cryptocurrencyData){
            myLoveCheckboxData.add(new MyLoveCheckboxOutputModel(
                    data.isBooleanProperty(),
                    data.getName()
            ));
        }
//        this.myLoveCheckboxData = cryptocurrencyData;
    }

}
