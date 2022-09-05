package com.demo.swaggerknife4jutils.service.file;

import com.demo.swaggerknife4jutils.bean.excel.ExcelImport;
import com.demo.swaggerknife4jutils.common.ApiResult;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    ApiResult importData(MultipartFile file, Class<ExcelImport> clazz);

    ApiResult exportData(Class<ExcelImport> clazz);

}
