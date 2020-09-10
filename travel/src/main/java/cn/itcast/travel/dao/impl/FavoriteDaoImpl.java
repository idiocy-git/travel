package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int countFavoriteByRid(int rid) {
        sql = "select count(rid) from tab_favorite where rid = ?";
        int count = 0;
        try {
            count = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public void add(int rid, int uid) {
        sql = "insert into tab_favorite values(?,?,?)";
        jdbcTemplate.update(sql,rid,new Date(),uid);
    }
}
