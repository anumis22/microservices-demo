package com.example.restfulwebservices.user;

import org.springframework.stereotype.Component;
import com.example.restfulwebservices.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoService {
    private static List<User> usersList = new ArrayList<>();
    private static int usersCount = 3;

    static {
        usersList.add(new User(1, "Anu", new Date()));
        usersList.add(new User(2, "Swati", new Date()));
        usersList.add(new User(3, "Junaid", new Date()));
    }

    public List<User> findAll() {
        return usersList;
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }
        usersList.add(user);
        return user;
    }

    public User findOne(int id) {
        Optional<User> userOpt = usersList.stream().filter(u -> u.getId() == id).findFirst();
        return userOpt.equals(Optional.empty()) ? null : userOpt.get();
    }

    public boolean deleteById(int id) {
        User userToRemove = findOne(id);
        if(userToRemove != null)
            return usersList.remove(userToRemove);
        return false;
    }
}
