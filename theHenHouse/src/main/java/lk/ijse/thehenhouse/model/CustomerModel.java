package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.CustomerTM;
import lk.ijse.thehenhouse.dto.tm.itemTM;
import lk.ijse.thehenhouse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CustomerModel {

    public static List<CustomerTM> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer";

        List<CustomerTM> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new CustomerTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));

        }
        return data;

    }

    public static List<String> getCustomerAllId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT customer_id FROM Customer";

        List<String> custId = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            custId.add(resultSet.getString(1));
        }
        return custId;
    }

    public static CustomerTM searchById(String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, cusId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new CustomerTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static CustomerTM search(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, customerId);

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String adress=resultSet.getString(3);
            String contact=resultSet.getString(4);

            return new CustomerTM(id,name,adress,contact);
        }
        return null;
    }

    public static boolean save(String custId, String custName, String custAdress, String customerContact) throws SQLException {
        String sql = "INSERT INTO customer(customer_id,  customer_name, customer_address, customer_contact) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql,custId,custName,custAdress,customerContact);
    }

    public static boolean update(CustomerTM customerTM) throws SQLException {
        String sql = "UPDATE Customer SET customer_name = ?, customer_address = ?, customer_contact = ? WHERE customer_id = ?";
        return CrudUtil.execute(sql, customerTM.getName(), customerTM.getAddress(), customerTM.getContact(),customerTM.getId());

    }

    public static boolean delete(String customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        return CrudUtil.execute(sql,customerId);
    }



}
