package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品dao实现
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql = null;

    @Override
    public int findTotalCount(int cid, String rname) {
        sql = "select count(cid) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        return jdbcTemplate.queryForObject(sb.toString(), Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        sql = "select * from tab_route  where 1=1  ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(pageSize);
        return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    @Override
    public Route findById(int rid) {
        sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
