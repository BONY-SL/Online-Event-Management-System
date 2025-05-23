package org.event.manage.eventmanage.service;

import org.event.manage.eventmanage.dto.GetUserRole;
import org.event.manage.eventmanage.dto.LoginRequest;
import org.event.manage.eventmanage.dto.RegisterRequest;

public interface AuthService {

    boolean register(RegisterRequest request );

    GetUserRole login(LoginRequest loginRequest);
}
