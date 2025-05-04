package org.event.manage.eventmanage.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartEventDTO {
    private String name;
    private LocalDate date;
}
