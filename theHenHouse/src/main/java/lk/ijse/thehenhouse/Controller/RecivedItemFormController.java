package lk.ijse.thehenhouse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import lk.ijse.thehenhouse.Sound.Notify;
import org.controlsfx.control.Notifications;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thehenhouse.dto.SupplierCartDTO;
import lk.ijse.thehenhouse.dto.tm.*;
import lk.ijse.thehenhouse.model.ItemModel;
import lk.ijse.thehenhouse.model.Patterns;
import lk.ijse.thehenhouse.model.RecivedItemModel;
import lk.ijse.thehenhouse.model.SupplierOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecivedItemFormController implements Initializable {

    @FXML
    private AnchorPane recivedItemAnchorPane;

    @FXML
    private JFXComboBox<String> itemDescription;

    @FXML
    private Label unitPriceLbl;

    @FXML
    private Label itemCodeLbl;

    @FXML
    private JFXTextField qtyTxtField;

    @FXML
    private JFXButton addCartBtn;

    @FXML
    private Label orderIdLbl;

    @FXML
    private Label orderDateLbl;

    @FXML
    private JFXComboBox<String> supplierIdComboBox;

    @FXML
    private Label supplierNameLbl;

    @FXML
    private ImageView addBtn;

    @FXML
    private Label amoutnLbl;

    @FXML
    private JFXButton conformBtn;
    @FXML
    private TableView<Received_itemTM> recivedItemTble;
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> ColUnitPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> ColTotal;


    @FXML
    private Label totalLble;

    private ObservableList<Received_itemTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAllCustomerId();
            getAllItemDescriptions();
            setCellValueFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setCellValueFactory() {
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        ColUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));


    }

    public void getAllCustomerId() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        List<String> customerId = RecivedItemModel.getCustomerAllId();
        for (String id : customerId) {
            obList.add(String.valueOf(id));
        }
        supplierIdComboBox.setItems(obList);

    }

    public void supplierSelectOnAction(ActionEvent actionEvent) {
        String supplierId = supplierIdComboBox.getSelectionModel().getSelectedItem();
        try {
            SupplierTM customer = RecivedItemModel.searchById(supplierId);
            supplierNameLbl.setText(customer.getSup_name());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void getAllItemDescriptions() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();

            List<String> description = ItemModel.getDescription();
            for (String desc : description) {
                obList.add(desc);
            }
            itemDescription.setItems(obList);
        } catch (SQLException e) {
            System.out.println("erro");
        }

    }

    private void setItemLabalsDetails(itemTM item) {
        itemCodeLbl.setText(item.getItem_id());
        unitPriceLbl.setText(String.valueOf(item.getItem_unit_price()));

    }

    public void descriptionOnAction(ActionEvent actionEvent) {
        String code = itemDescription.getSelectionModel().getSelectedItem();

        try {
            itemTM item = ItemModel.searchById(code);
            setItemLabalsDetails(item);
            qtyTxtField.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void addCartOnAction(ActionEvent actionEvent) {
        if(Patterns.getOrderQty().matcher(qtyTxtField.getText()).matches()){
            String description = itemDescription.getValue();
            String code = itemCodeLbl.getText();
            double unitPrice = Double.parseDouble(unitPriceLbl.getText());
            int qty = Integer.parseInt(qtyTxtField.getText());
            double total = qty * unitPrice;

            //   Button btnRemove = new Button("Remove");
            // btnRemove.setCursor(Cursor.HAND);
            totalLble.setText(String.valueOf(total));

            //     setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

            if (!obList.isEmpty()) {
                for (int i = 0; i < recivedItemTble.getItems().size(); i++) {
                    if (colItemCode.getCellData(i).equals(code)) {
                        qty += (int) colQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setAmount(total);

                        recivedItemTble.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            Received_itemTM tm = new Received_itemTM(description, code, unitPrice, qty, total);

            obList.add(tm);
            recivedItemTble.setItems(obList);
            calculateNetTotal();

            qtyTxtField.setText("");
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalied Qty !!!")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .showWarning();
        }

    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < recivedItemTble.getItems().size(); i++) {
            double total = (double) ColTotal.getCellData(i);
            netTotal += total;
        }
        totalLble.setText(String.valueOf(netTotal));


    }

    @FXML
    void btnConformOnAAction(ActionEvent event) {
        String supplier_id = supplierIdComboBox.getSelectionModel().getSelectedItem();

        List<SupplierCartDTO> supplierCartDTO = new ArrayList<>();

        for (int i = 0; i < recivedItemTble.getItems().size(); i++) {

            Received_itemTM received_itemTM = obList.get(i);

            supplierCartDTO.add(new SupplierCartDTO(received_itemTM.getDescription(), received_itemTM.getItem_id(),
                    received_itemTM.getUnitPrice(), received_itemTM.getQty(), received_itemTM.getAmount()));

        }
        try {

            boolean isAdded = SupplierOrderModel.placeOrder(supplier_id, supplierCartDTO);

            if (isAdded){
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Received item Added !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();
            }else {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Received item not Added !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void qtyOnrelse(KeyEvent keyEvent) {
        String x = qtyTxtField.getText();
        if(Patterns.getOrderQty().matcher(qtyTxtField.getText()).matches()){
            qtyTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            qtyTxtField.setStyle("-fx-text-fill:Red;-fx-background-radius:30;");
        }if(x.isEmpty()){
            qtyTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }
    }

