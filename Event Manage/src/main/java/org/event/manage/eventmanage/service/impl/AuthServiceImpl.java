package org.event.manage.eventmanage.service.impl;

import org.event.manage.eventmanage.dto.GetUserRole;
import org.event.manage.eventmanage.dto.LoginRequest;
import org.event.manage.eventmanage.dto.RegisterRequest;
import org.event.manage.eventmanage.exception.InvalidCredentials;
import org.event.manage.eventmanage.exception.UserAlreadyExist;
import org.event.manage.eventmanage.exception.UserNotFound;
import org.event.manage.eventmanage.model.User;
import org.event.manage.eventmanage.service.AuthService;
import org.event.manage.eventmanage.util.HibernateUtilService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServiceImpl implements AuthService {


    @Override
    public boolean register(RegisterRequest request) throws UserAlreadyExist{

        Session session = HibernateUtilService.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            User existingUser = session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", request.getEmail())
                    .uniqueResult();

            if (existingUser != null) {
                throw new UserAlreadyExist("User with email " + request.getEmail() + " already exists");
            }

            String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(hashedPassword)
                    .role(request.getRole())
                    .phone(request.getPhone())
                    .build();
            session.save(user);
            transaction.commit();
            return true;

        } catch (UserAlreadyExist e) {
            System.out.println(1);
            if (transaction != null) transaction.rollback();
            throw e;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public GetUserRole login(LoginRequest loginRequest) throws UserNotFound, InvalidCredentials {

        try (Session session = HibernateUtilService.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
                query.setParameter("email", loginRequest.getEmail());

                User user = query.uniqueResult();

                if (user == null) {
                    throw new UserNotFound("User with email " + loginRequest.getEmail() + " not found.");
                }

                if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                    throw new InvalidCredentials("Incorrect password for user " + loginRequest.getEmail());
                }

                transaction.commit();
                GetUserRole getUserRole = new GetUserRole();
                getUserRole.setRole(user.getRole());
                return getUserRole;

            } catch (UserNotFound | InvalidCredentials e) {
                transaction.rollback();
                throw e;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Unexpected error during login", e);
            }
        }
    }
}
