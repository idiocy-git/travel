package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 分类信息servlet
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 查询所有分类
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.调用service查询所有
        List<Category> categoryList = categoryService.findAll();
        // 2.序列化json返回
        writeValue(categoryList,response);
    }

}
