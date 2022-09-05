package com.demo.swaggerknife4jutils.service.file.impl;

import com.demo.swaggerknife4jutils.bean.excel.ExcelImport;
import com.demo.swaggerknife4jutils.service.file.ExcelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelServiceImpl extends ExcelAbstract<ExcelImport> implements ExcelService {

    @Override
    public void write(List<ExcelImport> data) {
        for (ExcelImport excelImport: data){
            System.out.println(excelImport);
        }
    }

    @Override
    public List<ExcelImport> query() {
        return null;
    }

}
