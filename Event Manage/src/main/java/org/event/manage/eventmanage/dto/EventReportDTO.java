package org.event.manage.eventmanage.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventReportDTO {

    private String name;
    private LocalDate date;
    private String venue;
    private int capacity;
    private int registerdUsers;
    private int totalAttendance;
}
