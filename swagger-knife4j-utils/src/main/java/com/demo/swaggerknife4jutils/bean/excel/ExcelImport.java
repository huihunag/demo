package com.demo.swaggerknife4jutils.bean.excel;

import com.demo.swaggerknife4jutils.utils.file.excel.core.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExcelImport implements Serializable {

    @Excel(name = "药品编码", cellType = Excel.ColumnType.STRING)
    private String drugCode;

    @Excel(name = "标准编码", cellType = Excel.ColumnType.STRING)
    private String standardEncoding;

    @Excel(name = "药品名称", cellType = Excel.ColumnType.STRING)
    private String drugName;

    @Excel(name = "通用名", cellType = Excel.ColumnType.STRING)
    private String commonName;

    @Excel(name = "剂型", cellType = Excel.ColumnType.STRING)
    private String dosageForm;

    @Excel(name = "规格", cellType = Excel.ColumnType.STRING)
    private String specification;

    @Excel(name = "包装规格", cellType = Excel.ColumnType.STRING)
    private String packagingSpecifications;

    @Excel(name = "批准文号", cellType = Excel.ColumnType.STRING)
    private String approvalNumber;

    @Excel(name = "本位码", cellType = Excel.ColumnType.STRING)
    private String baseCode;

    @Excel(name = "国药准字", cellType = Excel.ColumnType.STRING)
    private String chineseMedicineQuasiCharacter;

    @Excel(name = "注册证号", cellType = Excel.ColumnType.STRING)
    private String registrationNumber;

    @Excel(name = "国家药品代码", cellType = Excel.ColumnType.STRING)
    private String nationalDrugCode;

    @Excel(name = "是否国家药品对照", cellType = Excel.ColumnType.STRING)
    private String whetherTheNationalDrugControl;

    @Excel(name = "是否GPO药品", cellType = Excel.ColumnType.STRING)
    private String isItAGPODrug;

    @Excel(name = "GPO名称", cellType = Excel.ColumnType.STRING)
    private String gpoName;

    @Excel(name = "是否国基药品", cellType = Excel.ColumnType.STRING)
    private String whetherTheNationalBaseDrug;

    @Excel(name = "国家集采标识", cellType = Excel.ColumnType.STRING)
    private String nationalCentralizedProcurementLogo;

    @Excel(name = "是否可替代药品", cellType = Excel.ColumnType.STRING)
    private String alternativeMedicines;

    @Excel(name = "是否抗菌药物", cellType = Excel.ColumnType.STRING)
    private String areAntibacterialDrugs;

    @Excel(name = "是否抗疫药品", cellType = Excel.ColumnType.STRING)
    private String whetherAntiEpidemicDrugs;

    @Excel(name = "是否急救药品", cellType = Excel.ColumnType.STRING)
    private String whetherEmergencyMedicine;

    @Excel(name = "是否禁用", cellType = Excel.ColumnType.STRING)
    private String whetherToDisable;

    @Excel(name = "是否社康药品", cellType = Excel.ColumnType.STRING)
    private String isItASocialHealthDrug;

    @Excel(name = "是否国家谈判药品", cellType = Excel.ColumnType.STRING)
    private String whetherTheStateNegotiatesDrugs;
}
