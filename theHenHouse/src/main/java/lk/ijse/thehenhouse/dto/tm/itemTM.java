package lk.ijse.thehenhouse.dto.tm;


import java.util.List;

public class itemTM {

     private String item_id;
     private String item_Description;
     private Double item_unit_price;
     private int item_qty_on_hand;

    public itemTM(String item_id, String item_Description, Double item_unit_price, int item_qty_on_hand) {
        this.item_id = item_id;
        this.item_Description = item_Description;
        this.item_unit_price = item_unit_price;
        this.item_qty_on_hand = item_qty_on_hand;
    }




    public  String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_Description() {
        return item_Description;
    }

    public void setItem_Description(String item_Description) {
        this.item_Description = item_Description;
    }
    public void setItem_unit_price(int item_unit_price) {
        this.item_unit_price = Double.valueOf(item_unit_price);

    }

    public Double getItem_unit_price() {
        return item_unit_price;
    }



    public int getItem_qty_on_hand() {
        return item_qty_on_hand;
    }

    public void setItem_qty_on_hand(int item_qty_on_hand) {
        this.item_qty_on_hand = item_qty_on_hand;
    }
}


