package org.event.manage.eventmanage.service.impl;

import org.event.manage.eventmanage.dto.RegisterRequest;
import org.event.manage.eventmanage.model.User;
import org.event.manage.eventmanage.service.AuthService;
import org.event.manage.eventmanage.util.HibernateUtilService;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuthServiceImpl implements AuthService {


    public boolean register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .phone(request.getPhone())
                .build();

        Transaction transaction = null;
        try (Session session = HibernateUtilService.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
