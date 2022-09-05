package com.demo.swaggerknife4jutils.controller.file;

import com.demo.swaggerknife4jutils.common.ApiResult;
import com.demo.swaggerknife4jutils.config.CustomFileConfig;
import com.demo.swaggerknife4jutils.utils.file.excel.common.StringUtils;
import com.demo.swaggerknife4jutils.utils.file.excel.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @description: 文件上传下载
 * @author: huangxinwei
 * @date: 2021/1/22 9:52
 */
@Api(tags = "文件上传下载")
@Controller
@RequestMapping("/file/upDown")
@Slf4j
public class UpDownloadController {

    @Autowired
    private CustomFileConfig customFileConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @PostMapping("/download")
    @ApiOperation(value = "通用下载请求", notes = "通用下载请求", produces = "application/octet-stream")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);

            String filePath = System.getProperty("user.dir") + customFileConfig.getUpDownloadAddr() + "/" + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());

            // 下载后删除文件
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件异常", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "通用上传请求", notes = "通用上传请求")
    public ApiResult uploadFile(@RequestParam MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return ApiResult.failed("文件不存在");
        }
        try {
            // 扩展名
            String fileName = System.currentTimeMillis() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            // 上传文件路径
            String filePath = System.getProperty("user.dir")+ customFileConfig.getUpDownloadAddr() + "/"+ fileName;

            File path = new File(filePath);
            // 判断父级目录是否存在

            if (!path.getParentFile().exists()) {
                path.getParentFile().mkdirs();
            }
            // 文件对拷
            file.transferTo(path);

            return ApiResult.success(customFileConfig.getUpDownloadAddr() + "/"+ fileName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ApiResult.failed("操作失败");
        }
    }

}
