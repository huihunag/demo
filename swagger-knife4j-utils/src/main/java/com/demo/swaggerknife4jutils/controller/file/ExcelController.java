package com.demo.swaggerknife4jutils.controller.file;

import com.demo.swaggerknife4jutils.bean.excel.ExcelImport;
import com.demo.swaggerknife4jutils.common.ApiResult;
import com.demo.swaggerknife4jutils.config.CustomFileConfig;
import com.demo.swaggerknife4jutils.utils.file.excel.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @description excel
 * @Date 2022/6/20 21:59
 * @Author HUANGXINWEI
 */
@Controller
@RequestMapping("/file/excel")
@Slf4j
@Api(tags = "excel操作", description = "excel操作")
public class ExcelController {

    @Autowired
    private CustomFileConfig customFileConfig;

    @PostMapping("/export")
    @ResponseBody
    @ApiOperation("导出数据")
    public ApiResult exportData() {
        List<ExcelImport> list = new ArrayList<>();
        ExcelUtil<ExcelImport> util = new ExcelUtil<>(ExcelImport.class);
        return util.exportExcel(list, "导出数据", customFileConfig.getUpDownloadAddr());
    }

    @PostMapping("/import")
    @ResponseBody
    @ApiOperation("导入数据")
    public ApiResult importData(@RequestParam MultipartFile file) throws Exception {
        try {
            if (file.isEmpty()) {
                return ApiResult.failed("文件不存在");
            }
            ExcelUtil<ExcelImport> util = new ExcelUtil<>(ExcelImport.class);
            List<ExcelImport> ExcelImports = util.importExcel(file.getInputStream());

            if (ExcelImports == null) {
                return ApiResult.failed("请导入有数据的excel");
            }

            return ApiResult.success(ExcelImports);
        } catch (Exception e) {
            log.error("导入数据错误：{}", e.getMessage(), e);
            return ApiResult.failed("导入数据错误");
        }
    }

}
