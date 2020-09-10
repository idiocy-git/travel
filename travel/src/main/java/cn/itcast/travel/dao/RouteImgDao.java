package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * 商品图片dao
 */
public interface RouteImgDao {
    /**
     * 根据 rid 查询商品图片
     * @param rid 商品id
     * @return 结果集
     */
    List<RouteImg> findByRid(int rid);
}
