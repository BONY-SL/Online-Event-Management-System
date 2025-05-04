package org.event.manage.eventmanage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_event")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false)
    private int ticketsCount;
}
