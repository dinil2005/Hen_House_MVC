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
import lk.ijse.thehenhouse.dto.tm.SupplierTM;
import lk.ijse.thehenhouse.model.Patterns;
import lk.ijse.thehenhouse.model.SupplierModel;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SuppliersFormController implements Initializable {

    @FXML
    private AnchorPane supplierAnchorPane;

    @FXML
    private TextField SupplierId;

    @FXML
    private TextField supplierName;

    @FXML
    private TextField supplierContac;

    @FXML
    private TextField supplierVehiclNum;

    @FXML
    private Button deletBtn;

    @FXML
    private Button serchBtn;

    @FXML
    private Button addSupplierBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private TableView<SupplierTM> supplierTbl;

    @FXML
    private TableColumn<?, ?> colsuId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colSupContact;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAll();
            setCellValueFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void setCellValueFactory() {
        colsuId.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("sup_name"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("sup_contact"));

    }

    void getAll() throws SQLException {
        ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
        List<SupplierTM> cusList = SupplierModel.getAll();


        for(SupplierTM supplierTM : cusList) {
            obList.add(new SupplierTM(
                    supplierTM.getSup_id(),
                    supplierTM.getSup_name(),
                    supplierTM.getSup_contact()
            ));

        }

        supplierTbl.setItems(obList);
    }

    public void addSuplierOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getSupplierIdPattern().matcher(SupplierId.getText()).matches()){
            if(Patterns.getSupplierNamePatern().matcher(supplierName.getText()).matches()){
                if(Patterns.getSupllierContactPatern().matcher(supplierContac.getText()).matches()){
                    String sup_id =SupplierId.getText();
                    String sup_name = supplierName.getText();
                    String sup_contact = supplierContac.getText();

                    try {
                        boolean add = SupplierModel.save(sup_id, sup_name, sup_contact);
                        if (add) {
                            Notify.playSound();
                            Notifications.create()
                                    .title("Notification")
                                    .text("Supplier ADD !!!")
                                    .position(Pos.TOP_RIGHT)
                                    .darkStyle()
                                    .showInformation();
                            getAll();
                        }
                    }catch (SQLException e){
                        Notify.playSound();
                        Notifications.create()
                                .title("Notification")
                                .text("A Supplier Is Registered For This ID Number. Please Check Again !!")
                                .position(Pos.TOP_RIGHT)
                                .darkStyle()
                                .showWarning();
                    }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid  Number. Please Check Again !!!")
                            .position(Pos.TOP_RIGHT)
                            .darkStyle()
                            .showWarning();
                }
            }else{
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Invalid  Name. Please Check Again !!!")
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

    public void serchOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getSupplierIdPattern().matcher(SupplierId.getText()).matches()){
            String itemId = SupplierId.getText();
            SupplierTM supplierTM = SupplierModel.search(itemId);
            if (supplierTM != null) {
                SupplierId.setText(supplierTM.getSup_id());
                supplierName.setText(supplierTM.getSup_name());
                supplierContac.setText(supplierTM.getSup_contact());
            } else {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("No Supplier is registered for this ID number")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showWarning();
            }
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid Supplier Id Please Check !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();
        }

    }

    public void updateOnAction(ActionEvent actionEvent) {
        if(Patterns.getSupplierIdPattern().matcher(SupplierId.getText()).matches()){
            if(Patterns.getSupplierNamePatern().matcher(supplierName.getText()).matches()){
                if(Patterns.getSupllierContactPatern().matcher(supplierContac.getText()).matches()){
                    String id = SupplierId.getText();
                    String name = supplierName.getText();
                    String contact = supplierContac.getText();
                    SupplierTM supplierTM = new SupplierTM(id,name,contact);
                    try {
                        boolean updateOk = SupplierModel.update(supplierTM);
                        if(updateOk){
                            Notify.playSound();
                            Notifications.create()
                                    .title("Notification")
                                    .text("Supplier Updated Success!")
                                    .position(Pos.TOP_RIGHT)
                                    .darkStyle()
                                    .showInformation();
                            getAll();
                        }
                    } catch (SQLException e) {
                        Notify.playSound();
                        new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
                    }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid Supplier Contact Please Check !!!")
                            .position(Pos.TOP_RIGHT)
                            .darkStyle()
                            .showWarning();
                }
            }else{
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Invalid Supplier Name Please Check !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showWarning();
            }
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid Supplier Id Please Check !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();
        }

    }

    public void deletOnAction(ActionEvent actionEvent) {
        String sup_id = SupplierId.getText();


        try {
            boolean  deleteItem = SupplierModel.delete(sup_id);
            if(deleteItem){
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Supplier Deleted!")
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
        supplierName.clear();
        supplierContac.clear();
        SupplierId.clear();
    }

    public void supplierKeyReleseOnAction(KeyEvent keyEvent) {
        String x =SupplierId.getText();
        if(Patterns.getSupplierIdPattern().matcher(SupplierId.getText()).matches()){
            SupplierId.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            SupplierId.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            SupplierId.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void supplierNameRelesedOnAction(KeyEvent keyEvent) {
        String x =supplierName.getText();
        if(Patterns.getSupplierNamePatern().matcher(supplierName.getText()).matches()){
           supplierName.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            supplierName.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            supplierName.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void contactRelesedOnAction(KeyEvent keyEvent) {
        String x =supplierContac.getText();
        if(Patterns.getSupllierContactPatern().matcher(supplierContac.getText()).matches()){
            supplierContac.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            supplierContac.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            supplierContac.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }
}
