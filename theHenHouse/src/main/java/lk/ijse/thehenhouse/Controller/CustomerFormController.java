package lk.ijse.thehenhouse.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thehenhouse.dto.tm.CustomerTM;
import lk.ijse.thehenhouse.Sound.Notify;
import lk.ijse.thehenhouse.model.CustomerModel;
import lk.ijse.thehenhouse.model.Patterns;
import org.controlsfx.control.Notifications;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javax.sound.sampled.*;


public class CustomerFormController implements Initializable {

    private final static String URL = "jdbc:mysql://localhost:3306/hen_house";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TableColumn<CustomerTM, String> colId;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colContact;

    @FXML
    private JFXTextField customerIdTxtField;

    @FXML
    private JFXTextField custNameTxtField;

    @FXML
    private JFXTextField custContacTxtField;

    @FXML
    private JFXTextField custAdressTxtField;

    @FXML
    private Button deletBtn;

    @FXML
    private Button serchBtn;

    @FXML
    private Button saveBtn;


    @FXML
    private Button updateBtn;

    @FXML
    private AnchorPane customeAnchorPane;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        loadDateCustomerTable();
        getAll();


    }


    void loadDateCustomerTable() {

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }
    void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<CustomerTM> cusList = CustomerModel.getAll();


            for (CustomerTM customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact()
                ));

            }

            tblCustomer.setItems(obList);
            customerIdTxtField.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();

            Notifications.create()
                    .title("Notification")
                    .text("Query error!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showError();


        }
    }


    public void saveOnAction(ActionEvent actionEvent) throws SQLException {

        if(Patterns.getCustomerIdPattern().matcher(customerIdTxtField.getText()).matches()){
            if(Patterns.getCustomerNamePattern().matcher(custNameTxtField.getText()).matches()){
              if(Patterns.getCustomerAddressPattern().matcher(custAdressTxtField.getText()).matches()){
                if(Patterns.getCustomerMobilePattern().matcher(custContacTxtField.getText()).matches()){
                    String id = customerIdTxtField.getText();
                    String description = custNameTxtField.getText();
                    String qtyOnHand = custAdressTxtField.getText();
                    String unitPrice =custContacTxtField.getText();
                    try {
                        boolean add = CustomerModel.save(id, description, qtyOnHand, unitPrice);
                        if (add) {
                            Notify.playSound();
                            Notifications.create()
                                    .title("Notification")
                                    .text("Customer Registered Successful !!!")
                                    .position(Pos.TOP_RIGHT)
                                    .darkStyle()
                                    .showInformation();
                                    getAll();
                        }
                    }catch (SQLException e){
                        Notify.playSound();
                        Notifications.create()
                                .title("Notification")
                                .text("A Customer Is Registered For This ID Number. Please Check Again !!!")
                                .darkStyle()
                                .position(Pos.TOP_RIGHT)
                                .showError();
                        Notify.playSound();
                    }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid Mobile Number. Please Check Again !!!")
                            .position(Pos.TOP_RIGHT)
                            .darkStyle()
                            .showError();
                }
              }else{
                  Notify.playSound();
                  Notifications.create()
                          .title("Notification")
                          .text("Invalid Address. Please Check Again  !!!")
                          .position(Pos.TOP_RIGHT)
                          .darkStyle()
                          .showError();

              }
            }else{
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Invalid  Name. Please Check Again !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showError();

            }
        }else {
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid ID. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showError();

        }

    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getCustomerIdPattern().matcher(customerIdTxtField.getText()).matches()){
            if(Patterns.getCustomerNamePattern().matcher(custNameTxtField.getText()).matches()){
                if(Patterns.getCustomerAddressPattern().matcher(custAdressTxtField.getText()).matches()){
                    if(Patterns.getCustomerMobilePattern().matcher(custContacTxtField.getText()).matches()){
                        String id = customerIdTxtField.getText();
                        String name = custNameTxtField.getText();
                        String adress = custAdressTxtField.getText();
                        String contact = custContacTxtField.getText();

                        CustomerTM customerTM = new CustomerTM(id, name, adress, contact);
                        try {
                            boolean updateOk = CustomerModel.update(customerTM);
                            if (updateOk) {
                                Notify.playSound();
                                Notifications.create()
                                        .title("Notification")
                                        .text("Customer Updated Successful !!!")
                                        .position(Pos.TOP_RIGHT)
                                        .darkStyle()
                                        .showInformation();
                                         getAll();
                            }
                        } catch (SQLException e) {
                            Notify.playSound();
                            Notifications.create()
                                    .title("Notification")
                                    .text("Sql Erro")
                                    .position(Pos.TOP_RIGHT)
                                    .darkStyle()
                                    .showInformation();

                        }
                    }else{
                        Notify.playSound();
                        Notifications.create()
                                .title("Notification")
                                .text("Invalid Mobile Number. Please Check Again !!!")
                                .position(Pos.TOP_RIGHT)
                                .darkStyle()
                                .showError();
                    }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid Address. Please Check Again  !!!")
                            .position(Pos.TOP_RIGHT)
                            .darkStyle()
                            .showError();
                }
            }else{
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Invalid  Name. Please Check Again !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showError();
            }
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid ID. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showError();

        }

    }

    public void serchOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getCustomerIdPattern().matcher(customerIdTxtField.getText()).matches()){
            String customerId = customerIdTxtField.getText();
            CustomerTM customerTM = CustomerModel.search(customerId);
            if (customerTM != null) {
                customerIdTxtField.setText(customerTM.getId());
                custNameTxtField.setText(customerTM.getName());
                custAdressTxtField.setText(customerTM.getAddress());
                custContacTxtField.setText(customerTM.getContact());
            } else {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("No Customer Is Registered For This Id Number")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();
            }
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalid ID. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();

        }


    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String itemId = customerIdTxtField.getText();


        try {
            boolean deleteItem = CustomerModel.delete(itemId);
            if (deleteItem) {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Customer Deleted Successful !!!")
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
        custNameTxtField.clear();
        customerIdTxtField.clear();
        custAdressTxtField.clear();
        custContacTxtField.clear();
    }

////////////////////////////// Customer Form txt key Released On Actions /////////////////////////////////////////////////////

   public void CustomeOnReleseAction(KeyEvent keyEvent) {
        String x = customerIdTxtField.getText();
        if( Patterns.getCustomerIdPattern().matcher(customerIdTxtField.getText()).matches()){
            customerIdTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30 ;");
           }else{
            customerIdTxtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30 ;");
           }if(x.isEmpty()){
           customerIdTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30 ;");
       }

    }

    public void customerNameReleseOnAction(KeyEvent keyEvent) {
        String x = custNameTxtField.getText();
        if(Patterns.getCustomerNamePattern().matcher(custNameTxtField.getText()).matches()){
            custNameTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30 ;");
        }else{
            custNameTxtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30 ;");
        }if(x.isEmpty()){
            custNameTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30 ;");
        }

    }

    public void customeAdresKeyReleseOnAction(KeyEvent keyEvent) {
        String x = custAdressTxtField.getText();
        if(Patterns.getCustomerAddressPattern().matcher(custAdressTxtField.getText()).matches()){
            custAdressTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            custAdressTxtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30 ;");
        }if(x.isEmpty()){
            custAdressTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30 ;");
        }
    }

    public void customerContacReleseOnAction(KeyEvent keyEvent) {
        String x = custContacTxtField.getText();
        if(Patterns.getCustomerMobilePattern().matcher(custContacTxtField.getText()).matches()){
            custContacTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            custContacTxtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            custContacTxtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }


    private static final String NOTIFICATION_SOUND_FILE = "D:\\Semeseter 1 Final Project\\notificationSound";

    private static void playNotificationSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audio = AudioSystem.getAudioInputStream(new File(NOTIFICATION_SOUND_FILE));
        Clip clip =AudioSystem.getClip();
        clip.open(audio);
        clip.start();
    }

}


