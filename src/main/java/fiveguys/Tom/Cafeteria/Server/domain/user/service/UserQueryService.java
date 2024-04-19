package fiveguys.Tom.Cafeteria.Server.domain.user.service;

import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;

public interface UserQueryService {
    public User getUserById(Long userId);
    public User getUserBySocialId(String socialId);

    boolean isExistBySocialId(String socialId);
}
