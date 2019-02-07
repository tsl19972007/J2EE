package service;

import model.User;
import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findUser(String username, String password){
        return userDao.findUser(username,password);
    }

    @Override
    public boolean addUser(User user){
        return userDao.addUser(user);
    }

    @Override
    public void updateUser(User user){
        userDao.updateUser(user);
    }

}
