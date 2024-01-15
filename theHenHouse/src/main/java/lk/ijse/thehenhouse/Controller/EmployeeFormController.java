package lk.ijse.thehenhouse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.thehenhouse.Sound.Notify;
import org.controlsfx.control.Notifications;
import lk.ijse.thehenhouse.dto.tm.EmployeeTM;
import lk.ijse.thehenhouse.model.EmployeeModel;
import lk.ijse.thehenhouse.model.Patterns;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class EmployeeFormController implements Initializable {
    @FXML
    private AnchorPane employeeAnchorPane;

    @FXML
    private TextField employeid;

    @FXML
    private TextField empName;

    @FXML
    private TextField empContact;

    @FXML
    private TextField empAdresss;

    @FXML
    private Label employeeSalaryLbl;

    @FXML
    private Button deletBtn;

    @FXML
    private Button serchBtn;

    @FXML
    private Button addSupplierBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private TableView<EmployeeTM> employeeTbl;
    @FXML
    private TableColumn<?, ?> coldId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colAdress;

    @FXML
    private TableColumn<?, ?> colSalry;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();;
        getAll();
    }
    void setCellValueFactory() {
        coldId.setCellValueFactory(new PropertyValueFactory<>("Emplo_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Emplo_name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Emplo_contact"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("Emplo_address"));
        colSalry.setCellValueFactory(new PropertyValueFactory<>("Emplo_salary"));
    }

    void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<EmployeeTM> cusList = EmployeeModel.getAll();


            for(EmployeeTM employeeTM : cusList) {
                obList.add(new EmployeeTM(
                        employeeTM.getEmplo_id(),
                        employeeTM.getEmplo_name(),
                        employeeTM.getEmplo_contact(),
                        employeeTM.getEmplo_address(),
                        employeeTM.getEmplo_salary()
                ));


            }

            employeeTbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            Notifications.create()
                    .title("Notification")
                    .text("Query error!")
                    .showInformation();

        }
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getEmployeeIdPattern().matcher(employeid.getText()).matches()){
            if(Patterns.getEmployeeNamePatern().matcher(empName.getText()).matches()){
                if(Patterns.getEmployeeContactPatern().matcher(empContact.getText()).matches()){
                    if(Patterns.getEmployeeAdressPatern().matcher(empAdresss.getText()).matches()){
                        String id = employeid.getText();
                        String name = empName.getText();
                        String contact =empContact.getText();
                        String adress = empAdresss.getText();
                        Double salary = null;

                        try {
                            boolean add = EmployeeModel.save(id, name, contact, adress, salary);
                            if (add) {
                                Notify.playSound();
                                Notifications.create()
                                        .title("Notification")
                                        .text("Supplier ADD !!!")
                                        .darkStyle()
                                        .position(Pos.TOP_RIGHT)
                                        .showInformation();
                                getAll();
                            }
                        }catch (SQLException e){
                            Notify.playSound();
                            Notifications.create()
                                    .title("Notification")
                                    .text("A Employee Is Registered For This ID Number. Please Check Again !!")
                                    .position(Pos.TOP_RIGHT)
                                    .darkStyle()
                                    .showError();
                        }
                  }else{
                        Notify.playSound();
                        Notifications.create()
                                .title("Notification")
                                .text("Invalid  Address. Please Check Again !!!")
                                .darkStyle()
                                .position(Pos.TOP_RIGHT)
                                .showError();
                    }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid  Number. Please Check Again !!!")
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
                    .text("Invalid  ID. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showError();
            }
    }

    public void serchOnAction(ActionEvent actionEvent) throws SQLException {
        if(Patterns.getEmployeeIdPattern().matcher(employeid.getText()).matches()){
            String employeidText= employeid.getText();
            EmployeeTM employeeTM = EmployeeModel.search(employeidText);
            if (employeeTM != null) {
                employeid.setText(employeeTM.getEmplo_id());
                empName.setText(employeeTM.getEmplo_name());
                empContact.setText(employeeTM.getEmplo_contact());
                empAdresss.setText(employeeTM.getEmplo_address());
                employeeSalaryLbl.setText(String.valueOf(employeeTM.getEmplo_salary()));
            } else {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("No Employee is registered for this ID number")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showWarning();
            }
        }else {
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Wrong Employee Id")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        if(Patterns.getEmployeeIdPattern().matcher(employeid.getText()).matches()){
            if(Patterns.getEmployeeNamePatern().matcher(empName.getText()).matches()){
                if(Patterns.getEmployeeAdressPatern().matcher(empAdresss.getText()).matches()){
                   if(Patterns.getEmployeeContactPatern().matcher(empContact.getText()).matches()){
                       String id = employeid.getText();
                       String name = empName.getText();
                       String contact = empContact.getText();
                       String adress=empAdresss.getText();
                       Double salry = Double.valueOf(employeeSalaryLbl.getText());

                       EmployeeTM employeeTM = new EmployeeTM(id,name,contact,adress,salry);
                       try {
                           boolean updateOk = EmployeeModel.update(employeeTM);
                           if(updateOk){
                               Notify.playSound();
                               Notifications.create()
                                       .title("Notification")
                                       .text("Employee Updated Successful !!!")
                                       .position(Pos.TOP_RIGHT)
                                       .darkStyle()
                                       .showInformation();
                               getAll();
                           }
                       } catch (SQLException e) {
                           Notify.playSound();
                           Notifications.create()
                                   .title("Notification")
                                   .text("oops! something happened!!!!")
                                   .position(Pos.TOP_RIGHT)
                                   .showInformation();
                       }
                   } else{
                       Notify.playSound();
                       Notifications.create()
                               .title("Notification")
                               .text("Invalid  Number. Please Check Again !!!")
                               .position(Pos.TOP_RIGHT)
                               .darkStyle()
                               .showError();
                   }
                }else{
                    Notify.playSound();
                    Notifications.create()
                            .title("Notification")
                            .text("Invalid  Address. Please Check Again !!!")
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
                    .text("Invalid  ID. Please Check Again !!!")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showError();
        }


    }

    public void deletOnAction(ActionEvent actionEvent) {
        String itemId = employeid.getText();


        try {
            boolean deleteItem = EmployeeModel.delete(itemId);
            if (deleteItem) {
                Notify.playSound();
                Notifications.create()
                        .title("Notification")
                        .text("Supplier Deleted Successful !!!")
                        .position(Pos.TOP_RIGHT)
                        .darkStyle()
                        .showInformation();
                getAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atendaneOnAction(ActionEvent actionEvent) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/AttendanceForm.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Employees Attendance");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                getAll();
            }
        });
        stage.show();


    }

    public void clearOnAction(ActionEvent actionEvent) {
        employeid.clear();
        empName.clear();
        empContact.clear();
        empAdresss.clear();
        employeeSalaryLbl.setText(" ");

    }


////////////////////////////// Employee Form txt key Released On Actions /////////////////////////////////////////////////////


    public void employeeIdReleseOnAction(KeyEvent keyEvent) {
        String x =employeid.getText();
        if(Patterns.getEmployeeIdPattern().matcher(employeid.getText()).matches()){
            employeid.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            employeid.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            employeid.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void nameReleseKeyOnAction(KeyEvent keyEvent) {
        String x = empName.getText();
        if(Patterns.getEmployeeNamePatern().matcher(empName.getText()).matches()){
            empName.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            empName.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            empName.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }

    }

    public void contactKeyReleseOnAction(KeyEvent keyEvent) {
        String x = empContact.getText();
        if(Patterns.getEmployeeContactPatern().matcher(empContact.getText()).matches()){
            empContact.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            empContact.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            empContact.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void adressKeyReleseOnAction(KeyEvent keyEvent) {
        String x = empAdresss.getText();
        if(Patterns.getEmployeeAdressPatern().matcher(empAdresss.getText()).matches()){
            empAdresss.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            empAdresss.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            empAdresss.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

}

