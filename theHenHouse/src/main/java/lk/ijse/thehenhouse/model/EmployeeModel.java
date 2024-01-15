package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.CustomerTM;
import lk.ijse.thehenhouse.dto.tm.EmployeeTM;
import lk.ijse.thehenhouse.dto.tm.itemTM;
import lk.ijse.thehenhouse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeModel {
    private final static String URL = "jdbc:mysql://localhost:3306/hen_house";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static boolean save(String id, String name, String contact, String address, Double salary) throws SQLException {
        String sql = "INSERT INTO employee(employee_id, employee_name, employee_contact, employee_address,employee_salary) VALUES(?, ?, ?, ?,?)";
        return CrudUtil.execute(sql, id, name, contact, address, salary);
    }

    public static boolean update(EmployeeTM employeeTM) throws SQLException {
        String sql = "UPDATE employee SET employee_name = ?, employee_contact = ?, employee_address = ? WHERE employee_id = ?";
        return CrudUtil.execute(sql, employeeTM.getEmplo_name(), employeeTM.getEmplo_contact(), employeeTM.getEmplo_address(), employeeTM.getEmplo_id());

    }
    public static boolean delete(String customerId) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        return CrudUtil.execute(sql,customerId);
    }

    public static EmployeeTM search(String employeeId) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, employeeId);

        if (resultSet.next()) {
            String employee_Id = resultSet.getString(1);
            String employeeName = resultSet.getString(2);
            String employeeContact = resultSet.getString(3);
            String employeeAdress = resultSet.getString(4);
            Double salry = resultSet.getDouble(5);

            return new EmployeeTM(employee_Id, employeeName, employeeContact, employeeAdress, salry);
        }
        return null;
    }

    public static List<String> getEmployeeAllId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT employee_id FROM  employee";

        List<String> employeeId = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            employeeId.add(resultSet.getString(1));
        }
        return employeeId;
    }

    public static boolean addSalary(String employeeId, Double salary) throws SQLException {
        String sql = "UPDATE employee SET employee_salary = ? WHERE employee_id  = ? ";
        return CrudUtil.execute(sql,salary,employeeId);
    }


    public static List<EmployeeTM> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";

        List<EmployeeTM> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new EmployeeTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));

        }
        return data;
    }
}
