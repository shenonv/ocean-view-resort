package com.oceanview.service;

import com.oceanview.dao.UserDAO;
import com.oceanview.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public boolean login(String username, String password) {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }
}