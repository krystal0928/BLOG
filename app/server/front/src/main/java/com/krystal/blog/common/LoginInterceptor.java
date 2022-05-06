package com.krystal.blog.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.krystal.blog.common.annotation.NoNeedLogIn;
import com.krystal.blog.common.beans.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor{

    private boolean checkNoNeedLogIn(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            NoNeedLogIn noNeedLogIn = handlerMethod.getMethod().getAnnotation(NoNeedLogIn.class);

            // 如果标记了注解，则判断权限
            if (noNeedLogIn != null && noNeedLogIn.value()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        //这句话是解决乱码的
        response.setHeader("Content-Type", "text/html;charset=UTF-8");

        // 不需要拦截
        if (checkNoNeedLogIn(handler)) {
            return true;
        }

        // token 存在才允许访问接口
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            String res = JSONUtil.toJsonStr(R.error(400, "请求失败，token参数无效"));
            response.getWriter().write(res);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
