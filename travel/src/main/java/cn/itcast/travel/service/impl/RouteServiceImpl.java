package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> routePageBean = new PageBean<Route>();
        // totalCount; // 总记录数
        int totalCount = routeDao.findTotalCount(cid ,rname);
        routePageBean.setTotalCount(totalCount);

        // totalPage; // 总页数,= 总记录数 % 每页显示条数 == 0 ? 总记录数 / 每页显示条数 : (总记录数 / 每页显示条数) + 1
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        routePageBean.setTotalPage(totalPage);

        // currentPage; // 当前页码
        routePageBean.setCurrentPage(currentPage);

        // pageSize; // 每页显示条数
        routePageBean.setPageSize(pageSize);

        // List<T> list; // 每页显示的数据集合
        int start = (currentPage - 1) * pageSize; //开始的记录数,=(当前页码-1) * 每页显示条数
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        routePageBean.setList(list);

        return routePageBean;
    }

    @Override
    public Route findOne(int rid) {
        // 根据rid 查询商品详情
        Route route = routeDao.findById(rid);
        // 根据rid 查询商品所有图片并设置进集合
        route.setRouteImgList(routeImgDao.findByRid(route.getRid()));
        // 根据route 查询卖家信息
        route.setSeller(sellerDao.findById(route.getSid()));
        // 根据 route ID 获取收藏次数
        route.setCount(favoriteDao.countFavoriteByRid(route.getRid()));
        return route;
    }
}
