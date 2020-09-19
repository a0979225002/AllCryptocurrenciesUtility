package com.lipin;

import com.lipin.Utils.Currency_Information_Util;
import com.lipin.Utils.XML_IO_Util;
import com.lipin.controller.CryptocurrenciesController;
import com.lipin.model.CryptocurrencyModel;
import com.lipin.model.MyLoveCheckboxOutputModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * JavaFX App
 */
public class App extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    XML_IO_Util xmlIoUtil = null;
    List<MyLoveCheckboxOutputModel> myLoveList = null;//從xml讀取來的list
    File file = new File("./myLoveSave.xml");

    //ObservableList = JavaFX新集合類,為了同步更新數據而新增的JavaFX類
    public ObservableList<CryptocurrencyModel> cryptocurrencyData = null;
    public ObservableList<CryptocurrencyModel> myLoveData = null;

    //Construtor
    public App() {
        xmlIoUtil = new XML_IO_Util();
        setTableData();
    }


    //對兩個Table加入資料
    public void setTableData() {

        cryptocurrencyData = FXCollections.observableArrayList();
        myLoveData = FXCollections.observableArrayList();

        LinkedHashMap<String, ArrayList> cryptocurrenciesInMap = new LinkedHashMap<>();
        Currency_Information_Util currencyInformationUtil = new Currency_Information_Util();

        cryptocurrenciesInMap = currencyInformationUtil.getCryptocurrencies(currencyInformationUtil.getCryptocurrenciesJson(
                Currency_Information_Util.apiKey,
                Currency_Information_Util.start,
                Currency_Information_Util.limit,
                Currency_Information_Util.convert
        ));
        //查看根目錄是否有保存我的最愛(./myLoveSave.xml)的存檔
        //xmlIoUtil.getMyLoveFilePath(file):
        //檢查是否有該(./myLoveSave.xml)檔案與該xml的註冊表,如果無該檔案或註冊表會回傳null
        if (xmlIoUtil.getMyLoveFilePath(file) != null && xmlIoUtil.getMyLoveFilePath(file).equals(file)) {
            myLoveList = xmlIoUtil.loadCryptocurrenciseFromFile(file);

            //將資料保存在Map中
            for (MyLoveCheckboxOutputModel data : myLoveList) {
                myLoveToMap.put(data.getName(), data.getBooleanProperty());

            }

        }

        /**
         * 格式:{ name , [0=name, 1=symbol, 2=market_cap,
         *      3=price, 4=circulating_supply, 5=volume_24h, 6=percent_change_1h,
         *          7=percent_change_24h, 8=percent_change_7d]}
         * 取出Map的key
         * 然後尋訪,取出Value
         */
        int rank = 0;
        for (String key : cryptocurrenciesInMap.keySet()) {
            rank++;
            cryptocurrencyData.add(new CryptocurrencyModel(
                    getXMlMyLove(key),
                    (Object) rank,
                    cryptocurrenciesInMap.get(key).get(0).toString(),
                    cryptocurrenciesInMap.get(key).get(1).toString(),
                    (BigInteger) cryptocurrenciesInMap.get(key).get(2),
                    (BigDecimal) cryptocurrenciesInMap.get(key).get(3),
                    (BigInteger) cryptocurrenciesInMap.get(key).get(4),
                    (BigInteger) cryptocurrenciesInMap.get(key).get(5),
                    (BigDecimal) cryptocurrenciesInMap.get(key).get(6),
                    (BigDecimal) cryptocurrenciesInMap.get(key).get(7),
                    (BigDecimal) cryptocurrenciesInMap.get(key).get(8)
            ));
        }
        for (CryptocurrencyModel data:cryptocurrencyData){
            if ((Boolean) data.isBooleanProperty()){
                myLoveData.add(data);
            }
        }
    }

    Map<String, Object> myLoveToMap = new HashMap<>();//將從xml讀取來的list轉成map
    //比對貨幣名稱,抓取該貨幣的Checkbox的布林值
    public Boolean getXMlMyLove(String name) {
        //如果有xml檔已經存在時
        if (myLoveList != null) {

            if (myLoveToMap.get(name) != null) {
                Boolean myLoveBoolean = (Boolean) myLoveToMap.get(name);
                return myLoveBoolean;
            } else {
                System.out.println(name);
                return false;
            }
        //如果沒有xml檔時,所有貨幣的Checkbox都是false
        } else {
            return false;
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("虛擬貨幣查詢工具");

        initRootLayout();
        addCryptocurrenciesLayout();
    }

    //初始化RootLayout
    public void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //顯示RootLayout
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

        getPrimaryStage();
    }

    public AnchorPane cryptocurrenciesOverview = null;

    //在RootLayout中加入CryptocurrenciesLayout
    public void addCryptocurrenciesLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("view/CryptocurrenciesLayout.fxml"));


        try {
            cryptocurrenciesOverview = loader.load();

            CryptocurrenciesController controller = loader.getController();
            controller.setMainApp(this);
            controller.searchListener(cryptocurrencyData, controller.cryptocurrencyTable);
            controller.searchListener(myLoveData,controller.cryptocurrencyTable_MyLove);


        } catch (IOException e) {
            e.printStackTrace();
        }

        rootLayout.setCenter(cryptocurrenciesOverview);


    }

    public Stage getPrimaryStage() {
        System.out.println("返回主頁");
        return primaryStage;
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        //如果Response回來的狀態非401正常狀態,將不給保存
        //避免撈不到資料時,被空List覆蓋資料
        if (Currency_Information_Util.responseStatusCode == 401){
            //將我的最愛的Checkbox ture or false 加入註冊表中,與xml檔中
            xmlIoUtil.saveCryptocurrenciseDataToFile(file, cryptocurrencyData);
        }
//        File file = new File("./sava.txt");
//
//        try {
//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
//
//            CryptocurrencyModel[] cryptocurrencyModels = new CryptocurrencyModel[cryptocurrencyData.size()];
//
//            cryptocurrencyData.toArray(cryptocurrencyModels);
//
//            out.writeObject(cryptocurrencyModels);
//
//            System.out.println(cryptocurrencyModels);
//        }catch (IOException e){
//            System.out.println(e.toString());
//        }

    }

    public static void main(String[] args) {
        launch();
    }
}