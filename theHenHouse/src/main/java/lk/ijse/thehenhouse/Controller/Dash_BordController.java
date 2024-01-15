package lk.ijse.thehenhouse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Dash_BordController {

    private AnchorPane root;
    @FXML

    private AnchorPane newDash;

    @FXML
    private AnchorPane dddd;

    @FXML
    private Button customerBtn;

    @FXML
    private Button itemsBtn;

    @FXML
    private JFXButton logutBtns;

    @FXML
    private Button orderBtn;

    @FXML
    private Button supplierBtn;

    @FXML
    private Button employeesBtn;

    @FXML
    private Button recivedItemsBtn;

    @FXML
    private AnchorPane root_second;

    @FXML
    private Label datelbl;

    @FXML
    private Label timeLbl;


    public void initialize() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashView.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);
        initClock();

    }
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, (e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            this.timeLbl.setText(LocalDateTime.now().format(formatter));
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
              this.datelbl.setText(formatter2.format(date));
        }, new KeyValue[0]), new KeyFrame(Duration.seconds(1.0D), new KeyValue[0])});
        clock.setCycleCount(-1);
        clock.play();
    }




    public void itemBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/itemForm.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);
    }

    public void orderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/orderForm.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);

    }

    public void suppliersOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/suppliersForm.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);
    }

    public void employeeBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);
    }

    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);

    }

    public void recivedBtnOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/recivedItemForm.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);
    }

    public void dashOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashView.fxml"));
        root_second.getChildren().clear();
        root_second.getChildren().add(anchorPane);
    }

}
