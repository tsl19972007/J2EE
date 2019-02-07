package service;

import model.User;
import dao.UserDao;
import factory.DaoFactory;


public class UserServiceImpl implements UserService {
    private UserDao userDao;

    private static UserServiceImpl userServiceImpl = new UserServiceImpl();

    public static UserServiceImpl getInstance() {
        return userServiceImpl;
    }

    public UserServiceImpl() {
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
