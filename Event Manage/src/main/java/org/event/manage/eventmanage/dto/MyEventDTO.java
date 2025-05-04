package org.event.manage.eventmanage.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyEventDTO {

    private String eventName;
    private LocalDate date;
    private String venue;
    private int ticketsBooked;

}
