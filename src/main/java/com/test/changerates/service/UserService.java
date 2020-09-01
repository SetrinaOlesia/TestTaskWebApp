package com.test.changerates.service;

import com.test.changerates.entity.User;

public interface UserService {
    User register(User user);
    User getUserById(long id);
}
