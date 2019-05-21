package com.ninggc.gp.controller;

import com.ninggc.gp.service.FileService;
import com.ninggc.gp.util.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController extends IController {

    FileService fileService = null;
    private String folder = "/upload/1/1503130115/";

    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        if (!file.isEmpty()) {
            String saveFileName = file.getOriginalFilename();
            File saveFile = new File(request.getSession().getServletContext().getRealPath(folder) + saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return Log.debug(saveFile.getName() + " 上传成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return Log.debug("上传失败," + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                return Log.debug("上传失败," + e.getMessage());
            }
        } else {
            return Log.debug("上传失败，因为文件为空.");
        }

    }


    /**
     * 多文件上传
     *
     * @param request
     * @return
     */
    @RequestMapping("/uploadFiles")
    @ResponseBody
    public String uploadFiles(HttpServletRequest request) throws IOException {
        File savePath = new File(request.getSession().getServletContext().getRealPath(folder));
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    return "第 " + i + " 个文件上传有错误" + e.getMessage();
                }
            } else {
                return "第 " + i + " 个文件为空";
            }
        }
        return "所有文件上传成功";
    }

    @RequestMapping("test")
    public String test() {
        return "upload";
    }

    @RequestMapping("/{filename}")
    public void download(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) {
        try (InputStream inputStream = new FileInputStream(new File(folder, filename + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {

            //设置内容类型为下载类型
            response.setContentType("application/x-download");
            //设置请求头 和 文件下载名称
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            //用 common-io 工具 将输入流拷贝到输出流
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkFileLocation(String location, String account, int process_id) {
        try(SqlSession session = openSession()) {
            initService(session);

            com.ninggc.gp.data.File pojo = new com.ninggc.gp.data.File();
            pojo.setFilename("");
            pojo.setProcess_id(process_id);

            fileService.insert(pojo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initService(SqlSession session) throws IOException {
        fileService = new FileService(session);
    }
}
