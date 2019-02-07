package service;

import model.User;

public interface UserService {
    public User findUser(String username, String password);

    //return false if username is duplicate
    public boolean addUser(User user);

    public void updateUser(User user);
}
