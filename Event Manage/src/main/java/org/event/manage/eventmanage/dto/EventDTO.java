package org.event.manage.eventmanage.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private int id;
    private String name;
    private LocalDate date;
    private String venue;
    private String description;
    private int capacity;
    private String brochureFilePath;
    private String action;
    private double latitude;
    private int availableTickets;
    private double longitude;


    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", venue='" + venue + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", brochureFilePath='" + brochureFilePath + '\'' +
                ", action='" + action + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
