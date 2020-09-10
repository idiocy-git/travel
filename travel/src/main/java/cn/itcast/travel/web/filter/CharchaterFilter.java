package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CharchaterFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 将父类接口转为子接口
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        // 获取请求方法
        String method = servletRequest.getMethod();
        // 解决post请求中文数据乱码问题
        if (method.equalsIgnoreCase("post")) {
            servletRequest.setCharacterEncoding("utf-8");
        }
        // 处理响应乱码问题
        servletResponse.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
