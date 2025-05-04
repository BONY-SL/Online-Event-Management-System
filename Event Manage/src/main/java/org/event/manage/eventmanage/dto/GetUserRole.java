package org.event.manage.eventmanage.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRole {

    private String role;
    private String email;

    @Override
    public String toString() {
        return "GetUserRole{" +
                "role='" + role + '\'' +
                '}';
    }
}
