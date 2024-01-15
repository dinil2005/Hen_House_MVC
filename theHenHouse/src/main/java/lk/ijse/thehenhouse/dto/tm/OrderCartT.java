package lk.ijse.thehenhouse.dto.tm;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class OrderCartT  {

    String description;
    String itemId;
    int qty;
    double unitPrice;

    public OrderCartT( String description, String item_id, int qty, Double amount) {
        this.description=description;
        this.itemId=item_id;
        this.qty=qty;
        this.unitPrice=amount;


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

        public String getItemId() {
        return itemId;
    }

    public void setItemId(String code) {
        this.itemId = code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
