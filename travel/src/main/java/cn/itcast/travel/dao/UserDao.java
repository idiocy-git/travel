package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * 用户dao
 */
public interface UserDao {
    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return 用户对象
     */
    User findByUserName(String userName);

    /**
     *  保存用户
     * @param user 用户对象
     */
    void save(User user);

    /**
     * 根据唯一注册码修改用户激活状态
     * @param code 唯一注册码
     * @param status 激活状态
     */
    int updateStatus(String code,String status);

    /**
     *  根据注册码查询用户信息
     * @return 用户对象
     */
    User findByUserCode(String code);

    /**
     * 根据用户名及密码查询用户信息
     * @param username 用户名
     * @param password 密码
     * @return 用户对象
     */
    User findByUsernameAndPassword(String username, String password);
}
