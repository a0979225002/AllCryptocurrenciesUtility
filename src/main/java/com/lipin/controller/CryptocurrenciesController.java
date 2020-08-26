package com.lipin.controller;

import com.lipin.App;
import com.lipin.model.CryptocurrencyModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

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
    private TableColumn<CryptocurrencyModel,String> marketCapColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> priceColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> circulating_SupplyColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> volume_24hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> change_1hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> change_24hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel,String> change_7dColumn;

    private App mainApp;

    //無參 Constructor
    public CryptocurrenciesController(){

    }

    //JavaFX 初始化調用,執行後就會自動調用
    @FXML
    private void initialize(){
        cryptocurrencyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //一般寫法
        randColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CryptocurrencyModel, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<CryptocurrencyModel, Number> cryptocurrencyModelNumberCellDataFeatures) {
                return cryptocurrencyModelNumberCellDataFeatures.getValue().rankProperty();
            }
        });


        //lamda寫法
        nameColumn.setCellValueFactory(callbake->callbake.getValue().nameProperty());
        symbolColumn.setCellValueFactory(callbake->callbake.getValue().symbolProperty());
        marketCapColumn.setCellValueFactory(callbake->callbake.getValue().marketCapProperty());
        priceColumn.setCellValueFactory(callbake->callbake.getValue().priceProperty());
        circulating_SupplyColumn.setCellValueFactory(callbake->callbake.getValue().circulating_SupplyProperty());
        volume_24hColumn.setCellValueFactory(callbake->callbake.getValue().volume_24hProperty());
        change_1hColumn.setCellValueFactory(callbake->callbake.getValue().change_1hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_1hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, String>, TableCell<CryptocurrencyModel, String>>() {
            @Override
            public TableCell<CryptocurrencyModel, String> call(TableColumn<CryptocurrencyModel, String> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel,String>(){
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        if (s==null){
                            setText(null);
                        }else {
                            setText(s);

                            setAlignment(Pos.CENTER);
                            if (s.substring(0,1).equals("0")){
                                setTextFill(Color.RED);
                            }else {
                                setTextFill(Color.GREEN);
                            }
                        }

                    }
                };
            }
        });

        change_24hColumn.setCellValueFactory(callbake->callbake.getValue().change_24hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_24hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, String>, TableCell<CryptocurrencyModel, String>>() {
            @Override
            public TableCell<CryptocurrencyModel, String> call(TableColumn<CryptocurrencyModel, String> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel,String>(){
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);

                        if (s==null){
                            setText(null);

                        }else {
                            setText(s);
                            setAlignment(Pos.CENTER);
                            if (s.substring(0,1).equals("0")){
                                setTextFill(Color.RED);
                            }else {
                                setTextFill(Color.GREEN);
                            }
                        }

                    }
                };
            }
        });
        change_7dColumn.setCellValueFactory(callbake->callbake.getValue().change_7dProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_7dColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, String>, TableCell<CryptocurrencyModel, String>>() {
            @Override
            public TableCell<CryptocurrencyModel, String> call(TableColumn<CryptocurrencyModel, String> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel,String>(){
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);

                        if (s==null){
                            setText(null);

                        }else {
                            setText(s);
                            setAlignment(Pos.CENTER);
                            if (s.substring(0,1).equals("0")){
                                setTextFill(Color.RED);
                            }else {
                                setTextFill(Color.GREEN);
                            }
                        }

                    }
                };
            }
        });
    }

    public void setMainApp(App mainApp){
        this.mainApp = mainApp;

        cryptocurrencyTable.setItems(mainApp.getCryptocurrencyData());
    }

}
