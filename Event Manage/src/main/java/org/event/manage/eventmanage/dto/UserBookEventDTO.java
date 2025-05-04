package org.event.manage.eventmanage.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBookEventDTO {

    private String email;
    private int eventId;
    private int count;
    private String action;
}