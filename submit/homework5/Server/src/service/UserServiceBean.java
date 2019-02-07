package service;

import model.User;
import dao.UserDao;
import factory.DaoFactory;

import javax.ejb.Stateless;

@Stateless
public class UserServiceBean implements UserService {
    private UserDao userDao;

    private final static UserServiceBean userServiceBean = new UserServiceBean();

    public static UserServiceBean getInstance() {
        return userServiceBean;
    }

    public UserServiceBean() {
        userDao = DaoFactory.getUserDao();
    }

    @Override
    public User findUser(String username, String password){
        return userDao.findUser(username,password);
    }

    @Override
    public boolean addUser(User user){
        return userDao.addUser(user);
    }

    public void updateUser(User user){
        userDao.updateUser(user);
    }

}
