package com.lipin.Utils;

import com.lipin.App;
import com.lipin.model.CryptocurrencyListWrapper;
import com.lipin.model.CryptocurrencyModel;
import com.lipin.model.MyLoveCheckboxOutputModel;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.collections.ObservableList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;


public class XML_IO_Util {
    public List<MyLoveCheckboxOutputModel> loadCryptocurrenciseFromFile(File file){
        List<MyLoveCheckboxOutputModel> myLoveLsit = new ArrayList();
        try {
            JAXBContext context = JAXBContext.newInstance(CryptocurrencyListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            //從xml中讀取資料
            CryptocurrencyListWrapper wrapper = (CryptocurrencyListWrapper) um.unmarshal(file);

            myLoveLsit.addAll(wrapper.getCryptocurrencyData());

        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("XML_IO_Util.loadCryptocurrenciseFromFile:"+e.toString());
        }

        return myLoveLsit;
    }

    //保存xml文件
    public void saveCryptocurrenciseDataToFile(File file, ObservableList<CryptocurrencyModel> cryptocurrencyData){
        try {
            JAXBContext context = JAXBContext.newInstance(CryptocurrencyListWrapper.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

            CryptocurrencyListWrapper wrapper = new CryptocurrencyListWrapper();
            wrapper.setCryptocurrencyData(cryptocurrencyData);

            //將xml保存
            m.marshal(wrapper,file);

            //將文件路徑保存在註冊表
            setCryptocurrencyDataFilePath(file);

        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("XML_IO_Util.saveCryptocurrenciseDataToFile:"+e.toString());
        }


    }

    //檢查有無xml檔,如無xml檔則回傳null
    public File getMyLoveFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        String filePath = prefs.get("myLoveSave", null);

        if (filePath != null&&file.exists()) {
            return new File(filePath);
        }else {
            return null;
        }
    }

    public void setCryptocurrencyDataFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);

        if (file != null) {
            prefs.put("myLoveSave", file.getPath());
            System.out.println("XML_IO_Util.setCryptocurrencyDataFilePath:保存成功:"+file.getPath());
        } else if (file.exists()){
            prefs.remove("myLoveSave");
            System.out.println("XML_IO_Util.setPreferencesFilePath:刪除成功");
        }
    }
}
