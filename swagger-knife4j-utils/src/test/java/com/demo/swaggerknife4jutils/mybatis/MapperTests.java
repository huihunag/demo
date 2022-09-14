package com.demo.swaggerknife4jutils.mybatis;

import com.demo.swaggerknife4jutils.bean.plus.UserDO;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

interface UserMapper{
    @Select("select * from user where id = #{id} and name = #{name}")
    List<UserDO> selectUserList(String id, String name);
}

@SpringBootTest
public class MapperTests {

    @Test
    void test() {
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(MapperTests.class.getClassLoader(), new Class<?>[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Select an = method.getAnnotation(Select.class);
                Map<String, Object> nameArgMap = buildMethodArgNameMap(method, args);
                if(an != null){
                    String[] value = an.value();
                    // 暂时当一条sql处理
                    String sql = value[0];
                    sql = parseSQL(sql, nameArgMap);
                    System.out.println(sql);
                    // 方法返回类型
                    System.out.println(method.getReturnType());
                    // 方法返回类型泛型
                    System.out.println(method.getGenericReturnType());
                }
                return null;
            }
        });
        userMapper.selectUserList("1", "test");
    }

    public static String parseSQL(String sql, Map<String, Object> nameArgMap){
        StringBuilder stringBuilder = new StringBuilder();
        int length = sql.length();
        for(int i = 0; i < length; i++){
            char c = sql.charAt(i);
            if(c == '#'){
                int nextIndex = i + 1;
                char nextChar = sql.charAt(nextIndex);
                if(nextChar != '{'){
                    throw new RuntimeException(String.format("这里应该为#{\nsql:%s\nindex:%d"
                    , stringBuilder.toString(), nextIndex));
                }
                StringBuilder arg = new StringBuilder();
                i = parseSQLArg(arg, sql, nextIndex);
                String argName = arg.toString();
                Object argValue = nameArgMap.get(argName);
                if(argValue == null){
                    throw new RuntimeException(String.format("找不到参数#{\nsql:%s\nindex:%d"
                            , stringBuilder.toString(), nextIndex));
                }
                stringBuilder.append(argValue.toString());
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static int parseSQLArg(StringBuilder arg, String sql, int nextIndex) {
        nextIndex++;
        for(; nextIndex < sql.length(); nextIndex++){
            char c = sql.charAt(nextIndex);
            if(c != '}'){
                arg.append(c);
                continue;
            }else {
                return nextIndex;
            }
        }
        throw new RuntimeException(String.format("缺少右括号\nindex:%d", nextIndex));
    }


    public static Map<String, Object> buildMethodArgNameMap(Method method, Object[] args){
        Map<String, Object> nameArgMap = Maps.newHashMap();
        Parameter[] parameters = method.getParameters();
        int[] index = {0};
        Arrays.stream(parameters).forEach(parameter -> {
            String name = parameter.getName();
            nameArgMap.put(name, args[index[0]++]);
        });
        return nameArgMap;
    }

}
