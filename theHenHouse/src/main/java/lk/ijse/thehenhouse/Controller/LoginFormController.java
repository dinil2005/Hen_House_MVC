package lk.ijse.thehenhouse.Controller;
//import animatefx.animation.Bounce;
//import com.jfoenix.controls.JFXPasswordField;
//import com.jfoenix.controls.JFXTextField;
//import controller.Financial_OfficerDashboard.FinancialOfficerDashboardController;
//import controller.crop_HarvestorDashboard.HarvestorDashboardController;
//import controller.face.UnlockingFormController;
//import controller.manager.DashBoardFormController;
//import db.DbConnection;
import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.lang.invoke.SwitchPoint;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//import module.User;
//import service.QrPerformance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.thehenhouse.Sound.Notify;
import lk.ijse.thehenhouse.model.ItemModel;
import lk.ijse.thehenhouse.model.Patterns;
import lk.ijse.thehenhouse.model.UserModel;

//import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.io.IOException;
import java.text.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class LoginFormController implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    private TextField email_txtField;

    @FXML
    private PasswordField password_txtField;

    @FXML
    private Button loginBtn;


    @FXML
    private Label timeLbl;


    public void LoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        email();
        isValidMail();

        }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, (e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            this.timeLbl.setText(LocalDateTime.now().format(formatter));
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            //  this.lbldate.setText(formatter2.format(date));
        }, new KeyValue[0]), new KeyFrame(Duration.seconds(1.0D), new KeyValue[0])});
        clock.setCycleCount(-1);
        clock.play();
    }

    private void isValidMail() {
        boolean isValidMail=false;
        String mail=email_txtField.getText();
         final Pattern emailPattern = Pattern.compile("^([a-z]{0,22})(\\d{0,20})([@gmail.com]{10,10})?$");
        if ( mail.matches(String.valueOf(emailPattern))){
            isValidMail=true;
        }else{
            Notify.playSound();
            Notifications.create()
                    .title("Notification")
                    .text("Invalied Mail")
                    .position(Pos.TOP_RIGHT)
                    .darkStyle()
                    .showWarning();
        }
    }

    void email() throws SQLException, IOException {
         String txtmail = email_txtField.getText();
         String txtPw = password_txtField.getText();

        boolean valid=UserModel.check(txtmail,txtPw);

      if(valid){

                 AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Dash_Bord.fxml"));
                 Scene scene = new Scene(anchorPane);
                 Stage stage =(Stage)root.getScene().getWindow();
                 stage.setScene(scene);
                 stage.setTitle("Dash Bord");
                 stage.centerOnScreen();
            }else{
          Notify.playSound();
          Notifications.create()
                  .title("Notification")
                  .text("User Name Wrong !!")
                  .darkStyle()
                  .position(Pos.TOP_RIGHT)
                  .showWarning();


          }
//      if (email_txtField.getText().isEmpty() || password_txtField.getText().isEmpty()){
//            Notifications.create()
//                    .title("Notification")
//                    .text("Input Values")
//                    .position(Pos.TOP_RIGHT)
//                    .showInformation();
//
//        }

        }

    public void emailKeyReleseOnAction(KeyEvent keyEvent) {
        String x = email_txtField.getText();
        if(Patterns.getEmailPattern().matcher(email_txtField.getText()).matches()){
            email_txtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            email_txtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            email_txtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    public void passwordKeyReleseOnAction(KeyEvent keyEvent) {
        String x =password_txtField.getText();
        if(Patterns.getPassword().matcher(password_txtField.getText()).matches()){
            password_txtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }else{
            password_txtField.setStyle("-fx-text-fill: RED;-fx-background-radius:30;");
        }if(x.isEmpty()){
            password_txtField.setStyle("-fx-text-fill: WHITE;-fx-background-radius:30;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();

    }

}





