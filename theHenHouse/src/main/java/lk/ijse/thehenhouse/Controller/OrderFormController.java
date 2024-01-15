package lk.ijse.thehenhouse.Controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.thehenhouse.Sound.Notify;
import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.CustomerTM;
import lk.ijse.thehenhouse.dto.tm.OrderCartT;
import lk.ijse.thehenhouse.dto.tm.ShopOrderDetailsTM;
import lk.ijse.thehenhouse.dto.tm.itemTM;
import lk.ijse.thehenhouse.model.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thehenhouse.qr.qrGenaroter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class OrderFormController implements Initializable {

    @FXML
    private AnchorPane orderAnchorPane;

    @FXML
    private TableView<ShopOrderDetailsTM> orderTable;
    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colAction;
    @FXML
    private Label orderIdLbl;

    @FXML
    private Label orderDateLbl;
    @FXML
    private Label totalLbl;

    @FXML
    private Label customerNameLbl;

    @FXML
    private ComboBox<String> customerIdComboBox;

    @FXML
    private ComboBox<String> itemIdDescriptionComBox;

    @FXML
    private Label unitPriceLbl;

    @FXML
    private Label itemCodeLbl;

    @FXML
    private Label qtyOnHandLbl;

    @FXML
    private TextField qtyTxtField;

    @FXML
    private JFXButton customerShortcutBtn;

    @FXML
    private Button addCartBtn;

    @FXML
    private Button conformBtn;


    private ObservableList<ShopOrderDetailsTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllItemDescriptions();
        setOrderDate();
        getAllCustomerId();
        generateNextOrderId();
        setCellValueFactory();


    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));


    }


    public void getAllItemDescriptions() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();

            List<String> description = ItemModel.getDescription();
            for (String desc : description) {
                obList.add(desc);
            }
            itemIdDescriptionComBox.setItems(obList);
        } catch (SQLException e) {
            System.out.println("erro");
        }

    }

    public void getAllCustomerId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> customerId = CustomerModel.getCustomerAllId();
            for (String id : customerId) {
                obList.add(String.valueOf(id));
            }
            customerIdComboBox.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setOrderDate() {
        orderDateLbl.setText(String.valueOf(LocalDate.now()));
    }

    public void itemDescriptionBoxOnAction(ActionEvent actionEvent) {
        String code = itemIdDescriptionComBox.getSelectionModel().getSelectedItem();

        try {
            itemTM item = ItemModel.searchById(code);
            setItemLabalsDetails(item);
            qtyTxtField.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setItemLabalsDetails(itemTM item) {
        itemCodeLbl.setText(item.getItem_id());
        unitPriceLbl.setText(String.valueOf(item.getItem_unit_price()));
        qtyOnHandLbl.setText(String.valueOf(item.getItem_qty_on_hand()));
    }

    private void generateNextOrderId() {
        try {
            String nextId = shopOrderModel.generateNextOrderId();
            orderIdLbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void customerIdComboBoxOnAction(ActionEvent actionEvent) {
        String cus_id = customerIdComboBox.getSelectionModel().getSelectedItem();
        try {
            CustomerTM customer = CustomerModel.searchById(cus_id);
            customerNameLbl.setText(customer.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("SQL error!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showInformation();
        }
    }


    public void addCartOnAction(ActionEvent actionEvent) {
        if(Patterns.getOrderQty().matcher(qtyTxtField.getText()).matches()){
            String code = itemCodeLbl.getText();
            String description = itemIdDescriptionComBox.getValue();
            double unitPrice = Double.parseDouble(unitPriceLbl.getText());
            int qty = Integer.parseInt(qtyTxtField.getText());

            double total = qty * unitPrice;
            Button btnRemove = new Button("Remove");
            btnRemove.setCursor(Cursor.HAND);
            totalLbl.setText(String.valueOf(total));

            setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

            if (!obList.isEmpty()) {
                for (int i = 0; i < orderTable.getItems().size(); i++) {
                    if (colItemCode.getCellData(i).equals(code)) {
                        qty += (int) colQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setQty(qty);
                        obList.get(i).setAmount(total);

                        orderTable.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            ShopOrderDetailsTM tm = new ShopOrderDetailsTM(code, description, unitPrice, qty, total, btnRemove);

            obList.add(tm);
            orderTable.setItems(obList);
            calculateNetTotal();

            qtyTxtField.setText("");
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Please Check Qty")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();
        }

    }

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            int index = orderTable.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Please Select Row First !!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();
            } else {
                Notify.playSound();
                Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();



                if (result.orElse(no) == yes) {

                    obList.remove(index);

                    orderTable.refresh();
                    calculateNetTotal();
                }
            }
        });

    }

    public void conformOnAction(ActionEvent actionEvent) {
        String oId = orderIdLbl.getText();
        String cusId = customerIdComboBox.getValue();

        List<OrderCartT> cartDTOList = new ArrayList<>();

        for (int i = 0; i < orderTable.getItems().size(); i++) {
            ShopOrderDetailsTM tm = obList.get(i);

            OrderCartT cartDTO = new OrderCartT(tm.getDescription(), tm.getOrder_id(), tm.getQty(), tm.getUnit_price());
            cartDTOList.add(cartDTO);
        }

        try {
            boolean isPlaced = ShopOrderPlaceModel.placeOrder(oId, cusId, cartDTOList);
            if (isPlaced) {
                generateNextOrderId();
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Your Order Placed!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();
            } else {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Order Not Placed!")
                        .darkStyle()
                        .position(Pos.TOP_RIGHT)
                        .showInformation();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("SQL error!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showInformation();
        }
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < orderTable.getItems().size(); i++) {
            double total  = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        totalLbl.setText(String.valueOf(netTotal));


    }

    public void reportOnAction(ActionEvent actionEvent) throws JRException, SQLException {



        JasperDesign jasperDesign = JRXmlLoader.load("D:\\theHenHouse\\src\\main\\resources\\reports\\order.jrxml");
        String sql = "SELECT * FROM shop_order_details";

        JRDesignQuery updateQuary = new JRDesignQuery();
        updateQuary.setText(sql);

        jasperDesign.setQuery(updateQuary);

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);
    }


    public void qtyReleseOnAction(KeyEvent keyEvent) {
       String x = qtyTxtField.getText();
        if(Patterns.getOrderQty().matcher(qtyTxtField.getText()).matches()){
            qtyTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            qtyTxtField.setStyle("-fx-text-fill:Red;-fx-background-radius:30;");
        }if(x.isEmpty()){
            qtyTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void qrOnAction(ActionEvent actionEvent) {
        if (!totalLbl.getText().isEmpty()) {
            qrGenaroter qrGenerator = new qrGenaroter();
            qrGenerator.setData(totalLbl.getText());
            try {
                qrGenerator.getGenerator();
            } catch (IOException | WriterException e) {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text(String.valueOf(e))
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();
            }
            File file = new File(qrGenerator.getPath());
            Image image = new Image(file.toURI().toString());
            QrFormController.image=image;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Qr.Form.fxml"));

            try {
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("QR CODE");
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Input Data First!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();
        }
    }


}





