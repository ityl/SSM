
package com.ssm.boot.controller;

import com.ssm.boot.dto.dept.Dept;
import com.ssm.boot.enums.ErrorCodeEnum;
import com.ssm.boot.service.DeptService;
import com.ssm.boot.util.ResponseUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

@RestController
@RequestMapping("/k")
public class DeptDtoController {

    @Value("${bone.jpg.dir}")
    private String jpgDir;

    @Autowired
    private DeptService deptService;
    private static final Logger logger = LoggerFactory.getLogger(DeptDtoController.class);

    @RequestMapping({"/list"})
    public void getList(HttpServletResponse response) {
        List var2 = this.deptService.getDeptList();

        try {
            ResponseUtil.renderSuccessJson(response, "ok", this.deptService.getDeptList());
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/id/{id}"})
    public void getDeptById(HttpServletResponse response, @PathVariable("id") String id) {
        try {
            ResponseUtil.renderSuccessJson(response, "ok", this.deptService.selectById(id));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/add"})
    public void getDeptById(HttpServletResponse response, Dept dept) {
        try {
            ResponseUtil.renderSuccessJson(response, "ok", Integer.valueOf(this.deptService.insert(dept)));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/del"})
    public void deleteByID(HttpServletResponse response, @RequestParam("id") String id) {
        try {
            ResponseUtil.renderSuccessJson(response, "ok", Integer.valueOf(this.deptService.del(id)));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping({"/all"})
    public void all(HttpServletResponse response) {
        try {
            ResponseUtil.renderSuccessJson(response, "ok", Integer.valueOf(this.deptService.all()));
        } catch (Exception e) {
            ResponseUtil.renderFailJson(response, ErrorCodeEnum.INVALID_PARAM);
            logger.error(" exception", e);
        }

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam(value = "file", required = true) MultipartFile uploadFile,
                       String fileMd5,
                       String chunk,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        if (request instanceof MultipartHttpServletRequest) {
            logger.info("文件唯一标识" + fileMd5);
            String OriginalFilename = uploadFile.getOriginalFilename();
            String format = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1);
            logger.info("格式" + format);

            File fileMD5 = new File(jpgDir + fileMd5);
            if (!fileMD5.exists()) {
                fileMD5.mkdir();
            }
            File file = new File(jpgDir + fileMd5 + "/" + chunk);
            try {
                logger.info(OriginalFilename + "上传文件名");
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(file, false));
                out.write(uploadFile.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ResponseUtil.renderSuccessJson(response, "ok", 1);

    }


    @RequestMapping(value = "/chunk", method = RequestMethod.POST)
    public void upload(
            String fileMd5,
            String fileName,
            HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println(fileName);
        System.out.println(fileMd5);

        File fileMD5 = new File(jpgDir + fileMd5);
        File[] fileArray = fileMD5.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()){
                    return  false;
                }
                return true;
            }
        });

        List<File> fileList = new ArrayList<>(Arrays.asList(fileArray));

        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.valueOf(o1.getName())-Integer.valueOf(o2.getName());
            }
        });

        System.out.println(fileList);


        File outFile = new File(jpgDir+fileName);

        try {
            outFile.createNewFile();

            //输出流

            FileChannel outChannel = new FileOutputStream(outFile).getChannel();

            FileChannel inChannel;

            for (File file:fileList){
                inChannel = new FileInputStream(file).getChannel();
                inChannel.transferTo(0,inChannel.size(),outChannel);
                inChannel.close();
                file.delete();
            }
            File file = new File(jpgDir+fileMd5);
            if (file.exists()&&file.isDirectory()){
                file.delete();
            }
            outChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseUtil.renderSuccessJson(response, "ok", 1);
    }

    //断点续传

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public void check(
            String fileMd5,
            String chunk,
            String chunkSize,
            HttpServletRequest request,
            HttpServletResponse response) {

        File file = new File(jpgDir+fileMd5+"/"+chunk);
        if (file.exists()&&file.length()==Integer.parseInt(chunkSize)){
            ResponseUtil.renderSuccessJson(response, "ok", 1);
        }
    }


    @RequestMapping(value = "/chunk/upload", method = RequestMethod.POST)
    public void chunkupload(@RequestParam(value = "file", required = true) MultipartFile uploadFile,
                            HttpServletRequest request, HttpServletResponse response) {
        if (request instanceof MultipartHttpServletRequest) {
            String OriginalFilename = uploadFile.getOriginalFilename();
            String format = OriginalFilename.substring(OriginalFilename.lastIndexOf(".") + 1);
            logger.info("格式" + format);
            File file = new File(jpgDir + OriginalFilename);
            try {
                logger.info(OriginalFilename + "上传文件名");
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(file, false));
                out.write(uploadFile.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ResponseUtil.renderSuccessJson(response, "ok", 1);

    }

}