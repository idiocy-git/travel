package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     *  用户注册方法
     * @return 是否成功
     */
    boolean register(User user);

    /**
     * 核实用户名是否重复
     * @param userName 用户名
     * @return 用户对象
     */
    User checkUserName(String userName);

    /**
     *  激活用户
     * @param code 唯一注册码
     */
    boolean active(String code);

    /**
     * 用户登录
     * @return 用户对象
     */
    User login(User user);
}
