package com.ism.data.repository.jpa;

import java.util.List;
import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;
import jakarta.persistence.*;

public class UserRepositoryJPA extends RepositoryJPA<User> implements UserRepositoryI {
    
    ClientRepositoryI clientRepository;
    public UserRepositoryJPA(Class<User> type, ClientRepositoryI clientRepository) {
        super(type);
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean insert(User user) {
        try {
            if (em.contains(user) || user != null) {
                em.merge(user); 
            } else {
                em.persist(user); 
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }
    @Override
    public User selectById(int id) {
        try {
            return em.find(User.class, id);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par ID : " + e.getMessage());
            return null;
        }
    }

    @Override
    public User selectByLogin(String login) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.login = :login", User.class);
            // TypedQuery<User> query = em.createNamedQuery("User.findByLogin", User.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé avec le login : " + login);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par login : " + e.getMessage());
            return null;
        }
    }

    @Override
    public User selectByUserEtat(boolean etat) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.actif = :etat", User.class);
            query.setParameter("etat", etat);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé avec l'état : " + etat);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par état : " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> selectByRole(UserRole role) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.userRole = :role", User.class);
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche des utilisateurs par rôle : " + e.getMessage());
            return null;
        }
    }

    @Override
    public User selectByEmail(String email) {
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé avec l'email : " + email);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur par email : " + e.getMessage());
            return null;
        }
    }

    // @Override
    //   public User validateUser(String login, String password) {
    //     String sql = "SELECT 1 FROM \"user\" WHERE login = ? AND password = ?";
        
    //     User user =null;
    //     try {
    //         TypedQuery<User> query = em.createQuery(
    //             sql, User.class);
    //         query.setParameter(1, login);
    //         query.setParameter(2, password);
    //         // user = em.createQuery(sql,User.class).getSingleResult();
    //         return query.getSingleResult();

    //         // ResultSet resultSet = stmt.executeQuery();
    //     } catch (NoResultException e) {
    //         System.out.println("Aucun utilisateur trouvé avec login : " + login);
    //         return null;
    //     } catch (Exception e) {
    //         System.out.println("Erreur lors de la recherche de l'utilisateur par email : " + e.getMessage());
    //         return null;
    //     }
    // }
    @Override
    public User validateUser(String login, String password) {
        String sql = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password";
        
        User user = null;
        try {
            TypedQuery<User> query = em.createQuery(sql, User.class);
            query.setParameter("login", login);
            query.setParameter("password", password);
            user = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun utilisateur trouvé avec login : " + login);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
            return null;
        }
        return user;
    }

    
      
}
