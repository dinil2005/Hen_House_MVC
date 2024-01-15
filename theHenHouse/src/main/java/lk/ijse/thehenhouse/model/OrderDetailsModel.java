package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.OrderCartT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderDetailsModel {

    public static boolean save(String oId, List<OrderCartT> cartDTOList) throws SQLException {
        for(OrderCartT dto :  cartDTOList) {
            if(!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, OrderCartT dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO shop_order_details(order_id, description, item_id, qty,unit_price) VALUES (?, ?, ?, ?,?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setString(2, dto.getDescription());
        pstm.setString(3, dto.getItemId());
        pstm.setInt(4,dto.getQty());
        pstm.setDouble(5,dto.getUnitPrice());

        return pstm.executeUpdate() > 0;

    }


}
