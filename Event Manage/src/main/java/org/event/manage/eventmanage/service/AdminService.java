package org.event.manage.eventmanage.service;

import org.event.manage.eventmanage.dto.*;

import java.util.List;

public interface AdminService {

    EventDTO addNewEvent(EventDTO eventDTO);

    List<EventDTO> getAllEvents();

    boolean deleteEventById(int getEventId);

    List<ListUserEventDTO> getRegisterdUsersForEvent();

    List<ChartEventDTO> getAllEventsForChart();

    DashboardCountDTO getDashBoardCounts();

    List<EventReportDTO> getReports();
}
