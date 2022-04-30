package com.krystal.blog.api;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.krystal.blog.common.beans.ApplicationTemplate;
import com.krystal.blog.common.beans.R;
import com.krystal.blog.common.util.Const;
import com.krystal.blog.common.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
public class FileController {

    @Resource
    private ApplicationTemplate applicationTemplate;

    /**
     * 上传文件
     * @return
     */
    @PostMapping(value = "/api/file/upload")
    public Object upload(HttpServletRequest httpServletRequest){
        // 根据目录id获取目录路径
        String uploadPath = FileUtil.addPathSeparate(applicationTemplate.getBaseDirectory(), Const.BLOG_FILE);
        FileUtil.buildFileByPath(uploadPath);
        log.info("/api/file/upload......upload path: [{}]", uploadPath);

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) httpServletRequest;
        // 取得request中的所有文件名
        Iterator<String> iterator = multiRequest.getFileNames();
        try {
            List fileList = new ArrayList();
            while(iterator.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iterator.next());
                if (file != null) {
                    // 取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    File destTempFile = new File(uploadPath, myFileName);

                    FileUtil.copyInputStreamToFile(file, destTempFile);

                    // 获取文件拓展名
                    String extName = FileUtil.getExtName(myFileName);
                    // 重命名文件
                    String newFileName = System.currentTimeMillis() + RandomUtil.randomString(5) + "." + extName;
                    File finalFile = new File(uploadPath, newFileName);
                    if(destTempFile.renameTo(finalFile)){
                        log.info("/api/file/upload......rename file.........new file name: [{}]", finalFile.getPath());
                    }

                    // 计算文件结果
                    String result = FileUtil.removeBaseDirectory(applicationTemplate.getBaseDirectory(), finalFile.getPath());
                    result = FileUtil.addPathSeparate(getServerPath(httpServletRequest), result);
                    log.info("/api/file/upload...file path result: [{}]", result);
                    fileList.add(result);
                }
            }
            return R.ok("upload file success")
                    .put(Const.DATA, fileList);
        } catch (Exception e){
            log.error("/api/file/upload......upload file failed......msg: [{}]......cause: [{}]",
                    e.getMessage(), e.getCause());
            return R.error(400, "upload file failed");
        }
    }

    // 获取服务地址
    private String getServerPath(HttpServletRequest httpServletRequest) {
        return String.format("%s://%s:%s",
                httpServletRequest.getScheme(),
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort() + httpServletRequest.getContextPath());
    }
}
