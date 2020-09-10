package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * 商品dao
 */
public interface RouteDao {


    /**
     * 根据 cid 查询总记录数
     *
     * @param cid 类别ID
     * @return 该类别数量
     */
    int findTotalCount(int cid, String rname);

    /**
     * 根据 cid start pageSize 查询当前页数据集合
     *
     * @param cid      类别ID
     * @param start    开始记录数
     * @param pageSize 每页显示数量
     * @return 结果集
     */
    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 根据ID查询商品详情
     *
     * @param rid 商品ID
     * @return 结果集
     */
    Route findById(int rid);
}
