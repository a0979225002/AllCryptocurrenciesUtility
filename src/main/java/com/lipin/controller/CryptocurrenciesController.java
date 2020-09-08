package com.lipin.controller;

import com.lipin.App;
import com.lipin.Utils.Currency_Information_Util;
import com.lipin.model.CryptocurrencyModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

public class CryptocurrenciesController {
    @FXML
    private TableView<CryptocurrencyModel> cryptocurrencyTable;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> randColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> nameColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> symbolColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> marketCapColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> priceColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> circulating_SupplyColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> volume_24hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> change_1hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> change_24hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,Number> change_7dColumn;
    @FXML
    private TextField searchText;




    private App mainApp;

    //無參 Constructor
    public CryptocurrenciesController(){

    }

    //JavaFX 初始化調用,執行後就會自動調用
    @FXML
    private void initialize(){
        setTableData();

        //監聽用戶點擊Table哪個欄位
        cryptocurrencyTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CryptocurrencyModel>() {
            @Override
            public void changed(ObservableValue<? extends CryptocurrencyModel> observableValue, CryptocurrencyModel oldData, CryptocurrencyModel newData) {
                if (oldData!=null){
                    System.out.println("oldData:"+oldData.getName());
                }else {
                    System.out.println("null");
                }

                if (newData!=null) {
                    System.out.println("newData:" + newData.getName());
                }
            }
        });

    }


    /**
     * 載入TableView欄位資料
     */
    public void setTableData(){
        //將Number格式化數字用
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        cryptocurrencyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //一般寫法
        randColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CryptocurrencyModel, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<CryptocurrencyModel, Number> cryptocurrencyModelNumberCellDataFeatures) {
                return cryptocurrencyModelNumberCellDataFeatures.getValue().rankProperty();
            }
        });
        //增加Rand的格子寬度
        randColumn.setMinWidth(15);

        //lamda寫法
        nameColumn.setCellValueFactory(callbake->callbake.getValue().nameProperty());

        symbolColumn.setCellValueFactory(callbake->callbake.getValue().symbolProperty());
        marketCapColumn.setCellValueFactory(callbake->callbake.getValue().marketCapProperty());
        marketCapColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number ==null){
                            setText(null);
                        }else {
                            setText(numberFormat.format(number));
                        }
                    }
                };
            }
        });
        priceColumn.setCellValueFactory(callbake->callbake.getValue().priceProperty());
        priceColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number==null){
                            setText(null);
                        }else {
                            setTextFill(Color.rgb(0, 102, 255));
                            setText("$"+numberFormat.format(number));
                        }
                    }
                };
            }
        });
        circulating_SupplyColumn.setCellValueFactory(callbake->callbake.getValue().circulating_SupplyProperty());
        circulating_SupplyColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number == null){
                            setText(null);
                        }else {
                            setText(numberFormat.format(number));
                        }
                    }
                };
            }
        });
        volume_24hColumn.setCellValueFactory(callbake->callbake.getValue().volume_24hProperty());
        volume_24hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null){
                            setText(null);
                        }else {
                            setTextFill(Color.rgb(0, 102, 255));
                            setText("$"+numberFormat.format(number));
                        }
                    }
                };
            }
        });

        change_1hColumn.setCellValueFactory(callbake->callbake.getValue().change_1hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_1hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel,Number>(){
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number==null){
                            setText(null);
                        }else {
                            setText(number+"%");

                            setAlignment(Pos.CENTER);
                            if (number.doubleValue()<0){
                                setTextFill(Color.GREEN);
                            }else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });

        change_24hColumn.setCellValueFactory(callbake->callbake.getValue().change_24hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_24hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel,Number>(){
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number==null){
                            setText(null);

                        }else {
                            setText(number+"%");
                            setAlignment(Pos.CENTER);
                            if (number.doubleValue()<0){
                                setTextFill(Color.GREEN);
                            }else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });
        change_7dColumn.setCellValueFactory(callbake->callbake.getValue().change_7dProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_7dColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel,Number > call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel,Number>(){
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number==null){
                            setText(null);

                        }else {
                            setText(number+"%");
                            setAlignment(Pos.CENTER);
                            if (number.doubleValue()<0){

                                setTextFill(Color.GREEN);
                            }else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });
    }

    //搜尋功能
    public void searchListener(){
        System.out.println(mainApp == null);
        /**
         *  搜尋欄位功能
         */
//        將要搜尋的資料加入FilteredList,以便search時可以尋找該資料
        FilteredList<CryptocurrencyModel> fileterData = new FilteredList<>(mainApp.cryptocurrencyData, new Predicate<CryptocurrencyModel>() {
            @Override
            public boolean test(CryptocurrencyModel cryptocurrencyModel) {
                return true;
            }
        });
        searchText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                fileterData.setPredicate(new Predicate<CryptocurrencyModel>() {
                    @Override
                    public boolean test(CryptocurrencyModel cryptocurrencyModel) {

                        if (newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        //將字轉成小寫
                        String searchToLowerCase = newValue.toLowerCase();

                        //當cryptocurrencyModel內的貨幣名稱,有包含在搜尋的字中就返回true
                        if (cryptocurrencyModel.getName().contains(newValue)){
                            return true;//匹配貨幣名稱時
                        }else if (cryptocurrencyModel.getSymbol().contains(newValue)){
                            return true;//匹配貨幣簡稱時
                        }

                        return false;//不匹配
                    }
                });
            }
        });
        SortedList<CryptocurrencyModel> sortedData = new SortedList<>(fileterData);

        sortedData.comparatorProperty().bind(cryptocurrencyTable.comparatorProperty());
        cryptocurrencyTable.setItems(sortedData);
    }

    //載入取MainApp中add的Data;
    public void setMainApp(App mainApp){
        this.mainApp = mainApp;

        cryptocurrencyTable.setItems(mainApp.cryptocurrencyData);

    }

    /**
     * 刷新TableView數據的按鈕
     * @param actionEvent
     */
    public void updateTableview(ActionEvent actionEvent) {
        actionEvent.consume();
        mainApp = null;
        cryptocurrencyTable.setItems(null);

        setMainApp(new App());
        searchText.setText("");
        searchListener();

        System.out.println("有來媽");

    }
}
