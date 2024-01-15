package lk.ijse.thehenhouse.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import lk.ijse.thehenhouse.Sound.Notify;
import org.controlsfx.control.Notifications;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class qrGenaroter {
    private String data;
    private String path;
    public void setData(String data) {
        this.data = data;
    }
    public String getPath() {
        return path;
    }

    public void getGenerator() throws IOException, WriterException {
        path = "D:\\Semeseter 1 Final Project\\QrCode\\QrCode" + data + ".png";
        BitMatrix encode = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        Path path1 = Paths.get(path);
        MatrixToImageWriter.writeToPath(encode, path.substring(path.lastIndexOf('.') + 1), path1);
        Notify.playSound();
        Notifications.create()
                .title("Notification")
                .text(data+": QR Successfully Generated")
                .position(Pos.TOP_RIGHT)
                .darkStyle()
                .showInformation();
        
    }


}

