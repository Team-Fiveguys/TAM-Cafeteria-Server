package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import org.springframework.stereotype.Service;

public interface CafeteriaCommandService {
    public Cafeteria enroll(Cafeteria cafeteria);

}
