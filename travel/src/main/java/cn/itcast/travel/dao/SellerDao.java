package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * 商家Dao
 */
public interface SellerDao {
    /**
     * 根据ID 查询商家信息
     * @param sid 商家ID
     * @return 结果
     */
    Seller findById(int sid);
}
