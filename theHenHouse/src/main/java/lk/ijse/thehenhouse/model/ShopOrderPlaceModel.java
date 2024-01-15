package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.dto.tm.OrderCartT;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ShopOrderPlaceModel {

    public static boolean placeOrder(String oId, String cusId, List<OrderCartT> cartDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved =shopOrderModel.shopOrder(oId, String.valueOf(LocalDate.now()),cusId);
            System.out.println(isSaved);
            if (isSaved) {
                boolean isUpdated = ItemModel.updateQty(cartDTOList);
                System.out.println(isUpdated);
                if (isUpdated) {
                    boolean isOrderDetailSaved =OrderDetailsModel.save(oId, cartDTOList);
                    System.out.println(isOrderDetailSaved);
                    if (isOrderDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
         //   System.out.println("finally");
            con.setAutoCommit(true);
        }
    }


}
