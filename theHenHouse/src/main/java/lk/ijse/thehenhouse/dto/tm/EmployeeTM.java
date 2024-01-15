package lk.ijse.thehenhouse.dto.tm;


public class EmployeeTM {

    String emplo_id;
    String emplo_name;
    String emplo_contact;
    String emplo_address;
    Double emplo_salary;

    public EmployeeTM(String emploid, String emploname, String emplocontact, String emploaddress, Double emplosalary) {
        this.emplo_id=emploid;
        this.emplo_name=emploname;
        this.emplo_contact=emplocontact;
        this.emplo_address=emploaddress;
        this.emplo_salary=emplosalary;
    }


    public String getEmplo_id() {
        return emplo_id;
    }

    public void setEmplo_id(String emplo_id) {
        this.emplo_id = emplo_id;
    }

    public String getEmplo_name() {
        return emplo_name;
    }

    public void setEmplo_name(String emplo_name) {
        this.emplo_name = emplo_name;
    }

    public String getEmplo_contact() {
        return emplo_contact;
    }

    public void setEmplo_contact(String emplo_contact) {
        this.emplo_contact = emplo_contact;
    }

    public String getEmplo_address() {
        return emplo_address;
    }

    public void setEmplo_address(String emplo_address) {
        this.emplo_address = emplo_address;
    }

    public Double getEmplo_salary() {
        return emplo_salary;
    }

    public void setEmplo_salary(Double emplo_salary) {
        this.emplo_salary = emplo_salary;
    }



}
