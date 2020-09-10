package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    /**
     * 根据类别进行分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> pageQuery(int cid ,int currentPage , int pageSize, String rname);

    /**
     * 根据Id查询商品详情
     * @param rid 商品ID
     * @return 结果集
     */
    Route findOne(int rid);
}
