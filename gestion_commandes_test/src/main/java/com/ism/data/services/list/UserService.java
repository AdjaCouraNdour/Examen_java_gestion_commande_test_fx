package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.repository.interfaces.UserRepositoryI;
import com.ism.data.services.interfaces.UserServiceImpl;

public class UserService implements UserServiceImpl {
    
    UserRepositoryI repo;

    public UserService(UserRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public boolean save(User object) {
        return repo.insert(object);
    }

    @Override
    public List<User> show() {
        return repo.selectAll();
    }

    @Override
    public User getById(int id) {
        return repo.selectById(id);
    }

    @Override
    public User getByLogin(String login) {
        return repo.selectByLogin(login);
    }


    public List<User> getByUserEtat() {
        return 
        repo.selectAll().stream()
            .filter(user -> user.isActif() == true)
            .toList();
    }

    @Override
    public User getByEmail(String email) {
        return repo.selectByEmail(email);
    }

    @Override
    public List<User> getByUserRole(UserRole role) {
        return 
        repo.selectAll().stream()
            .filter(user -> user.getUserRole() == role)
            .toList();
    }

    @Override
    public void effacer(User object) {
        repo.remove(object);
    }

    @Override
    public boolean update(User object) {
        return repo.update(object);
    }

    @Override
    public User validateUser(String login, String password) {
        return repo.validateUser(login,password);
    }

} 


   
    
