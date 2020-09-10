package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * 商品类别Dao
 */
public interface CategoryDao {
    /**
     * 查询所有
     * @return
     */
    List<Category> findAll();
}
