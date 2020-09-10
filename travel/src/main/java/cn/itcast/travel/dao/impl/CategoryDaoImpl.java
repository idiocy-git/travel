package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 商品类别dao实现
 */
public class CategoryDaoImpl implements CategoryDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    String sql = null;

    @Override
    public List<Category> findAll() {
        sql = "select * from tab_category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
