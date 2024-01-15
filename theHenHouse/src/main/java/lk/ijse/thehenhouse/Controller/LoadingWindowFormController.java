package lk.ijse.thehenhouse.Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingWindowFormController implements Initializable {

    @FXML
    private ProgressBar prgBar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    updateProgress(i, 55);
                    Thread.sleep(55);
                }
                return null;
            }
        };

        prgBar.progressProperty().bind(task.progressProperty());



        task.setOnSucceeded(event -> {
            try {
                Parent loginParent = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
                Scene loginScene = new Scene(loginParent);
                Stage loginStage = new Stage();
                loginStage.setResizable(false);
                Image icon = new Image(getClass().getResourceAsStream("/img/whiteColueHenLogo.png"));
                loginStage.getIcons().add(icon);
                loginStage.setTitle("Loading");
                loginStage.setScene(loginScene);
                loginStage.show();

                // Close the welcome stage
                ((Stage) prgBar.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new Thread(task).start();
    }


}
