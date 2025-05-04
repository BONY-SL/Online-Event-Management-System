package org.event.manage.eventmanage.service.impl;

import org.event.manage.eventmanage.dto.EventDTO;
import org.event.manage.eventmanage.dto.ListUserEventDTO;
import org.event.manage.eventmanage.dto.UserDTO;
import org.event.manage.eventmanage.model.Event;
import org.event.manage.eventmanage.model.UserBookEvent;
import org.event.manage.eventmanage.service.AdminService;
import org.event.manage.eventmanage.util.HibernateUtilService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminServiceImpl implements AdminService {


    @Override
    public EventDTO addNewEvent(EventDTO eventDTO) {

        try(Session session = HibernateUtilService.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            Event event = Event.builder()
                    .name(eventDTO.getName())
                    .date(eventDTO.getDate())
                    .venue(eventDTO.getVenue())
                    .description(eventDTO.getDescription())
                    .capacity(eventDTO.getCapacity())
                    .brochureFilePath(eventDTO.getBrochureFilePath())
                    .latitude(eventDTO.getLatitude())
                    .longitude(eventDTO.getLongitude())
                    .availableTickets(eventDTO.getAvailableTickets())
                    .build();

            session.save(event);
            transaction.commit();
            eventDTO.setId(event.getId());
        } catch (Exception e) {
            throw new RuntimeException("Registration failed", e);
        }
        return eventDTO;
    }

    @Override
    public List<EventDTO> getAllEvents() {

        List<EventDTO> eventDTOList;

        try (Session session = HibernateUtilService.getSessionFactory().openSession()){

            Transaction transaction = session.beginTransaction();

            List<Event> eventList;

            Query<Event> eventQuery = session.createQuery("FROM Event", Event.class);

            eventList = eventQuery.list();

            eventDTOList = eventList.stream()
                    .map(event -> EventDTO.builder()
                            .id(event.getId())
                            .name(event.getName())
                            .date(event.getDate())
                            .venue(event.getVenue())
                            .description(event.getDescription())
                            .capacity(event.getCapacity())
                            .brochureFilePath(event.getBrochureFilePath())
                            .latitude(event.getLatitude())
                            .longitude(event.getLongitude())
                            .availableTickets(event.getAvailableTickets())
                            .build()
                    ).collect(Collectors.toList());

            transaction.commit();

        }catch (Exception e) {
            throw new RuntimeException("Registration failed", e);
        }

        return eventDTOList;
    }

    @Override
    public boolean deleteEventById(int getEventId) {
        try (Session session = HibernateUtilService.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "DELETE FROM Event WHERE id = :id";
            int deletedCount = session.createQuery(hql)
                    .setParameter("id", getEventId)
                    .executeUpdate();

            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            throw new RuntimeException("Deletion failed", e);
        }
    }

    @Override
    public List<ListUserEventDTO> getRegisterdUsersForEvent() {

        List<ListUserEventDTO> listUserEventDTOS = new ArrayList<>();
        try (Session session = HibernateUtilService.getSessionFactory().openSession()){

            Transaction transaction = session.beginTransaction();

            String getData = "SELECT DISTINCT e FROM Event e JOIN FETCH e.bookings b JOIN FETCH b.user";
            List<Event> eventList = session.createQuery(getData, Event.class).getResultList();

            for (Event event : eventList){
                List<UserDTO> userDTOList = event.getBookings().stream()
                        .map(b-> UserDTO.builder()
                                .name(b.getUser().getName())
                                .email(b.getUser().getEmail())
                                .build())
                        .collect(Collectors.toList());
                ListUserEventDTO dto = ListUserEventDTO.builder()
                        .eventId(event.getId())
                        .eventName(event.getName())
                        .date(event.getDate())
                        .venue(event.getVenue())
                        .capacity(event.getCapacity())
                        .userDTOList(userDTOList)
                        .totalRegisteredUsers(userDTOList.size())
                        .build();

                listUserEventDTOS.add(dto);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUserEventDTOS;
    }
}
