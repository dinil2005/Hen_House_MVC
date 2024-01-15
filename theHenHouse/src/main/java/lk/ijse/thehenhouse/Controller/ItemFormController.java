package lk.ijse.thehenhouse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thehenhouse.Sound.Notify;
import lk.ijse.thehenhouse.dto.tm.itemTM;
import lk.ijse.thehenhouse.model.ItemModel;
import lk.ijse.thehenhouse.model.Patterns;
import org.controlsfx.control.Notifications;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ItemFormController implements Initializable {

    @FXML
    private AnchorPane itemAnchorPane;

    @FXML
    private TableView<itemTM> itemTable;


    @FXML
    private TableColumn<?, ?> itemIdRow;

    @FXML
    private TableColumn<?, ?> descriptionRow;

    @FXML
    private TableColumn<?, ?> unitPriceRow;

    @FXML
    private TableColumn<?, ?> qtyOnHnadRow;

    @FXML
    private TextField itemIdTxtFiled;

    @FXML
    private TextField descriptionTxtField;

    @FXML
    private TextField unitPriceTxtField;

    @FXML
    private TextField qtyOnHandTxtField;

    @FXML
    private Button addItemBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deletBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();

    }

    void setCellValueFactory() {
        itemIdRow.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        descriptionRow.setCellValueFactory(new PropertyValueFactory<>("item_Description"));
        unitPriceRow.setCellValueFactory(new PropertyValueFactory<>("item_unit_price"));
        qtyOnHnadRow.setCellValueFactory(new PropertyValueFactory<>("item_qty_on_hand"));
    }

    void getAll() {
        try {
            ObservableList<itemTM> obList = FXCollections.observableArrayList();
            List<itemTM> itemList = ItemModel.getAllItems();

            for(itemTM item : itemList) {
                obList.add(new itemTM(
                        item.getItem_id(),
                        item.getItem_Description(),
                        item.getItem_unit_price(),
                        item.getItem_qty_on_hand()
                ));
            }
            itemTable.setItems(obList);
            itemTable.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Query error!")
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
        }
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getItemIdPattern().matcher(itemIdTxtFiled.getText()).matches()){
            if(Patterns.getItemDescriptionPattern().matcher(descriptionTxtField.getText()).matches()){
                if(Patterns.getItemUnitPricePattern().matcher(unitPriceTxtField.getText()).matches()){
                    if(Patterns.getItemQtyPattern().matcher(qtyOnHandTxtField.getText()).matches()){
                        String id = itemIdTxtFiled.getText();
                        String description = descriptionTxtField.getText();
                        Double unitPrice = Double.valueOf(unitPriceTxtField.getText());
                        int qtyOnHand = Integer.parseInt(qtyOnHandTxtField.getText());

                        try {
                            boolean add = ItemModel.save(id, description, unitPrice, qtyOnHand);
                            if (add) {
                                Notify.playSound();
                                Notifications.create()
                                        .title("Notification")
                                        .text("Item ADD Successful!!!")
                                        .position(Pos.TOP_RIGHT)
                                        .darkStyle()
                                        .showInformation();
                                getAll();
                            }
                        }catch (SQLException e){
                            Notify.playSound();
                            Notifications.create()
                                    .title("Notification")
                                    .text("A Item Is Registered For This ID Number. Please Check Again !!")
                                    .position(Pos.TOP_RIGHT)
                                    .darkStyle()
                                    .showError();
                        }
                    }else{
                        Notify.playSound();
                        Notifications.create()
                                .title("Notification")
                                .text("Invalid  Qty. Please Check Again !!!")
                                .position(Pos.TOP_RIGHT)
                                .darkStyle()
                                .showWarning();
                    }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid  Unit Price. Please Check Again !!!")
                            .position(Pos.TOP_RIGHT)
                            .darkStyle()
                            .showWarning();
                }
            }else{
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Invalid  Description. Please Check Again !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showError();
            }
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid  ID. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showError();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getItemIdPattern().matcher(itemIdTxtFiled.getText()).matches()){
            String itemId = itemIdTxtFiled.getText();
            itemTM itemTM = ItemModel.search(itemId);

            if (itemTM != null) {
                itemIdTxtFiled.setText(itemTM.getItem_id());
                descriptionTxtField.setText(itemTM.getItem_Description());
                unitPriceTxtField.setText(String.valueOf(itemTM.getItem_unit_price()));
                qtyOnHandTxtField.setText(String.valueOf(itemTM.getItem_qty_on_hand()));
            } else {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("There are No items for this ID number ")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showWarning();
            }
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid  Id. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showError();
        }

    }

    public void updateOnAction(ActionEvent actionEvent) {
        if(Patterns.getItemIdPattern().matcher(itemIdTxtFiled.getText()).matches()){
            if(Patterns.getItemDescriptionPattern().matcher(descriptionTxtField.getText()).matches()){
                if(Patterns.getItemUnitPricePattern().matcher(unitPriceTxtField.getText()).matches()){
                    if(Patterns.getItemQtyPattern().matcher(qtyOnHandTxtField.getText()).matches()){
                        String id = itemIdTxtFiled.getText();
                        String description = descriptionTxtField.getText();
                        Double unitPrice = Double.valueOf(unitPriceTxtField.getText());
                        int qtyOnHand = Integer.parseInt(qtyOnHandTxtField.getText());

                        itemTM item = new itemTM(id,description,unitPrice,qtyOnHand);

                        try {
                            boolean updateOk = ItemModel.update(item);
                            if(updateOk){
                                Notify.playSound();
                                Notifications.create()
                                        .title("Notification")
                                        .text("Item Updated Successful !!!")
                                        .position(Pos.TOP_RIGHT)
                                        .darkStyle()
                                        .showInformation();
                                        getAll();
                            }
                        } catch (SQLException e) {
                            Notify.playSound();
                            Notifications.create()
                                    .title("Notification")
                                    .text("Sql error!")
                                    .position(Pos.TOP_RIGHT)
                                    .showInformation();
                        }
                    }else{
                        Notify.playSound();
                        Notifications.create()
                                .title("Notification")
                                .text("Invalid  Qty. Please Check Again !!!")
                                .position(Pos.TOP_RIGHT)
                                .darkStyle()
                                .showWarning();
                    }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid  Unit Price. Please Check Again !!!")
                            .position(Pos.TOP_RIGHT)
                            .darkStyle()
                            .showWarning();
                }
            }else{
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Invalid  Description. Please Check Again !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showWarning();
            }
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid  ID. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();
        }



    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String itemId = itemIdTxtFiled.getText();


        try {
            boolean  deleteItem = ItemModel.delete(itemId);
            if(deleteItem){
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Item Deleted!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();
                getAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void clearOnAction(ActionEvent actionEvent) {
        itemIdTxtFiled.clear();
        descriptionTxtField.clear();
        unitPriceTxtField.clear();
        qtyOnHandTxtField.clear();
    }

    public void itemIdKeyReleasedOnAction(KeyEvent keyEvent) {
        String x = itemIdTxtFiled.getText();
        if(Patterns.getItemIdPattern().matcher(itemIdTxtFiled.getText()).matches()){
            itemIdTxtFiled.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            itemIdTxtFiled.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            itemIdTxtFiled.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void itemDescKeyReleasedOnAction(KeyEvent keyEvent) {
        String x =descriptionTxtField.getText();
        if(Patterns.getItemDescriptionPattern().matcher(descriptionTxtField.getText()).matches()){
            descriptionTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            descriptionTxtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            descriptionTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }

    }

    public void unitPriceKeyReleseOnAction(KeyEvent keyEvent) {
        String x =unitPriceTxtField.getText();
        if(Patterns.getItemUnitPricePattern().matcher(unitPriceTxtField.getText()).matches()){
            unitPriceTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            unitPriceTxtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            unitPriceTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void qtyKeyRelseOnAction(KeyEvent keyEvent) {
        String x =qtyOnHandTxtField.getText();
        if(Patterns.getItemQtyPattern().matcher(qtyOnHandTxtField.getText()).matches()){
            qtyOnHandTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            qtyOnHandTxtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            qtyOnHandTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }
}






