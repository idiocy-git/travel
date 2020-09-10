package cn.itcast.travel.web.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 用户激活拦截器
 */
@WebFilter("/activeUserServlet")
public class ActiveUserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null && !"".equals(code)) {
            filterChain.doFilter(request, response);
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("请携带激活码激活用户信息！");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
