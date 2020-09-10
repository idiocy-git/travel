package cn.itcast.travel.dao.impl;


import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 商品图片dao实现
 */
public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql = null;

    @Override
    public List<RouteImg> findByRid(int rid) {
        List<RouteImg> routeImgs = null;
        sql = "select * from tab_route_img where rid = ?";
        try {
            routeImgs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return routeImgs;
    }
}
