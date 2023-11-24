package cn.lwt_server.interceptor;

import cn.lwt_server.pojo.Result;
import com.alibaba.fastjson.JSON;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle ...");
        String url = request.getRequestURI().toString();
        log.info("请求的url: {}", url);
        System.out.println("请求的url: " + url);

        String jwt = request.getHeader("token");

        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头为空,返回未登录的信息");
            System.out.println("请求头为空,返回未登录的信息");
            Result result = new Result(1, "NOT_LOGIN", null);
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey("cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=").build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析失败");
            System.out.println("解析失败");
            Result result = new Result(1,"NOT_LOGIN",null);
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }

        log.info("令牌合法,放行");
        System.out.println("令牌合法,放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle ...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}
