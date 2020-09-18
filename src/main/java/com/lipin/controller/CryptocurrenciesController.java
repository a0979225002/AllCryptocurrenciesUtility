package com.lipin.controller;

import com.lipin.App;
import com.lipin.Utils.Currency_Information_Util;
import com.lipin.model.CryptocurrencyModel;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
    public TableView<CryptocurrencyModel> cryptocurrencyTable;
    @FXML
    private TableColumn<CryptocurrencyModel, Object> randColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, String> nameColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, String> symbolColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> marketCapColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> priceColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> circulating_SupplyColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> volume_24hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> change_1hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> change_24hColumn;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> change_7dColumn;
    @FXML
    private TextField searchText;
    @FXML
    private Button addMyLove;


    @FXML
    public TableView<CryptocurrencyModel> cryptocurrencyTable_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Object> randColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, String> nameColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, String> symbolColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> marketCapColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> priceColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> circulating_SupplyColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> volume_24hColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> change_1hColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> change_24hColumn_MyLove;
    @FXML
    private TableColumn<CryptocurrencyModel, Number> change_7dColumn_MyLove;

    private App mainApp;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    //無參 Constructor
    public CryptocurrenciesController() {

    }

    //JavaFX 初始化調用,執行後就會自動調用
    public ObservableList<CryptocurrencyModel> myLoveData;
    @FXML
    private void initialize() {
        myLoveData = FXCollections.observableArrayList();
        setTableData();
        setMyLoveTable();

        //監聽用戶點擊Table哪個欄位
        cryptocurrencyTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CryptocurrencyModel>() {
            @Override
            public void changed(ObservableValue<? extends CryptocurrencyModel> observableValue, CryptocurrencyModel oldData, CryptocurrencyModel newData) {
                if (oldData != null) {
                    System.out.println("監聽oldData被點擊:" + oldData.getName());
                } else {
                    System.out.println("監聽oldData是否被點擊:null");
                }

                if (newData != null) {
                    System.out.println("newData:" + newData.isBooleanProperty());
                }
            }
        });


    }

    /**
     * 將有勾選為我的最愛的貨幣加入(我的最愛Table中)
     */
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    public void setMyLoveTable() {
        cryptocurrencyTable_MyLove.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //設置拖移功能
        cryptocurrencyTable_MyLove.setRowFactory(callback->{
            TableRow<CryptocurrencyModel> row =  new TableRow<>();
            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    CryptocurrencyModel t = myLoveData.remove(draggedIndex);
                    int dropIndex ;
                    if (row.isEmpty()) {
                        dropIndex = myLoveData.size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }
                    myLoveData.add(dropIndex, t);
                    event.setDropCompleted(true);
                    cryptocurrencyTable_MyLove.getSelectionModel().select(dropIndex);
                    event.consume();

                }
            });

            return row;
        });

        randColumn_MyLove.setCellValueFactory(callbck->callbck.getValue().rankProperty());
        randColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Object>, TableCell<CryptocurrencyModel, Object>>() {
            @Override
            public TableCell<CryptocurrencyModel, Object> call(TableColumn<CryptocurrencyModel, Object> cryptocurrencyModelObjectTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Object o, boolean b) {
                        super.updateItem(o, b);
                        int rank = 0;
                        if (o!=null){
                            rank++;
                            setText(rank+"");
                        }
                    }
                };
            }
        });
        nameColumn_MyLove.setCellValueFactory(callback->callback.getValue().nameProperty());
        symbolColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().symbolProperty());
        marketCapColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().marketCapProperty());
        marketCapColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setText(numberFormat.format(number));
                        }
                    }
                };
            }
        });
        priceColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().priceProperty());
        priceColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setTextFill(Color.rgb(0, 102, 255));
                            setText("$" + numberFormat.format(number));
                        }
                    }
                };
            }
        });
        circulating_SupplyColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().circulating_SupplyProperty());
        circulating_SupplyColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number == null) {
                            setText(null);
                        } else {
                            setText(numberFormat.format(number));
                        }
                    }
                };
            }
        });
        volume_24hColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().volume_24hProperty());
        volume_24hColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setTextFill(Color.rgb(0, 102, 255));
                            setText("$" + numberFormat.format(number));
                        }
                    }
                };
            }
        });

        change_1hColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().change_1hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_1hColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel, Number>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setText(number + "%");

                            setAlignment(Pos.CENTER);
                            if (number.doubleValue() < 0) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });

        change_24hColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().change_24hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_24hColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel, Number>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number == null) {
                            setText(null);

                        } else {
                            setText(number + "%");
                            setAlignment(Pos.CENTER);
                            if (number.doubleValue() < 0) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });
        change_7dColumn_MyLove.setCellValueFactory(callbake -> callbake.getValue().change_7dProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_7dColumn_MyLove.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel, Number>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number == null) {
                            setText(null);

                        } else {
                            setText(number + "%");
                            setAlignment(Pos.CENTER);
                            if (number.doubleValue() < 0) {

                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });

    }

    /**
     * 載入TableView欄位資料
     */
    public void setTableData() {
        //將Number格式化數字用

        cryptocurrencyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        randColumn.setCellValueFactory(callback -> callback.getValue().rankProperty());
        //增加Rand的格子寬度
        randColumn.setMinWidth(15);

        randColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Object>, TableCell<CryptocurrencyModel, Object>>() {
            @Override
            public TableCell<CryptocurrencyModel, Object> call(TableColumn<CryptocurrencyModel, Object> cryptocurrencyModelObjectTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object o, boolean b) {
                        super.updateItem(o, b);
                        setAlignment(Pos.CENTER);
                        if (o != null) {
                            setText(o.toString());
                        }

                    }
                };
            }
        });

        //lamda寫法
        nameColumn.setCellValueFactory(callbake -> callbake.getValue().nameProperty());

        symbolColumn.setCellValueFactory(callbake -> callbake.getValue().symbolProperty());
        marketCapColumn.setCellValueFactory(callbake -> callbake.getValue().marketCapProperty());
        marketCapColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setText(numberFormat.format(number));
                        }
                    }
                };
            }
        });
        priceColumn.setCellValueFactory(callbake -> callbake.getValue().priceProperty());
        priceColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setTextFill(Color.rgb(0, 102, 255));
                            setText("$" + numberFormat.format(number));
                        }
                    }
                };
            }
        });
        circulating_SupplyColumn.setCellValueFactory(callbake -> callbake.getValue().circulating_SupplyProperty());
        circulating_SupplyColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number == null) {
                            setText(null);
                        } else {
                            setText(numberFormat.format(number));
                        }
                    }
                };
            }
        });
        volume_24hColumn.setCellValueFactory(callbake -> callbake.getValue().volume_24hProperty());
        volume_24hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelNumberTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setTextFill(Color.rgb(0, 102, 255));
                            setText("$" + numberFormat.format(number));
                        }
                    }
                };
            }
        });

        change_1hColumn.setCellValueFactory(callbake -> callbake.getValue().change_1hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_1hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel, Number>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);
                        if (number == null) {
                            setText(null);
                        } else {
                            setText(number + "%");

                            setAlignment(Pos.CENTER);
                            if (number.doubleValue() < 0) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });

        change_24hColumn.setCellValueFactory(callbake -> callbake.getValue().change_24hProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_24hColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel, Number>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number == null) {
                            setText(null);

                        } else {
                            setText(number + "%");
                            setAlignment(Pos.CENTER);
                            if (number.doubleValue() < 0) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });
        change_7dColumn.setCellValueFactory(callbake -> callbake.getValue().change_7dProperty());

        //動態更改字體顏色,跟股票漲跌一樣,漲就是紅色,跌就是綠色
        change_7dColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Number>, TableCell<CryptocurrencyModel, Number>>() {
            @Override
            public TableCell<CryptocurrencyModel, Number> call(TableColumn<CryptocurrencyModel, Number> cryptocurrencyModelStringTableColumn) {
                return new TableCell<CryptocurrencyModel, Number>() {
                    @Override
                    protected void updateItem(Number number, boolean b) {
                        super.updateItem(number, b);

                        if (number == null) {
                            setText(null);

                        } else {
                            setText(number + "%");
                            setAlignment(Pos.CENTER);
                            if (number.doubleValue() < 0) {

                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }

                    }
                };
            }
        });
    }

    //搜尋功能
    public void searchListener(ObservableList tableData,TableView tableView) {
        /**
         *  搜尋欄位功能
         */
//        將要搜尋的資料加入FilteredList,以便search時可以尋找該資料
        FilteredList<CryptocurrencyModel> fileterData = new FilteredList<>(tableData, new Predicate<CryptocurrencyModel>() {
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

                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        //將字轉成小寫
                        String searchToLowerCase = newValue.toLowerCase();

                        //當cryptocurrencyModel內的貨幣名稱,有包含在搜尋的字中就返回true
                        if (cryptocurrencyModel.getName().contains(newValue)) {
                            return true;//匹配貨幣名稱時
                        } else if (cryptocurrencyModel.getSymbol().contains(newValue)) {
                            return true;//匹配貨幣簡稱時
                        }

                        return false;//不匹配
                    }
                });
            }
        });
        SortedList<CryptocurrencyModel> sortedData = new SortedList<>(fileterData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    //載入取MainApp中add的Data;
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;

        cryptocurrencyTable.setItems(mainApp.cryptocurrencyData);
        cryptocurrencyTable_MyLove.setItems(myLoveData);

    }

    /**
     * 刷新TableView數據的按鈕
     *
     * @param actionEvent
     */
    ObservableList<CryptocurrencyModel> cryptocurrencyData;

    public void updateTableview(ActionEvent actionEvent) {
        actionEvent.consume();


        cryptocurrencyData = cryptocurrencyTable.getItems();

        Currency_Information_Util currencyInformationUtil = new Currency_Information_Util();
        LinkedHashMap<String, ArrayList> cryptocurrenciesInMap = new LinkedHashMap<>();
        cryptocurrenciesInMap = currencyInformationUtil.getCryptocurrencies(currencyInformationUtil.getCryptocurrenciesJson(
                Currency_Information_Util.apiKey,
                Currency_Information_Util.start,
                Currency_Information_Util.limit,
                Currency_Information_Util.convert
        ));
        int rank = 0;
        for (CryptocurrencyModel cryptocurrencyModel : cryptocurrencyData) {
            String name = cryptocurrencyModel.getName();
            rank++;
            cryptocurrencyModel.setRank(rank);
            if (cryptocurrenciesInMap.get(name) != null) {
                cryptocurrencyModel.setSymbol(cryptocurrenciesInMap.get(name).get(1).toString());
                cryptocurrencyModel.setMarketCap((BigInteger) cryptocurrenciesInMap.get(name).get(2));
                cryptocurrencyModel.setPrice((BigDecimal) cryptocurrenciesInMap.get(name).get(3));
                cryptocurrencyModel.setCirculating_Supply((BigInteger) cryptocurrenciesInMap.get(name).get(4));
                cryptocurrencyModel.setVolume_24h((BigInteger) cryptocurrenciesInMap.get(name).get(5));
                cryptocurrencyModel.setChange_1h((BigDecimal) cryptocurrenciesInMap.get(name).get(6));
                cryptocurrencyModel.setChange_24h((BigDecimal) cryptocurrenciesInMap.get(name).get(7));
                cryptocurrencyModel.setChange_7d((BigDecimal) cryptocurrenciesInMap.get(name).get(8));
            } else {
                System.out.println("此虛擬貨幣" + name + "已經不存在");
            }

        }

        cryptocurrencyTable.setItems(cryptocurrencyData);

        searchText.setText("");
        searchListener(mainApp.cryptocurrencyData,cryptocurrencyTable);
        searchListener(myLoveData,cryptocurrencyTable_MyLove);

    }

    /**
     * 加入我的最愛列表
     *
     * @param actionEvent
     */
    boolean buttomEvent = true;
    public void addMyLove(ActionEvent actionEvent) {
        cryptocurrencyTable.setEditable(true);
        cryptocurrencyTable_MyLove.setEditable(true);

        //打開我的最愛功能時的判斷
        if (buttomEvent == true) {
            openMyLoveBottum();
            openMyLoveBottum(randColumn_MyLove);
            buttomEvent = false;
            //關閉我的最愛功能時的判斷
        } else {
            closeMyLoveButtom(randColumn);
            closeMyLoveButtom(randColumn_MyLove);
            buttomEvent = true;
        }

    }
    //更改綁定的列,目的讓MyLoveTable擁有動態調換第一個列rand的功能,能使用CheckBox功能
    private void openMyLoveBottum(TableColumn<CryptocurrencyModel,Object> column){
        column.setText("最愛");
        column.setCellValueFactory(callback->callback.getValue().booleanPropertyProperty());
        column.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Object>, TableCell<CryptocurrencyModel, Object>>() {
            @Override
            public TableCell<CryptocurrencyModel, Object> call(TableColumn<CryptocurrencyModel, Object> cryptocurrencyModelObjectTableColumn) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Object o, boolean b) {
                        super.updateItem(o, b);
                        CheckBox checkBox = null;
                        if (o!=null){
                            checkBox = new CheckBox();
                            setAlignment(Pos.CENTER);
                            if (this.getTableRow() != null) {

                                ObservableList<CryptocurrencyModel> listdata = this.getTableView().getItems();
                                CryptocurrencyModel cryptocurrencyModel = listdata.get(this.getTableRow().getIndex());
                                checkBox.selectedProperty().bindBidirectional(cryptocurrencyModel.booleanPropertyProperty());
                            }

                        }
                        setGraphic(checkBox);
                    }
                };
            }
        });

    }
    //AllTable的動態更改第一個列的功能,能夠使用CheckBox功能
    //當用戶點擊CheckBox將該貨幣加入最愛,會直接將該該列class加入(myLoveData)list中
    //反之將會將myLoveData這個list內該列index刪除
    private void openMyLoveBottum(){
        randColumn.setText("最愛");
        addMyLove.setText("關閉CheckBox");

        randColumn.setCellValueFactory(callBack -> callBack.getValue().booleanPropertyProperty());
        randColumn.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Object>, TableCell<CryptocurrencyModel, Object>>() {
            @Override
            public TableCell<CryptocurrencyModel, Object> call(TableColumn<CryptocurrencyModel, Object> cryptocurrencyModelObjectTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object o, boolean b) {
                        super.updateItem(o, b);
                        CheckBox checkBox = null;
                        if (o != null) {

                            checkBox = new CheckBox();
                            setAlignment(Pos.CENTER);
                            //綁定動態更改數據,當用戶點擊checkBox時,會隨即更新Model內的值
                            if (this.getTableRow() != null) {
                                ObservableList<CryptocurrencyModel> listdata = this.getTableView().getItems();
                                CryptocurrencyModel cryptocurrencyModel = listdata.get(this.getTableRow().getIndex());
                                checkBox.selectedProperty().bindBidirectional(cryptocurrencyModel.booleanPropertyProperty());

                                //如果用戶點擊將該貨幣加入我的最愛
                                //檢查該貨幣是否已存在在myLoveData中
                                //如果沒有存在就加入到myLoveData陣列中
                                if ((Boolean) o == true && !myLoveData.contains(listdata.get(this.getTableRow().getIndex()))) {
                                    myLoveData.add(listdata.get(this.getTableRow().getIndex()));
                                    cryptocurrencyTable_MyLove.setItems(myLoveData);
                                    searchListener(myLoveData,cryptocurrencyTable_MyLove);

                                    //當用戶選擇將該貨幣解除我的最愛時
                                    //檢查該貨幣是否已存在在myLoveData中
                                    //如果存在就將myLoveData陣列中該class刪除
                                } else if ((Boolean) o == false && myLoveData.contains(listdata.get(this.getTableRow().getIndex()))) {
                                    myLoveData.remove(listdata.get(this.getTableRow().getIndex()));
                                    cryptocurrencyTable_MyLove.setItems(myLoveData);
                                    searchListener(myLoveData,cryptocurrencyTable_MyLove);
                                }
                            }
                        }
                        setGraphic(checkBox);
                    }
                };
            }
        });
    }
    private void closeMyLoveButtom(TableColumn<CryptocurrencyModel,Object> column){
        column.setText("Rand");
        addMyLove.setText("開啟我的最愛");
        column.setCellValueFactory(callBack -> callBack.getValue().rankProperty());
        column.setCellFactory(new Callback<TableColumn<CryptocurrencyModel, Object>, TableCell<CryptocurrencyModel, Object>>() {
            @Override
            public TableCell<CryptocurrencyModel, Object> call(TableColumn<CryptocurrencyModel, Object> cryptocurrencyModelObjectTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Object o, boolean b) {
                        super.updateItem(o, b);

                        if (o != null) {
                            setAlignment(Pos.CENTER);
                            setText(o + "");
                        }
                    }
                };
            }
        });
    }
}
