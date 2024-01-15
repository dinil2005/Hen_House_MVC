package lk.ijse.thehenhouse.dto.tm;


public class SupplierTM {

    private String sup_id;
    private String sup_name;
    private String sup_contact;

    public SupplierTM(String sup_id, String sup_name, String sup_contact) {
        this.sup_id = sup_id;
        this.sup_name = sup_name;
        this.sup_contact = sup_contact;
    }

    public SupplierTM(String string, String string1, String string2, String string3) {
        this.sup_id=string;
        this.sup_name=string1;
        this.sup_contact=string2;
    }

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public String getSup_name() {
        return sup_name;
    }

    public void setSup_name(String sup_name) {
        this.sup_name = sup_name;
    }

    public String getSup_contact() {
        return sup_contact;
    }

    public void setSup_contact(String sup_contact) {
        this.sup_contact = sup_contact;
    }
}
