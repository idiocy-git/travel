package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 返回所有分类
     * @return 结果集
     */
    List<Category> findAll();
}
