package lk.ijse.thehenhouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ResourceBundle;

public class QrFormController implements Initializable {
    public static Image image;
    @FXML
    private ImageView qrPic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      setImage();
    }

    private void setImage() {
        qrPic.setImage(image);
    }
}
