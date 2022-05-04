package com.krystal.blog.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

import java.io.File;

public class HtmlUtil {

    /**
     * 模版转换成 html 字符串
     * @param templatePath
     * @return
     */
    public static String templateToHtml(String templatePath, String content) {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate(templatePath);
        String result = template.render(Dict.create().set("content", content));
        return result;
    }

    /**
     * html 字符串转文件
     * @param filePath
     * @param fileName
     * @param html
     * @return
     */
    public static boolean htmlToFile(String filePath, String fileName, String html) {
        File parentFile = new File(filePath);
        if (!FileUtil.exist(parentFile)) {
            FileUtil.createTempFile(parentFile);
        }
        File file = FileUtil.file(parentFile, fileName);
        FileWriter writer = new FileWriter(file);
        writer.write(html);
        return true;
    }
}
