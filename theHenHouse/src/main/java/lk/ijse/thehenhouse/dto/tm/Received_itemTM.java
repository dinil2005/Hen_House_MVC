package lk.ijse.thehenhouse.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Received_itemTM  {
    String item_id;
    String description;
    int qty;
    Double unitPrice;
    Double amount;


    public Received_itemTM(String description, String code, double unitPrice, int qty, double total) {
        item_id=code;
        this.description=description;
        this.qty=qty;
        this.unitPrice= unitPrice;
        amount=total;
    }
}
