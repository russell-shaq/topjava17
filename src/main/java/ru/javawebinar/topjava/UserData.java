package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    public static List<User> USERS = new ArrayList<>();

    static {
        USERS.add(new User("Ruslan", "shakirovsa@gmail.com", "123456", Role.ROLE_USER));
        USERS.add(new User("Vladlena", "vladlena@gmail.com", "123456", Role.ROLE_USER));
        USERS.add(new User("Radyk", "radyk@gmail.com", "123456", Role.ROLE_USER));
        USERS.add(new User("Oxana", "oxana@gmail.com", "123456", Role.ROLE_ADMIN));
    }
}
