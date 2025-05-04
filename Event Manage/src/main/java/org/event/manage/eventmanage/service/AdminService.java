package org.event.manage.eventmanage.service;

import org.event.manage.eventmanage.dto.EventDTO;
import org.event.manage.eventmanage.dto.ListUserEventDTO;

import java.util.List;

public interface AdminService {

    EventDTO addNewEvent(EventDTO eventDTO);

    List<EventDTO> getAllEvents();

    boolean deleteEventById(int getEventId);

    List<ListUserEventDTO> getRegisterdUsersForEvent();
}
