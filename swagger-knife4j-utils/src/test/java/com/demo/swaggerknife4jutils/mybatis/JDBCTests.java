package com.demo.swaggerknife4jutils.mybatis;

import com.demo.swaggerknife4jutils.bean.enums.SexEnum;
import com.demo.swaggerknife4jutils.bean.plus.UserDO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest
public class JDBCTests {

    @Test
    void test() {
        System.out.println(selectJDBC("select * from user"));
    }

    private static Connection  connection;
    private static String url = "jdbc:mysql://127.0.0.1:3306/mall?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone =Asia/Shanghai";
    private static String user = "root";
    private static String password = "huangxinwei";
    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static Object selectJDBC(String sql){
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<UserDO> userList = Lists.newArrayList();
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                UserDO userDO = new UserDO();
                userDO.setId(resultSet.getLong("id"));
                userDO.setRealName(resultSet.getString("nickname"));
                userDO.setSex(SexEnum.bySexEnumtoCode(resultSet.getInt("sex")));
                userDO.setCreateTime(resultSet.getObject("create_time",LocalDateTime.class));
                userDO.setIsDelete(resultSet.getInt("is_delete"));
                userDO.setRealName(resultSet.getString("real_name"));
                userDO.setVersion(resultSet.getInt("version"));
                userList.add(userDO);
            }
            return userList;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

}
