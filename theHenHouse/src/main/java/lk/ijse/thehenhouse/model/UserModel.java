package lk.ijse.thehenhouse.model;

import lk.ijse.thehenhouse.db.DBConnection;
import lk.ijse.thehenhouse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public static boolean check(String txtmail, String txtPw) throws SQLException {
        String sql="SELECT * FROM user WHERE user_mail = ?";
        ResultSet resultSet=CrudUtil.execute(sql,txtmail);
        if(resultSet.next()){

          if( resultSet.getString(2).equals(txtPw))
          {
            return true;
            }
        }
return false;
    }



}
