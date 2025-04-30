package org.event.manage.eventmanage.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(nullable = false)
    private String venue;

    @Column()
    private String description;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "brochure_path")
    private String brochureFilePath;


}
