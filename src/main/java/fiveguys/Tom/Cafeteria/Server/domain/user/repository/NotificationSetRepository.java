package fiveguys.Tom.Cafeteria.Server.domain.user.repository;

import fiveguys.Tom.Cafeteria.Server.domain.user.entity.NotificationSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationSetRepository extends JpaRepository<NotificationSet, Long> {
    public List<NotificationSet> findAll();
}
