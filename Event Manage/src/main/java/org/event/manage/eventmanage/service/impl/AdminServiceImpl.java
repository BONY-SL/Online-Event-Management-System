package org.event.manage.eventmanage.service.impl;

import org.event.manage.eventmanage.dto.EventDTO;
import org.event.manage.eventmanage.model.Event;
import org.event.manage.eventmanage.service.AdminService;
import org.event.manage.eventmanage.util.HibernateUtilService;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
