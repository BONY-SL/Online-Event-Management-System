package org.event.manage.eventmanage.service;

import org.event.manage.eventmanage.dto.MyEventDTO;
import org.event.manage.eventmanage.dto.UserBookEventDTO;

import java.util.List;

public interface UserService {
    boolean eventBook(UserBookEventDTO userBookEventDTO);
    List<MyEventDTO> getMyAllEvents(String email);
}
