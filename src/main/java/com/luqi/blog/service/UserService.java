package com.luqi.blog.service;

import com.luqi.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
