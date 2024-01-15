package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.OrderCartT;
import lk.ijse.thehenhouse.dto.tm.ShopOrderDetailsTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class shopOrderModel {


    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM shop_order ORDER BY order_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] strings = currentOrderId.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "O0" + id;
        }
        return "O001";
    }

    public static boolean shopOrder(String oId, String date , String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO shop_order(order_id, order_date, customer_id) VALUES (?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setString(2, String.valueOf(date));
        pstm.setString(3, cusId);

        return pstm.executeUpdate() > 0;
    }

}
