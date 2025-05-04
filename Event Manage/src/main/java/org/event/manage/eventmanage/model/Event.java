package org.event.manage.eventmanage.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false ,name = "avalable_count")
    private int availableTickets;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBookEvent> bookings = new ArrayList<>();

}
