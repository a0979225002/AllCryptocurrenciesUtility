package com.lipin;

import com.lipin.Utils.Currency_Information_Util;
import com.lipin.controller.CryptocurrenciesController;
import com.lipin.model.CryptocurrencyModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JavaFX App
 */
public class App extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    //ObservableList = JavaFX新集合類,為了同步更新數據而新增的JavaFX類
    private ObservableList<CryptocurrencyModel> cryptocurrencyData = FXCollections.observableArrayList();

//    Construtor
    public App(){



        LinkedHashMap<String, ArrayList<String>> cryptocurrenciesInMap = new LinkedHashMap<>();
        Currency_Information_Util currencyInformationUtil = new Currency_Information_Util();

        cryptocurrenciesInMap = currencyInformationUtil.getCryptocurrencies(currencyInformationUtil.getCryptocurrenciesJson(
                Currency_Information_Util.apiKey,
                Currency_Information_Util.start,
                Currency_Information_Util.limit,
                Currency_Information_Util.convert
        ));


        /**
         * 格式:[0=name, 1=symbol, 2=market_cap,
         *      3=price, 4=circulating_supply, 5=volume_24h, 6=percent_change_1h,
         *          7=percent_change_24h, 8=percent_change_7d]
         * 取出Map的key
         * 然後尋訪,取出Value
         */
        int rank = 0;
        for (String key:cryptocurrenciesInMap.keySet()){
            rank++;
            cryptocurrencyData.add(new CryptocurrencyModel(
                    rank,
                    cryptocurrenciesInMap.get(key).get(0),
                    cryptocurrenciesInMap.get(key).get(1),
                    cryptocurrenciesInMap.get(key).get(2),
                    cryptocurrenciesInMap.get(key).get(3),
                    cryptocurrenciesInMap.get(key).get(4),
                    cryptocurrenciesInMap.get(key).get(5),
                    cryptocurrenciesInMap.get(key).get(6),
                    cryptocurrenciesInMap.get(key).get(7),
                    cryptocurrenciesInMap.get(key).get(8)
            ));
        }


    }




    public ObservableList<CryptocurrencyModel> getCryptocurrencyData(){
        return cryptocurrencyData;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("虛擬貨幣查詢工具");

        initRootLayout();
        addCryptocurrenciesLayout();
    }

    //初始化RootLayout
    public void initRootLayout(){
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

    //在RootLayout中加入CryptocurrenciesLayout
    public void addCryptocurrenciesLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("view/CryptocurrenciesLayout.fxml"));
        AnchorPane CryptocurrenciesOverview = null;
        try {
            CryptocurrenciesOverview = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rootLayout.setCenter(CryptocurrenciesOverview);

        CryptocurrenciesController controller = loader.getController();
        controller.setMainApp(this);

    }

    public Stage getPrimaryStage(){
        System.out.println("返回主頁");
        return primaryStage;
    }
    public static void main(String[] args) {
        launch();
    }
}