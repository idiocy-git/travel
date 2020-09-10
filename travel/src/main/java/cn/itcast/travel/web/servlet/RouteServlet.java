package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 旅游线路商品展示servlet
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        // 解决get中文乱码问题
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        rname = rname.equalsIgnoreCase("null") ? null : rname;
        // 2.处理参数
        // 类别ID
        int cid = (cidStr != null && cidStr.length() > 0 && !cidStr.equalsIgnoreCase("null")) ? Integer.valueOf(cidStr) : 0;
        // 当前页码,如果不传递默认为第1页
        int currentPage = (currentPageStr != null && currentPageStr.length() > 0) ? Integer.valueOf(currentPageStr) : 1;
        // 每页显示条数,如果为空,默认每页显示8条数据
        int pageSize = (pageSizeStr != null && pageSizeStr.length() > 0) ? Integer.valueOf(pageSizeStr) : 8;
        // 3.调用service查询PageBean对象
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
        // 4.将PageBean对象序列化为Json,返回
        writeValue(routePageBean, response);
    }

    /**
     * 查询一个商品详情信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收ID 参数
        String ridStr = request.getParameter("rid");
        int rid = (ridStr != null && ridStr.length() > 0 && !ridStr.equalsIgnoreCase("null")) ? Integer.valueOf(ridStr) : 0;
        // 2.调用service查询
        Route route = routeService.findOne(rid);
        // 3.转为json写回客户端
        writeValue(route, response);
    }

    /**
     * 判断当前登录用户是否收藏过该线路
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取线路ID
        String ridStr = request.getParameter("rid");
        int rid = (ridStr != null && !"".equals(ridStr) && !ridStr.equalsIgnoreCase("null")) ? Integer.valueOf(ridStr) : 0;
        // 2.获取当前登录用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            // 用户未登录
            uid = 0;
        } else {
            // 用户已经登录
            uid = user.getUid();
        }
        // 3.调用FavoriteService 查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        // 4.写回客户端
        writeValue(flag, response);
    }

    /**
     * 添加收藏线路方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取线路ID
        String ridStr = request.getParameter("rid");
        int rid = (ridStr != null && !"".equals(ridStr) && !ridStr.equalsIgnoreCase("null")) ? Integer.valueOf(ridStr) : 0;
        // 2.获取当前登录用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            // 用户未登录
            return;
        } else {
            // 用户已经登录
            uid = user.getUid();
        }
        // 3.调用FavoriteService 添加收藏
        favoriteService.add(rid,uid);
    }

}
