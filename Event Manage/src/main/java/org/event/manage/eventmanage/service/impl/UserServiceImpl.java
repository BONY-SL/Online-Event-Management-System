package org.event.manage.eventmanage.service.impl;

import org.event.manage.eventmanage.dto.MyEventDTO;
import org.event.manage.eventmanage.dto.UserBookEventDTO;
import org.event.manage.eventmanage.exception.EventNotFound;
import org.event.manage.eventmanage.exception.InsufficientTicketsException;
import org.event.manage.eventmanage.exception.UserNotFound;
import org.event.manage.eventmanage.model.Event;
import org.event.manage.eventmanage.model.User;
import org.event.manage.eventmanage.model.UserBookEvent;
import org.event.manage.eventmanage.service.UserService;
import org.event.manage.eventmanage.util.HibernateUtilService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {


    @Override
    public boolean eventBook(UserBookEventDTO userBookEventDTO) throws UserNotFound , EventNotFound,InsufficientTicketsException{
        try (Session session = HibernateUtilService.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
                query.setParameter("email", userBookEventDTO.getEmail());
                User user = query.uniqueResult();

                Query<Event> query2 = session.createQuery("FROM Event WHERE id = :id", Event.class);
                query2.setParameter("id", userBookEventDTO.getEventId());
                Event event = query2.uniqueResult();

                if (user == null) {
                    throw new UserNotFound("User with email " + userBookEventDTO.getEmail() + " not found.");
                }

                if (event == null) {
                    throw new EventNotFound("Event Not Found " + userBookEventDTO.getEventId() + " not found.");
                }

                int requestedTickets = userBookEventDTO.getCount();
                if (event.getAvailableTickets() < requestedTickets) {
                    throw new InsufficientTicketsException("Only " + event.getAvailableTickets() + " tickets left.");
                }


                event.setAvailableTickets(event.getAvailableTickets() - requestedTickets);
                session.update(event);

                UserBookEvent userBookEvent = UserBookEvent.builder()
                        .user(user)
                        .event(event)
                        .ticketsCount(userBookEventDTO.getCount())
                        .build();

                session.save(userBookEvent);
                transaction.commit();
                return true;

            } catch (UserNotFound | EventNotFound | InsufficientTicketsException e) {
                transaction.rollback();
                throw e;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("Unexpected error during login", e);
            }
        }
    }

    @Override
    public List<MyEventDTO> getMyAllEvents(String email) {
        try (Session session = HibernateUtilService.getSessionFactory().openSession()){

            Transaction transaction = session.beginTransaction();

            Query<User> userQuery = session.createQuery("FROM User WHERE email = :email", User.class);
            userQuery.setParameter("email", email);
            User user = userQuery.uniqueResult();

            if (user == null) {
                transaction.commit();
                return List.of();
            }

            List<UserBookEvent> bookings = user.getBookings();

            List<MyEventDTO> myEvents = bookings.stream().map(booking -> {
                Event event = booking.getEvent();
                return MyEventDTO.builder()
                        .eventName(event.getName())
                        .date(event.getDate())
                        .venue(event.getVenue())
                        .ticketsBooked(booking.getTicketsCount())
                        .build();
            }).collect(Collectors.toList());

            transaction.commit();
            return myEvents;
        }catch (Exception e) {
            return List.of();
        }
    }
}
