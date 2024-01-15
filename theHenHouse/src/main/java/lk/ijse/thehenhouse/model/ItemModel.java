package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.SupplierCartDTO;
import lk.ijse.thehenhouse.dto.tm.OrderCartT;
import lk.ijse.thehenhouse.dto.tm.itemTM;
import lk.ijse.thehenhouse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemModel {
    private final static String URL = "jdbc:mysql://localhost:3306/hen_house";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public static boolean save(String id, String description, Double unitPrice, int qtyOnHand) throws SQLException {
        String sql = "INSERT INTO Item(item_id, description, unit_price, qty_on_hand) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql, id, description, unitPrice, qtyOnHand);
    }

    public static itemTM search(String itemId) throws SQLException {
        String sql = "SELECT * FROM Item WHERE item_id = ?";

        ResultSet resultSet = CrudUtil.execute(sql, itemId);

        if (resultSet.next()) {
            String item_code = resultSet.getString(1);
            String item_description = resultSet.getString(2);
            Double item_unit_price = resultSet.getDouble(3);
            int item_qty_on_hand = resultSet.getInt(4);

            return new itemTM(item_code, item_description, item_unit_price, item_qty_on_hand);
        }
        return null;
    }

    public static boolean update(itemTM item) throws SQLException {
        String sql = "UPDATE item SET description = ?, unit_price = ?, qty_on_hand = ? WHERE item_id = ?";
        return CrudUtil.execute(sql, item.getItem_Description(), item.getItem_unit_price(), item.getItem_qty_on_hand(), item.getItem_id());

    }

    public static boolean delete(String itemId) throws SQLException {
            String sql = "DELETE FROM Item WHERE item_id = ?";
            return CrudUtil.execute(sql,itemId);
        }


    public static List<String> getDescription() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT description FROM item";

        List<String> description = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        while (resultSet.next()) {
            description.add(resultSet.getString(1));
        }
        return description;
    }

    public static  List<itemTM> getAllItems() throws SQLException {
        String sql = "SELECT * FROM item";

        List<itemTM> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new itemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return data;

    }

    public static itemTM searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Item WHERE description = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new itemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }

    public static boolean updateQty(List<OrderCartT> cartDTOList) throws SQLException {
        for (OrderCartT dto : cartDTOList) {
            if(!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderCartT dto) throws SQLException {

        String sql = "UPDATE item SET qty_on_hand = (qty_on_hand - ?) WHERE item_id = ?";

       return CrudUtil.execute(sql,dto.getQty(),dto.getItemId());
    }

    public static boolean updateSupQty(List<SupplierCartDTO> supplierCartDTO) throws SQLException {
        for (SupplierCartDTO dto : supplierCartDTO) {
            if(!updateSupQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateSupQty(SupplierCartDTO dto) throws SQLException {

        String sql = "UPDATE item SET qty_on_hand = (qty_on_hand + ?) WHERE item_id = ?";

        return CrudUtil.execute(sql,dto.getQty(),dto.getItem_code());
    }
}





