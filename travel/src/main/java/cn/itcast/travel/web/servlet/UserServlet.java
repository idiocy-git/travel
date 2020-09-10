package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 *  用户信息servlet
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    ResultInfo info = null;
    UserService userService = new UserServiceImpl();

    /**
     * 激活用户方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void checkCheckCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        info = new ResultInfo();
        // 获取用书输入验证码
        String check = req.getParameter("check");
        // 从session获取验证码
        HttpSession session = req.getSession();
        String checkCode = (String) session.getAttribute("checkCode_session");
        session.removeAttribute("checkCode_session"); // 防止取到重复验证码值
        if (!check.equalsIgnoreCase(checkCode)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        } else {
            info.setFlag(true);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 校验用户名方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void checkUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        info = new ResultInfo();
        // 获取用户输入用户名
        String username = req.getParameter("username");
        User user = userService.checkUserName(username);
        if (user != null) {
            info.setFlag(false);
            info.setErrorMsg("用户名已被占用,请重新输入用户名!");
        } else {
            info.setFlag(true);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 用户注册方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        info = new ResultInfo();
        // 1.获取数据
        Map<String, String[]> map = req.getParameterMap();
        // 2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 3.调用service完成注册

        boolean flag = userService.register(user);
        // 4.响应结果集
        info.setFlag(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 用户登录
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        info = new ResultInfo();
        User user = new User();
        Map<String, String[]> parameterMap = req.getParameterMap();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        User loginUser = userService.login(user);
        if (loginUser == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
        }
        if (loginUser != null && !"Y".equalsIgnoreCase(loginUser.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户未激活,请先激活用户!");
        }
        if (loginUser != null && "Y".equalsIgnoreCase(loginUser.getStatus())) {
            req.getSession().setAttribute("user", loginUser);
            info.setFlag(true);
            info.setData(loginUser.toString());
        }
       /* ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(), info);*/

       writeValue(info,resp);
    }

    /**
     * 用户退出
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }


    /**
     * 激活用户方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        String code = req.getParameter("code");
        boolean flag = userService.active(code);
        String msg = null;
        if (flag) {
            msg = "激活成功,请<a href='login.html'>登录</a>";
        } else {
            msg = "激活失败,请联系管理员!";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(msg);
    }

    /**
     * 显示登录后用户信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        /*ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(resp.getOutputStream(),user);*/
        writeValue(user,resp);
    }

}
