package com.demo.swaggerknife4jutils;

import com.demo.swaggerknife4jutils.bean.enums.SexEnum;
import com.demo.swaggerknife4jutils.bean.plus.UserDO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@SpringBootTest
public class JDBCTests {

    @Test
    void test() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<UserDO> userList = Lists.newArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mall?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone =Asia/Shanghai", "root", "huangxinwei");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from user");
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
            System.out.println(userList);
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
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
