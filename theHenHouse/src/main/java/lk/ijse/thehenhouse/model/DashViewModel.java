package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DashViewModel {


    public String checkCustomerTotal() throws SQLException {
        String sql = "SELECT COUNT(customer_id) FROM customer;";
        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()){
            String tot = resultSet.getString(1);
            return tot;
        }
        return "0";


    }
    public String checkEmployeesTotal() throws SQLException {
        String sql = "SELECT COUNT(employee_id) FROM employee;";
        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()){
            String tot = resultSet.getString(1);
            return tot;
        }
        return "0";


    }
    public String checkItemsTotal() throws SQLException {
        String sql = "SELECT COUNT(item_id) FROM item;";
        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()){
            String tot = resultSet.getString(1);
            return tot;
        }
        return "0";


    }


        }



