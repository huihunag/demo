package com.demo.swaggerknife4jutils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class NhsTests {

    @Test
    void test() {
        String body = "hisPatientSerial\tAns\t50\t是\t原就诊医疗机构门诊就诊流水号\n" +
                "orgUuid\tAns\t50\t是\t原就诊医疗机构编码 \n" +
                "orgName\tAns\t50\t是\t原就 诊医疗机构名称\n" +
                "orgDeptUuid\tAns\t50\t是\t原就诊科室编码\n" +
                "orgDeptName\tVAR\t100\t是\t原就诊科室名称\n" +
                "orgDeptEmpUuid\tAns\t50\t是\t原就诊科室医生编码\n" +
                "orgDeptEmpName\tVAR\t100\t是\t原就诊科室医生的姓名\n" +
                "pname\tVAR\t100\t是\t姓名\n" +
                "patientId\tAns\t50\t否\t原就诊医疗机构就诊人唯一ID号 \n" +
                "haelthCardId\tAns\t64\t否\t电子健康卡ID\n" +
                "sex\tAns\t1\t是\t性别代码，1：男2：女 \n" +
                "birthday\tD\t8\t是\t出生日期 \n" +
                "idType\tAns\t2\t是\t证件类型\n" +
                "idcard\tAns\t20\t是\t身份证号码\n" +
                "mobile\tN\t20\t是\t手机号码\n" +
                "visitType\tAns\t10\t是\t就诊类型 VISI_TYPE_DICT 字典\n" +
                "registerFlag\tVAR\t1\t是\t是否医保 0:否 1：是\n" +
                "siType\tVAR\t4\t否\t医疗保险类别代码 SI_TYPE_DICT 字典\n" +
                "sdate\tT\t14\t是\t就诊时间\n" +
                "diagWmedicineCode\tAns\t200\t否\t门诊诊断编码（主要诊断）:填写院内诊断代码。若有多条，填写主要诊断。多个以“;”间隔\n" +
                "diagWmedicine\tVAR\t2000\t否\t门诊诊断名称：填写院内诊断名称\n" +
                "symptomDesc\tVAR\t2000\t否\t症状描述\n" +
                "recipeList\tArray\t-\t是\t处方列表数据，采用JSON Array方式包含";

        String recipeStr = "recipeId\tAns\t50\t是\t处方流水号\n" +
                "orgDeptUuid\tAns\t50\t是\t开方科室编码\n" +
                "orgDeptName\tVAR\t100\t是\t开方科室简称\n" +
                "orgDeptEmpUuid\tAns\t20\t是\t开方科室医生编码\n" +
                "orgDeptEmpName\tVAR\t100\t是\t开方科室医生的姓名\n" +
                "recipeType\tVAR\t2\t是\t处方类型 : 1.中药饮片处方 2.中成药处方 3.西药处方 4.院内制剂  9.其他 \n" +
                "diagTime\tT\t14\t是\t开方时间\n" +
                "amount\tN\t12\t是\t处方金额，单位(元) 小数位4位\n" +
                "xDiagWmedicineCode\tAns\t200\t否\t西医诊断编码， 填写院内西医诊断编码，多个以“;”间隔。\n" +
                "xDiagWmedicine\tVAR\t2000\t否\t西医诊断名称， 填写院内西医诊断名称，多个以“;”间隔\n" +
                "chColumnCount\tAns\t10\t否\t中药书写列数草药处方使用。必须填写。医生按“君、臣、佐、使”书写时所使用的列数。取值只能为1、2、3、4\n" +
                "herbalUse\tVAR\t100\t否\t中药用法，如煎服、外涂；字符串不超过100，中药必填\n" +
                "agentNum\tN\t12\t否\t中药付数（剂数），中药必填\n" +
                "chdrgType\tVAR\t1\t否\t中药饮片处方类别: 1. 散装中药饮片 2. 小包装中药饮片 3. 中药配方颗粒处方数 9. 中药配方颗粒处方数 \n" +
                "chdrgNum\tN\t12\t否\t中药饮片总剂（帖）数:中药饮片处方使用。必须填写。指医院中药饮片处方（膏方除外）的总剂（贴）数\n" +
                "chdrgDesc\tAns\t500\t否\t中药饮片处方：中药饮片处方的详细描述\n" +
                "remark\tVAR\t500\t否\t备注说明\n" +
                "recipeMediList\tArray\t-\t是\t处方药品列表数据，采用JSON Array方式包含";

        String recipeMediStr = "generalName\tVAR\t200\t是\t平台药品通用名：国家医药管理局核定的药品法定名称，与国际通用的药品名称、我国药典及国家药品监督管理部门颁发药品标准中的名称一致\n" +
                "platDrugId\tAns\t32\t是\t平台药品编码\n" +
                "hisDrugId\tAns\t32\t是\t互联网医疗机构药品编码\n" +
                "hisDrugName\tVAR\t200\t是\t互联网医疗机构药品名称以下为医疗机构处方原信息\n" +
                "typeCode\tAns\t50\t否\t药品分类代码, 0: 非药品 1.西药 2.中成药 3.中草药 9.其他中药\n" +
                "typeName\tVAR\t200\t否\t医疗机构药品分类名称\n" +
                "quantity\tN\t10\t是\t数量，购买数量包装数量。\n" +
                "quantityUnit\tVAR\t10\t是\t数量单位\n" +
                "unitPrice\tN\t10\t是\t单价（元），小数位5位 包装单位单价。例：每盒的价格\n" +
                "costTotal\tN\t10\t是\t金额（元）小数位2位11\n" +
                "packingSpec\tVAR\t50\t是\t包装规格，例:10毫克*10片*2板/盒\n" +
                "packingUnit\tVAR\t10\t是\t包装单位：盒、瓶\n" +
                "days\tN\t5\t是\t用药天数\n" +
                "freqCode\tAns\t50\t否\t用药频率编码  FREQ_CODE_DICT 字典\n" +
                "freqName\tVAR\t50\t是\t用药频率，如：每日一次\n" +
                "dosage\tN\t12\t是\t每次用量\n" +
                "doseUnit\tVAR\t20\t是\t剂量单位，药品规格最小单位(例：毫克) \n" +
                "totalDosage\tN\t12\t是\t总用量\n" +
                "dosageUnit\tVAR\t50\t是\t总用量单位，医生提供的用法用量单位\n" +
                "usageName\tVAR\t100\t是\t用法，如:口服\n" +
                "usageAdvice\tVAR\t200\t否\t医嘱嘱托，如戒酒、餐前、餐后等字符串\n" +
                "skinTest\tVAR\t50\t否\t皮试判别标志 0 否；1 是\n" +
                "itemSort\tVAR\t200\t否\t组内排序：草药排序时，以四列计算，不足四列时也需留空列，即:排序为5的，一定指第二行第一列，不管是按3列书写还是4列书写\n" +
                "drugChLeft\tVAR\t200\t否\t中草药名称前标注的特殊要求：中草药处方：对饮片的产地、炮制有特殊要求的，应当在药品名称之前写明\n" +
                "drugChRight\tVAR\t200\t否\t中草药名称右上方标注的特殊要求：中草药处方：调剂、煎煮的特殊要求注明在药品右上方，并加括号，如打碎、先煎、后下等";

        JSONObject bodyJson = new JSONObject(true);
        Arrays.stream(body.split("\n")).forEach(tr->{
            String[] tds = tr.split("\t");
            bodyJson.put(tds[0],tds[4]);
        });
        JSONArray recipeList = new JSONArray();
        JSONObject recipeJson = new JSONObject(true);
        Arrays.stream(recipeStr.split("\n")).forEach(tr->{
            String[] tds = tr.split("\t");
            recipeJson.put(tds[0],tds[4]);
        });
        JSONArray recipeMediList = new JSONArray();
        JSONObject recipeMediJson = new JSONObject(true);
        Arrays.stream(recipeMediStr.split("\n")).forEach(tr->{
            String[] tds = tr.split("\t");
            recipeMediJson.put(tds[0],tds[4]);
        });
        recipeMediList.add(recipeMediJson);
        recipeJson.put("recipeMediList",recipeMediList);
        recipeList.add(recipeJson);
        bodyJson.put("recipeList",recipeList);
        System.out.println(bodyJson);
    }

    public void setJSONObject(boolean sign, String str, JSONObject jsonObject){
        Arrays.stream(str.split("\n")).forEach(tr->{
            String[] tds = tr.split("\t");
            jsonObject.put(tds[0],tds[4]);
        });
    }

    @Test
    void test2() {
        String body = "orgUuid\tVAR\t50\t是\t原就诊机构ID\n" +
                "medicalSerial\tVAR\t50\t是\t原就诊病历流水号\n" +
                "hisPatientSerial\tVAR\t50\t是\t原门诊就诊流水号\n" +
                "diagnosisCode\tVAR\t800\t否\t门诊诊断编码（主要诊断）\n" +
                "diagnosisName\tVAR\t512\t否\t门诊诊断名称\n" +
                "diagnosisType\tVAR\t2\t否\t编码类型： 01：西医；02：中医；\n" +
                "symptom\tANS\t2000\t否\t主诉\n" +
                "medicalHistory\tVAR\t4000\t否\t现病史列表，有多项时用“；” 隔开; \n" +
                "oldMedicalHistory\tVAR\t4000\t否\t既往史列表，有多项时用“；”隔开\n" +
                "allergicHistory\tVAR\t4000\t否\t过敏史列表，有多项时用“；”隔开\n" +
                "allergicCode\tVAR\t50\t否\t过敏源代码列表，有多项时用“；”隔开 ALLERGIC_CODE_DICT 字典\n" +
                "symptomDesc\tVAR\t1024\t否\t症状描述列表，有多项时用“；”隔开\n" +
                "symptomCode\tVAR\t50\t否\t症状代码列表，使用症状代码 SYMPTOM_CODE_DICT字典,有多项时用“；”隔开\n" +
                "diagWzCode\tVAR\t800\t否\t初步诊断--西医诊断编码列表，多个以 “;”间隔\n" +
                "diagWzName\tVAR\t2000\t否\t初步诊断--西医诊断名称列表，多个以 “;”间隔\n" +
                "diagCzCode\tVAR\t64\t否\t初步诊断--中医病名代码列表，多个以 “;”间隔\n" +
                "diagCzName\tVAR\t1500\t否\t初步诊断--中医病名名称列表，多个以 “;”间隔\n" +
                "diagTcmCode\tVAR\t64\t否\t初步诊断--中医证候代码列表，多个以 “;”间隔\n" +
                "diagTcmName\tVAR\t512\t否\t初步诊断--中医证 候名称列表，多个以“;”间隔";

        JSONObject bodyJson = new JSONObject(true);
        Arrays.stream(body.split("\n")).forEach(tr->{
            String[] tds = tr.split("\t");
            bodyJson.put(tds[0],tds[4]);
        });
        System.out.println(bodyJson);
    }

    @Test
    void test3() {
        String body = "orgUuid\tVAR\t50\t是\t原就诊机构ID\n" +
                "medicalSerial\tVAR\t50\t是\t原就诊病历流水号\n" +
                "hisPatientSerial\tVAR\t50\t是\t原门诊就诊流水号\n" +
                "diagnosisCode\tVAR\t800\t否\t门诊诊断编码（主要诊断）\n" +
                "diagnosisName\tVAR\t512\t否\t门诊诊断名称\n" +
                "diagnosisType\tVAR\t2\t否\t编码类型： 01：西医；02：中医；\n" +
                "symptom\tANS\t2000\t否\t主诉\n" +
                "medicalHistory\tVAR\t4000\t否\t现病史列表，有多项时用“；” 隔开; \n" +
                "oldMedicalHistory\tVAR\t4000\t否\t既往史列表，有多项时用“；”隔开\n" +
                "allergicHistory\tVAR\t4000\t否\t过敏史列表，有多项时用“；”隔开\n" +
                "allergicCode\tVAR\t50\t否\t过敏源代码列表，有多项时用“；”隔开 ALLERGIC_CODE_DICT 字典\n" +
                "symptomDesc\tVAR\t1024\t否\t症状描述列表，有多项时用“；”隔开\n" +
                "symptomCode\tVAR\t50\t否\t症状代码列表，使用症状代码 SYMPTOM_CODE_DICT字典,有多项时用“；”隔开\n" +
                "diagWzCode\tVAR\t800\t否\t初步诊断--西医诊断编码列表，多个以 “;”间隔\n" +
                "diagWzName\tVAR\t2000\t否\t初步诊断--西医诊断名称列表，多个以 “;”间隔\n" +
                "diagCzCode\tVAR\t64\t否\t初步诊断--中医病名代码列表，多个以 “;”间隔\n" +
                "diagCzName\tVAR\t1500\t否\t初步诊断--中医病名名称列表，多个以 “;”间隔\n" +
                "diagTcmCode\tVAR\t64\t否\t初步诊断--中医证候代码列表，多个以 “;”间隔\n" +
                "diagTcmName\tVAR\t512\t否\t初步诊断--中医证 候名称列表，多个以“;”间隔";

        Arrays.stream(body.split("\n")).forEach(tr->{
            String[] tds = tr.split("\t");
            System.out.println("/**");
            System.out.println(" * " + tds[4]);
            System.out.println(" * \t" +
                    "+N\t12\t是");
            System.out.println(" */");
            System.out.println("private String " + tds[0] + ";");
        });
    }

    @Test
    void test4() {
        String body = "orderSerial\tAns\t50\t是\t平台订单号码,由平台生成提供，同一笔支付值相同，字符串 \n" +
                "orderStatus\tVAR\t2\t是\t订单状态：1：待支付，2：等待就诊，3：正在接诊，4：完成诊疗, 5：已取消，6：超时作废, 7:退款  8.已完成\n" +
                "payStatus\tVAR\t2\t是\t支付状态： 0:未支付  1：已支付\n" +
                "refundStatus\tVAR\t2\t是\t退款状态：0：未退款  1：已退款\n" +
                "Reply_status\tVAR\t2\t是\t处方审批状态：0，待审批（默认），1已批复 ，2已拒绝\n" +
                "payType\t VAR\t2\t是\t支付类型DICT_HOSP_PAY_CHARGE_TYPE \n" +
                "payAmount\tN\t10\t是\t支付总金额 \n" +
                "siPayJson\tVAR\t4000\t否\t支付 \n" +
                "orderDesc\tAns\t500\t否\t订单说明";

        Arrays.stream(body.split("\n")).forEach(tr->{
            String[] tds = tr.split("\t");
            if(tds[3].equals("是")){
                System.out.println("@NotBlank(message = \""+ tds[4] +",不能为空\")");
                System.out.println("@Length(max = "+tds[2]+", message = \""+ tds[4] +",过长\")");
            }else {
                System.out.println("/**");
                System.out.println(" * " + tds[4]);
                System.out.println(" * \t" +
                        "+N\t12\t是");
                System.out.println(" */");
            }
            System.out.println("private String " + tds[0] + ";");
        });
    }

}
