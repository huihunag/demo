package com.demo.swaggerknife4jutils.controller.file;

import com.demo.swaggerknife4jutils.bean.excel.ExcelImport;
import com.demo.swaggerknife4jutils.common.ApiResult;
import com.demo.swaggerknife4jutils.config.CustomFileConfig;
import com.demo.swaggerknife4jutils.service.file.ExcelService;
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
    @Autowired
    private ExcelService excelService;

    @PostMapping("/export")
    @ResponseBody
    @ApiOperation(value = "导出数据")
    public ApiResult exportData() {
        return excelService.exportData(ExcelImport.class);
    }

    @PostMapping("/import")
    @ResponseBody
    @ApiOperation("导入数据")
    public ApiResult importData(@RequestParam MultipartFile file) throws Exception {
        return excelService.importData(file, ExcelImport.class);
    }

}
