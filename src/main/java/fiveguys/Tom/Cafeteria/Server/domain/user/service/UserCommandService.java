package fiveguys.Tom.Cafeteria.Server.domain.user.service;

import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;

public interface UserCommandService {
    public User create(User user);
}
