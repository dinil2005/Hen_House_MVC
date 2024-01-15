package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.SupplierCartDTO;
import lk.ijse.thehenhouse.dto.tm.SupplierTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecivedItemModel {
    public static List<String> getCustomerAllId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT  supplier_id FROM supplier";

        List<String> suplierId = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            suplierId.add(resultSet.getString(1));
        }
        return suplierId;
    }
    public static SupplierTM searchById(String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier WHERE  supplier_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, cusId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new SupplierTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }

    public static boolean recivedItemDetails(String supId,SupplierCartDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO received_item_detail(supplier_id, received_item_id, qty,amount,date) VALUES (?, ?, ?,?,?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, supId);
        pstm.setString(2, dto.getItem_code());
        pstm.setInt(3, dto.getQty());
        pstm.setDouble(4, dto.getTotal());
        pstm.setString(5, String.valueOf(LocalDate.now()));

        return pstm.executeUpdate() > 0;
    }

    public static boolean add(String supplier_id, List<SupplierCartDTO> supplierCartDTO) throws SQLException {

        for (SupplierCartDTO supplierCart : supplierCartDTO){

            if (!(recivedItemDetails(supplier_id,supplierCart))){
                return false;
            }

        }
        return true;

    }
}
