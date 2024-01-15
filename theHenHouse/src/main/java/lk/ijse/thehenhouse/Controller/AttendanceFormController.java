package lk.ijse.thehenhouse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import lk.ijse.thehenhouse.Sound.Notify;
import org.controlsfx.control.Notifications;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.thehenhouse.model.CustomerModel;
import lk.ijse.thehenhouse.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AttendanceFormController implements Initializable {
    @FXML
    private AnchorPane AttendanceAnchorPane;

    @FXML
    private ComboBox<String> employeeIdCmboBox;

    @FXML
    private Label dateLbl;

    @FXML
    private ComboBox<String> ateendanceTypeCombox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAttendanceDate();
        getAllEmployeesId();
        setAtendanceType();
    }

    private void setAtendanceType() {
        String [] attendaneTypes = {"1 Hour","2 Hour","3 Hour","4 Hour"};
        ateendanceTypeCombox.getItems().addAll(attendaneTypes);
    }

    private void setAttendanceDate() {
        dateLbl.setText(String.valueOf(LocalDate.now()));
    }

    public void getAllEmployeesId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> employessId = EmployeeModel.getEmployeeAllId();
            for (String id : employessId) {
                obList.add(String.valueOf(id));
            }
            employeeIdCmboBox.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void conformOnAction(ActionEvent actionEvent)  {
        String employeeId= employeeIdCmboBox.getSelectionModel().getSelectedItem();
        String attendanceType = ateendanceTypeCombox.getSelectionModel().getSelectedItem();

        Double salary = 0.00;
        if (attendanceType.equals("1 Hour")){
            salary=2000.00;
        }if(attendanceType.equals("2 Hour")){
            salary=4000.00;
        }if(attendanceType.equals("3 Hour")){
            salary=6000.00;
        }if(attendanceType.equals("4 Hour")){
            salary=8000.00;
        }
        boolean isAdded = false;
        try {
            isAdded = EmployeeModel.addSalary(employeeId,salary);
        if (isAdded){
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Salary ADD")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Salary Not ADD")
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .showError();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


