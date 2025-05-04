package org.event.manage.eventmanage.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCountDTO {

    private long totalCount;
    private long totalRegisterdUsers;
    private long bookedTickets;
    private long totalUpcommingEvents;
}
