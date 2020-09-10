package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    List<Category> categoryList = null;

    @Override
    public List<Category> findAll() {
        /*
            1. 从redis中查询
                1.1 获取jedis客户端
                1.2 使用sortedset排序查询
            2. 判断查询的集合是否为空
            3. 如果为空,需要从数据库查询,并将数据存入redis
            4. 如果不为空,直接返回
         */
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        if (categorys == null || categorys.size() <= 0) { // 如果为空
            // 查询数据库
            categoryList = categoryDao.findAll();
            // 存入redis
            for (Category category : categoryList) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        } else { // 如果不为空
            categoryList = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
