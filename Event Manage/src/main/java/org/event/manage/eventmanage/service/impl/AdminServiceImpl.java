package org.event.manage.eventmanage.service.impl;

import org.event.manage.eventmanage.dto.EventDTO;
import org.event.manage.eventmanage.model.Event;
import org.event.manage.eventmanage.service.AdminService;
import org.event.manage.eventmanage.util.HibernateUtilService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

}
