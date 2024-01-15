package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.SupplierCartDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SupplierOrderModel {
    public static boolean placeOrder(String supplier_id, List<SupplierCartDTO> supplierCartDTO) throws SQLException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isAdded = RecivedItemModel.add(supplier_id, supplierCartDTO);

            if (isAdded) {
                boolean isUpdated = ItemModel.updateSupQty(supplierCartDTO);
                if (isAdded) {
                    con.setAutoCommit(true);
                    return true;
                }

            }
            return false;

        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
            return false;
        }finally {
            con.setAutoCommit(true);
        }


    }
}
