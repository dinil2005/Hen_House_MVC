package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.CustomerTM;
import lk.ijse.thehenhouse.dto.tm.SupplierTM;
import lk.ijse.thehenhouse.dto.tm.itemTM;
import lk.ijse.thehenhouse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SupplierModel {
    private final static String URL = "jdbc:mysql://localhost:3306/hen_house";
    private final static Properties props = new Properties();


    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }
    public static boolean save(String sup_id, String sup_name, String sup_contact) throws SQLException {
        String sql ="INSERT INTO supplier(supplier_id, name, contact) VALUES(?, ?, ?)";
        return CrudUtil.execute(sql, sup_id,sup_name,sup_contact);
    }

    public static SupplierTM search(String sup_id) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplier_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, sup_id);

        if (resultSet.next()) {
            String sup_Id = resultSet.getString(1);
            String sup_Name = resultSet.getString(2);
            String sup_Contact = resultSet.getString(3);


            return new SupplierTM(sup_Id,sup_Name,sup_Contact);
        }
        return null;
    }

    public static boolean update(SupplierTM supplierTM) throws SQLException {
        String sql = "UPDATE supplier SET name = ?, contact = ?  WHERE supplier_id = ?";
        return CrudUtil.execute(sql, supplierTM.getSup_name(), supplierTM.getSup_contact(),supplierTM.getSup_id());

    }


    public static boolean delete(String sup_id) throws SQLException {

//        try (Connection con = DBConnection.getInstance().getConnection()) {
//            String sql = "DELETE FROM supplier WHERE supplier_id = ?";
//
//            PreparedStatement pstm = con.prepareStatement(sql);
//            pstm.setString(1, sup_id);
//
//            return pstm.executeUpdate() > 0;
        String sql = "DELETE FROM supplier WHERE supplier_id = ?";
        return CrudUtil.execute(sql,sup_id);

        }


    public static List<SupplierTM> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";

        List<SupplierTM> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new SupplierTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
            //System.out.println(data);
        }
        return data;

    }
}
