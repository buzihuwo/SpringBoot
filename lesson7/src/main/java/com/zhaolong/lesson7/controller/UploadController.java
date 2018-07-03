package com.zhaolong.lesson7.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
public class UploadController {
    private  final  static Logger logger=LoggerFactory.getLogger(UploadController.class);

    @GetMapping("/index")
    public String index() {
        logger.info("访问了index方法");
        logger.debug("记录debug日志");
        logger.error("访问了error错误方法");
        return "index";
    }

    @PostMapping("/upload")
    public @ResponseBody
    String upload(HttpServletRequest request, MultipartFile file) {
        try {
            //上传目录地址
            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdir();//创建文件夹
            executeUpload(uploadDir, file);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }

    @PostMapping("/uploads")
    public @ResponseBody
    String uploads(HttpServletRequest request, MultipartFile[] file) {
        try {
            //上传目录地址
            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdir();//创建文件夹
            for (int i = 0; i < file.length; i++) {
                String name = file[i].getOriginalFilename();
                if (!name.isEmpty()) {
                    //调用上传方法
                    executeUpload(uploadDir, file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }

    private void executeUpload(String uploadDir, MultipartFile file) throws Exception {
        //文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filnNaem = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filnNaem);
        //将上传文件写入到服务器端的文件内
        file.transferTo(serverFile);
    }
}
