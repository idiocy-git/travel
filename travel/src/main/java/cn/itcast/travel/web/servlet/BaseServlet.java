package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * servlet 方法分发管理
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取请求路径
        String uri = req.getRequestURI();
        // 2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        // 3.获取方法对象Method
        try {
            // 忽略访问权限修饰符,获取方法,不推荐使用
            // Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 暴力反射,不推荐使用
            // method.setAccessible(true);
            // 获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 4.执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将传入的对象序列化为Json,并写回客户端
     *
     * @param object
     * @param response
     * @throws IOException
     */
    public void writeValue(Object object, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), object);
    }

    /**
     * 将对象序列化为Json,返回
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
