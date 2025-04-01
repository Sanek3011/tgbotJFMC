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
        user.setState(state);
        dao.update(user);
    }


    public User getUserByName(String userName)  {
        try {
            return dao.getUserByUsername(userName);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer deleteUser(String username) {
       return dao.deleteByUsername(username);
    }

    public void changeRoleByUsername(User user, String role) throws IllegalArgumentException {

            Role roles = Role.valueOf(role.toUpperCase());
        System.out.println("----> полученная роль"+roles);
            user.setRole(roles);
        System.out.println(user.getRole()+"<------ Установленная роль");
            dao.update(user);
    }
    public List<Long> getAllUsersTgId() {
        return dao.getAllTgIdUsers();

    }

    public void updateUserScore(User user, String op, String num) {
        Integer score = user.getScore();
        int number = Integer.parseInt(num);
        switch (op) {
            case "plus":
                user.setScore(score+number);
                dao.update(user);
                break;
            case "minus":
                user.setScore(score-number);
                if (user.getScore() < 0) {
                    user.setScore(0);
                }
                dao.update(user);
                break;
            default:
                throw new IllegalStateException();
        }
    }
}
