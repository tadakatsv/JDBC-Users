package org.example.app.mapper;

import org.example.app.entity.User;

import java.util.Map;

public class UserMapper {

    public User mapData(Map<String, String> data) {
        User user = new User();
        if (data.containsKey("id"))
            user.setId(Long.parseLong(data.get("id")));
        if (data.containsKey("name"))
            user.setName(data.get("name"));
        if (data.containsKey("surname"))
            user.setSurname(data.get("surname"));
        if (data.containsKey("email"))
            user.setEmail(data.get("email"));
        return user;
    }
}
