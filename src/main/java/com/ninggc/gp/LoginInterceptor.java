package com.ninggc.gp;

import com.ninggc.gp.util.Log;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//
//        HttpSession session = request.getSession();
//        Object ob = session.getAttribute("user");
//        if (ob != null) {
////            设置允许跨域访问
////            如果已经登陆就不拦截
//            return true;
//        }
//        Log.debug("LoginInterceptor: " + request.getRequestURI());
//        session.setAttribute("preurl", request.getRequestURI());
//        StringBuffer url = request.getRequestURL();
//        //将url转到login
//        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/login").toString();
//        response.sendRedirect(tempContextUrl);
//        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView model) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }

}
