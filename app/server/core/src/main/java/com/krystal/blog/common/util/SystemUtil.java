package com.krystal.blog.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by idiot on 2017/4/10.
 * @description  获取系统相关信息
 */
public class SystemUtil {
    private static Logger logger = LoggerFactory.getLogger(SystemUtil.class);

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static String Windows = "windows";

    /**
     * @description 判断当前系统是否是windows
     * @return
     */
    public static boolean isWindows(){
        return OS.indexOf(Windows) >= 0;
    }

    /**
     * 获取当前系统的jdk版本
     * @return
     */
    public static String getJavaVersion(){
        String javaVersion = System.getProperty("java.version");
        return javaVersion.substring(0, 3);
    }

}