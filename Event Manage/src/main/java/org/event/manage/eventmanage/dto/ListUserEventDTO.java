package org.event.manage.eventmanage.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListUserEventDTO {

    private int eventId;
    private String eventName;
    private LocalDate date;
    private String venue;
    private int capacity;
    private List<UserDTO> userDTOList;
    private int totalRegisteredUsers;
}
