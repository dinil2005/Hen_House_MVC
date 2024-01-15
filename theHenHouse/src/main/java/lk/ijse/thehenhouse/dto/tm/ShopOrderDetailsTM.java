package lk.ijse.thehenhouse.dto.tm;


import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ShopOrderDetailsTM {
    private String order_id;
    private String description;
    private Double unit_price;
    private int qty;
    private Double amount;
    private Button remove;

}
