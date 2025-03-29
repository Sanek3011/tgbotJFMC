package org.example.service;

import jakarta.persistence.NoResultException;
import org.example.dao.UserDao;
import org.example.model.Report;
import org.example.model.Role;
import org.example.model.State;
import org.example.model.User;

import java.util.List;

public class UserService {
    private UserDao dao = new UserDao();

    public List<User> getAllUsers() {
        return dao.getAll();
    }

    public User getUserByTgId(Long chatId) {
        try {
            return dao.getUserByTgId(chatId);
        } catch (NoResultException e) {
            return null;
        }
    }
    public void saveUser(User user) {
        dao.save(user);
    }

    public void updateUsername(Long chatId, String nickname) {
        User userByTgId = getUserByTgId(chatId);
        userByTgId.setName(nickname);
        dao.update(userByTgId);

    }
    public void updateUserState(User user, State state){
//        User userByTgId = getUserByTgId(chatId);
        user.setState(state);
        dao.update(user);
    }


    public User getUserByName(String userName) throws Exception {
        return dao.getUserByUsername(userName);
    }

    public Integer deleteUser(String username) {
       return dao.deleteByUsername(username);
    }

    public void changeRoleByUsername(User user, String role) throws IllegalArgumentException {

            Role roles = Role.valueOf(role.toUpperCase());
            dao.changeRoleByUser(user, roles);




    }
}
