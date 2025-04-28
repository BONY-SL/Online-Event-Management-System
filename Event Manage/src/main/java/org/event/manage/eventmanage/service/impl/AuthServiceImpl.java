package org.event.manage.eventmanage.service.impl;

import org.event.manage.eventmanage.dto.RegisterRequest;
import org.event.manage.eventmanage.exception.UserAlreadyExist;
import org.event.manage.eventmanage.model.User;
import org.event.manage.eventmanage.service.AuthService;
import org.event.manage.eventmanage.util.HibernateUtilService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServiceImpl implements AuthService {


    @Override
    public boolean register(RegisterRequest request) {

        Transaction transaction = null;
        try {
            Session session = HibernateUtilService.getSessionFactory().openSession();
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
        }
    }
}
