package com.krystal.blog.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by idiot on 2016/12/28.
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * @description 拷贝文件流到文件
     * @param multipartFile
     * @param tempPartFile
     */
    public static void copyInputStreamToFile(MultipartFile multipartFile, File tempPartFile) {
        File file = null;
        try {
            // multipartFile 转 file
            file = FileUtil.convert(multipartFile);
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(tempPartFile);
            byte[] b = new byte[102400];
            int n;
            while ((n = fis.read(b)) != -1) {
                fos.write(b, 0, n);
            }
            fis.close();
            fos.close();
        }catch (Exception e){
            logger.error("fileInputStream to file......message: [{}]...caused: [{}]", e.getMessage(), e.getCause());
        }finally {
            // 用上面的 multipartFile 转 file方法  会在磁盘里面生成一个文件   干掉它
            if(file != null && file.exists()){
                file.delete();
            }
        }
    }

    /**
     * @description 合并分片文件到新文件里面
     * @param partFile  分片文件
     * @param destTempFile  新文件
     */
    public static void copyFile(File partFile, File destTempFile) {
        try {
            FileInputStream fis = new FileInputStream(partFile);
            FileOutputStream fos = new FileOutputStream(destTempFile, true);
            byte[] b = new byte[1024000];
            int n;
            while ((n = fis.read(b)) != -1) {
                fos.write(b, 0, n);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            logger.error("old file to new file......message: [{}]...caused: [{}]", e.getMessage(), e.getCause());
        }
    }

    /**
     * @description 删除目录以及目录下的文件
     * @param tempFileDir
     */
    public static void deleteDirectory(File tempFileDir) {
        if(tempFileDir.exists()){
            deleteFile(tempFileDir);
        }
    }

    public static void deleteFile(File file){
        if(file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }

    /**
     * @description multipartFile 转 file   使用该方法会在磁盘里生成一个文件(通常是在项目根路径下)
     * @param file
     * @return
     */
    public static File convert(MultipartFile file) {
        File convertFile = new File(file.getOriginalFilename());
        try {
            if(convertFile.createNewFile()){
                FileOutputStream fos = new FileOutputStream(convertFile);
                fos.write(file.getBytes());
                fos.close();
            }else logger.error("convert file failed......");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return convertFile;
    }

    /**
     * @description 替换反斜杠\ 为正斜杠 /  通常windows中才会存在反斜杠
     * @param filePath
     * @return
     */
    public static String getTrulyPath(String filePath){
        if(SystemUtil.isWindows()){
            filePath = filePath.replaceAll("\\\\", "/");
        }
        return filePath;
    }

    /**
     * @description  根据当前系统构建存储目录
     * @param path
     * @return
     */
    public static File buildFileByPath(String path){
        // 保证文件夹存在
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        logger.info("directory path......filePath: [{}]", path);
        return fileDir;
    }

    /**
     * @description 移除windows系统下的根目录
     * @param baseDirectory
     * @param path
     * @return
     */
    public static String removeBaseDirectory(String baseDirectory, String path){
        return FileUtil.getTrulyPath(path).replaceAll(baseDirectory, "");
    }

    /**
     * @description 给目录加上"/"分隔符
     * @param path
     * @return
     */
    public static String addPathSeparate(String path){
        if(!path.endsWith("/")){
            path += "/";
        }
        return path;
    }

    public static String addPathSeparate(String ... paths) {
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (String path : paths){
            if(i > 0 && i < paths.length && path.startsWith("/"))
                path = path.replaceFirst("^/", "");
            if(i == paths.length-1)
                buffer.append(path);
            else
                buffer.append(addPathSeparate(path));
            i++;
        }
        return buffer.toString();
    }


    /**
     * @description  根据文件名  获取文件的拓展名
     * @param myFileName
     */
    public static String getExtName(String myFileName) {
        if(myFileName == null || "".equals(myFileName))
            return null;
        int index = myFileName.lastIndexOf(".");
        // 文件没有拓展名
        if(index == -1) {
            return null;
        }
        // 获取文件拓展名
        return myFileName.substring(index + 1);
    }

}