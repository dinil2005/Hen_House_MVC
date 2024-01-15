package lk.ijse.thehenhouse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SupplierCartDTO {
    private String description;
    private String item_code;
    private Double unit_price;
    private Integer qty;
    private Double total;
}
