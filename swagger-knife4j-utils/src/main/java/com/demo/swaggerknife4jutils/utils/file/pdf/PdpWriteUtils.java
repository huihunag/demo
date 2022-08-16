package com.demo.swaggerknife4jutils.utils.file.pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * PDF工具类
 */
@Component
public class PdpWriteUtils {
    private static final Logger logger = LoggerFactory.getLogger(PdpWriteUtils.class);

    float SIGN_IMAGE_WIDTH = 500f;

    /**
     *    *  将PDF转换成base64编码
     *    *  1.使用BufferedInputStream和FileInputStream从File指定的文件中读取内容；
     *    *  2.然后建立写入到ByteArrayOutputStream底层输出流对象的缓冲输出流BufferedOutputStream
     *    *  3.底层输出流转换成字节数组，然后由BASE64Encoder的对象对流进行编码
     *    *
     */
    public String getPDFBinary(String filePath) {
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout = null;
        try {
            //建立读取文件的文件输出流
            fin = new FileInputStream(new File(filePath));
            //在文件输出流上安装节点流（更大效率读取）
            bin = new BufferedInputStream(fin);
            // 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
            baos = new ByteArrayOutputStream();
            //创建一个新的缓冲输出流，以将数据写入指定的底层输出流
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1048];
            int len = bin.read(buffer);
            while (len != -1) {
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
            byte[] bytes = baos.toByteArray();
            //sun公司的API
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encodeBuffer(bytes).trim().replaceAll("\r|\n", "");
            //apache公司的API
            //return Base64.encodeBase64String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
                bin.close();
                //关闭 ByteArrayOutputStream 无效。此类中的方法在关闭此流后仍可被调用，而不会产生任何 IOException
                //baos.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *    * 将base64编码转换成PDF，保存到
     *    * @param base64sString
     *    * 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *    *  2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *    *  3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *    *  4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     */
    public boolean base64StringToPDF(String base64sString, String filePath) {
        logger.info("#######filePath:" + filePath);
        int i = StringUtils.isNotBlank(base64sString) ? base64sString.length() : 0;
        logger.info("#######base64sString:" + i);
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            //将base64编码的字符串解码成字节数组
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64sString);
            //apache公司的API
            //byte[] bytes = Base64.decodeBase64(base64sString);
            //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            //指定输出的文件
            File file = new File(filePath);
            if (file.getParentFile().exists() == false) {
                file.getParentFile().mkdirs();
            }
            //创建到指定文件的输出流
            fout = new FileOutputStream(file);
            //为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
            return true;
        } catch (IOException e) {
            logger.error("########转换异常PDF异常", e);
            return false;
        } finally {
            try {
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                logger.error("########转换异常PDF关闭资源异常", e);
            }
        }
    }

    public void setValuePDF(Object object, JSONObject objJson) {
        try {
            String value = objJson.getString("value");
            // 使用反射处理默认配置
            Field field = object.getClass().getDeclaredField(value);
            if (field == null) {
                value = "-";
            } else {
                field.setAccessible(true);
                value = String.valueOf(field.get(object));
                value = StringUtils.isNotBlank(value)?value:"";
                String handle = objJson.getString("handle");
                if ("1".equals(handle)) {
                    // 加密解密
                } else if ("2".equals(handle)) {
                    // 格式转换
                } else if ("3".equals(handle)) {
                    // 字典转换
                } else if ("4".equals(handle)) {
                    value = transparentImage(value,10);
                }
            }
            objJson.put("value", value);
        } catch (Exception e) {
            objJson.put("value", "-");
            logger.error("########生成签名文件，数据转换异常", e);
        }
    }

    /**
     * @description 生成pdf文件
     * @Date 2022/2/25 16:19
     * @Author Administrator
     * @Param DIR
     * @Param PCP_SRC_FILE
     * @Param PCP_DEST_FILE
     * @Param jsonObject
     * @Return void
     * json配置说明
     * 1.k:v-->文件内容标识（定位）:相关属性
     * 1.1.公共属性
     * type:text->文本，radio->单选（当前仅支持所有题目选择标识相同的，例如全为是否），image->图片base
     * value:需要覆盖pdf的值，配置为相关类属性，由反射获取,image为图片base,（radio为数组，并非覆盖文本，覆盖文本为choose）
     * x:横向偏移量
     * y:纵向偏移量
     * handle:反射数据处理，0:默认，不处理，1:解密处理，2:时间处理，3:数据字典转换，4:图片背景透明处理
     * 1.2.radio专属
     * choose:覆盖文本
     * option:选项选值
     * 1.3.image专属
     * size:图片大小比例,数值越大图片越小
     * 1.4.时间处理
     * space:空格数
     * 1.4.数据字典
     * code:数据字典code
     */
    public void generateSignPDF(String DIR, String PCP_SRC_FILE, String PCP_DEST_FILE, JSONObject jsonObject) {
        try {
            // 创建一个pdf读入流
            byte[] pdfData = getBytesByFile(DIR + PCP_SRC_FILE);
            PdfReader reader = new PdfReader(pdfData); // PdfReader reader = new PdfReader(new byte[10]);
            // 根据一个pdfreader创建一个pdfStamper.用来生成新的pdf.
            FileOutputStream fout = new FileOutputStream(DIR + PCP_DEST_FILE);// ByteArrayOutputStream
            PdfStamper stamper = new PdfStamper(reader, fout);

            // 这个字体是itext-asian.jar中自带的 所以不用考虑操作系统环境问题.
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); // set font
            // baseFont不支持字体样式设定.但是font字体要求操作系统支持此字体会带来移植问题.
            Font font = new Font(bf, 10);
            font.setStyle(Font.BOLD);
            font.getBaseFont();
            float yP = -10;
            float xP = 12;

            jsonObject.forEach((key, value) -> {
                List<CharPosition> personDrugList = null;
                try {
                    personDrugList = getCharPositionList(key, pdfData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (personDrugList == null || personDrugList.size() == 0) {
                    return;
                }
                JSONObject valueJSONObject = JSONObject.parseObject(value.toString());
                String type = valueJSONObject.getString("type");
                String val = valueJSONObject.getString("value");
                String strValue = StringUtils.isNotBlank(val)?val:"";

                float finalX = valueJSONObject.containsKey("x") ? Float.valueOf(valueJSONObject.getString("x")) : 0;
                float finalY = valueJSONObject.containsKey("y") ? Float.valueOf(valueJSONObject.getString("y")) : 0;
                switch (type) {
                    case "text":
                        personDrugList.forEach(personDrug -> {
                                    // 获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
                                    PdfContentByte over = stamper.getOverContent(personDrug.getPageNum());
                                    // 开始写入文本
                                    over.beginText();
                                    // 设置字体和大小
                                    over.setFontAndSize(font.getBaseFont(), 11);
                                    // 设置字体颜色
                                    over.setColorFill(BaseColor.BLACK);
                                    // 设置字体的输出位置
                                    over.setTextMatrix(personDrug.getX() + key.length() * xP + finalX, personDrug.getY() + yP + finalY);
                                    // 要输出的text
                                    over.showText(String.valueOf(strValue));
                                    over.endText();
                                }
                        );
                        break;
                    case "radio":
                        JSONArray jsonArray = valueJSONObject.getJSONArray("value");
                        String radioValue = String.valueOf(valueJSONObject.get("choose"));
                        if (personDrugList.size() >= jsonArray.size()) {
                            Integer option = valueJSONObject.getInteger("option");
                            for (int j = 0; j < jsonArray.size(); j++) {
                                if (!option.equals(jsonArray.getInteger(j))) {
                                    continue;
                                }
                                CharPosition personDrug = personDrugList.get(j);
                                // 获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
                                PdfContentByte over = stamper.getOverContent(personDrug.getPageNum());
                                // 开始写入文本
                                over.beginText();
                                // 设置字体和大小
                                over.setFontAndSize(font.getBaseFont(), 11);
                                // 设置字体颜色
                                over.setColorFill(BaseColor.BLACK);
                                // 设置字体的输出位置
                                over.setTextMatrix(personDrug.getX() + finalX, personDrug.getY() + yP + finalY);
                                // 要输出的text
                                over.showText(String.valueOf(radioValue));
                                over.endText();
                            }
                        }
                        break;
                    case "image":
                        personDrugList.forEach(personDrug -> {
                            // 获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
                            PdfContentByte over = stamper.getOverContent(personDrug.getPageNum());
                            // 创建一个image对象.
                            try {
                                byte[] pcpLog = Base64.getDecoder().decode(String.valueOf(strValue));
                                Image image = Image.getInstance(pcpLog);
                                // 图片的位置
                                image.setAbsolutePosition(personDrug.getX() + key.length() * xP + finalX, personDrug.getY() + yP + finalY);
                                float size = valueJSONObject.containsKey("size") ? Float.valueOf(valueJSONObject.getString("size")) : 2;
                                float newWidth = SIGN_IMAGE_WIDTH / size;
                                float newHeight = SIGN_IMAGE_WIDTH/image.getWidth()*image.getHeight() / size;
                                image.scaleAbsolute(newWidth, newHeight);
                                image.setTransparency(new int[]{255, 255, 255, 255, 255, 255});
                                over.addImage(image);
                            } catch (Exception e) {
                                logger.error("########生成签名文件，插入图片异常", e);
                            }
                        });
                        break;
                    default:
                        break;
                }
            });
            stamper.close();
        } catch (Exception e) {
            logger.error("########生成签名文件异常", e);
        }
    }

    //将文件转换成Byte数组
    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<CharPosition> getCharPositionList(String keyword, byte[] pdfData) throws IOException {
        //5.调用方法，给定关键字和文件
        List<float[]> positions = findKeywordPostions(pdfData, keyword);
        List<CharPosition> charPositionList = new ArrayList<>();
        if (positions != null && positions.size() > 0) {
            for (float[] position : positions) {
                charPositionList.add(new CharPosition((int) position[0], position[1], position[2]));
            }
        }
        return charPositionList;
    }

    /**
     * findKeywordPostions
     *
     * @param pdfData 通过IO流 PDF文件转化的byte数组
     * @param keyword 关键字
     * @return List<float [ ]> : float[0]:pageNum float[1]:x float[2]:y
     * @throws IOException
     */
    public static List<float[]> findKeywordPostions(byte[] pdfData, String keyword) throws IOException {
        List<float[]> result = new ArrayList<>();
        List<PdfPageContentPositions> pdfPageContentPositions = getPdfContentPostionsList(pdfData);
        for (PdfPageContentPositions pdfPageContentPosition : pdfPageContentPositions) {
            List<float[]> charPositions = findPositions(keyword, pdfPageContentPosition);
            if (charPositions == null || charPositions.size() < 1) {
                continue;
            }
            result.addAll(charPositions);
        }
        return result;
    }

    private static List<float[]> findPositions(String keyword, PdfPageContentPositions pdfPageContentPositions) {
        List<float[]> result = new ArrayList<>();
        String content = pdfPageContentPositions.getContent();
        List<float[]> charPositions = pdfPageContentPositions.getPositions();
        for (int pos = 0; pos < content.length(); ) {
            int positionIndex = content.indexOf(keyword, pos);
            if (positionIndex == -1) {
                break;
            }
            float[] postions = charPositions.get(positionIndex);
            result.add(postions);
            pos = positionIndex + 1;
        }
        return result;
    }

    private static List<PdfPageContentPositions> getPdfContentPostionsList(byte[] pdfData) throws IOException {
        PdfReader reader = new PdfReader(pdfData);
        List<PdfPageContentPositions> result = new ArrayList<>();
        int pages = reader.getNumberOfPages();
        for (int pageNum = 1; pageNum <= pages; pageNum++) {
            float width = reader.getPageSize(pageNum).getWidth();
            float height = reader.getPageSize(pageNum).getHeight();
            PdfRenderListener pdfRenderListener = new PdfRenderListener(pageNum, width, height);
            // 解析pdf，定位位置
            PdfContentStreamProcessor processor = new PdfContentStreamProcessor(pdfRenderListener);
            PdfDictionary pageDic = reader.getPageN(pageNum);
            PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
            try {
                processor.processContent(ContentByteUtils.getContentBytesForPage(reader, pageNum), resourcesDic);
            } catch (IOException e) {
                reader.close();
                throw e;
            }
            String content = pdfRenderListener.getContent();
            List<CharPosition> charPositions = pdfRenderListener.getcharPositions();
            List<float[]> positionsList = new ArrayList<>();
            for (CharPosition charPosition : charPositions) {
                float[] positions = new float[]{charPosition.getPageNum(), charPosition.getX(), charPosition.getY()};
                positionsList.add(positions);
            }
            PdfPageContentPositions pdfPageContentPositions = new PdfPageContentPositions();
            pdfPageContentPositions.setContent(content);
            pdfPageContentPositions.setPostions(positionsList);
            result.add(pdfPageContentPositions);
        }
        reader.close();
        return result;
    }

    private static class CharPosition {
        private int pageNum = 0;
        private float x = 0;
        private float y = 0;

        public CharPosition(int pageNum, float x, float y) {
            this.pageNum = pageNum;
            this.x = x;
            this.y = y;
        }

        public int getPageNum() {
            return pageNum;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        @Override
        public String toString() {
            return "[pageNum=" + this.pageNum + ",x=" + this.x + ",y=" + this.y + "]";
        }
    }


    private static class PdfPageContentPositions {
        private String content;
        private List<float[]> positions;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<float[]> getPositions() {
            return positions;
        }

        public void setPostions(List<float[]> positions) {
            this.positions = positions;
        }
    }

    private static class PdfRenderListener implements RenderListener {
        private int pageNum;
        private float pageWidth;
        private float pageHeight;
        private StringBuilder contentBuilder = new StringBuilder();
        private List<CharPosition> charPositions = new ArrayList<>();

        public PdfRenderListener(int pageNum, float pageWidth, float pageHeight) {
            this.pageNum = pageNum;
            this.pageWidth = pageWidth;
            this.pageHeight = pageHeight;
        }

        public void beginTextBlock() {
        }

        public void renderText(TextRenderInfo renderInfo) {
            List<TextRenderInfo> characterRenderInfos = renderInfo.getCharacterRenderInfos();
            for (TextRenderInfo textRenderInfo : characterRenderInfos) {
                String word = textRenderInfo.getText();
                if (word.length() > 1) {
                    word = word.substring(word.length() - 1, word.length());
                }
                com.itextpdf.awt.geom.Rectangle2D.Float rectangle = textRenderInfo.getAscentLine().getBoundingRectange();
                float x = (float) rectangle.getX();
                float y = (float) rectangle.getY();
                // 这两个是关键字在所在页面的XY轴的百分比
                float xPercent = Math.round(x / pageWidth * 10000) / 10000f;
                float yPercent = Math.round((1 - y / pageHeight) * 10000) / 10000f;
                CharPosition charPosition = new CharPosition(pageNum, (float) x, (float) y);
                charPositions.add(charPosition);
                contentBuilder.append(word);
            }
        }

        public void endTextBlock() {
        }

        public void renderImage(ImageRenderInfo renderInfo) {
        }

        public String getContent() {
            return contentBuilder.toString();
        }

        public List<CharPosition> getcharPositions() {
            return charPositions;
        }
    }

    /**
     * 设置源图片为背景透明，并设置透明度
     *
     * @param base64   源图片
     * @param alpha      透明度
     * @throws IOException
     */
    private String transparentImage(String base64,int alpha){
        try {
            // 图片base64转BufferedImage
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage srcImage = ImageIO.read(bais);

            int imgWidth = srcImage.getWidth(); //取得图片的长和宽
            int imgHeight = srcImage.getHeight();
            int c = srcImage.getRGB(3, 3);
            //防止越位
            if (alpha < 0) {
                alpha = 0;
            } else if (alpha > 10) {
                alpha = 10;
            }

            BufferedImage bi = new BufferedImage(imgWidth, imgHeight,
                    BufferedImage.TYPE_4BYTE_ABGR);//新建一个类型支持透明的BufferedImage
            for (int i = 0; i < imgWidth; ++i){//把原图片的内容复制到新的图片，同时把背景设为透明
                for (int j = 0; j < imgHeight; ++j) {//把背景设为透明
                    if (srcImage.getRGB(i, j) == c) {
                        bi.setRGB(i, j, c & 0x00ffffff);
                    } else {//设置透明度
                        int rgb = bi.getRGB(i, j);
                        rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
                        bi.setRGB(i, j, rgb);
                    }
                }
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", stream);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(stream.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
            return base64;
        }
    }

    public static void main(String[] args) throws Exception {
        StringBuilder base64String = new StringBuilder();
        base64String.append("JVBERi0xLjcNJcjIyMjIyMgNMSAwIG9iago8PC9UeXBlL0NhdGFsb2cvVmVyc2lvbi8xLjcvUGFnZXMgMyAwIFIvT3V0bGluZXMgMiAwIFIvTWV0YWRhdGEgMTQgMCBSPj4NCmVuZG9iagoyIDAgb2JqCjw8L1R5cGUvT3V0bGluZXM+Pg0KZW5kb2JqCjMgMCBvYmoKPDwvVHlwZS9QYWdlcy9LaWRzWzggMCBSXS9Db3VudCAxPj4NCmVuZG9iago0IDAgb2JqCjw8L0F1dGhvcigpL0NyZWF0aW9uRGF0ZShEOjIwMjAwNTE0MTEzNzIzKzA4JzAwJykvTW9kRGF0ZShEOjIwMjAwNTE0MTEzNzIzKzA4JzAwJykvUHJvZHVjZXIoQXNwb3NlLlBERiBmb3IgLk5FVCAxOS41KS9TdWJqZWN0KCkvVGl0bGUoKS9DcmVhdG9yKEFzcG9zZSBMdGQuKT4+DQplbmRvYmoKNiAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvTGVuZ3RoIDg+PnN0cmVhbQ0KeJwDAAAAAAENCmVuZHN0cmVhbQ0KZW5kb2JqCjcgMCBvYmoKWy9QREZdDQplbmRvYmoKOCAwIG9iago8PC9UeXBlL1BhZ2UvUGFyZW50IDMgMCBSL01lZGlhQm94WzAgMCAyNzggMjYxXS9Db250ZW50cyAxMiAwIFIvUmVzb3VyY2VzPDwvUHJvY1NldCAxMCAwIFIvRXh0R1N0YXRlPDwvR1MwPDwvQk0vTm9ybWFsPj4+Pi9YT2JqZWN0PDwvSW0wIDExIDAgUj4+Pj4vQ3JvcEJveFswIDAgMjc4IDI2MV0+Pg0KZW5kb2JqCjkgMCBvYmoKPDwvRmlsdGVyL0ZsYXRlRGVjb2RlL0xlbmd0aCA4Pj5zdHJlYW0NCnicAwAAAAABDQplbmRzdHJlYW0NCmVuZG9iagoxMCAwIG9iagpbL1BERl0NCmVuZG9iagoxMSAwIG9iago8PC9GaWx0ZXIvRENURGVjb2RlL0xlbmd0aCA0NzA5NC9UeXBlL1hPYmplY3QvU3VidHlwZS9JbWFnZS9XaWR0aCAyNzgvSGVpZ2h0IDI2MS9Db2xvclNwYWNlL0RldmljZVJHQi9CaXRzUGVyQ29tcG9uZW50IDgvTmFtZS9JbTA+PnN0cmVh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RH8W/ipZeD9G8A23ilvi/wDHvwMY/CXh++1jU9I0n+xfhr8UvBvh1/sl7r+rz/bpNJfUp/tfl3N5NDBbRw/ob8Kvhh4G+CXwv+G/wY+GGhjwx8NPhF4C8H/DD4eeGhqWr6yPD3gbwD4e07wp4S0Mav4gv9V17VRpOgaTp9gNS1vVNS1e++z/AGrUr+8vJZriTvaKACoLr/j2uP8ArhL/AOi2qeoLr/j2uP8ArhL/AOi2oA46iiiuc5wro9I/49n/AOu7f+i4qKKuG/y/VFw3+X6o1aKKK1NQooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACoLr/j2uP+uEv/AKLaiigDjqKKK5znP//ZDQplbmRzdHJlYW0NCmVuZG9iagoxMiAwIG9iagpbOSAwIFIgMTMgMCBSXQ0KZW5kb2JqCjEzIDAgb2JqCjw8L0ZpbHRlci9GbGF0ZURlY29kZS9MZW5ndGggNDQ+PnN0cmVhbQ0KeJzj5eIq5NJ3DzZQSC/mMjK3UDAAQiMzQzCdnMul75lroOCSzxUIAKYcCHENCmVuZHN0cmVhbQ0KZW5kb2JqCjE0IDAgb2JqCjw8L0xlbmd0aCAyMzcvVHlwZS9NZXRhZGF0YS9TdWJ0eXBlL1hNTD4+c3RyZWFtDQo8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/Pgo8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIj4KIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgLz4KIDwvcmRmOlJERj4KPC94OnhtcG1ldGE+Cjw/eHBhY2tldCBlbmQ9InIiPz4NCmVuZHN0cmVhbQ0KZW5kb2JqCnhyZWYKMCA1DQowMDAwMDAwMDAwIDY1NTM1IGYNCjAwMDAwMDAwMTggMDAwMDAgbg0KMDAwMDAwMDEwNyAwMDAwMCBuDQowMDAwMDAwMTQyIDAwMDAwIG4NCjAwMDAwMDAxOTQgMDAwMDAgbg0KNiA5DQowMDAwMDAwMzY5IDAwMDAwIG4NCjAwMDAwMDA0NDUgMDAwMDAgbg0KMDAwMDAwMDQ2OCAwMDAwMCBuDQowMDAwMDAwNjU1IDAwMDAwIG4NCjAwMDAwMDA3MzEgMDAwMDAgbg0KMDAwMDAwMDc1NSAwMDAwMCBuDQowMDAwMDQ4MDE3IDAwMDAwIG4NCjAwMDAwNDgwNDkgMDAwMDAgbg0KMDAwMDA0ODE2MyAwMDAwMCBuDQp0cmFpbGVyCjw8L1Jvb3QgMSAwIFIvSW5mbyA0IDAgUi9JRFs8MzkzNjJEMzkzMzJENDQzODJEMzk0NjJEMzE0NDJENDM+PDM5MzYyRDM5MzMyRDQ0MzgyRDM5NDYyRDMxNDQyRDQzPl0vU2l6ZSAxNT4+CnN0YXJ0eHJlZgo0ODQ3OAolJUVPRgo=");
        new PdpWriteUtils().base64StringToPDF(base64String.toString(), "d:/test6.pdf");

        System.out.println(new PdpWriteUtils().getPDFBinary("d:/1.pdf"));
    }
}
