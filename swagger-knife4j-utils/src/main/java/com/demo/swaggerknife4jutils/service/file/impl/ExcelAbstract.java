package com.demo.swaggerknife4jutils.service.file.impl;

import com.demo.swaggerknife4jutils.bean.excel.ExcelImport;
import com.demo.swaggerknife4jutils.common.ApiResult;
import com.demo.swaggerknife4jutils.config.CustomFileConfig;
import com.demo.swaggerknife4jutils.utils.file.excel.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class ExcelAbstract<T> {

    @Autowired
    private CustomFileConfig customFileConfig;

    /**
     * @description 写入本地
     * @Date 2022/9/5 17:45
     * @Author Administrator
     * @Param data
     * @Return void
     */
    public abstract void write(List<T> data);

    /**
     * @description 导入数据
     * @Date 2022/9/5 17:45
     * @Author Administrator
     * @Param file
     * @Param clazz
     * @Return com.demo.swaggerknife4jutils.common.ApiResult
     */
    public ApiResult importData(MultipartFile file, Class<T> clazz){
        try {
            if (file.isEmpty()) {
                return ApiResult.failed("文件不存在");
            }
            ExcelUtil<T> util = new ExcelUtil<T>(clazz);
            List<T> ExcelImports = util.importExcel(file.getInputStream());

            if (ExcelImports == null) {
                return ApiResult.failed("请导入有数据的excel");
            }

            write(ExcelImports);

            return ApiResult.success(ExcelImports);
        } catch (Exception e) {
            log.error("导入数据错误：{}", e.getMessage(), e);
            return ApiResult.failed("导入数据错误");
        }
    }

    /**
     * @description 查询数据
     * @Date 2022/9/5 17:47
     * @Author Administrator
     * @Param data
     * @Return void
     */
    public abstract List<T> query();

    /**
     * @description 导出数据
     * @Date 2022/9/5 17:46
     * @Author Administrator
     * @Param file
     * @Param clazz
     * @Return com.demo.swaggerknife4jutils.common.ApiResult
     */
    public ApiResult exportData(Class<T> clazz){
        List<T> list = query();
        ExcelUtil<T> util = new ExcelUtil<>(clazz);
        return util.exportExcel(list, "导出数据", customFileConfig.getUpDownloadAddr());
    }

}
