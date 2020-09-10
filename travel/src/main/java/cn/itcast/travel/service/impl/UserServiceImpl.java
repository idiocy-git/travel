package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        // 用户注册成功
        userDao.save(user);
        // 给用户发送右键
        String content = "<a href='http://localhost/travel/user/activeUser?code="+user.getCode()+"'>点击激活用户</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public User checkUserName(String userName) {
        try {
            User byUserName = userDao.findByUserName(userName);
            return byUserName;
        } catch (Exception ex) {
            System.out.println("没有找到该用户名对象,正常异常！！");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean active(String code) {
        User user = userDao.findByUserCode(code);
        user.setStatus("Y");
        if (user!= null){
            userDao.updateStatus(user.getCode(),user.getStatus());
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
