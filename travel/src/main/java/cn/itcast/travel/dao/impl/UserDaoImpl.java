package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 用户Dao实现
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    private String sql;

    @Override
    public User findByUserName(String userName) {
        User user = null;
        try {
            sql = "select * from tab_user where username=?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) {
        sql = "insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(), user.getName(), user.getBirthday(),
                user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public int updateStatus(String code, String status) {
        sql = "update tab_user set status = ? where code = ?";
        return jdbcTemplate.update(sql,status,code);
    }

    @Override
    public User findByUserCode(String code) {
        User user = null;
        try {
            sql = "select * from tab_user where code=?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            sql = "select * from tab_user where username = ? and password = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username ,password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }
}
