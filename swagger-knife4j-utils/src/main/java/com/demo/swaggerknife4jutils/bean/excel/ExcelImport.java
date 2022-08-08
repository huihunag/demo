package com.demo.swaggerknife4jutils.bean.excel;

import com.demo.swaggerknife4jutils.utils.file.excel.core.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExcelImport implements Serializable {

    @Excel(name = "药品编码", cellType = Excel.ColumnType.STRING)
    private String 药品编码;

    @Excel(name = "标准编码", cellType = Excel.ColumnType.STRING)
    private String 标准编码;

    @Excel(name = "药品名称", cellType = Excel.ColumnType.STRING)
    private String 药品名称;

    @Excel(name = "通用名", cellType = Excel.ColumnType.STRING)
    private String 通用名;

    @Excel(name = "剂型", cellType = Excel.ColumnType.STRING)
    private String 剂型;

    @Excel(name = "规格", cellType = Excel.ColumnType.STRING)
    private String 规格;

    @Excel(name = "包装规格", cellType = Excel.ColumnType.STRING)
    private String 包装规格;

    @Excel(name = "批准文号", cellType = Excel.ColumnType.STRING)
    private String 批准文号;

    @Excel(name = "本位码", cellType = Excel.ColumnType.STRING)
    private String 本位码;

    @Excel(name = "国药准字", cellType = Excel.ColumnType.STRING)
    private String 国药准字;

    @Excel(name = "注册证号", cellType = Excel.ColumnType.STRING)
    private String 注册证号;

    @Excel(name = "国家药品代码", cellType = Excel.ColumnType.STRING)
    private String 国家药品代码;

    @Excel(name = "是否国家药品对照", cellType = Excel.ColumnType.STRING)
    private String 是否国家药品对照;

    @Excel(name = "是否GPO药品", cellType = Excel.ColumnType.STRING)
    private String 是否GPO药品;

    @Excel(name = "GPO名称", cellType = Excel.ColumnType.STRING)
    private String GPO名称;

    @Excel(name = "是否国基药品", cellType = Excel.ColumnType.STRING)
    private String 是否国基药品;

    @Excel(name = "国家集采标识", cellType = Excel.ColumnType.STRING)
    private String 国家集采标识;

    @Excel(name = "是否可替代药品", cellType = Excel.ColumnType.STRING)
    private String 是否可替代药品;

    @Excel(name = "是否抗菌药物", cellType = Excel.ColumnType.STRING)
    private String 是否抗菌药物;

    @Excel(name = "是否抗疫药品", cellType = Excel.ColumnType.STRING)
    private String 是否抗疫药品;

    @Excel(name = "是否急救药品", cellType = Excel.ColumnType.STRING)
    private String 是否急救药品;

    @Excel(name = "是否禁用", cellType = Excel.ColumnType.STRING)
    private String 是否禁用;

    @Excel(name = "是否社康药品", cellType = Excel.ColumnType.STRING)
    private String 是否社康药品;

    @Excel(name = "是否国家谈判药品", cellType = Excel.ColumnType.STRING)
    private String 是否国家谈判药品;
}
