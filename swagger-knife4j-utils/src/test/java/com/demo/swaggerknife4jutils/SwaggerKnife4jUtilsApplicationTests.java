package com.demo.swaggerknife4jutils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
class SwaggerKnife4jUtilsApplicationTests {

    @Test
    void contextLoads() {
        String file = "D:\\Project\\Demo\\demo\\swagger-knife4j-utils\\src\\main\\resources\\test\\temp.txt";
        // 创建文件字节输入流对象
        try(FileInputStream fis = new FileInputStream(file)) {
            // 开始读
            int readData = 0;
            while((readData = fis.read()) != -1){
                System.out.println(readData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
