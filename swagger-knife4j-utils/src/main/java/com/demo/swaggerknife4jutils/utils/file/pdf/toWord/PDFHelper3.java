package com.demo.swaggerknife4jutils.utils.file.pdf.toWord;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

import java.io.*;

public class PDFHelper3 {

    public static void main(String[] args) throws IOException {
        pdf2doc("D:\\File\\Demo\\demo\\swagger-knife4j-utils\\src\\main\\resources\\《市级电子健康码提供疫苗接种服务改造指引（试行）》无红头.pdf");
    }

    //pdf转doc
    public static void pdf2doc(String pdfPath) {
        long old = System.currentTimeMillis();
        try {
            //新建一个word文档
            String wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".docx";
            FileOutputStream os = new FileOutputStream(wordPath);
            //doc是将要被转化的word文档
            Document doc = new Document(pdfPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.DocX);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Pdf 转 Word 失败...");
            e.printStackTrace();
        }
    }


}
