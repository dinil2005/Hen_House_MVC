package lk.ijse.thehenhouse.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.CustomerTM;
import lk.ijse.thehenhouse.model.DashViewModel;
import lk.ijse.thehenhouse.util.CrudUtil;
import lombok.SneakyThrows;


import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

public class DashViewController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/hen_house";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }
    @FXML
    private AnchorPane dashViewAnchorPane;

    @FXML
    private Label timeViewLbl;

    @FXML
    private Label employeesCountLbl;

    @FXML
    private Label custViewLbl;

    @FXML
    private Label itemCountLbl;
    @FXML
    private Label custNumLbl;

    @FXML
    private Label employeNumLbl;

    @FXML
    private Label itemsNumLbl;

    @FXML
    private BarChart<?, ?> itemsBarChart;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();
        try {
            addCountCustomer();
            addCountEmployeesCount();
            addCountItemCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, (e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            this.timeViewLbl.setText(LocalDateTime.now().format(formatter));
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
          //  this.lbldate.setText(formatter2.format(date));
        }, new KeyValue[0]), new KeyFrame(Duration.seconds(1.0D), new KeyValue[0])});
        clock.setCycleCount(-1);
        clock.play();
    }

    void addCountCustomer() throws SQLException {
       DashViewModel dashViewModel = new DashViewModel();
       String x = dashViewModel.checkCustomerTotal();
        custNumLbl.setText(x);
    }

    @SneakyThrows
    void  addCountEmployeesCount(){
        DashViewModel dashViewModel = new DashViewModel();
        String x = dashViewModel.checkEmployeesTotal();
        employeesCountLbl.setText(x);
    }

    @SneakyThrows
    void  addCountItemCount(){
        DashViewModel dashViewModel = new DashViewModel();
        String x = dashViewModel.checkItemsTotal();
        itemCountLbl.setText(x);
    }

    }

